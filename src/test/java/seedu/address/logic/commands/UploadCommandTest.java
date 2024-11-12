package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;


/**
 * Contains integration tests (interaction with the Model) and unit tests for UploadCommand.
 */
public class UploadCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UploadCommand uploadCommand = new UploadCommand(outOfBoundIndex);
        assertCommandFailure(uploadCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        UploadCommand uploadFirstCommand = new UploadCommand(INDEX_FIRST_PERSON);
        UploadCommand uploadSecondCommand = new UploadCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(uploadFirstCommand.equals(uploadFirstCommand));

        // same values -> returns true
        UploadCommand uploadFirstCommandCopy = new UploadCommand(INDEX_FIRST_PERSON);
        assertTrue(uploadFirstCommand.equals(uploadFirstCommandCopy));

        // different types -> returns false
        assertFalse(uploadFirstCommand.equals(1));

        // null -> returns false
        assertFalse(uploadFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(uploadFirstCommand.equals(uploadSecondCommand));
    }
}
