package simulator_platform.simulator;

import java.io.IOException;
import java.util.HashMap;

public interface ISimulatorSet {

    void runAll() throws Exception;

    HashMap analyseAllResults() throws IOException;

    HashMap getAllEndData() throws IOException;
}
