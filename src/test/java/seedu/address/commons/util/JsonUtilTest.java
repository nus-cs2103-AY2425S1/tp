package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.testutil.SerializableTestClass;
import seedu.address.testutil.TestUtil;

/**
 * Tests JSON Read and Write
 */
public class JsonUtilTest {

    private static final Path SERIALIZATION_FILE = TestUtil.getFilePathInSandboxFolder("serialize.json");
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonUtilTest");

    @BeforeEach
    public void setUp() throws IOException {
        Files.createDirectories(TEST_DATA_FOLDER);
    }

    @Test
    public void serializeObjectToJsonFile_noExceptionThrown() throws IOException {
        SerializableTestClass serializableTestClass = new SerializableTestClass();
        serializableTestClass.setTestValues();

        JsonUtil.serializeObjectToJsonFile(SERIALIZATION_FILE, serializableTestClass);

        assertEquals(FileUtil.readFromFile(SERIALIZATION_FILE), SerializableTestClass.JSON_STRING_REPRESENTATION);
    }

    @Test
    public void deserializeObjectFromJsonFile_noExceptionThrown() throws IOException {
        FileUtil.writeToFile(SERIALIZATION_FILE, SerializableTestClass.JSON_STRING_REPRESENTATION);

        SerializableTestClass serializableTestClass = JsonUtil
            .deserializeObjectFromJsonFile(SERIALIZATION_FILE, SerializableTestClass.class);

        assertEquals(serializableTestClass.getName(), SerializableTestClass.getNameTestValue());
        assertEquals(serializableTestClass.getListOfLocalDateTimes(), SerializableTestClass.getListTestValues());
        assertEquals(serializableTestClass.getMapOfIntegerToString(), SerializableTestClass.getHashMapTestValues());
    }

    @Test
    public void jsonUtil_readJsonStringToObjectInstance_correctObject() throws IOException {
        TestClass testClass = new TestClass("Test String", 123);
        String json = JsonUtil.toJsonString(testClass);

        TestClass readBack = JsonUtil.fromJsonString(json, TestClass.class);

        assertEquals(testClass, readBack, "Object read back should be equal to original object");
    }

    @Test
    public void jsonUtil_writeThenReadObjectToJson_correctObject() throws IOException {
        TestClass testClass = new TestClass("Test String", 123);

        Path testFile = TEST_DATA_FOLDER.resolve("testFile.json");
        Files.createDirectories(testFile.getParent());
        Files.deleteIfExists(testFile);

        JsonUtil.saveJsonFile(testClass, testFile);
        assert Files.exists(testFile) : "File was not created";
        TestClass readBack = JsonUtil.deserializeObjectFromJsonFile(testFile, TestClass.class);
        assertEquals(testClass, readBack, "Object read back from file should be equal to original object");
        Files.deleteIfExists(testFile);
    }

    /**
     * A test class to be converted to/from JSON
     */
    private static class TestClass {
        private String stringField;
        private int intField;

        public TestClass() {}

        TestClass(String stringField, int intField) {
            this.stringField = stringField;
            this.intField = intField;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof TestClass)) {
                return false;
            }
            TestClass other = (TestClass) obj;
            return stringField.equals(other.stringField)
                    && intField == other.intField;
        }
    }
}
