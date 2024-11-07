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
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code VolunteerViewCommand}.
 */
public class VolunteerViewCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Volunteer volunteerToView = model.getFilteredVolunteerList().get(INDEX_FIRST.getZeroBased());
        VolunteerViewCommand volunteerViewCommand = new VolunteerViewCommand(INDEX_FIRST);

        String expectedMessage = String.format(VolunteerViewCommand.MESSAGE_VIEW_VOLUNTEER_SUCCESS,
                volunteerToView.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.viewVolunteer(volunteerToView);

        assertCommandSuccess(volunteerViewCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredVolunteerList().size() + 1);
        VolunteerViewCommand volunteerViewCommand = new VolunteerViewCommand(outOfBoundIndex);

        assertCommandFailure(volunteerViewCommand, model, Messages.MESSAGE_INVALID_VOLUNTEER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        // Set up the filtered list to show only the first volunteer
        showVolunteerAtIndex(model, INDEX_FIRST);

        Volunteer volunteerToView = model.getFilteredVolunteerList().get(INDEX_FIRST.getZeroBased());
        VolunteerViewCommand volunteerViewCommand = new VolunteerViewCommand(INDEX_FIRST);

        String expectedMessage = String.format(VolunteerViewCommand.MESSAGE_VIEW_VOLUNTEER_SUCCESS,
                volunteerToView.getName());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showVolunteerAtIndex(expectedModel, INDEX_FIRST);
        expectedModel.viewVolunteer(volunteerToView);

        assertCommandSuccess(volunteerViewCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showVolunteerAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getVolunteerList().size());
        VolunteerViewCommand volunteerViewCommand = new VolunteerViewCommand(outOfBoundIndex);
        assertCommandFailure(volunteerViewCommand, model, Messages.MESSAGE_INVALID_VOLUNTEER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        VolunteerViewCommand viewFirstVolunteerCommand = new VolunteerViewCommand(INDEX_FIRST);
        VolunteerViewCommand viewSecondVolunteerCommand = new VolunteerViewCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(viewFirstVolunteerCommand.equals(viewFirstVolunteerCommand));

        // same values -> returns true
        VolunteerViewCommand viewFirstVolunteerCommandCopy = new VolunteerViewCommand(INDEX_FIRST);
        assertTrue(viewFirstVolunteerCommand.equals(viewFirstVolunteerCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstVolunteerCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstVolunteerCommand.equals(null));

        // different index -> returns false
        assertFalse(viewFirstVolunteerCommand.equals(viewSecondVolunteerCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        VolunteerViewCommand volunteerViewCommand = new VolunteerViewCommand(targetIndex);
        String expected = VolunteerViewCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, volunteerViewCommand.toString());
    }
}
