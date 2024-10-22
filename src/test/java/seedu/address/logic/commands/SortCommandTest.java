package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.NameComparator;

/**
 * Contains integration tests (interaction with the Model) for {@code SortCommand}.
 */
public class SortCommandTest {
    @Test
    public void equals() {

        SortCommand sortFirstCommand = new SortCommand(new NameComparator());
        SortCommand sortSecondCommand = new SortCommand(new NameComparator());

        // same object -> returns true
        assertTrue(sortFirstCommand.equals(sortFirstCommand));

        // different types -> returns false
        assertFalse(sortFirstCommand.equals(1));

        // null -> returns false
        assertFalse(sortFirstCommand.equals(null));

        // same comparator -> returns true
        assertTrue(sortFirstCommand.equals(sortSecondCommand));
    }

}
