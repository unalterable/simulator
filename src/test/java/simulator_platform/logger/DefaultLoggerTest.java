package simulator_platform.logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DefaultLoggerTest {

    private static final String TEST_LOG_ENTRY0 = "THIS IS A TEST LOG ENTRY0";
    private static final String TEST_LOG_ENTRY1 = "THIS IS A TEST LOG ENTRY1";
    final String TEST_LOG_DIR = "src/test/java/simulator_platform/logger/";
    final String TEST_LOG_NAME = "test_log";

    ILogger sut;

    @Before
    public void setUp() throws Exception {
        delete_test_file_if_exists();
        sut = new DefaultLogger(TEST_LOG_DIR, TEST_LOG_NAME);
    }

    @Test
    public void getPath_gives_path() throws Exception {
        assertEquals(TEST_LOG_DIR, sut.getDir());
    }

    @Test
    public void log_logs_correctly() throws Exception {
        sut.log(TEST_LOG_ENTRY0, TEST_LOG_ENTRY1);
        assertEquals(TEST_LOG_ENTRY0 + "," + TEST_LOG_ENTRY1, test_file_lines().get(0));
    }

    @Test
    public void retrieveLogs_works_correctly() throws Exception {
        sut.log("0", TEST_LOG_ENTRY0);
        sut.log("1",TEST_LOG_ENTRY1);
        assertEquals("0,"+TEST_LOG_ENTRY0, sut.retrieveLogs().get(0));
        assertEquals("1,"+TEST_LOG_ENTRY1, sut.retrieveLogs().get(1));
    }

    @After
    public void tearDown() throws Exception {
        //delete_test_file_if_exists();
    }

    private void delete_test_file_if_exists() {
        File file = new File(TEST_LOG_DIR, TEST_LOG_NAME);
        if (file.exists()) file.delete();
    }

    private List<String> test_file_lines() {
        List<String> strings = null;
        try {
            strings = Files.readAllLines(Paths.get(TEST_LOG_DIR + TEST_LOG_NAME));
        } catch (IOException e) { fail("Could Not Find File: " + TEST_LOG_DIR + TEST_LOG_NAME); }
        return strings;
    }
}