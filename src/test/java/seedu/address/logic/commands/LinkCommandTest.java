package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.LinkCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.ALICE_NAME;
import static seedu.address.testutil.TypicalPersons.BENSON_NAME;
import static seedu.address.testutil.TypicalPersons.CARL_NAME;
import static seedu.address.testutil.TypicalPersons.DANIEL_NAME;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.ELLE_NAME;
import static seedu.address.testutil.TypicalPersons.getUnlinkedAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class LinkCommandTest {

    private Model model = new ModelManager(getUnlinkedAddressBook(), new UserPrefs());

    @Test
    public void execute_studentAndParentUnlinked_success() {
        LinkCommand linkFirstCommand = new LinkCommand(ALICE_NAME, ELLE_NAME);

        Model changedModel = new ModelManager(getUnlinkedAddressBook(), new UserPrefs());
        changedModel.setPerson(changedModel.personFromName(ALICE_NAME), ALICE);
        changedModel.setPerson(changedModel.personFromName(ELLE_NAME), ELLE);

        String expectedMessage = String.format(MESSAGE_SUCCESS, ALICE_NAME, ELLE_NAME);

        assertCommandSuccess(linkFirstCommand, model, expectedMessage, changedModel);
    }

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
        LinkCommand linkFirstCommandDifferentStudent = new LinkCommand(CARL_NAME, BENSON_NAME);
        LinkCommand linkFirstCommandDifferentParent = new LinkCommand(ALICE_NAME, DANIEL_NAME);
        assertFalse(linkFirstCommand.equals(linkFirstCommandDifferentStudent));
        assertFalse(linkFirstCommand.equals(linkFirstCommandDifferentParent));

        // null -> returns false
        assertFalse(linkFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(linkFirstCommand.equals(linkSecondCommand));
    }
}
