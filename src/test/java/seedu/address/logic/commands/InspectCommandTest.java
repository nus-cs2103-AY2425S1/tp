package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code InspectCommand}.
 */
public class InspectCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_outOfBoundIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        InspectCommand inspectCommand = new InspectCommand(outOfBoundIndex);
        String expectedErrorMessage = String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                outOfBoundIndex.getOneBased());

        assertCommandFailure(inspectCommand, model, expectedErrorMessage);
    }

    @Test
    public void equals() {
        InspectCommand inspectFirstCommand = new InspectCommand(INDEX_FIRST);
        InspectCommand inspectSecondCommand = new InspectCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(inspectFirstCommand.equals(inspectFirstCommand));

        // same values -> returns true
        InspectCommand inspectFirstCommandCopy = new InspectCommand(INDEX_FIRST);
        assertTrue(inspectFirstCommand.equals(inspectFirstCommandCopy));

        // different types -> returns false
        assertFalse(inspectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(inspectSecondCommand.equals(null));

        // different person -> returns false
        assertFalse(inspectFirstCommand.equals(inspectSecondCommand));
    }
}
