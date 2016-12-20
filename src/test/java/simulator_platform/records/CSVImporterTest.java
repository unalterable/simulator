package simulator_platform.records;

import org.junit.Before;
import org.junit.Test;
import simulator_platform.IRecordImportMap;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;


public class CSVImporterTest {

    final String DIR = "src/test/java/simulator_platform/records/";
    final String TEST_CSV_PATH = DIR + "test_csv.csv";
    final String BAD_FILE_PATH = DIR + "iDontExist.csv";

    CSVImporter sut, badSut;
    IRecordImportMap mockMap;

    @Before
    public void setUp() throws Exception {
        mockMap = mock(IRecordImportMap.class);
        sut = new CSVImporter(TEST_CSV_PATH, mockMap);
        badSut = new CSVImporter(BAD_FILE_PATH, mockMap);
    }

    @Test(expected = FileNotFoundException.class)
    public void importRecords_throws_FileNotFoundException_when_file_not_found() throws Exception {
        badSut.importRecords();
    }

    @Test
    public void records_returns_right_number_of_records() throws Exception {
        assertEquals(3, sut.importRecords().size());
    }

    @Test
    public void records_calls_mapStringValuesToRecord_for_every_value() throws Exception {
        sut.importRecords();
        verify(mockMap, times(9)).mapStringValuesToRecord(any(Record.class), anyString(), anyString() );
    }
}