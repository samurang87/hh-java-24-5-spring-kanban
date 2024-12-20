package de.neuefische.springkanban.spelling;

import io.github.ollama4j.OllamaAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OllamaConfig {
    @Bean(name = "productionOllamaAPI")
    public OllamaAPI productionOllamaAPI() {
        return new OllamaAPI("http://localhost:11434/");
    }
}