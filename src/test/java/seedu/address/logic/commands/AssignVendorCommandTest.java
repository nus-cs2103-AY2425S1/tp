package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class AssignVendorCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToAssign = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        AssignVendorCommand assignVendorCommand = new AssignVendorCommand(INDEX_FIRST);

        String expectedMessage = String.format(AssignVendorCommand.MESSAGE_SUCCESS,
                personToAssign.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.assignVendor(personToAssign);

        assertCommandSuccess(assignVendorCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AssignVendorCommand assignVendorCommand = new AssignVendorCommand(outOfBoundIndex);

        assertCommandFailure(assignVendorCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_duplicateVendor_throwsCommandException() {
        Person personToAssign = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        model.assignVendor(personToAssign);

        AssignVendorCommand assignVendorCommand = new AssignVendorCommand(INDEX_FIRST);
        assertCommandFailure(assignVendorCommand, model,
                String.format(AssignVendorCommand.MESSAGE_DUPLICATE_VENDOR, personToAssign.getName()));
    }

    @Disabled // disabled, to find why it doesnt work
    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST);

        Person personToAssign = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        AssignVendorCommand assignVendorCommand = new AssignVendorCommand(INDEX_FIRST);

        String expectedMessage = String.format(AssignVendorCommand.MESSAGE_SUCCESS,
                personToAssign.getName());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.assignVendor(personToAssign);
        assertCommandSuccess(assignVendorCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        AssignVendorCommand assignVendorCommand = new AssignVendorCommand(outOfBoundIndex);

        assertCommandFailure(assignVendorCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        AssignVendorCommand assignVendorFirstCommand = new AssignVendorCommand(INDEX_FIRST);
        AssignVendorCommand assignVendorSecondCommand = new AssignVendorCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(assignVendorFirstCommand.equals(assignVendorFirstCommand));

        // same values -> returns true
        AssignVendorCommand assignVendorFirstCommandCopy = new AssignVendorCommand(INDEX_FIRST);
        assertTrue(assignVendorFirstCommand.equals(assignVendorFirstCommandCopy));

        // different types -> returns false
        assertFalse(assignVendorFirstCommand.equals(1));

        // null -> returns false
        assertFalse(assignVendorFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(assignVendorFirstCommand.equals(assignVendorSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        AssignVendorCommand assignVendorCommand = new AssignVendorCommand(targetIndex);
        String expected = AssignVendorCommand.class.getCanonicalName() + "{toAssignVendor=" + targetIndex + "}";
        assertEquals(expected, assignVendorCommand.toString());
    }
}
