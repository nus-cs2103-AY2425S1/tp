package seedu.address.logic.exporter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.UserPrefs;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.testutil.TypicalPersons.CHARLIE;

public class VcfExporterTest {
    private static final String ALICE_VCF_FILE_PATH = "charlie.vcf";

    private VcfExporter vcfExporter;

    @TempDir
    public Path temporaryFolder;

    @BeforeEach
    public void setUp() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setExportDirectoryPath(temporaryFolder.resolve("exports"));
        vcfExporter = new VcfExporter(userPrefs);
    }

    private Path getTestFilePath(String fileName) {
        String path = "/logic/exporter/VcfExporterTest/" + fileName;
        try {
            URL url = VcfExporterTest.class.getResource(path);
            assertNotNull(url, path + " does not exist.");
            return Path.of(url.toURI());
        } catch (URISyntaxException e) {
            fail("Failed to get URI for resource: " + path);
            return null;
        }
    }

    private String readTestFile(String fileName) {
        try {
            return FileUtil.readFromFile(getTestFilePath(fileName));
        } catch (IOException ie) {
            fail("Failed to load resource: " + fileName);
            return null;
        }
    }

    @Test
    public void export_onePerson_createsPersonVcf() {
        // Assert that the file does not exist at first
        Path exportPath = vcfExporter.getExportPath();
        assertFalse(FileUtil.isFileExists(exportPath));

        // Export creates a file with the person's details
        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(CHARLIE);
        assertDoesNotThrow(() -> vcfExporter.exportAddressBook(addressBook));

        // Assert that the file exists now
        assertTrue(FileUtil.isFileExists(exportPath));
        try {
            String expectedValue = readTestFile(ALICE_VCF_FILE_PATH);
            String actualValue = FileUtil.readFromFile(exportPath);

            assertEquals(expectedValue, actualValue);
        } catch (IOException ie) {
            fail(ie);
        }
    }
}
