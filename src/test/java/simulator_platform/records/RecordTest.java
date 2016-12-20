package simulator_platform.records;

import org.junit.*;

import static org.junit.Assert.*;


public class RecordTest {

    final String BAD_KEY = "badkey1";
    final String KEY1 = "KEY1";
    final String KEY2 = "KEY2";
    final int TEST_ID = 6789;


    Record sut;

    @Before
    public void setUp() {
        sut = new Record(TEST_ID);
    }


    @Test
    public void getID() throws Exception {
        assertEquals(TEST_ID, sut.getID());
    }

    @Test
    public void setStr_and_getStr() throws Exception {
        String first = "first";
        String second = "second";

        sut.setStr(KEY1, first);
        sut.setStr(KEY1, second);
        sut.setStr(KEY2, first);

        assertEquals(second, sut.getStr(KEY1));
        assertEquals(first, sut.getStr(KEY2));
    }
    @Test
    public void setInt_and_getInt() throws Exception {
        int first = 1;
        int second = 2;

        sut.setInt(KEY1, first);
        sut.setInt(KEY1, second);
        sut.setInt(KEY2, first);

        assertEquals(second, sut.getInt(KEY1));
        assertEquals(first, sut.getInt(KEY2));
    }
    @Test
    public void setIntFromStr_and_getInt() throws Exception {
        int first = 1;
        int second = 2;
        String firstAsStr = "1";
        String secondAsStr = "2";

        sut.setIntFromStr(KEY1, firstAsStr);
        sut.setIntFromStr(KEY1, secondAsStr);
        sut.setIntFromStr(KEY2, firstAsStr);

        assertEquals(second, sut.getInt(KEY1));
        assertEquals(first, sut.getInt(KEY2));
    }
    @Test
    public void setBool_and_getBool() throws Exception {
        Boolean first = true;
        Boolean second = false;

        sut.setBool(KEY1, first);
        sut.setBool(KEY1, second);
        sut.setBool(KEY2, first);

        assertEquals(second, sut.getBool(KEY1));
        assertEquals(first, sut.getBool(KEY2));
    }
    @Test
    public void setBoolFromStr_and_getBool() throws Exception {
        String truthAsStr = "1";
        String liesAsStr = "0";

        sut.setBoolFromStr(KEY1, truthAsStr);
        sut.setBoolFromStr(KEY1, liesAsStr);
        sut.setBoolFromStr(KEY2, truthAsStr);

        assertEquals(false, sut.getBool(KEY1));
        assertEquals(true, sut.getBool(KEY2));

        truthAsStr = "true";
        sut.setBoolFromStr(KEY1, truthAsStr);

        assertEquals(true, sut.getBool(KEY1));

    }
    @Test
    public void setDouble_and_getDouble() throws Exception {
        Double first = 12.34;
        Double second = 56.78;

        sut.setDouble(KEY1, first);
        sut.setDouble(KEY1, second);
        sut.setDouble(KEY2, first);

        assertTrue(second.equals(sut.getDouble(KEY1)));
        assertTrue(first.equals(sut.getDouble(KEY2)));
    }
    @Test
    public void setDoubleFromString_and_getDouble() throws Exception {
        Double first = 12.34;
        Double second = 56.78;
        String firstAsStr = "12.34";
        String secondAsStr = "56.78";

        sut.setDoubleFromStr(KEY1, firstAsStr);
        sut.setDoubleFromStr(KEY1, secondAsStr);
        sut.setDoubleFromStr(KEY2, firstAsStr);

        assertTrue(second.equals(sut.getDouble(KEY1)));
        assertTrue(first.equals(sut.getDouble(KEY2)));
    }
}