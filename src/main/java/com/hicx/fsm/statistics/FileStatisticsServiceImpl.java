package com.hicx.fsm.statistics;

import com.hicx.fsm.extraction.FileContentExtractionService;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileStatisticsServiceImpl implements FileStatisticsService {

    private static final Pattern DOT_PATTERN = Pattern.compile("\\.");
    private final FileContentExtractionService fileContentExtractionService;

    public FileStatisticsServiceImpl(FileContentExtractionService fileContentExtractionService) {
        this.fileContentExtractionService = fileContentExtractionService;
    }

    @Override
    public FileStatistics getStatistics(String filePath) throws IOException {
        String content = fileContentExtractionService.getText(filePath);
        String fileName = Paths.get(filePath).toFile().getName();
        FileStatistics fileStatistics = new FileStatistics();
        fileStatistics.setFileName(fileName);
        fileStatistics.setWordCount(getWordCount(content));
        fileStatistics.setDotCount(getDotCount(content));
        fileStatistics.setMostUsedWords(getMostUsedWords(content));
        return fileStatistics;
    }

    private List<String> getMostUsedWords(String content) {
        Map<String, Integer> wordFrequencyMap = getWordFrequencyMap(content);
        Optional<Integer> highestCount = wordFrequencyMap.values().stream().max(Integer::compareTo);
        if (highestCount.isPresent()) {
            return collectMostUsedWords(wordFrequencyMap, highestCount.get());
        }
        return Collections.emptyList();
    }

    private List<String> collectMostUsedWords(
            Map<String, Integer> wordFrequencyMap, int highestCount) {

        List<String> mostUsedWords = new ArrayList<>();
        for (String key : wordFrequencyMap.keySet()) {
            int count = wordFrequencyMap.get(key);
            if (count == highestCount) {
                mostUsedWords.add(key);
            }
        }
        return mostUsedWords;
    }

    private Map<String, Integer> getWordFrequencyMap(String content) {
        String[] words = content.split("\\s");
        Map<String, Integer> wordFrequencyMap = new HashMap<>();
        for (String word : words) {
            addWordToMap(wordFrequencyMap, word);
        }
        return wordFrequencyMap;
    }

    private void addWordToMap(Map<String, Integer> wordFrequencyMap, String word) {
        String sanitizedWord = word.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        boolean existingInMap = wordFrequencyMap.containsKey(sanitizedWord);
        int count = 0;
        if (existingInMap) {
            count = wordFrequencyMap.get(sanitizedWord);
        }
        wordFrequencyMap.put(sanitizedWord, count + 1);
    }

    private int getDotCount(String content) {
        Matcher matcher = DOT_PATTERN.matcher(content);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    private int getWordCount(String content) {
        String[] tokens = content.split("\\s");
        return tokens.length;
    }
}
