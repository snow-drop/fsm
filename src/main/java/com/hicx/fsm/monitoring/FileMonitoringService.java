package com.hicx.fsm.monitoring;

import java.io.IOException;

public interface FileMonitoringService {

    void monitor(String directory) throws IOException, InterruptedException;
}
