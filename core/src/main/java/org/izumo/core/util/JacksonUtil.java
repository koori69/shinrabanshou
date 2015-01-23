/**
 * JacksonUtil.java
 *
 * Copyright 2011 Baidu, Inc.
 *
 * Baidu licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.izumo.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @author Baidu API Team (apihelp@baidu.com)
 * 
 * @version 1.1.8, $Date: 2011-3-18$
 * 
 */
public abstract class JacksonUtil {
    private final static Logger LOGGER = LoggerFactory.getLogger(JacksonUtil.class);

    // can reuse, share globally
    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.disable(SerializationFeature.WRITE_NULL_MAP_VALUES);
    }

    public static final String obj2Str(Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonParseException e) {
            LOGGER.error("对象转json出错", e);
        } catch (JsonMappingException e) {
            LOGGER.error("对象转json出错", e);
        } catch (IOException e) {
            LOGGER.error("对象转json出错", e);
        }
        return null;
    }

    public static final void writeObj(OutputStream out, Object value) {
        try {
            mapper.writeValue(out, value);
        } catch (JsonParseException e) {
            LOGGER.error("writeObj出错", e);
        } catch (JsonMappingException e) {
            LOGGER.error("writeObj出错", e);
        } catch (IOException e) {
            LOGGER.error("writeObj出错", e);
        }
    }

    public static final <T> T str2Obj(String s, Class<T> valueType) {
        try {
            return mapper.readValue(s, valueType);
        } catch (JsonParseException e) {
            LOGGER.error("json转对象出错，字符串为:{}", s, e);
        } catch (JsonMappingException e) {
            LOGGER.error("json转对象出错，字符串为:{}", s, e);
        } catch (IOException e) {
            LOGGER.error("json转对象出错，字符串为:{}", s, e);
        }
        return null;
    }

    public static final <T> T readObj(InputStream in, Class<T> valueType) {
        try {
            return mapper.readValue(in, valueType);
        } catch (JsonParseException e) {
            LOGGER.error("json转对象出错", e);
        } catch (JsonMappingException e) {
            LOGGER.error("json转对象出错", e);
        } catch (IOException e) {
            LOGGER.error("json转对象出错", e);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static final <T> T readObj(InputStream in, JavaType valueType) {
        try {
            return (T) mapper.readValue(in, valueType);
        } catch (JsonParseException e) {
            LOGGER.error("json转对象出错", e);
        } catch (JsonMappingException e) {
            LOGGER.error("json转对象出错", e);
        } catch (IOException e) {
            LOGGER.error("json转对象出错", e);
        }
        return null;
    }

    public static final <T> T str2Obj(String s, Class<?> collectionClass, Class<?>... elementClasses) {
        JavaType javaType = mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
        try {
            return mapper.readValue(s, javaType);
        } catch (JsonParseException e) {
            LOGGER.error("json转对象出错，字符串为:{}", s, e);
        } catch (JsonMappingException e) {
            LOGGER.error("json转对象出错，字符串为:{}", s, e);
        } catch (IOException e) {
            LOGGER.error("json转对象出错，字符串为:{}", s, e);
        }
        return null;

    }

    /**
     * 处理类似ResultResponse<PageList<FOrderInfoResponse>>的值.
     * @param s
     * @param parametrized
     * @param parameterClasses1
     * @param parameterClasses2
     * @return
     */
    public static final <T> T str2SimpleObj(String s, Class<?> parametrized, Class parameterClasses1,
            Class parameterClasses2) {
        JavaType javaType1 = mapper.getTypeFactory().constructParametricType(parameterClasses1, parameterClasses2);
        JavaType javaType = mapper.getTypeFactory().constructParametricType(parametrized, javaType1);
        try {
            return mapper.readValue(s, javaType);
        } catch (JsonParseException e) {
            LOGGER.error("json转对象出错，字符串为:{}", s, e);
        } catch (JsonMappingException e) {
            LOGGER.error("json转对象出错，字符串为:{}", s, e);
        } catch (IOException e) {
            LOGGER.error("json转对象出错，字符串为:{}", s, e);
        }
        return null;

    }

}
