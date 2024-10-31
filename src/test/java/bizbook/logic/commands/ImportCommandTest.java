package bizbook.logic.commands;

import static bizbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static bizbook.logic.commands.ImportCommand.MESSAGE_SUCCESS;
import static bizbook.testutil.TypicalFileTypes.FILE_TYPE_CSV;
import static bizbook.testutil.TypicalFileTypes.FILE_TYPE_VCF;
import static bizbook.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import bizbook.model.AddressBook;
import bizbook.model.Model;
import bizbook.model.ModelManager;
import bizbook.testutil.TestUtil;


/**
 * Contains integration tests (interaction with the Model) for {@code ImportCommand}.
 */
public class ImportCommandTest {
    private static final String TYPICAL_VCF_FILE_PATH = "ImportCommandTest/typical.vcf";

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    private Path convertToRelative(Path absolutePath) {
        Path currentPath = Paths.get(".").toAbsolutePath();
        return currentPath.relativize(absolutePath);
    }

    @Test
    public void execute_importAbsolutePath_success() {
        Path path = TestUtil.getResourceFilePath(ImportCommandTest.class, TYPICAL_VCF_FILE_PATH);

        AddressBook addressBook = getTypicalAddressBook();
        expectedModel.setAddressBook(addressBook);
        CommandResult expectedCommandResult = new CommandResult(
                String.format(MESSAGE_SUCCESS, addressBook.getPersonList().size()), false, false);

        assertCommandSuccess(new ImportCommand(FILE_TYPE_VCF, path), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_importRelativePath_success() {
        Path path = convertToRelative(
                TestUtil.getResourceFilePath(ImportCommandTest.class, TYPICAL_VCF_FILE_PATH));

        AddressBook addressBook = getTypicalAddressBook();
        expectedModel.setAddressBook(addressBook);
        CommandResult expectedCommandResult = new CommandResult(
                String.format(MESSAGE_SUCCESS, addressBook.getPersonList().size()), false, false);

        assertCommandSuccess(new ImportCommand(FILE_TYPE_VCF, path), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void equals() {
        ImportCommand importFirstCommand = new ImportCommand(FILE_TYPE_CSV, Path.of("a"));

        // same object -> returns true
        assertTrue(importFirstCommand.equals(importFirstCommand));

        // same values -> returns true
        ImportCommand importFirstCommandCopy = new ImportCommand(FILE_TYPE_CSV, Path.of("a"));
        assertTrue(importFirstCommand.equals(importFirstCommandCopy));

        // different types -> returns false
        assertFalse(importFirstCommand.equals(1));

        // null -> returns false
        assertFalse(importFirstCommand.equals(null));

        // different file type -> returns false
        assertFalse(importFirstCommand.equals(new ImportCommand(FILE_TYPE_VCF, Path.of("a"))));

        // different paths -> returns false
        assertFalse(importFirstCommand.equals(new ImportCommand(FILE_TYPE_CSV, Path.of("b"))));
    }

    @Test
    public void toStringMethod() {
        Path path = Path.of("x", "y");
        ImportCommand importCommand = new ImportCommand(FILE_TYPE_VCF, Path.of("x", "y"));
        String secondImportExpected = ImportCommand.class.getCanonicalName()
                + "{fileType=" + FILE_TYPE_VCF
                + ", path=" + path + "}";
        assertEquals(secondImportExpected, importCommand.toString());
    }
}
