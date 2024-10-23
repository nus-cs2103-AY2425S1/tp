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
public class DeleteDoctorCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());


    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteDoctorCommand deleteCommand = new DeleteDoctorCommand(outOfBoundIndex);
        assertThrows(CommandException.class, () -> deleteCommand.execute(model));
    }


    @Test
    public void equals() {
        DeleteDoctorCommand deleteDoctorFirstCommand = new DeleteDoctorCommand(INDEX_FIRST_PERSON);
        DeleteDoctorCommand deleteDoctorSecondCommand = new DeleteDoctorCommand(INDEX_SECOND_PERSON);

        assertTrue(deleteDoctorFirstCommand.equals(deleteDoctorFirstCommand));

        // different types -> returns false
        assertFalse(deleteDoctorFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteDoctorFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteDoctorFirstCommand.equals(deleteDoctorSecondCommand));
    }


    @Test
    public void toString_test() {
        DeleteDoctorCommand deleteDoctorFirstCommand = new DeleteDoctorCommand(INDEX_FIRST_PERSON);
        String expected = "seedu.address.logic.commands.DeleteDoctorCommand{"
                + "targetIndex=seedu.address.commons.core.index.Index{"
                + "zeroBasedIndex=0}}";
        assertEquals(expected, deleteDoctorFirstCommand.toString());
    }
}
