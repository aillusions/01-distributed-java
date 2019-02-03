package com.zalizniak.kafka;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TestData {

    private String key;
    private List<String> words;

    public TestData() {
        words = new ArrayList<>();
    }
}
