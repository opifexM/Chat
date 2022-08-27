package com.netology.javachat.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.File;
import java.io.IOException;

public class YamlSettings {
    public static ConfigSettings readConfigFile(String fileName) {
        ObjectMapper mapper = new YAMLMapper();
        try {
            return mapper.readValue(new File(fileName), ConfigSettings.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
