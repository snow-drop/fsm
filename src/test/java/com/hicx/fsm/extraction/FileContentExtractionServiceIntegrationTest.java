package com.hicx.fsm.extraction;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FileContentExtractionServiceIntegrationTest {

    private FileContentExtractionService fileContentExtractionService;

    @BeforeAll
    void setup() {
        fileContentExtractionService = new FileContentExtractionServiceImpl();
    }

    @Test
    void getText() throws IOException {
        String textFilePath = getTestFilePath();
        boolean fileExists = Files.exists(Paths.get(textFilePath));
        assertTrue(fileExists);
        String fileContent = fileContentExtractionService.getText(textFilePath);
        assertNotNull(fileContent);
        System.out.println(fileContent);
    }

    private String getTestFilePath() {
        String srcDirectory = System.getProperty("user.dir");
        String relativeTextFilePath = "src/test/resources/code-principles.txt";
        return String.format("%s/%s", srcDirectory, relativeTextFilePath);
    }
}
