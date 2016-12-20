package simulator_platform.simulator;

import simulator_platform.ISimulatorRules;

import java.io.IOException;
import java.util.List;


public class Simulator implements ISimulator {

    private final ISimulatorRules rules;
    private final List data;

    public Simulator(ISimulatorRules rules) throws Exception {
        this.rules = rules;
        this.data = rules.getStartData();
    }

    @Override
    public void run() throws Exception {
        while(rules.continueTicking())
            rules.tick( data );
    }

    @Override
    public List getAnalysedResults() throws IOException {
        return rules.analyseResults(data);
    }

    @Override
    public List getCurrentData() throws IOException {
        return data;
    }
}