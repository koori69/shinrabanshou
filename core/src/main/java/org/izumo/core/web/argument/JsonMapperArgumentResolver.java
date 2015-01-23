package org.izumo.core.web.argument;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.izumo.core.web.annotation.RequestJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapperArgumentResolver implements HandlerMethodArgumentResolver {
    private static final Logger log = LoggerFactory.getLogger(JsonMapperArgumentResolver.class);
    private ObjectMapper objectMapper;
    private static final String PATH_DELIMITER = "/";

    public JsonMapperArgumentResolver() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestJson.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        try {
            RequestJson jsonAnn = parameter.getParameterAnnotation(RequestJson.class);
            String path = jsonAnn.path();
            String allParam = getRequestParam(webRequest);
            if (allParam == null) {
                return null;
            }
            allParam = URLDecoder.decode(allParam, "utf-8");
            JsonNode node = objectMapper.readTree(allParam);
            if ((path == null) || ("".equals(path))) {
                path = parameter.getParameterName();
                if (node.has(path)) {
                    return objectMapper.readValue(node.path(path).traverse(), getReferenceType(parameter));
                }
                try {
                    return objectMapper.readValue(allParam, getReferenceType(parameter));
                } catch (Throwable e) {
                    e.printStackTrace();
                    return null;
                }
            }
            String[] paths = StringUtils.split(path, "/");
            for (String p : paths) {
                node = node.path(p);
            }
            if (node == null) {
                return null;
            }
            return objectMapper.readValue(node.traverse(), getReferenceType(parameter));
        } catch (Exception e) {
            log.error("can't generate param [" + parameter.getParameterName() + "] for url "
                    + webRequest.getNativeRequest(HttpServletRequest.class).getServletPath() + ", and source input is "
                    + getRequestParam(webRequest));

            throw e;
        }
    }

    private JavaType getReferenceType(MethodParameter parameter) {
        Type type = parameter.getGenericParameterType();
        if ((type instanceof ParameterizedType)) {
            Type[] genericTypes = ((ParameterizedType) type).getActualTypeArguments();
            Class<?> parameterType = parameter.getParameterType();
            if (Collection.class.isAssignableFrom(parameterType)) {
                if ((genericTypes.length >= 1) && ((genericTypes[0] instanceof Class))) {
                    return objectMapper.getTypeFactory().constructCollectionType(
                            (Class<? extends Collection>) parameter.getParameterType(), (Class) genericTypes[0]);
                }
            } else if (Map.class.isAssignableFrom(parameter.getParameterType())) {
                if ((genericTypes.length >= 2) && ((genericTypes[0] instanceof Class))
                        && ((genericTypes[1] instanceof Class))) {
                    return objectMapper.getTypeFactory().constructMapType(
                            (Class<? extends Map>) parameter.getParameterType(), (Class) genericTypes[0],
                            (Class) genericTypes[1]);
                }
                if ((genericTypes.length == 1) && ((genericTypes[0] instanceof Class))) {
                    return objectMapper.getTypeFactory().constructMapType(
                            (Class<? extends Map>) parameter.getParameterType(), (Class) genericTypes[0], Object.class);
                }
                return objectMapper.getTypeFactory().constructMapType(
                        (Class<? extends Map>) parameter.getParameterType(), Object.class, Object.class);
            }
            throw new UnsupportedOperationException("Unsuppored Reference " + parameter.getParameterName()
                    + " To JavaType " + parameter.getParameterType().getName());
        }
        return objectMapper.getTypeFactory().constructType(parameter.getParameterType());
    }

    private String getRequestParam(NativeWebRequest webRequest) throws IOException {
        HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        String method = httpServletRequest.getMethod();
        if ((method.equals("GET")) || (method.equals("DELETE"))) {
            return httpServletRequest.getQueryString();
        }
        StringBuilder buffer = new StringBuilder();

        BufferedReader reader = httpServletRequest.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        return buffer.toString();
    }
}
