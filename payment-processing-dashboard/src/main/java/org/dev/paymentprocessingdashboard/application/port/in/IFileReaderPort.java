package org.dev.paymentprocessingdashboard.application.port.in;

import java.io.IOException;
import java.util.List;

public interface IFileReaderPort {
    List<String> readLines() throws IOException;
    long getLastKnownPosition();
    void saveLastKnownPosition() throws IOException;
}
