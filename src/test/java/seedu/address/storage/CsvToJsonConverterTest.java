package seedu.address.storage;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

import seedu.address.storage.exceptions.ConverterException;
import seedu.address.testutil.TestUtil;

public class CsvToJsonConverterTest {
    private String testImportFilePath = "src/test/data/ConverterTestUtil/ImportTest";
    @Test
    public void CsvToJsonConverter_emptyDirectory_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, "The import file is missing. Restart the program.",
                () -> new CsvToJsonConverter(new File("")));
    }

    @Test
    public void CsvToJsonConverter_correctFieldNames() {
        CsvToJsonConverter testConverter = new CsvToJsonConverter(new File(testImportFilePath));
        assertEquals(new File(testImportFilePath), testConverter.getDirectory());

        String[] expectedFields = {"contactType", "name", "phone", "email", "telegramHandle", "moduleName", "remark",
                "set"};
        assertEquals(Arrays.toString(expectedFields), Arrays.toString(testConverter.getPersonFieldNames()));
    }

    @Test
    public void convertAllCsvFile_validFile_correctJsonFile() {
        List<File> expected = new ArrayList<>();
        assertTrue(
                expected.add(
                        new File("src/test/data/ConverterTestUtil/importTestJsonValid.json")
                )
        );
        CsvToJsonConverter testConverter = new CsvToJsonConverter(
                new File("src/test/data/ConverterTestUtil/ImportTestValid")
        );
        assertJsonFileEqualsNoException(expected, testConverter, true);
    }

    @Test
    public void convertAllCsvFile_nonDirectory_returnsEmptyJsonFile() {
        CsvToJsonConverter testConverter = new CsvToJsonConverter(
                new File("src/test/data/ConverterTestUtil/test.csv")
        );
        List<File> expected = new ArrayList<>();
        assertJsonFileEqualsNoException(expected, testConverter, true);
    }

    /**
     * If isEquals is true, checks if expected and actual .json contain the same content.
     * If isEquals is false, checks if they contain different content.
     * @param expected the expected content of a .json file
     * @param actual the actual content of a .json file
     * @param isEqual boolean expected when comparing expected and actual
     */
    public void assertJsonFileEqualsNoException(List<File> expected, CsvToJsonConverter actual, boolean isEqual) {
        try {
            List<File> convertedFileList = actual.convertAllCsvFiles();
            int expectedSize = expected.size();
            int convertedFileListSize = convertedFileList.size();

            assertEquals(expectedSize, convertedFileListSize);
            for (int i = 0; i < expectedSize; i++) {
                File file1 = expected.get(i);
                File file2 = convertedFileList.get(i);

                assertEquals(isEqual, TestUtil.areJsonFilesEqual(file1, file2));
            }
        } catch (ConverterException | IOException ce) {
            fail();
        }
    }

}
