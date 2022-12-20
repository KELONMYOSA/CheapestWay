package ru.cheapestway.collector;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ProjectConfig {
    private static final Properties props = new Properties();

    public static Properties props() throws IOException {
        if (props.isEmpty()) {
            loadProps();
        }
        return props;
    }

    private static void loadProps() throws IOException {
        String rootPath = new File("").getAbsolutePath();
        String configPath = rootPath + "\\config.properties";
        props.load(new FileInputStream(configPath));
    }
}
