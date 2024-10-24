package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code UnmarkAttendanceCommand}.
 */
public class UnmarkAttendanceCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToUnmark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnmarkAttendanceCommand unmarkAttendanceCommand = new UnmarkAttendanceCommand(INDEX_FIRST_PERSON);

        String expectedMessage = UnmarkAttendanceCommand.MESSAGE_SUCCESS;

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.unmarkAttendance(personToUnmark);

        assertCommandSuccess(unmarkAttendanceCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnmarkAttendanceCommand unmarkAttendanceCommand = new UnmarkAttendanceCommand(outOfBoundIndex);

        assertCommandFailure(unmarkAttendanceCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Person personToUnmark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnmarkAttendanceCommand unmarkAttendanceCommand = new UnmarkAttendanceCommand(INDEX_FIRST_PERSON);
        String expectedMessage = UnmarkAttendanceCommand.MESSAGE_SUCCESS;
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(p -> p.equals(personToUnmark));
        expectedModel.unmarkAttendance(personToUnmark);
        assertCommandSuccess(unmarkAttendanceCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        UnmarkAttendanceCommand unmarkAttendanceCommand = new UnmarkAttendanceCommand(outOfBoundIndex);

        assertCommandFailure(unmarkAttendanceCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        UnmarkAttendanceCommand unmarkFirstCommand = new UnmarkAttendanceCommand(INDEX_FIRST_PERSON);
        UnmarkAttendanceCommand unmarkSecondCommand = new UnmarkAttendanceCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(unmarkFirstCommand.equals(unmarkFirstCommand));

        // same values -> returns true
        UnmarkAttendanceCommand unmarkFirstCommandCopy = new UnmarkAttendanceCommand(INDEX_FIRST_PERSON);
        assertTrue(unmarkFirstCommand.equals(unmarkFirstCommandCopy));

        // different types -> returns false
        assertFalse(unmarkFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unmarkFirstCommand.equals(null));

        // different index -> returns false
        assertFalse(unmarkFirstCommand.equals(unmarkSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        UnmarkAttendanceCommand unmarkCommand = new UnmarkAttendanceCommand(targetIndex);
        String expected = UnmarkAttendanceCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, unmarkCommand.toString());
    }



    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code index} in the {@code model}'s
     * address book.
     */
    private void showPersonAtIndex(Model model, Index index) {
        assertTrue(index.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(index.getZeroBased());
        model.updateFilteredPersonList(p -> p.equals(person));

        assertEquals(1, model.getFilteredPersonList().size());
    }
}
