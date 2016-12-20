package simulator_platform.records;

import java.util.List;

public interface IImporter {
    List<Record> importRecords() throws Exception;
}
