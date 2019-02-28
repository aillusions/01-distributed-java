package com.zalizniak.websocketwithredis.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
@Setter
@Component
public class MyListener implements ApplicationListener<ContextRefreshedEvent> {

    private Map<Integer, String> map = null;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Ura!");
        try {
            populateMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populateMap() throws IOException {
        map = new HashMap<>();
        // TODO loop.. over songs d:\media\
        final File folder = new File("d:\\media\\");
        //listFilesForFolder(folder);

    }

    public void listFilesForFolder(final File folder) {
        File[] filesList = folder.listFiles();
        Integer filesListLenght = folder.listFiles().length;
        for (Integer i = 0; i < folder.listFiles().length; i++) {
            System.out.println("listFilesForFolder: " + filesList[i].getName());
            map.put(i, filesList[i].getName());
        }
    }


}
