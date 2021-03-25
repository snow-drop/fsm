package com.hicx.fsm.statistics;

import java.io.IOException;

public interface FileStatisticsService {
    FileStatistics getStatistics(String filePath) throws IOException;
}
