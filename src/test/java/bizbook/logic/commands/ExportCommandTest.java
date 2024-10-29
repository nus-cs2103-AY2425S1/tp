package bizbook.logic.commands;

import static bizbook.logic.commands.CommandTestUtil.assertCommandFailure;
import static bizbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static bizbook.logic.commands.ExportCommand.MESSAGE_EMPTY_ADDRESS_BOOK;
import static bizbook.logic.commands.ExportCommand.MESSAGE_SUCCESS;
import static bizbook.testutil.TypicalFileTypes.FILE_TYPE_CSV;
import static bizbook.testutil.TypicalFileTypes.FILE_TYPE_VCF;
import static bizbook.testutil.TypicalPersons.AMY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import bizbook.model.Model;
import bizbook.model.ModelManager;

/**
 * Contains integration tests (interaction with the Model) for {@code ExportCommand}.
 */
public class ExportCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_export_success() {
        model.addPerson(AMY);
        expectedModel.addPerson(AMY);

        CommandResult expectedCommandResult = new CommandResult(String.format(MESSAGE_SUCCESS, FILE_TYPE_CSV),
            false, false);
        assertCommandSuccess(new ExportCommand(FILE_TYPE_CSV), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_emptyAddressBook_throwsCommandException() {
        model.getFilteredPersonList().clear();
        expectedModel.getFilteredPersonList().clear();

        CommandResult expectedCommandResult = new CommandResult(String.format(MESSAGE_SUCCESS, FILE_TYPE_CSV),
                false, false);
        assertCommandFailure(new ExportCommand(FILE_TYPE_CSV), model, MESSAGE_EMPTY_ADDRESS_BOOK);
    }

    @Test
    public void equals() {
        ExportCommand exportFirstCommand = new ExportCommand(FILE_TYPE_CSV);
        ExportCommand exportSecondCommand = new ExportCommand(FILE_TYPE_VCF);

        // same object -> returns true
        assertTrue(exportFirstCommand.equals(exportFirstCommand));

        // same values -> returns true
        ExportCommand exportFirstCommandCopy = new ExportCommand(FILE_TYPE_CSV);
        assertTrue(exportFirstCommand.equals(exportFirstCommandCopy));

        // different types -> returns false
        assertFalse(exportFirstCommand.equals(1));

        // null -> returns false
        assertFalse(exportFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(exportFirstCommand.equals(exportSecondCommand));
    }

    @Test
    public void toStringMethod() {
        ExportCommand firstExportCommand = new ExportCommand(FILE_TYPE_CSV);
        String firstExportExpected = ExportCommand.class.getCanonicalName() + "{fileType=" + FILE_TYPE_CSV + "}";
        assertEquals(firstExportExpected, firstExportCommand.toString());

        ExportCommand secondExportCommand = new ExportCommand(FILE_TYPE_VCF);
        String secondExportExpected = ExportCommand.class.getCanonicalName() + "{fileType=" + FILE_TYPE_VCF + "}";
        assertEquals(secondExportExpected, secondExportCommand.toString());
    }
}
