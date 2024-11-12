package seedu.address.logic.commands.volunteercommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showVolunteerAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalVolunteers.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.volunteer.Volunteer;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code VolunteerDeleteCommand}.
 */
public class VolunteerDeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Volunteer volunteerToDelete = model.getFilteredVolunteerList().get(INDEX_FIRST.getZeroBased());
        VolunteerDeleteCommand volunteerDeleteCommand = new VolunteerDeleteCommand(INDEX_FIRST);

        String expectedMessage = String.format(VolunteerDeleteCommand.MESSAGE_DELETE_VOLUNTEER_SUCCESS,
                Messages.format(volunteerToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteVolunteer(volunteerToDelete);

        assertCommandSuccess(volunteerDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredVolunteerList().size() + 1);
        VolunteerDeleteCommand volunteerDeleteCommand = new VolunteerDeleteCommand(outOfBoundIndex);

        assertCommandFailure(volunteerDeleteCommand, model, Messages.MESSAGE_INVALID_VOLUNTEER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        // Set up filtered list to show only the first volunteer
        showVolunteerAtIndex(model, INDEX_FIRST);

        Volunteer volunteerToDelete = model.getFilteredVolunteerList().get(INDEX_FIRST.getZeroBased());
        VolunteerDeleteCommand volunteerDeleteCommand = new VolunteerDeleteCommand(INDEX_FIRST);

        String expectedMessage = String.format(VolunteerDeleteCommand.MESSAGE_DELETE_VOLUNTEER_SUCCESS,
                Messages.format(volunteerToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteVolunteer(volunteerToDelete);
        showNoVolunteer(expectedModel);

        assertCommandSuccess(volunteerDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showVolunteerAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;

        // Ensure that outOfBoundIndex is within the bounds of the full address book, but outside the filtered list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getVolunteerList().size());

        VolunteerDeleteCommand volunteerDeleteCommand = new VolunteerDeleteCommand(outOfBoundIndex);

        assertCommandFailure(volunteerDeleteCommand, model, Messages.MESSAGE_INVALID_VOLUNTEER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        VolunteerDeleteCommand deleteFirstCommand = new VolunteerDeleteCommand(INDEX_FIRST);
        VolunteerDeleteCommand deleteSecondCommand = new VolunteerDeleteCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        VolunteerDeleteCommand deleteFirstCommandCopy = new VolunteerDeleteCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different index -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        VolunteerDeleteCommand volunteerDeleteCommand = new VolunteerDeleteCommand(targetIndex);
        String expected = VolunteerDeleteCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, volunteerDeleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no volunteers.
     */
    private void showNoVolunteer(Model model) {
        model.updateFilteredVolunteerList(v -> false);
        assertTrue(model.getFilteredVolunteerList().isEmpty());
    }
}
