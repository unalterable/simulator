package simulator_platform.logger;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class DefaultLogger implements ILogger {

    private static final String NEW_LINE = "\n";
    private static final String SEPARATOR = ",";
    private String logDir;
    private String logName;

    public DefaultLogger(String directory, String fileName) throws IOException {
        this.logDir = directory;
        this.logName = fileName;
        create_log_file_if_none_exists();
    }

    @Override
    public void log(String key, String log) throws IOException {
        append_log_file(key + SEPARATOR + log + NEW_LINE);
    }

    @Override
    public String getDir() {
        return logDir;
    }

    @Override
    public String getFullPath() {
        return logDir + logName;
    }

    @Override
    public List<String> retrieveLogs() throws IOException {
        return Files.readAllLines(Paths.get(getFullPath()));
    }

    private void append_log_file(String logText) throws IOException {
        Files.write(Paths.get(getFullPath()), logText.getBytes(), StandardOpenOption.APPEND);
    }

    private void create_log_file_if_none_exists() throws IOException {
        File file = new File(getFullPath());
        if (!file.exists()) Files.write(Paths.get(getFullPath()), "".getBytes());
    }


}
