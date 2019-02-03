package com.zalizniak.activemq.jms;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Slf4j
@Configuration
@EnableJms
public class ActiveMqConfig {

    @Value("${com.zalizniak.dj-amq.activemq.broker-url}")
    private String brokerUrl;

    @Value("${com.zalizniak.dj-amq.activemq.user-name}")
    private String userName;

    @Value("${com.zalizniak.dj-amq.activemq.password}")
    private String password;

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(brokerUrl);
        activeMQConnectionFactory.setUserName(userName);
        activeMQConnectionFactory.setPassword(password);

        log.info("activeMQConnectionFactory configured with brokerUrl: " + brokerUrl + " and userName: " + userName);

        return activeMQConnectionFactory;
    }

    @Bean
    ObjectMapper jacksonJmsObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        // default settings for MappingJackson2MessageConverter
        //objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
        //objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //// add settings I want
        //objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        //objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //objectMapper.registerSubtypes(New3Dto.class, New2Dto.class, New1Dto.class);

        //
        //JavaTimeModule timeModule = new JavaTimeModule();
        // timeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE));
        // timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        // timeModule.addSerializer(DateTime.class, new CustomDateTimeSerializer());
        // timeModule.addDeserializer(DateTime.class, new CustomDateTimeDeserializer());
        // objectMapper.registerModule(timeModule);

        return objectMapper;
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");

        //
        converter.setObjectMapper(jacksonJmsObjectMapper());

        return converter;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(activeMQConnectionFactory());
        factory.setMessageConverter(jacksonJmsMessageConverter());
        return factory;
    }

    @Bean
    public CachingConnectionFactory cachingConnectionFactory() {
        return new CachingConnectionFactory(activeMQConnectionFactory());
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate rv = new JmsTemplate(cachingConnectionFactory());
        rv.setDeliveryPersistent(false);
        rv.setExplicitQosEnabled(true);
        rv.setMessageConverter(jacksonJmsMessageConverter());
        return rv;
    }

}
