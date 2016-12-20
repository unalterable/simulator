package agent_simulation;

import org.junit.Before;
import org.junit.Test;
import simulator_platform.ISimulatorRules;
import simulator_platform.ISimulatorSetRules;
import simulator_platform.records.CSVImporter;
import simulator_platform.records.IImporter;
import simulator_platform.records.Record;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AgentSimulationSetRulesTest {

    final String DIR = "src/test/java/agent_simulation/";
    final String TEST_DATA_PATH = DIR + "test_data.csv";
    final double aDouble = 0.0;

    IImporter mockImporter;
    List<Record> records;

    ISimulatorSetRules sut;

    @Before
    public void setUp() throws Exception {
        records = new CSVImporter(TEST_DATA_PATH, new AgentMap()).importRecords();

        mockImporter = mock(IImporter.class);
        when(mockImporter.importRecords()).thenReturn(records);

        sut = new AgentSimulationSetRules(mockImporter);
    }

    @Test
    public void getVariantFactorsForSet() throws Exception {
        for(Object factor : sut.getVariantFactorsForSet())
            assertThat(factor, instanceOf(Double.class));
    }

    @Test
    public void buildSimulationRules() throws Exception {
        assertThat(sut.buildSimulationRules(aDouble), instanceOf(ISimulatorRules.class));
    }

    @Test
    public void analyseSetResults() throws Exception {
        HashMap hash = new HashMap();
        assertEquals(hash, sut.analyseAllResults(hash));
    }



}