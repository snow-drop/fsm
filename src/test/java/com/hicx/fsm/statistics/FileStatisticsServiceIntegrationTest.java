package com.hicx.fsm.statistics;

import com.hicx.fsm.extraction.FileContentExtractionServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FileStatisticsServiceIntegrationTest {

    private FileStatisticsService fileStatisticsService;

    @BeforeAll
    void setup() {
        FileContentExtractionServiceImpl fileContentExtractionService = new FileContentExtractionServiceImpl();
        fileStatisticsService = new FileStatisticsServiceImpl(fileContentExtractionService);
    }

    @Test
    void getStatistics() throws IOException {
        String filePath = getTestFilePath();
        FileStatistics statistics = fileStatisticsService.getStatistics(filePath);
        assertEquals(8, statistics.getWordCount());
        assertEquals(2, statistics.getDotCount());
        assertEquals(1, statistics.getMostUsedWords().size());
        assertTrue(statistics.getMostUsedWords().contains("the"));
    }

    private String getTestFilePath() {
        String srcDirectory = System.getProperty("user.dir");
        String relativeTextFilePath = "src/test/resources/quote.txt";
        return Paths.get(srcDirectory, relativeTextFilePath).toString();
    }
}
