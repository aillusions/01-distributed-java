package com.zalizniak.kafka;

import org.apache.kafka.streams.kstream.Reducer;

public class TestReducer implements Reducer<TestData> {

    @Override
    public TestData apply(TestData value1, TestData value2) {
        value1.getWords().addAll(value2.getWords());
        return value1;
    }

}
