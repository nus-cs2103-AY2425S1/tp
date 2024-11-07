package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.UploadCommand.MESSAGE_UPLOAD_CANCELLED;
import static seedu.address.logic.commands.UploadCommand.MESSAGE_UPLOAD_ERROR;
import static seedu.address.logic.commands.UploadCommand.MESSAGE_UPLOAD_PERSON_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.ProfilePicFilePath;

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
