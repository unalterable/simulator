package simulator_platform;

import simulator_platform.records.Record;

import java.io.IOException;
import java.util.List;

public interface ISimulatorRules {

    List<Record> getStartData() throws Exception;

    Boolean continueTicking();

    void tick(List<Record> records) throws Exception;

    List analyseResults(List data) throws IOException;


}