package simulator_platform;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface ISimulatorSetRules {

    List getVariantFactorsForSet();

    ISimulatorRules buildSimulationRules(Object factor);

    HashMap analyseAllResults(HashMap allResults) throws IOException;
}
