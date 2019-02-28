package com.zalizniak.websocketwithredis.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.zalizniak.zws", ignoreUnknownFields = false)
@Getter
public class ApplicationProperties {

	private final Topic topic = new Topic();

	@Getter
	@Setter
	public static class Topic {
		private String message;
	}
}
