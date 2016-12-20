package simulator_platform;

import simulator_platform.records.Record;

public interface IRecordImportMap {

    void mapStringValuesToRecord(Record record, String key, String value);
}
