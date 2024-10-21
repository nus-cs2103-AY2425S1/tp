package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_UNIQUE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

public class DeleteLinkCommandTest {

    @Test
    public void execute_successfulDeleteLink() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.addLink(ALICE, BENSON);
        assertTrue(model.hasLink(ALICE, BENSON));
        model.deleteLink(ALICE, BENSON);
        assertFalse(model.hasLink(ALICE, BENSON));
    }

    @Test
    public void execute_nolinktodelete() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        DeleteLinkCommand deleteLinkCommand = new DeleteLinkCommand(ALICE.getNric(), BENSON.getNric());
        assertCommandFailure(deleteLinkCommand, model, DeleteLinkCommand.MESSAGE_NO_LINK);
    }

    @Test
    public void execute_successfulDeleteCommandLink() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.addLink(ALICE, BENSON);
        assertTrue(model.hasLink(ALICE, BENSON));
        DeleteLinkCommand deleteLinkCommand = new DeleteLinkCommand(ALICE.getNric(), BENSON.getNric());
        String expectedMessage = String.format(DeleteLinkCommand.MESSAGE_DELETE_LINK_SUCCESS,
                Messages.format(ALICE), Messages.format(BENSON));
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person newAlice = new Person(ALICE);
        Person newBenson = new Person(BENSON);
        expectedModel.addLink(newAlice, newBenson);
        assertTrue(expectedModel.hasLink(newAlice, newBenson));
        expectedModel.deleteLink(newAlice, newBenson);
        assertCommandSuccess(deleteLinkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nullPerson() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        DeleteLinkCommand linkCommand = new DeleteLinkCommand(new Nric(VALID_NRIC_UNIQUE), ALICE.getNric());
        assertCommandFailure(linkCommand, model, DeleteLinkCommand.PERSON_NOT_FOUND);
    }

    @Test
    public void equals() {
        DeleteLinkCommand deleteFirstCommand = new DeleteLinkCommand(ALICE.getNric(), BENSON.getNric());
        DeleteLinkCommand deleteSecondCommand = new DeleteLinkCommand(BENSON.getNric(), ALICE.getNric());

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteLinkCommand deleteFirstCommandCopy = new DeleteLinkCommand(ALICE.getNric(), BENSON.getNric());
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

}
