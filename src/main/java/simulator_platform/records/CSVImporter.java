package simulator_platform.records;

import com.opencsv.CSVReader;
import simulator_platform.IRecordImportMap;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CSVImporter implements IImporter {

    private String csvPath;
    private List<String[]> rows;
    private IRecordImportMap map;

    public CSVImporter(String fullPath, IRecordImportMap map) {
        this.map = map;
        this.csvPath = fullPath;
    }

    @Override
    public List<Record> importRecords() throws Exception {
        List ret = new ArrayList();
        List<String[]> rows = getCsvReader(csvPath).readAll();
        String[] headers = rows.get(0);
        for(int i = 1; i < rows.size(); i++)
            ret.add( newRecord(i-1, headers, rows.get(i)) );
        return ret;
    }


    private Record newRecord(int id, String[] headers, String[] values) throws Exception {
        Record record = new Record(id);
        for(int i = 0; i < headers.length; i++)
            map.mapStringValuesToRecord(record, headers[i], values[i]);
        return record;
    }

    private CSVReader getCsvReader(String filename) throws FileNotFoundException {
        return new CSVReader(new FileReader(filename));
    }
}
