package com.zalizniak.kafka;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KeyValueMapper;

public class TestKeyValueMapper implements KeyValueMapper<String, TestData, KeyValue<String, TestData>> {

    @Override
    public KeyValue<String, TestData> apply(String key, TestData value) {
        return new KeyValue<String, TestData>(value.getKey(), value);
    }
}
