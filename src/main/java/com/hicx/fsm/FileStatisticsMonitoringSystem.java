package com.hicx.fsm;

import com.hicx.fsm.extraction.FileContentExtractionService;
import com.hicx.fsm.extraction.FileContentExtractionServiceImpl;
import com.hicx.fsm.monitoring.FileMonitoringService;
import com.hicx.fsm.monitoring.FileMonitoringServiceImpl;
import com.hicx.fsm.statistics.FileStatisticsService;
import com.hicx.fsm.statistics.FileStatisticsServiceImpl;

import java.io.IOException;

public class FileStatisticsMonitoringSystem {

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length < 1) {
            throw new IllegalArgumentException("Must provide a directory path to monitor.");
        }
        FileContentExtractionService fileContentExtractionService = new FileContentExtractionServiceImpl();
        FileStatisticsService fileStatisticsService = new FileStatisticsServiceImpl(fileContentExtractionService);
        FileMonitoringService fileMonitoringService = new FileMonitoringServiceImpl(fileStatisticsService);
        fileMonitoringService.monitor(args[0]);
    }
}
