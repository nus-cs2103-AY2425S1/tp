package seedu.address.logic.commands.vendor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class UnassignVendorCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        UnassignVendorCommand unassignVendorCommand = new UnassignVendorCommand(outOfBoundIndex);

        assertCommandFailure(unassignVendorCommand, model,
                String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                        1, model.getFilteredPersonList().size()));
    }

    @Test
    public void equals() {
        UnassignVendorCommand unassignVendorFirstCommand = new UnassignVendorCommand(INDEX_FIRST);
        UnassignVendorCommand unassignVendorSecondCommand = new UnassignVendorCommand(INDEX_SECOND);

        // same object -> returns true
        assertEquals(unassignVendorFirstCommand, unassignVendorFirstCommand);

        // same values -> returns true
        UnassignVendorCommand unassignVendorFirstCommandCopy = new UnassignVendorCommand(INDEX_FIRST);
        assertEquals(unassignVendorFirstCommand, unassignVendorFirstCommandCopy);

        // null -> returns false
        assertNotEquals(null, unassignVendorFirstCommand);

        // different person -> returns false
        assertNotEquals(unassignVendorFirstCommand, unassignVendorSecondCommand);
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        UnassignVendorCommand unassignVendorCommand = new UnassignVendorCommand(targetIndex);
        String expected = UnassignVendorCommand.class.getCanonicalName() + "{toUnassignVendor=" + targetIndex + "}";
        assertEquals(expected, unassignVendorCommand.toString());
    }
}
