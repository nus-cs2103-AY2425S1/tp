package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_UNIQUE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for LinkCommand.
 */

public class LinkCommandTest {

    @Test
    public void execute_unsuccessfulLink() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.deleteLink(ALICE, BENSON);
        assertFalse(model.hasLink(ALICE, BENSON));
    }

    @Test
    public void execute_duplicateLink() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.addLink(ALICE, BENSON);
        assertTrue(model.hasLink(ALICE, BENSON));
        LinkCommand linkCommand = new LinkCommand(ALICE.getNric(), BENSON.getNric());
        assertCommandFailure(linkCommand, model, LinkCommand.MESSAGE_DUPLICATE_LINK);
    }

    @Test
    public void execute_samePerson() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        LinkCommand linkCommand = new LinkCommand(BENSON.getNric(), BENSON.getNric());
        assertCommandFailure(linkCommand, model, LinkCommand.SAME_PERSON);
    }

    @Test
    public void execute_nullPerson() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        LinkCommand linkCommand = new LinkCommand(new Nric(VALID_NRIC_UNIQUE), ALICE.getNric());
        assertCommandFailure(linkCommand, model, LinkCommand.PERSON_NOT_FOUND);
    }

    @Test
    public void execute_invalidRoleCaregiver() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        LinkCommand linkCommand = new LinkCommand(BENSON.getNric(), ALICE.getNric());
        assertCommandFailure(linkCommand, model, LinkCommand.ROLE_NOT_MATCH);
    }

    @Test
    public void execute_invalidRolePatient() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        LinkCommand linkCommand = new LinkCommand(ELLE.getNric(), BENSON.getNric());
        assertCommandFailure(linkCommand, model, LinkCommand.ROLE_NOT_MATCH);
    }

    @Test
    public void execute_successfulinkCommand() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        LinkCommand linkCommand = new LinkCommand(ALICE.getNric(), BENSON.getNric());

        String expectedMessage = String.format(LinkCommand.MESSAGE_SUCCESS, Messages.format(ALICE),
                Messages.format(BENSON));

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person newAlice = new Person(ALICE);
        Person newBenson = new Person(BENSON);
        expectedModel.addLink(newAlice, newBenson);

        assertCommandSuccess(linkCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void equals() {
        LinkCommand linkFirstCommand = new LinkCommand(ALICE.getNric(), BENSON.getNric());
        LinkCommand linkSecondCommand = new LinkCommand(BENSON.getNric(), ALICE.getNric());

        // same object -> returns true
        assertTrue(linkFirstCommand.equals(linkFirstCommand));

        // same values -> returns true
        LinkCommand linkFirstCommandCopy = new LinkCommand(ALICE.getNric(), BENSON.getNric());
        assertTrue(linkFirstCommand.equals(linkFirstCommandCopy));

        // different types -> returns false
        assertFalse(linkFirstCommand.equals(1));

        // null -> returns false
        assertFalse(linkFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(linkFirstCommand.equals(linkSecondCommand));
    }

}
