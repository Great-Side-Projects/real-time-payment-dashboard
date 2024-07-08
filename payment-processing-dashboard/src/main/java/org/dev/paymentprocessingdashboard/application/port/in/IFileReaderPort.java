package org.dev.paymentprocessingdashboard.application.port.in;

import java.io.IOException;
import java.util.List;

public interface IFileReaderPort {
    List<String> readLines(long lastKnownPosition) throws IOException;
    long getLastKnownPosition();
}
