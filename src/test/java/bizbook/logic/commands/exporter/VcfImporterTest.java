package bizbook.logic.commands.exporter;

import static bizbook.testutil.TypicalPersons.CHARLIE;
import static bizbook.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bizbook.logic.commands.exporter.exceptions.EmptyAddressBookException;
import bizbook.model.AddressBook;
import bizbook.testutil.TestUtil;

public class VcfImporterTest {
    private static final String TYPICAL_VCF_FILE_PATH = "VcfImporterTest/typical.vcf";
    private static final String SINGLE_VCF_FILE_PATH = "VcfImporterTest/single.vcf";
    private static final String EMPTY_VCF_FILE_PATH = "VcfImporterTest/empty.vcf";

    private VcfImporter vcfImporter;

    private Path convertToRelative(Path absolutePath) {
        Path currentPath = Paths.get(".").toAbsolutePath();
        return currentPath.relativize(absolutePath);
    }

    @BeforeEach
    public void setUp() {
        vcfImporter = new VcfImporter();
    }

    @Test
    public void import_fullPath_success() throws IOException {
        Path path = TestUtil.getResourceFilePath(VcfImporterTest.class, TYPICAL_VCF_FILE_PATH);
        assertTrue(path.isAbsolute());

        AddressBook expectedAddressBook = getTypicalAddressBook();
        AddressBook actualAddressBook = vcfImporter.importAddressBook(path);

        assertEquals(expectedAddressBook, actualAddressBook);
    }

    @Test
    public void import_relativePath_success() throws IOException {
        // Convert absolute path to be relative to current directory
        Path path = convertToRelative(
                TestUtil.getResourceFilePath(VcfImporter.class, TYPICAL_VCF_FILE_PATH));
        assertFalse(path.isAbsolute());

        AddressBook expectedAddressBook = getTypicalAddressBook();
        AddressBook actualAddressBook = vcfImporter.importAddressBook(path);

        assertEquals(expectedAddressBook, actualAddressBook);
    }

    @Test
    public void import_single_success() throws IOException {
        Path path = TestUtil.getResourceFilePath(VcfImporterTest.class, SINGLE_VCF_FILE_PATH);

        AddressBook expectedAddressBook = new AddressBook();
        expectedAddressBook.addPerson(CHARLIE);
        AddressBook actualAddressBook = vcfImporter.importAddressBook(path);

        assertEquals(expectedAddressBook, actualAddressBook);
    }

    @Test
    public void import_empty_throwsEmptyAddressBookException() {
        Path path = TestUtil.getResourceFilePath(VcfImporterTest.class, EMPTY_VCF_FILE_PATH);

        assertThrows(EmptyAddressBookException.class, () -> vcfImporter.importAddressBook(path));
    }
}
