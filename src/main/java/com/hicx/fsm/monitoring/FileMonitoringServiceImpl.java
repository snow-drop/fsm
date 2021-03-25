package com.hicx.fsm.monitoring;

import com.hicx.fsm.statistics.FileStatistics;
import com.hicx.fsm.statistics.FileStatisticsService;

import java.io.IOException;
import java.nio.file.*;

public class FileMonitoringServiceImpl implements FileMonitoringService {

    private final FileStatisticsService fileStatisticsService;

    public FileMonitoringServiceImpl(FileStatisticsService fileStatisticsService) {
        this.fileStatisticsService = fileStatisticsService;
    }

    @Override
    public void monitor(String directory) throws IOException, InterruptedException {
        System.out.println("Starting file monitoring service at: " + directory + "\n");
        Path path = getPath(directory);
        WatchService watchService = FileSystems.getDefault().newWatchService();
        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
        WatchKey watchKey;
        while ((watchKey = watchService.take()) != null) {
            for (WatchEvent<?> event : watchKey.pollEvents()) {
                processNewFile(event.context().toString(), directory);
            }
            watchKey.reset();
        }
    }

    private void processNewFile(String fileName, String rootDirectory) throws IOException {
        String filePath = Paths.get(rootDirectory, fileName).toString();
        printFileStatistics(fileName, filePath);
    }

    private void printFileStatistics(String fileName, String filePath) throws IOException {
        FileStatistics statistics = fileStatisticsService.getStatistics(filePath);
        System.out.println("File name: " + fileName);
        System.out.println("Number of words: " + statistics.getWordCount());
        System.out.println("Number of dots: " + statistics.getDotCount());
        System.out.println("Most used word(s): " + String.join(", ", statistics.getMostUsedWords()));
        System.out.println();
    }

    private Path getPath(String directory) throws IOException {
        Path path = Paths.get(directory);
        boolean directoryNotExisting = !Files.exists(path);
        if (directoryNotExisting) {
            Files.createDirectories(path);
        }
        return path;
    }
}
