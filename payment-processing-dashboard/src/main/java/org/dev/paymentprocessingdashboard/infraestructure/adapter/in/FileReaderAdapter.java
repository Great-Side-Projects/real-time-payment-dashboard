package org.dev.paymentprocessingdashboard.infraestructure.adapter.in;

import org.dev.paymentprocessingdashboard.application.port.in.IFileReaderPort;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileReaderAdapter implements IFileReaderPort {

    private static final String LOG_FILE_PATH = "log-generator/logs/transactions.log";
    private final Path path = Paths.get(LOG_FILE_PATH);
    private long lastKnownPosition = 0;

    @Override
    public List<String> readLines(long lastKnownPosition) throws IOException {

        List<String> lines = new ArrayList<>();
        try (RandomAccessFile file = new RandomAccessFile(path.normalize().toAbsolutePath().toFile(), "r")) {
            file.seek(lastKnownPosition);
            String line;
            while ((line = file.readLine()) != null) {
                lines.add(line);
            }
            this.lastKnownPosition = file.getFilePointer();
        }

        return lines;
    }
    @Override
    public long getLastKnownPosition() {
        return lastKnownPosition;
    }
}
