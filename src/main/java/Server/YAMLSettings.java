package Server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.File;
import java.io.IOException;

public class YAMLSettings {
    public static ServerSettings readConfigFile(String fileName) {
        ObjectMapper mapper = new YAMLMapper();
        try {
            return mapper.readValue(new File(fileName), ServerSettings.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
