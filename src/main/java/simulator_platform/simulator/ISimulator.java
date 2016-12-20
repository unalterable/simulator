package simulator_platform.simulator;

import java.io.IOException;
import java.util.List;

public interface ISimulator {

    void run() throws Exception;

    List getAnalysedResults() throws IOException;

    List getCurrentData() throws IOException;

}
