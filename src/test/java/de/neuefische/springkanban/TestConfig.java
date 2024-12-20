package de.neuefische.springkanban;

import de.neuefische.springkanban.spelling.SpellingChecker;
import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.utils.Options;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestConfiguration
public class TestConfig {

    @Bean(name = "testOllamaAPI")
    public OllamaAPI testOllamaAPI() throws OllamaBaseException, IOException, InterruptedException {
        OllamaAPI mockOllamaAPI = mock(OllamaAPI.class);
        when(
                mockOllamaAPI.generate(
                        anyString(),
                        anyString(),
                        anyBoolean(),
                        any(Options.class))
        ).thenReturn(
                new OllamaResult(
                        "mocked response",
                        0,
                        200));
        return mockOllamaAPI;
    }

    @Bean
    public SpellingChecker spellingChecker(@Qualifier("testOllamaAPI") OllamaAPI ollamaAPI) {
        return new SpellingChecker(ollamaAPI);
    }
}
