package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;


/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validNameUnfilteredList_success() {
        Person personToDelete = ALICE;
        DeleteCommand deleteCommand = new DeleteCommand(Optional.of(ALICE.getName().toString()),
                Optional.empty(), Optional.empty());

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNameUnfilteredList_throwsCommandException() {
        DeleteCommand deleteCommand = new DeleteCommand(Optional.of("Invalid Name"),
                Optional.empty(), Optional.empty());

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DELETED);
    }

    @Test
    public void execute_validPhoneUnfilteredList_success() {
        // Delete person by valid phone number (BENSON)
        Person personToDelete = BENSON;
        DeleteCommand deleteCommand = new DeleteCommand(Optional.empty(), Optional.of(BENSON.getPhone().toString()),
                Optional.empty());

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPhoneUnfilteredList_throwsCommandException() {
        // Try deleting a person by a phone number that doesn't exist
        DeleteCommand deleteCommand = new DeleteCommand(Optional.empty(), Optional.of("99999999"), Optional.empty());

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DELETED);
    }

    @Test
    public void execute_validEmailUnfilteredList_success() {
        // Delete person by valid email (ALICE)
        Person personToDelete = ALICE;
        DeleteCommand deleteCommand = new DeleteCommand(Optional.empty(), Optional.empty(),
                Optional.of(ALICE.getEmail().toString()));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidEmailUnfilteredList_throwsCommandException() {
        // Try deleting a person by an email that doesn't exist
        DeleteCommand deleteCommand = new DeleteCommand(Optional.empty(), Optional.empty(),
                Optional.of("nonexistent@example.com"));

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DELETED);
    }

    @Test
    public void equals_differentPhone_returnsFalse() {
        // Create two DeleteCommand objects with different phone values
        DeleteCommand deleteCommand1 = new DeleteCommand(
                Optional.of("Alice Pauline"),
                Optional.of("94351253"),
                Optional.of("alice@example.com")
        );

        DeleteCommand deleteCommand2 = new DeleteCommand(
                Optional.of("Alice Pauline"),
                Optional.of("98765432"),
                Optional.of("alice@example.com")
        );

        // Assert that they are not equal
        assertFalse(deleteCommand1.equals(deleteCommand2));
    }

    @Test
    public void equals_differentEmail_returnsFalse() {
        // Create two DeleteCommand objects with different email values
        DeleteCommand deleteCommand1 = new DeleteCommand(
                Optional.of("Alice Pauline"),
                Optional.of("94351253"),
                Optional.of("alice@example.com")
        );

        DeleteCommand deleteCommand2 = new DeleteCommand(
                Optional.of("Alice Pauline"),
                Optional.of("94351253"),
                Optional.of("bob@example.com")
        );

        // Assert that they are not equal
        assertFalse(deleteCommand1.equals(deleteCommand2));
    }

    @Test
    public void equals_samePhoneAndEmail_returnsTrue() {
        // Create two DeleteCommand objects with identical values
        DeleteCommand deleteCommand1 = new DeleteCommand(
                Optional.of("Alice Pauline"),
                Optional.of("94351253"),
                Optional.of("alice@example.com")
        );

        DeleteCommand deleteCommand2 = new DeleteCommand(
                Optional.of("Alice Pauline"),
                Optional.of("94351253"),
                Optional.of("alice@example.com")
        );

        // Assert that they are equal
        assertTrue(deleteCommand1.equals(deleteCommand2));
    }

    @Test
    public void equals_bothPhoneAndEmailEmpty_returnsTrue() {
        // Create two DeleteCommand objects with no phone and email
        DeleteCommand deleteCommand1 = new DeleteCommand(
                Optional.of("Alice Pauline"),
                Optional.empty(),
                Optional.empty()
        );

        DeleteCommand deleteCommand2 = new DeleteCommand(
                Optional.of("Alice Pauline"),
                Optional.empty(),
                Optional.empty()
        );

        // Assert that they are equal
        assertTrue(deleteCommand1.equals(deleteCommand2));
    }

    @Test
    public void execute_multipleMatchingPersons_throwsCommandException() {
        // Both ALICE and BENSON have names that match "Alice" (assuming case-insensitive match)
        DeleteCommand deleteCommand = new DeleteCommand(Optional.empty(), Optional.of("94351253"), Optional.empty());

        // Expect CommandException with MESSAGE_MULTIPLE_PERSONS_FOUND
        assertCommandFailure(deleteCommand, model, DeleteCommand.MESSAGE_MULTIPLE_PERSONS_FOUND);
    }

    @Test
    public void equals() {
        DeleteCommand deleteByNameCommand = new DeleteCommand(Optional.of(ALICE.getName().toString()),
                Optional.empty(), Optional.empty());
        DeleteCommand deleteByPhoneCommand = new DeleteCommand(Optional.empty(),
                Optional.of(BENSON.getPhone().toString()), Optional.empty());

        // same object -> returns true
        assertTrue(deleteByNameCommand.equals(deleteByNameCommand));

        // same values -> returns true
        DeleteCommand deleteByNameCommandCopy = new DeleteCommand(Optional.of(ALICE.getName().toString()),
                Optional.empty(), Optional.empty());
        assertTrue(deleteByNameCommand.equals(deleteByNameCommandCopy));

        // different types -> returns false
        assertFalse(deleteByNameCommand.equals(1));

        // null -> returns false
        assertFalse(deleteByNameCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteByNameCommand.equals(deleteByPhoneCommand));
    }

    @Test
    public void toString_validCommand() {
        DeleteCommand deleteCommand = new DeleteCommand(
                Optional.of("Alice Pauline"),
                Optional.of("94351253"),
                Optional.of("alice@example.com")
        );

        // Expected output
        String expectedString = "seedu.address.logic.commands.DeleteCommand{name=Optional[Alice Pauline],"
                + " phone=Optional[94351253], email=Optional[alice@example.com]}";

        // Assert that the toString output matches the expected string
        assertEquals(expectedString, deleteCommand.toString());
    }


    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
