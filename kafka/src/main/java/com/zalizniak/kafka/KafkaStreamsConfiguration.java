package com.zalizniak.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.HashMap;
import java.util.Map;

/**
 * https://github.com/mknutty/kafka-streams-spring-boot-json-example
 * https://jobs.zalando.com/tech/blog/running-kafka-streams-applications-aws/?
 */
@Slf4j
@Configuration
@EnableKafka
@EnableKafkaStreams
public class KafkaStreamsConfiguration {

    //@Autowired
    //private KafkaProperties kafkaProperties;

    //@Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    //public StreamsConfig kStreamsConfigs() {
    //    Map<String, Object> props = new HashMap<>();
    //    props.put(StreamsConfig.APPLICATION_ID_CONFIG, "test-streams");
    //    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
    //    props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
    //    props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, new JsonSerde<>(TestData.class).getClass());
    //    props.put(JsonDeserializer.DEFAULT_KEY_TYPE, String.class);
    //    props.put(JsonDeserializer.DEFAULT_VALUE_TYPE, TestData.class);
    //    return new StreamsConfig(props);
    //}

    @Bean
    public KStream<String, TestData> kStreamJson(StreamsBuilder builder) {
        KStream<String, TestData> stream = builder.stream("streams-json-input", Consumed.with(Serdes.String(), new JsonSerde<>(TestData.class)));

        KTable<String, TestData> combinedDocuments = stream
                .map(new TestKeyValueMapper())
                //.foreach(e -> log.info("Processed message: " + e))
                .groupByKey()
                .reduce(new TestReducer(), Materialized.<String, TestData, KeyValueStore<Bytes, byte[]>>as("streams-json-store"));

        combinedDocuments.toStream().to("streams-json-output", Produced.with(Serdes.String(), new JsonSerde<>(TestData.class)));

        return stream;
    }
}