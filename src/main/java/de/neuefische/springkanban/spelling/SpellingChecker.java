package de.neuefische.springkanban.spelling;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.utils.OptionsBuilder;
import io.github.ollama4j.utils.PromptBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SpellingChecker {
    private final OllamaAPI ollamaAPI;

    @Autowired
    public SpellingChecker(@Qualifier("productionOllamaAPI") OllamaAPI ollamaAPI) {
        this.ollamaAPI = ollamaAPI;
    }

    public String checkSpelling(String text) throws SpellingCheckerException {
        PromptBuilder promptBuilder =
                new PromptBuilder()
                        .addLine("Please check the following phrase for spelling. If correct, return the phrase as it is. . ")
                        .addLine("If incorrect, return the corrected version, no additional text.")
                        .addLine("If not English, leave it as is.")
                        .addSeparator()
                        .addLine("The phrase is: " + text);
        try {
            OllamaResult result =
                    ollamaAPI.generate("llama3.2", promptBuilder.build(), false, new OptionsBuilder().build());
            return result.getResponse();
        } catch (Exception e) {
            throw new SpellingCheckerException("Error while checking spelling: " + e.getMessage());
        }
    }
}
