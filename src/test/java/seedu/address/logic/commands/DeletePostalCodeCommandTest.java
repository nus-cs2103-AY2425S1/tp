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

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeletePostalCommand}.
 */
public class DeletePostalCodeCommandTest {

    private String postalCodeToDelete = "123456";

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
        String validPostalCode = "999999";
        DeletePostalCodeCommand deletePostalCommand = new DeletePostalCodeCommand(validPostalCode);

        assertCommandFailure(deletePostalCommand, model, Messages.MESSAGE_NO_MATCH);
    }
    @Test
    public void execute_invalidPostalCode_throwsCommandException() {
        String invalidPostalCode = "12345";
        DeletePostalCodeCommand deleteCommand = new DeletePostalCodeCommand(invalidPostalCode);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_POSTAL);
    }
    @Test
    public void equals() {
        DeletePostalCodeCommand deleteFirstCommand = new DeletePostalCodeCommand("123456");
        DeletePostalCodeCommand deleteSecondCommand = new DeletePostalCodeCommand("654321");

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeletePostalCodeCommand deleteFirstCommandCopy = new DeletePostalCodeCommand("123456");
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different postal codes -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

}
