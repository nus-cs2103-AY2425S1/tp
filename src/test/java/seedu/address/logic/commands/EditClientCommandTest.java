package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISSUE_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_RACHEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookAllWithCars;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditClientCommand.EditCarDescriptor;
import seedu.address.logic.commands.EditClientCommand.EditPersonDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.issue.Issue;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditClientCommand.
 */
public class EditClientCommandTest {

    private Model model = new ModelManager(getTypicalAddressBookAllWithCars(), new UserPrefs());
    private Model modelWithoutCar = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person editedPerson = new PersonBuilder().build();
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditCarDescriptor editCarDescriptor = new EditCarDescriptor();

        EditClientCommand editClientCommand = new EditClientCommand(INDEX_FIRST_PERSON,
            editPersonDescriptor, editCarDescriptor,
            true, false);

        String expectedMessage = Messages.formatSuccessMessage(editedPerson,
                EditClientCommand.MESSAGE_EDIT_CLIENT_SUCCESS);

        Model expectedModel = new ModelManager(new AddressBook(modelWithoutCar.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(modelWithoutCar.getFilteredPersonList().get(0), editedPerson);
        assertCommandSuccess(editClientCommand, modelWithoutCar, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person editedPerson = personInList.withName(VALID_NAME_RACHEL).withPhone(VALID_PHONE_BOB)
                .withIssues(VALID_ISSUE_HUSBAND).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_RACHEL)
                .withPhone(VALID_PHONE_BOB).withIssues(VALID_ISSUE_HUSBAND).build();
        EditClientCommand editClientCommand = new EditClientCommand(
                indexLastPerson, descriptor, new EditCarDescriptor(), true, false);

        String expectedMessage = Messages.formatSuccessMessage(editedPerson,
                EditClientCommand.MESSAGE_EDIT_CLIENT_SUCCESS);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(lastPerson, editedPerson);

        assertCommandSuccess(editClientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditClientCommand editClientCommand = new EditClientCommand(INDEX_FIRST_PERSON,
            new EditPersonDescriptor(),
            new EditCarDescriptor(),
            false, false);
        Person editedPerson = model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = Messages.formatSuccessMessage(editedPerson,
                EditClientCommand.MESSAGE_EDIT_CLIENT_SUCCESS);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editClientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_RACHEL).build();
        EditClientCommand editClientCommand = new EditClientCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_RACHEL).build(),
                new EditCarDescriptor(), true, false);

        String expectedMessage = Messages.formatSuccessMessage(editedPerson,
                EditClientCommand.MESSAGE_EDIT_CLIENT_SUCCESS);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);
        System.out.println("Expected model: " + expectedModel.getFilteredPersonList().get(0));
        assertCommandSuccess(editClientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstPerson).build();
        EditClientCommand editClientCommand = new EditClientCommand(
                INDEX_FIRST_PERSON, descriptor, new EditCarDescriptor(), true, false);
        try {
            assertCommandFailure(editClientCommand, model, EditClientCommand.MESSAGE_DUPLICATE_PERSON);
        } catch (AssertionError e) {
            System.out.println("Test failed with error: " + e.getMessage());
            throw e; // rethrow the exception to ensure the test still fails
        }
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit person in filtered list into a duplicate in address book
        Person personInList = model.getAddressBook().getPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditClientCommand editClientCommand = new EditClientCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder(personInList).build(), new EditCarDescriptor(), true, false);

        assertCommandFailure(editClientCommand, model, EditClientCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditClientCommand editClientCommand = new EditClientCommand(
                outOfBoundIndex, descriptor, new EditCarDescriptor(), true, false);

        assertCommandFailure(editClientCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        EditClientCommand editClientCommand = new EditClientCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder()
                        .withName(VALID_NAME_BOB).build(), new EditCarDescriptor(), true, false);

        assertCommandFailure(editClientCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit a person without a car should throw an error
     *
     */
    @Test
    public void execute_carDoesNotExist_failure() {
        EditClientCommand editClientCommand = new EditClientCommand(INDEX_FIRST_PERSON,
            new EditPersonDescriptor(), new EditCarDescriptor(), true, true);
        assertCommandFailure(editClientCommand, modelWithoutCar, EditClientCommand.MESSAGE_CAR_DOES_NOT_EXIST);
    }

    @Test
    public void execute_editIssueWithNoCar_failure() {
        Set<Issue> issues = new HashSet<>();
        issues.add(new Issue("lol"));
        EditPersonDescriptor descriptor = new EditPersonDescriptor();
        descriptor.setIssues(issues);
        EditClientCommand editClientCommand = new EditClientCommand(INDEX_FIRST_PERSON,
            descriptor, new EditCarDescriptor(), true, false);
        assertCommandFailure(editClientCommand, modelWithoutCar, EditClientCommand.MESSAGE_NO_CAR_TO_EDIT_ISSUES);
    }

    @Test
    public void execute_carDoesNotExistFilteredList_failure() {
        showPersonAtIndex(modelWithoutCar, INDEX_FIRST_PERSON);
        EditClientCommand editClientCommand = new EditClientCommand(INDEX_FIRST_PERSON,
            new EditPersonDescriptor(), new EditCarDescriptor(), true, true);
        assertCommandFailure(editClientCommand, modelWithoutCar, EditClientCommand.MESSAGE_CAR_DOES_NOT_EXIST);
    }

    @Test
    public void equals() {
        final EditClientCommand standardCommand = new EditClientCommand(INDEX_FIRST_PERSON,
            DESC_AMY,
            new EditCarDescriptor(),
            true, false);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditClientCommand commandWithSameValues = new EditClientCommand(INDEX_FIRST_PERSON,
            copyDescriptor, new EditCarDescriptor(),
            true, false);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditClientCommand(INDEX_SECOND_PERSON,
            DESC_AMY, new EditCarDescriptor(),
            true, false)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditClientCommand(INDEX_FIRST_PERSON,
            DESC_BOB, new EditCarDescriptor(),
            true, false)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        EditClientCommand editClientCommand = new EditClientCommand(
                index, editPersonDescriptor, new EditCarDescriptor(), true, false);
        String expected = EditClientCommand.class.getCanonicalName()
                + "{index=" + index + ", editPersonDescriptor=" + editPersonDescriptor + "}";
        assertEquals(expected, editClientCommand.toString());
    }

}
