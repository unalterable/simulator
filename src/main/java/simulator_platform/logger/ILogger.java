package simulator_platform.logger;

import java.io.IOException;
import java.util.List;

public interface ILogger {

    void log(String key, String log) throws IOException;

    String getDir();

    String getFullPath();

    List retrieveLogs() throws IOException;

}