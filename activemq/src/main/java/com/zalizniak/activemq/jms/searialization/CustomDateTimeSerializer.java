package com.zalizniak.activemq.jms.searialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import java.io.IOException;

/**
 * @author aillusions
 */
@Slf4j
public class CustomDateTimeSerializer extends JsonSerializer<DateTime> {

    @Override
    public void serialize(DateTime value, JsonGenerator gen, SerializerProvider arg2) throws IOException {

        if (value == null) {
            // do nothing
        } else {
            gen.writeObject(convertDateTimeToDto(value));
        }
    }

    public static Long convertDateTimeToDto(DateTime value) {
        return value.getMillis();
    }
}
