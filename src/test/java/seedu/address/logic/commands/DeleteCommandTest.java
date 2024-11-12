package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_OUT_OF_BOUNDS;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {


    @Test
    public void execute_validIndexUnfilteredList_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        try {
            Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

            NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate("Carl");
            model.updateFilteredPersonList(predicate);

            Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
            DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
            CommandResult commandResult = deleteCommand.execute(model);

            String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                    Messages.format(personToDelete));

            assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        } catch (CommandException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate("Carl");
        model.updateFilteredPersonList(predicate);
        Index outOfBoundIndex = INDEX_OUT_OF_BOUNDS;
        assertTrue(outOfBoundIndex.getZeroBased() > model.getAddressBook().getPersonList().size());
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);
        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validPhoneNumber_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person personToDelete = ALICE;
        Phone phone = personToDelete.getPhone();
        DeleteCommand deleteCommand = new DeleteCommand(phone);
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPhoneNumber_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Phone invalidPhone = new Phone("99999999");
        DeleteCommand deleteCommand = new DeleteCommand(invalidPhone);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PHONE_NUMBER);
    }

    @Test
    public void execute_validAddress_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person personToDelete = ALICE;
        Address address = personToDelete.getAddress();
        DeleteCommand deleteCommand = new DeleteCommand(address);
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        assertTrue(model.equals(expectedModel));
        expectedModel.deletePerson(personToDelete);
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidAddress_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Address address = new Address("Invalid addresss");
        DeleteCommand deleteCommand = new DeleteCommand(address);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_ADDRESS);
    }

    @Test
    public void execute_validEmail_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person personToDelete = ALICE;
        Email email = personToDelete.getEmail();
        DeleteCommand deleteCommand = new DeleteCommand(email);
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidEmail_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Email email = new Email("invalidemail@example.com");
        DeleteCommand deleteCommand = new DeleteCommand(email);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_EMAIL);
    }

    @Test
    public void execute_invalidTags_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Set<Tag> invalidTags = new HashSet<>();
        Tag invalidTag = new Tag("InvalidTag");
        invalidTags.add(invalidTag);
        DeleteCommand deleteCommand = new DeleteCommand(invalidTags);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_TAGS);
    }


    @Test
    public void execute_validPredicate_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate("Meier");
        DeleteCommand deleteCommand = new DeleteCommand(predicate);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(predicate);
        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommand deleteCommand = new DeleteCommand(targetIndex);
        String expected = DeleteCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
