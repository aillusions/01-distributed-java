package com.zalizniak.activemq.jms.searialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.io.IOException;

/**
 * @author aillusions
 */
@Slf4j
public class CustomDateTimeDeserializer extends JsonDeserializer<DateTime> {

    @Override
    public DateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        if (StringUtils.isBlank(jp.getValueAsString())) {
            return null;
        }
        try {
            return new DateTime(Long.parseLong(jp.getValueAsString()));
        } catch (IllegalArgumentException e) {
            log.error("Unable to deserialize data: from " + jp.getValueAsString());
            return null;
        }
    }
}
