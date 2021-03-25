package com.hicx.fsm.extraction;

import java.io.IOException;

public interface FileContentExtractionService {

    String getText(String textFilePath) throws IOException;
}
