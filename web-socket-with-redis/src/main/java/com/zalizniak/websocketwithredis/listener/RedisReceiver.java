package com.zalizniak.websocketwithredis.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zalizniak.websocketwithredis.model.ChatMessageDto;
import com.zalizniak.websocketwithredis.service.WebSocketMessageService;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class RedisReceiver {

	private final WebSocketMessageService webSocketMessageService;

	public RedisReceiver(WebSocketMessageService webSocketMessageService) {
		this.webSocketMessageService = webSocketMessageService;
	}

	// Invoked when message is publish to "chat" channel
	public void receiveChatMessage(String message) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		ChatMessageDto chatMessageDto = objectMapper.readValue(message, ChatMessageDto.class);
		log.info("Notification Message Received: " + chatMessageDto);
		webSocketMessageService.sendChatMessage(chatMessageDto);
	}
}
