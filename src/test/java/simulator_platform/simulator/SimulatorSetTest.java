package simulator_platform.simulator;

import org.junit.Before;
import org.junit.Test;
import simulator_platform.ISimulatorRules;
import simulator_platform.ISimulatorSetRules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class SimulatorSetTest {

    static final List TEST_VARIANCES = new ArrayList(){{
       add(1.1);
       add(5.2);
       add(8.5);
    }};
    static final int CORRECT_NUM_OF_SIMS = TEST_VARIANCES.size();

    ISimulatorSetRules mockSetRules;
    ISimulatorRules mockRules;

    SimulatorSet sut;

    @Before
    public void setUp() throws Exception {
        mockSetRules = mock(ISimulatorSetRules.class);
        mockRules = mock(ISimulatorRules.class);
        when(mockSetRules.buildSimulationRules(anyDouble())).thenReturn(mockRules);
        when(mockSetRules.getVariantFactorsForSet()).thenReturn(TEST_VARIANCES);

        sut = new SimulatorSet(mockSetRules);
    }

    @Test
    public void constructor_builds_correct_number_of_Simulations() throws Exception {
        verify(mockSetRules, times(CORRECT_NUM_OF_SIMS)).buildSimulationRules(anyDouble());
    }

    @Test
    public void run_uses_start_data_for_each_sim() throws Exception {
        sut.runAll();
        verify(mockRules, times(CORRECT_NUM_OF_SIMS)).getStartData();
      }

    @Test
    public void getResults() throws Exception {
        assertEquals(CORRECT_NUM_OF_SIMS, sut.getAllEndData().size());
    }

    @Test
    public void getAllAnalysedResults() throws Exception {
        sut.analyseAllResults();
        verify(mockRules, times(CORRECT_NUM_OF_SIMS)).analyseResults(anyList());
        verify(mockSetRules).analyseAllResults(any(HashMap.class));
    }
}