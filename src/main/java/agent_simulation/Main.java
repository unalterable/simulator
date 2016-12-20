package agent_simulation;

import simulator_platform.ISimulatorSetRules;
import simulator_platform.records.CSVImporter;
import simulator_platform.records.IImporter;
import simulator_platform.simulator.ISimulatorSet;
import simulator_platform.simulator.SimulatorSet;

public class Main {

    public static void main(String[] args) throws Exception {
        IImporter importer = new CSVImporter("data/agent_data.csv", new AgentMap());
        ISimulatorSetRules experimentRules = new AgentSimulationSetRules(importer);
        ISimulatorSet experiment = new SimulatorSet(experimentRules);
        experiment.runAll();
        experiment.analyseAllResults();
    }
}

