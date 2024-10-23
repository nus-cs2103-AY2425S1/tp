package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.PostalCode;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeletePostalCommand}.
 */
public class DeletePostalCodeCommandTest {

    private PostalCode postalCodeToDelete = new PostalCode("123456");

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void execute_validPostalCode_success() {
        DeletePostalCodeCommand deletePostalCodeCommand = new DeletePostalCodeCommand(postalCodeToDelete);

        List<Person> customersToDelete = model.getPeopleByPostalCode(postalCodeToDelete);
        String expectedMessage = String.format(DeletePostalCodeCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                postalCodeToDelete,
                customersToDelete.stream().map(person ->
                        person.getName().toString()).collect(Collectors.joining(", ")));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        for (Person person : customersToDelete) {
            expectedModel.deletePerson(person);
        }

        assertCommandSuccess(deletePostalCodeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validPostalCode_noPersonFound() {
        PostalCode validPostalCode = new PostalCode("999999");
        DeletePostalCodeCommand deletePostalCommand = new DeletePostalCodeCommand(validPostalCode);

        assertCommandFailure(deletePostalCommand, model, Messages.MESSAGE_NO_MATCH);
    }
    @Test
    public void equals() {
        PostalCode first = new PostalCode("123456");
        PostalCode second = new PostalCode("654321");
        DeletePostalCodeCommand deleteFirstCommand = new DeletePostalCodeCommand(first);
        DeletePostalCodeCommand deleteSecondCommand = new DeletePostalCodeCommand(second);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeletePostalCodeCommand deleteFirstCommandCopy = new DeletePostalCodeCommand(first);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different postal codes -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

}
