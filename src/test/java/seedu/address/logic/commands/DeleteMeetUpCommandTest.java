package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showMeetUpAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEETUP;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MEETUP;
import static seedu.address.testutil.TypicalMeetUps.getTypicalMeetUpList;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.meetup.MeetUp;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteMeetUpCommand}.
 */
public class DeleteMeetUpCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalMeetUpList());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        MeetUp meetUpToDelete = model.getFilteredMeetUpList().get(INDEX_FIRST_MEETUP.getZeroBased());
        DeleteMeetUpCommand deleteMeetUpCommand = new DeleteMeetUpCommand(INDEX_FIRST_MEETUP);

        String expectedMessage = String.format(DeleteMeetUpCommand.MESSAGE_DELETE_MEETUP_SUCCESS,
                Messages.format(meetUpToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), model.getMeetUpList());
        expectedModel.deleteMeetUp(meetUpToDelete);

        assertCommandSuccess(deleteMeetUpCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showMeetUpAtIndex(model, INDEX_FIRST_MEETUP);

        MeetUp meetUpToDelete = model.getFilteredMeetUpList().get(INDEX_FIRST_MEETUP.getZeroBased());
        DeleteMeetUpCommand deleteMeetUpCommand = new DeleteMeetUpCommand(INDEX_FIRST_MEETUP);

        String expectedMessage = String.format(DeleteMeetUpCommand.MESSAGE_DELETE_MEETUP_SUCCESS,
                Messages.format(meetUpToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), model.getMeetUpList());
        expectedModel.deleteMeetUp(meetUpToDelete);
        showNoMeetUp(expectedModel);

        assertCommandSuccess(deleteMeetUpCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showMeetUpAtIndex(model, INDEX_FIRST_MEETUP);

        Index outOfBoundIndex = INDEX_SECOND_MEETUP;
        // ensures that outOfBoundIndex is still in bounds of meetup list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getMeetUpList().getMeetUpList().size());

        DeleteMeetUpCommand deleteMeetUpCommand = new DeleteMeetUpCommand(outOfBoundIndex);

        assertCommandFailure(deleteMeetUpCommand, model, Messages.MESSAGE_INVALID_MEETUP_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteMeetUpCommand deleteFirstCommand = new DeleteMeetUpCommand(INDEX_FIRST_MEETUP);
        DeleteMeetUpCommand deleteSecondCommand = new DeleteMeetUpCommand(INDEX_SECOND_MEETUP);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteMeetUpCommand deleteFirstCommandCopy = new DeleteMeetUpCommand(INDEX_FIRST_MEETUP);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteMeetUpCommand deleteMeetUpCommand = new DeleteMeetUpCommand(targetIndex);
        String expected = DeleteMeetUpCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteMeetUpCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoMeetUp(Model model) {
        model.updateFilteredMeetUpList(p -> false);

        assertTrue(model.getFilteredMeetUpList().isEmpty());
    }
}
