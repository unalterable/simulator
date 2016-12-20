package agent_simulation;

import simulator_platform.logger.DefaultLogger;
import simulator_platform.logger.ILogger;
import simulator_platform.ISimulatorRules;
import simulator_platform.ISimulatorSetRules;
import simulator_platform.records.IImporter;

import java.io.IOException;
import java.util.*;

public class AgentSimulationSetRules implements ISimulatorSetRules {

    static final double BRAND_FACTOR_MIN = 0.1;
    static final double BRAND_FACTOR_MAX = 2.9;
    static final double BRAND_FACTOR_STEP = 0.1;

    IImporter importer;

    public AgentSimulationSetRules(IImporter importer){
        this.importer = importer;
    }

    @Override
    public ISimulatorRules buildSimulationRules(Object brandFactor) {
        double bf = (Double) brandFactor;
        return new AgentSimulationRules(bf, importer);
    }

    @Override
    public List getVariantFactorsForSet() {
        List factors = new ArrayList();
        for(double d = BRAND_FACTOR_MIN; d < BRAND_FACTOR_MAX; d += BRAND_FACTOR_STEP) factors.add(d);
        return factors;
    }

    @Override
    public HashMap analyseAllResults(HashMap allResults) throws IOException {
        Map<Double, List> map = new TreeMap<Double, List>(allResults);
        ILogger logger = new DefaultLogger("data/", "results.csv");
        logger.log("Brand Factor", "Agents Ending Breed C, Agents Ending Breed NC, Breed C Lost, Breed C Gained, Breed C Regained");
        for(Map.Entry<Double,List> entry : map.entrySet()){
            List<Integer> values = entry.getValue();
            String logEntry = "";
            for(int value : values) logEntry = logEntry + Integer.toString(value) + ",  ";
            logger.log(Double.toString(entry.getKey()), logEntry);
        }
        return allResults;
    }
}
