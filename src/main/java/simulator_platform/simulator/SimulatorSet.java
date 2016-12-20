package simulator_platform.simulator;

import simulator_platform.ISimulatorSetRules;

import java.io.IOException;
import java.util.HashMap;

public class SimulatorSet implements ISimulatorSet{

    private ISimulatorSetRules setRules;
    private HashMap<Object, ISimulator> simulations;

    public SimulatorSet(ISimulatorSetRules setRules) throws Exception {
        this.setRules = setRules;
        this.simulations = buildSimulations();
    }

    @Override
    public void runAll() throws Exception {
        for(ISimulator sim : simulations.values()) sim.run();
    }

    @Override
    public HashMap analyseAllResults() throws IOException {
        HashMap allResults = new HashMap();
        for (HashMap.Entry<Object, ISimulator> entry : simulations.entrySet())
            allResults.put(entry.getKey(), entry.getValue().getAnalysedResults());
        return setRules.analyseAllResults(allResults);
    }

    @Override
    public HashMap getAllEndData() throws IOException {
        HashMap allResults = new HashMap();
        for (HashMap.Entry<Object, ISimulator> entry : simulations.entrySet())
            allResults.put(entry.getKey(), entry.getValue().getCurrentData());
        return allResults;
    }

    private HashMap<Object, ISimulator> buildSimulations() throws Exception {
        HashMap simulations = new HashMap<Object, ISimulator>();
        for (Object factor : setRules.getVariantFactorsForSet())
            simulations.put(factor, buildSimulation(factor));
        return simulations;
    }

    private ISimulator buildSimulation(Object factor) throws Exception {
        return new Simulator(setRules.buildSimulationRules(factor));
    }
}
