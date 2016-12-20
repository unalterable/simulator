package agent_simulation;

import simulator_platform.logger.ILogger;
import org.junit.Before;
import org.junit.Test;
import simulator_platform.records.CSVImporter;
import simulator_platform.records.IImporter;
import simulator_platform.records.Record;
import simulator_platform.ISimulatorRules;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static agent_simulation.AgentMap.*;

public class AgentSimulationRulesTest {

    final String DIR = "src/test/java/agent_simulation/";
    final String TEST_DATA_PATH = DIR + "test_data.csv";


    IImporter mockImporter;
    ILogger mockLogger;
    List<Record> records;

    ISimulatorRules sut;

    @Before
    public void setUp() throws Exception {
        records = new CSVImporter(TEST_DATA_PATH, new AgentMap()).importRecords();

        mockLogger = mock(ILogger.class);
        mockImporter = mock(IImporter.class);
        when(mockImporter.importRecords()).thenReturn(records);

        sut = newAgentSimulation(0.0);
    }

    private AgentSimulationRules newAgentSimulation(double brandFactor) {
        return new AgentSimulationRules(brandFactor, mockImporter);
    }

    @Test
    public void getStartData_gets_records_from_importer() throws Exception {
        sut.getStartData();
        verify(mockImporter, times(1)).importRecords();
    }

    @Test
    public void tick_increases_age() throws Exception {
        assertEquals(66, records.get(0).getInt(AGE));
        sut.tick(records);
        assertEquals(67, records.get(0).getInt(AGE));
    }

    @Test
    public void tick_doesnt_change_breed_if_AutoRenew() throws Exception {
        assertEquals(BREED_C, records.get(4).getStr(BREED));
        sut.tick(records);
        assertEquals(BREED_C, records.get(4).getStr(BREED));
    }

    @Test
    public void tick_changes_Breed_C_where_appropriate1() throws Exception {
        assertEquals(BREED_C, records.get(0).getStr(BREED));
        assertEquals(BREED_C, records.get(1).getStr(BREED));
        sut.tick(records);
        assertEquals(BREED_NC, records.get(0).getStr(BREED));
        assertEquals(BREED_C, records.get(1).getStr(BREED));
    }

    @Test
    public void tick_changes_Breed_NC_where_appropriate2() throws Exception {
        sut = newAgentSimulation(1.0);
        assertEquals(BREED_NC, records.get(2).getStr(BREED));
        assertEquals(BREED_NC, records.get(3).getStr(BREED));
        sut.tick(records);
        assertEquals(BREED_NC, records.get(2).getStr(BREED));
        assertEquals(BREED_NC, records.get(3).getStr(BREED));
    }

    @Test
    public void tick_changes_Breed_NC_where_appropriate3() throws Exception {
        sut = newAgentSimulation(2.0);
        sut.tick(records);
        assertEquals(BREED_NC, records.get(2).getStr(BREED));
        assertEquals(BREED_C, records.get(3).getStr(BREED));
    }

    @Test
    public void tick_changes_Breed_NC_where_appropriate4() throws Exception {
        sut = newAgentSimulation(5.0);
        sut.tick(records);
        assertEquals(BREED_C, records.get(2).getStr(BREED));
        assertEquals(BREED_C, records.get(3).getStr(BREED));
    }

    @Test
    public void continueTicking_true_until_tick_called_15_times() throws Exception {
        assertEquals(true, sut.continueTicking());
        for (int i = 0; i < 15; i++)  sut.tick(records);
        assertEquals(false, sut.continueTicking());
    }

    @Test
    public void analyseResults1() throws Exception {
        sut = newAgentSimulation(0.0);
        sut.tick(records);
        assertEquals(BREED_NC, records.get(0).getStr(BREED)); // changed
        assertEquals(BREED_C, records.get(1).getStr(BREED));
        assertEquals(BREED_NC, records.get(2).getStr(BREED));
        assertEquals(BREED_NC, records.get(3).getStr(BREED));
        assertEquals(BREED_C, records.get(4).getStr(BREED));

        Object agentsInBreedC = sut.analyseResults(records).get(0);
        Object agentsInBreedNC = sut.analyseResults(records).get(1);
        Object breedCLost = sut.analyseResults(records).get(2);
        Object breedCGained = sut.analyseResults(records).get(3);
        Object breedCRegained = sut.analyseResults(records).get(4);

        assertEquals(2, agentsInBreedC);
        assertEquals(3, agentsInBreedNC);
        assertEquals(1, breedCLost);
        assertEquals(0, breedCGained);
        assertEquals(0, breedCRegained);
    }

    @Test
    public void analyseResults2() throws Exception {
        sut = newAgentSimulation(5.0);
        sut.tick(records);
        assertEquals(BREED_NC, records.get(0).getStr(BREED)); // changed
        assertEquals(BREED_C, records.get(1).getStr(BREED));
        assertEquals(BREED_C, records.get(2).getStr(BREED)); // changed
        assertEquals(BREED_C, records.get(3).getStr(BREED)); // changed
        assertEquals(BREED_C, records.get(4).getStr(BREED));

        Object agentsInBreedC = sut.analyseResults(records).get(0);
        Object agentsInBreedNC = sut.analyseResults(records).get(1);
        Object breedCLost = sut.analyseResults(records).get(2);
        Object breedCGained = sut.analyseResults(records).get(3);
        Object breedCRegained = sut.analyseResults(records).get(4);

        assertEquals(4, agentsInBreedC);
        assertEquals(1, agentsInBreedNC);
        assertEquals(1, breedCLost);
        assertEquals(2, breedCGained);
        assertEquals(0, breedCRegained);

        sut.tick(records);
        assertEquals(BREED_C, records.get(0).getStr(BREED)); // changed again
        breedCRegained = sut.analyseResults(records).get(4);
        assertEquals(1, breedCRegained);
    }
}