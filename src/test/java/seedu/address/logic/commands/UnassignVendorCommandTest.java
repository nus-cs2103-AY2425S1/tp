package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class UnassignVendorCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    // To add: test cases to check for validity
    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        UnassignVendorCommand unassignVendorCommand = new UnassignVendorCommand(outOfBoundIndex);

        assertCommandFailure(unassignVendorCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        UnassignVendorCommand unassignVendorFirstCommand = new UnassignVendorCommand(INDEX_FIRST_PERSON);
        UnassignVendorCommand unassignVendorSecondCommand = new UnassignVendorCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(unassignVendorFirstCommand.equals(unassignVendorFirstCommand));

        // same values -> returns true
        UnassignVendorCommand unassignVendorFirstCommandCopy = new UnassignVendorCommand(INDEX_FIRST_PERSON);
        assertTrue(unassignVendorFirstCommand.equals(unassignVendorFirstCommandCopy));

        // different types -> returns false
        assertFalse(unassignVendorFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unassignVendorFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(unassignVendorFirstCommand.equals(unassignVendorSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        UnassignVendorCommand unassignVendorCommand = new UnassignVendorCommand(targetIndex);
        String expected = UnassignVendorCommand.class.getCanonicalName() + "{toUnassignVendor=" + targetIndex + "}";
        assertEquals(expected, unassignVendorCommand.toString());
    }
}
