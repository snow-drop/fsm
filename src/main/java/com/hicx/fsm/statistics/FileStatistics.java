package com.hicx.fsm.statistics;

import java.util.List;

public class FileStatistics {

    private int wordCount;
    private int dotCount;
    private List<String> mostUsedWords;

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
