package simulator_platform.simulator;

import org.junit.Before;
import org.junit.Test;
import simulator_platform.ISimulatorRules;
import simulator_platform.records.Record;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class SimulatorTest {

    ISimulatorRules mockRules = mock(ISimulatorRules.class);
    Record mockRecord;

    Simulator sut;

    @Before
    public void before() throws Exception {
        mockRecord = mock(Record.class);
        when(mockRules.continueTicking()).thenReturn(false);
        when(mockRules.getStartData()).thenReturn(Arrays.asList(mockRecord, mockRecord));
        sut = new Simulator(mockRules);
    }

    @Test
    public void run_stops_when_rules_say_so() throws Exception {
        sut.run();
        verify( mockRules ).continueTicking();
    }

    @Test
    public void getCurrentData_gives_a_list() throws Exception {
        assertEquals(2, sut.getCurrentData().size());
    }

    @Test
    public void getAnalysedResults_forwards_messages_to_rules() throws Exception {
        sut.getAnalysedResults();
        verify( mockRules ).analyseResults(anyList());
    }
}