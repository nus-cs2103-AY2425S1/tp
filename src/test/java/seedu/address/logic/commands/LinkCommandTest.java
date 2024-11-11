package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE_NAME;
import static seedu.address.testutil.TypicalPersons.BENSON_NAME;
import static seedu.address.testutil.TypicalPersons.CARL_NAME;
import static seedu.address.testutil.TypicalPersons.DANIEL_NAME;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class LinkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        LinkCommand linkFirstCommand = new LinkCommand(ALICE_NAME, BENSON_NAME);
        LinkCommand linkSecondCommand = new LinkCommand(CARL_NAME, DANIEL_NAME);

        // same object -> returns true
        assertTrue(linkFirstCommand.equals(linkFirstCommand));

        // same values -> returns true
        LinkCommand linkFirstCommandCopy = new LinkCommand(ALICE_NAME, BENSON_NAME);
        assertTrue(linkFirstCommand.equals(linkFirstCommandCopy));

        // different types -> returns false
        assertFalse(linkFirstCommand.equals(1));

        // null -> returns false
        assertFalse(linkFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(linkFirstCommand.equals(linkSecondCommand));
    }
}
