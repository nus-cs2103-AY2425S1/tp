package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
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
    public void execute_successfulinkCommand() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        LinkCommand linkCommand = new LinkCommand(ALICE.getNric().value, BENSON.getNric().value);

        String expectedMessage = String.format(LinkCommand.MESSAGE_SUCCESS, Messages.format(ALICE),
                Messages.format(BENSON));

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person newAlice = new Person(ALICE);
        Person newBenson = new Person(BENSON);
        expectedModel.addLink(newAlice, newBenson);

        assertCommandSuccess(linkCommand, model, expectedMessage, expectedModel);

    }

}
