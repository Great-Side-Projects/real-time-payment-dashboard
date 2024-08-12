package org.dev.paymentprocessingdashboard.infraestructure.adapter.in;

import org.dev.paymentprocessingdashboard.application.port.in.IFileReaderPort;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileReaderAdapter implements IFileReaderPort {

    private static final String LOG_FILE_PATH = "log-generator/logs/transactions.log";
    private static final String POSITION_FILE_PATH = "payment-processing-dashboard/last_known_position.txt";
    private static Path path = Paths.get(LOG_FILE_PATH);
    private static Path positionFilePath = Paths.get(POSITION_FILE_PATH);
    private List<String> lines = new ArrayList<>();

    // This static block is used to determine the positionFilePath based on the user.dir system property
    // validation for unit tests
    static {
        String userDir = System.getProperty("user.dir");
        if (userDir.endsWith("payment-processing-dashboard")) {
            positionFilePath = Paths.get(userDir, "last_known_position.txt");
            path = Paths.get(userDir.replace("payment-processing-dashboard",""), LOG_FILE_PATH);
        }
    }
    private long lastKnownPosition = 0;

    public FileReaderAdapter() throws IOException {
        this.lastKnownPosition = readLastKnownPosition();
    }

    @Override
    public List<String> readLines() throws IOException {

        try (BufferedReader file = new BufferedReader(new FileReader(path.normalize().toAbsolutePath().toFile()))) {
            file.skip(this.lastKnownPosition);
            String line;
            lines = file.lines().toList();
            if (!lines.isEmpty()) {
                this.lastKnownPosition += lines.stream().mapToLong(l -> {
                    return l.length() + System.lineSeparator().length();
                }).sum();
            }
        }
        return lines;
    }

    @Override
    public long getLastKnownPosition() {
        return lastKnownPosition;
    }

    @Override
    public void saveLastKnownPosition() throws IOException {
        Files.writeString(positionFilePath, Long.toString(this.lastKnownPosition));
    }

    private long readLastKnownPosition() throws IOException {
        if (!Files.exists(positionFilePath)) {
            this.saveLastKnownPosition();
        }
        String positionString = Files.readString(positionFilePath);
        return Long.parseLong(positionString.trim());
    }
}
