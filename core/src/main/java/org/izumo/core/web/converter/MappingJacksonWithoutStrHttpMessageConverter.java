package org.izumo.core.web.converter;

import java.io.IOException;
import java.io.OutputStreamWriter;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.FileCopyUtils;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @author Kirs
 * 
 */
public class MappingJacksonWithoutStrHttpMessageConverter extends MappingJackson2HttpMessageConverter {

    @Override
    protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws IOException,
            HttpMessageNotWritableException {

        JsonEncoding encoding = getJsonEncoding(outputMessage.getHeaders().getContentType());
        JsonGenerator jsonGenerator = getObjectMapper().getFactory().createGenerator(outputMessage.getBody(), encoding);

        // A workaround for JsonGenerators not applying serialization features
        // https://github.com/FasterXML/jackson-databind/issues/12
        if (getObjectMapper().getSerializationConfig().isEnabled(SerializationFeature.INDENT_OUTPUT)) {
            jsonGenerator.useDefaultPrettyPrinter();
        }

        try {
            if (object instanceof String) {
                FileCopyUtils.copy(object.toString(), new OutputStreamWriter(outputMessage.getBody(), "UTF-8"));
            } else {
                getObjectMapper().writeValue(jsonGenerator, object);
            }
        } catch (JsonProcessingException ex) {
            throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);
        }
    }

    public void setIgnoreUnknownProperties(boolean ignoreUnknownProperties) {
        if (ignoreUnknownProperties == true) {
            getObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
    }
}
