package com.zalizniak.activemq.jms.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zalizniak.activemq.jms.searialization.CustomDateTimeDeserializer;
import com.zalizniak.activemq.jms.searialization.CustomDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnqueuedMessageDto {

    private Long eventId;

    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    private DateTime eventDate;
}
