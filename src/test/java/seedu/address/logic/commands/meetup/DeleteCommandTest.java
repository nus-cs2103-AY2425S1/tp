package seedu.address.logic.commands.meetup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showMeetUpAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEETUP;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MEETUP;
import static seedu.address.testutil.buyer.TypicalBuyers.getTypicalBuyerList;
import static seedu.address.testutil.meetup.TypicalMeetUps.getTypicalMeetUpList;
import static seedu.address.testutil.property.TypicalProperties.getTypicalPropertyList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.meetup.MeetUp;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalBuyerList(), new UserPrefs(), getTypicalMeetUpList(),
            getTypicalPropertyList());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        MeetUp meetUpToDelete = model.getFilteredMeetUpList().get(INDEX_FIRST_MEETUP.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_MEETUP);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_MEETUP_SUCCESS,
                Messages.format(meetUpToDelete));

        ModelManager expectedModel = new ModelManager(model.getBuyerList(), new UserPrefs(), model.getMeetUpList(),
                model.getPropertyList());
        expectedModel.deleteMeetUp(meetUpToDelete);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false, true, false, false);

        assertCommandSuccess(deleteCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showMeetUpAtIndex(model, INDEX_FIRST_MEETUP);

        MeetUp meetUpToDelete = model.getFilteredMeetUpList().get(INDEX_FIRST_MEETUP.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_MEETUP);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_MEETUP_SUCCESS,
                Messages.format(meetUpToDelete));

        Model expectedModel = new ModelManager(model.getBuyerList(), new UserPrefs(), model.getMeetUpList(),
                model.getPropertyList());
        expectedModel.deleteMeetUp(meetUpToDelete);
        showNoMeetUp(expectedModel);

        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false, true, false, false);
        assertCommandSuccess(deleteCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showMeetUpAtIndex(model, INDEX_FIRST_MEETUP);

        Index outOfBoundIndex = INDEX_SECOND_MEETUP;
        // ensures that outOfBoundIndex is still in bounds of meetup list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getMeetUpList().getMeetUpList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_MEETUP_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_MEETUP);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_MEETUP);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_MEETUP);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different buyer -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommand deleteCommand = new DeleteCommand(targetIndex);
        String expected = DeleteCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoMeetUp(Model model) {
        model.updateFilteredMeetUpList(m -> false);

        assertTrue(model.getFilteredMeetUpList().isEmpty());
    }
}
