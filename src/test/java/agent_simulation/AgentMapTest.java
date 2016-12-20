package agent_simulation;

import org.junit.Before;
import org.junit.Test;
import simulator_platform.records.Record;

import static org.mockito.Mockito.*;
import static agent_simulation.AgentMap.*;

public class AgentMapTest {

    final String TEST_VAL = "test val";

    AgentMap sut;
    Record mock;

    @Before
    public void setUp() throws Exception {
        sut = new AgentMap();
        mock = mock(Record.class);
    }

    @Test
    public void AGENT_BREED() {
        verifyStoredAsString(BREED);
    }

    @Test
    public void POLICY_ID() {
        verifyStoredAsDouble(POLICY_ID);
    }

    @Test
    public void AGE() {
        verifyStoredAsInt(AGE);
    }

    @Test
    public void SOCIAL_GRADE() {
        verifyStoredAsInt(SOCIAL_GRADE);
    }

    @Test
    public void PAYMENT_AT_PURCHASE() { verifyStoredAsDouble(PAYMENT_AT_PURCHASE); }

    @Test
    public void ATTRIBUTE_BRAND() {
        verifyStoredAsDouble( ATTRIBUTE_BRAND);
    }

    @Test
    public void ATTRIBUTE_PRICE() {
        verifyStoredAsDouble( ATTRIBUTE_PRICE);
    }

    @Test
    public void ATTRIBUTE_PROMOTIONS() {
        verifyStoredAsDouble( ATTRIBUTE_PROMOTIONS);
    }

    @Test
    public void AUTO_RENEW() {
        verifyStoredAsBoolean( AUTO_RENEW );
    }

    @Test
    public void INERTIA_FOR_SWITCH() {
        verifyStoredAsInt( INERTIA_FOR_SWITCH  );
    }

    private void verifyStoredAsString(String field) {
        sut.mapStringValuesToRecord(mock, field, TEST_VAL);
        verify(mock).setStr( field, TEST_VAL);
    }

    private void verifyStoredAsInt(String field) {
        sut.mapStringValuesToRecord(mock, field, TEST_VAL);
        verify(mock).setIntFromStr( field, TEST_VAL);
    }

    private void verifyStoredAsDouble(String field) {
        sut.mapStringValuesToRecord(mock, field, TEST_VAL);
        verify(mock).setDoubleFromStr( field, TEST_VAL);
    }

    private void verifyStoredAsBoolean(String field) {
        sut.mapStringValuesToRecord(mock, field, TEST_VAL);
        verify(mock).setBoolFromStr( field, TEST_VAL);
    }


}