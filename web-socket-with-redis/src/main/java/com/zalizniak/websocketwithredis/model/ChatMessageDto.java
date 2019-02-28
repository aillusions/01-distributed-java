package com.zalizniak.websocketwithredis.model;

import com.zalizniak.websocketwithredis.Dto.ObjectPointXY;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {

	private ObjectPointXY objectPointXY;
	private String hostname;
	private String song;
}
