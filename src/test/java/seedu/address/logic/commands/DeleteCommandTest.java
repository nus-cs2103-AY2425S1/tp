package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model getDefaultModel() {
        return new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_existingName_success() {
        for (Person person : getDefaultModel().getPersonList()) {
            Name name = person.getName();
            DeleteCommand cmd = new DeleteCommand(name);
            Model expectedModel = getDefaultModel();
            expectedModel.deletePerson(person);
            String expectedMessage = String.format("Deleted Person: %s", person.getName());
            assertCommandSuccess(cmd, getDefaultModel(), expectedMessage, expectedModel);
        }
    }

    @Test
    public void execute_nonExistingName_throwsCommandException() {
        Name name = new Name("Apple");
        DeleteCommand cmd = new DeleteCommand(name);
        assertCommandFailure(cmd, getDefaultModel(), Messages.MESSAGE_PERSON_NOT_FOUND);
    }
}
