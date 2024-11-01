package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.jupiter.api.Test;
public class InspectCommandTest {
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
