package com.hicx.fsm.monitoring;

import com.hicx.fsm.statistics.FileStatistics;
import com.hicx.fsm.statistics.FileStatisticsService;

import java.io.File;
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
        processExistingFiles(directory);
        WatchService watchService = FileSystems.getDefault().newWatchService();
        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
        WatchKey watchKey;
        while ((watchKey = watchService.take()) != null) {
            for (WatchEvent<?> event : watchKey.pollEvents()) {
                String filePath = Paths.get(directory, event.context().toString()).toString();
                processNewFile(filePath);
            }
            watchKey.reset();
        }
    }

    private void processExistingFiles(String directory) throws IOException {
        File[] existingFiles = new File(directory).listFiles();
        for (File file : existingFiles) {
            if (file.isFile()) {
                processNewFile(file.getPath());
            }
        }
    }

    private void processNewFile(String filePath) throws IOException {
        boolean isFile = !Files.isDirectory(Paths.get(filePath));
        if (isFile) {
            printFileStatistics(filePath);
            moveProcessedFile(filePath);
        }
    }

    private void moveProcessedFile(String filePath) throws IOException {
        File processedFile = new File(filePath);
        File rootFolder = processedFile.getParentFile();
        Path processedFolderPath = Paths.get(rootFolder.getPath(), "processed");
        boolean processedFolderNotExisting = !Files.exists(processedFolderPath);
        if (processedFolderNotExisting) {
            processedFolderPath = Files.createDirectories(processedFolderPath);
        }
        Path newProcessedFilePath = Paths.get(processedFolderPath.toString(), processedFile.getName());
        Files.move(processedFile.toPath(), newProcessedFilePath, StandardCopyOption.REPLACE_EXISTING);
    }

    private void printFileStatistics(String filePath) throws IOException {
        FileStatistics statistics = fileStatisticsService.getStatistics(filePath);
        String fileName = Paths.get(filePath).toFile().getName();
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
        } else {
            boolean isNotADirectory = !Files.isDirectory(path);
            if (isNotADirectory) {
                throw new IllegalArgumentException("Argument is not a directory.");
            }
        }
        return path;
    }
}
