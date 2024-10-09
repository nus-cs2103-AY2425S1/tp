package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeletePatientCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());


    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeletePatientCommand deleteCommand = new DeletePatientCommand(outOfBoundIndex);
        assertThrows(CommandException.class, () -> deleteCommand.execute(model));
    }


    @Test
    public void equals() {
        DeletePatientCommand deletePatientFirstCommand = new DeletePatientCommand(INDEX_FIRST_PERSON);
        DeletePatientCommand deletePatientSecondCommand = new DeletePatientCommand(INDEX_SECOND_PERSON);

        assertTrue(deletePatientFirstCommand.equals(deletePatientFirstCommand));

        // different types -> returns false
        assertFalse(deletePatientFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deletePatientFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deletePatientFirstCommand.equals(deletePatientSecondCommand));
    }


    @Test
    public void toString_test() {
        DeletePatientCommand deletePatientFirstCommand = new DeletePatientCommand(INDEX_FIRST_PERSON);
        String expected = "seedu.address.logic.commands.DeletePatientCommand{"
                + "targetIndex=seedu.address.commons.core.index.Index{"
                + "zeroBasedIndex=0}}";
        assertEquals(expected, deletePatientFirstCommand.toString());
    }
}
