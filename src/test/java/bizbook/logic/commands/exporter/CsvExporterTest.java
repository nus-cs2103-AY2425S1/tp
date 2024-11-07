package bizbook.logic.commands.exporter;

import static bizbook.logic.commands.exporter.Exporter.MESSAGE_EMPTY_ADDRESS_BOOK;
import static bizbook.testutil.TypicalPersons.AMY;
import static bizbook.testutil.TypicalPersons.BOB;
import static bizbook.testutil.TypicalPersons.CHARLIE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import bizbook.commons.util.FileUtil;
import bizbook.logic.commands.exporter.exceptions.InvalidAddressBookException;
import bizbook.model.AddressBook;
import bizbook.model.UserPrefs;
import bizbook.testutil.TestUtil;

public class CsvExporterTest {
    private static final String CHARLIE_VCF_FILE_PATH = "CsvExporterTest/charlie.csv";
    private static final String PEOPLE_VCF_FILE_PATH = "CsvExporterTest/people.csv";

    @TempDir
    public Path temporaryFolder;

    private CsvExporter csvExporter;

    @BeforeEach
    public void setUp() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setExportDirectoryPath(temporaryFolder.resolve("exports"));
        csvExporter = new CsvExporter(userPrefs);
    }

    @Test
    public void export_empty_throwsEmptyAddressBookException() {
        // Assert that the file does not exist at first
        Path exportPath = csvExporter.getExportPath();
        assertFalse(FileUtil.isFileExists(exportPath));

        AddressBook addressBook = new AddressBook();
        assertThrows(InvalidAddressBookException.class, () -> csvExporter.exportAddressBook(addressBook),
                MESSAGE_EMPTY_ADDRESS_BOOK);
        assertFalse(FileUtil.isFileExists(exportPath));
    }

    @Test
    public void export_onePerson_createsPersonVcf() {
        // Assert that the file does not exist at first
        Path exportPath = csvExporter.getExportPath();
        assertFalse(FileUtil.isFileExists(exportPath));

        // Export creates a file with the person's details
        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(CHARLIE);
        assertDoesNotThrow(() -> csvExporter.exportAddressBook(addressBook));

        // Assert that the file exists now
        assertTrue(FileUtil.isFileExists(exportPath));
        try {
            String expectedValue = TestUtil.readResourceFile(CsvExporterTest.class, CHARLIE_VCF_FILE_PATH);
            String actualValue = FileUtil.readFromFile(exportPath);

            assertEquals(expectedValue, actualValue);
        } catch (IOException ie) {
            fail(ie);
        }
    }

    @Test
    public void export_multiplePeople_createsPeopleVcf() {
        // Assert that the file does not exist at first
        Path exportPath = csvExporter.getExportPath();
        assertFalse(FileUtil.isFileExists(exportPath));

        // Export creates a file with the person's details
        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(AMY);
        addressBook.addPerson(BOB);
        addressBook.addPerson(CHARLIE);
        assertDoesNotThrow(() -> csvExporter.exportAddressBook(addressBook));

        // Assert that the file exists now
        assertTrue(FileUtil.isFileExists(exportPath));
        try {
            String expectedValue = TestUtil.readResourceFile(CsvExporterTest.class, PEOPLE_VCF_FILE_PATH);
            String actualValue = FileUtil.readFromFile(exportPath);

            assertEquals(expectedValue, actualValue);
        } catch (IOException ie) {
            fail(ie);
        }
    }
}
