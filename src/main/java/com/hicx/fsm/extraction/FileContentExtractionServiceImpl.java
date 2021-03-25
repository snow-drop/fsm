package com.hicx.fsm.extraction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.hicx.fsm.extraction.FileTypes.TEXT;

public class FileContentExtractionServiceImpl implements FileContentExtractionService {

    @Override
    public String getText(String textFilePath) throws IOException {
        Path filePath = Paths.get(textFilePath);
        String mimeType = Files.probeContentType(filePath);
        StringBuilder content = new StringBuilder();
        if (mimeType.equals(TEXT.getMimeType())) {
            extractTextFileContent(filePath, content);
        }
        return content.toString();
    }

    private void extractTextFileContent(Path filePath, StringBuilder content) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()));
        String fragment;
        while ((fragment = reader.readLine()) != null) {
            content.append(fragment);
            content.append("\n");
        }
    }
}
