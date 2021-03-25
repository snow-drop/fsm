package com.hicx.fsm.statistics;

import java.util.List;

public class FileStatistics {

    private String fileName;
    private int wordCount;
    private int dotCount;
    private List<String> mostUsedWords;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public int getDotCount() {
        return dotCount;
    }

    public void setDotCount(int dotCount) {
        this.dotCount = dotCount;
    }

    public List<String> getMostUsedWords() {
        return mostUsedWords;
    }

    public void setMostUsedWords(List<String> mostUsedWords) {
        this.mostUsedWords = mostUsedWords;
    }
}
