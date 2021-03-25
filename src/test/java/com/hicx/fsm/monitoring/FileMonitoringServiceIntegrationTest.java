package com.hicx.fsm.monitoring;

import com.hicx.fsm.extraction.FileContentExtractionService;
import com.hicx.fsm.extraction.FileContentExtractionServiceImpl;
import com.hicx.fsm.statistics.FileStatisticsService;
import com.hicx.fsm.statistics.FileStatisticsServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FileMonitoringServiceIntegrationTest {

    private FileMonitoringService fileMonitoringService;

    @BeforeAll
    void setup() {
        FileContentExtractionService fileContentExtractionService = new FileContentExtractionServiceImpl();
        FileStatisticsService fileStatisticsService = new FileStatisticsServiceImpl(fileContentExtractionService);
        fileMonitoringService = new FileMonitoringServiceImpl(fileStatisticsService);
    }

    @Test
    void startMonitoring() throws IOException, InterruptedException {
        String directory = getTestDirectory();
        fileMonitoringService.monitor(directory);
    }

    private String getTestDirectory() {
        String srcDirectory = System.getProperty("user.dir");
        String testDirectory = "sandbox/";
        return String.format("%s/%s", srcDirectory, testDirectory);
    }
}
