package com.zalizniak.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.concurrent.CountDownLatch;

@Slf4j
@Configuration
public class RedisConfig {

    @Value("${com.zalizniak.dj-redis.redis.host}")
    private String redisHost;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        LettuceConnectionFactory lettuceConFactory = new LettuceConnectionFactory();
        lettuceConFactory.setHostName(redisHost);
        lettuceConFactory.setPort(6379);
        return lettuceConFactory;
    }

    @Bean
    RedisTemplate<String, Object> redisTemplate() {
        final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(redisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericToStringSerializer<Object>(Object.class));
        template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
        return template;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(redisConnectionFactory());
        stringRedisTemplate.setEnableTransactionSupport(true);
        return stringRedisTemplate;
    }

    @Bean
    public RedisMessageListener1 redisMessageListener1(){
        return new RedisMessageListener1();
    }

    @Bean
    public RedisMessageListener2 redisMessageListener2(){
        return new RedisMessageListener2();
    }

    @Bean
    public MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(redisMessageListener1());
    }

    @Bean
    public MessageListenerAdapter messageListener2() {
        return new MessageListenerAdapter(redisMessageListener2());
    }

    @Bean
    public RedisMessageListenerContainer redisContainer() {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();

        container.setConnectionFactory(redisConnectionFactory());
        container.addMessageListener(messageListener(), topic());
        container.addMessageListener(messageListener2(), topic());

        return container;
    }

    @Bean
    @ConditionalOnProperty(value = "com.zalizniak.dj-redis.pubsub.publisher.enabled", havingValue = "true")
    public RedisPublisher redisPublisher() {
        return new RedisPublisher(redisTemplate(), topic());
    }

    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic("pubsub:queue");
    }

    @Bean
    public CountDownLatch latch1() {
        return new CountDownLatch(1);
    }

    @Bean
    public CountDownLatch latch2() {
        return new CountDownLatch(1);
    }
}
