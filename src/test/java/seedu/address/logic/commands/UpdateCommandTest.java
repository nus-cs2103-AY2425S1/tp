package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEVEL_S1_EXPRESS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_MATH;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.UpdateCommand.UpdatePersonDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.UpdatePersonDescriptorBuilder;
import seedu.address.ui.Ui.UiState;

/**
 * Contains integration tests (interaction with the Model) and unit tests for UpdateCommand.
 */
public class UpdateCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person updatedPerson = model.getAddressBook().getPersonList().get(0);
        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder(updatedPerson).build();
        UpdateCommand updateCommand = new UpdateCommand(updatedPerson.getName(), descriptor);

        String expectedMessage =
                String.format(UpdateCommand.MESSAGE_UPDATE_PERSON_SUCCESS, Messages.format(updatedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        Optional<Person> personToUpdate = model.getAddressBook()
                .getPersonList()
                .stream()
                .filter(person -> person.getName().equals(updatedPerson.getName()))
                .findFirst();

        if (personToUpdate.isPresent()) {
            expectedModel.setPerson(personToUpdate.get(), updatedPerson);
        } else {
            throw new IllegalStateException("Person to update not found");
        }

        assertCommandSuccess(updateCommand, model, expectedMessage, UiState.DETAILS, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person updatedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withLevel(VALID_LEVEL_S1_EXPRESS)
                .withSubjects(VALID_SUBJECT_MATH).build();

        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withLevel(VALID_LEVEL_S1_EXPRESS).withSubjects(VALID_SUBJECT_MATH).build();
        UpdateCommand updateCommand = new UpdateCommand(lastPerson.getName(), descriptor);

        String expectedMessage =
                String.format(UpdateCommand.MESSAGE_UPDATE_PERSON_SUCCESS, Messages.format(updatedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(lastPerson, updatedPerson);

        assertCommandSuccess(updateCommand, model, expectedMessage, UiState.DETAILS, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        Person updatedPerson = model.getFilteredPersonList().get(0);
        UpdateCommand updateCommand = new UpdateCommand(updatedPerson.getName(), new UpdatePersonDescriptor());

        String expectedMessage =
                String.format(UpdateCommand.MESSAGE_UPDATE_PERSON_SUCCESS, Messages.format(updatedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(updateCommand, model, expectedMessage, UiState.DETAILS, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person updatedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
        UpdateCommand updateCommand = new UpdateCommand(personInFilteredList.getName(),
                new UpdatePersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_PERSON_SUCCESS,
                Messages.format(updatedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), updatedPerson);

        assertCommandSuccess(updateCommand, model, expectedMessage, UiState.DETAILS, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder(firstPerson).build();
        UpdateCommand updateCommand = new UpdateCommand(model.getFilteredPersonList().get(1).getName(), descriptor);

        assertCommandFailure(updateCommand, model, UpdateCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // update person in filtered list into a duplicate in address book
        Person personInList = model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        UpdateCommand updateCommand = new UpdateCommand(model.getFilteredPersonList().get(0).getName(),
                new UpdatePersonDescriptorBuilder(personInList).build());

        assertCommandFailure(updateCommand, model, UpdateCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Name invalidName = new Name("Skibidi");
        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        UpdateCommand updateCommand = new UpdateCommand(invalidName, descriptor);

        assertCommandFailure(updateCommand, model, Messages.MESSAGE_INVALID_PERSON_UPDATE);
    }

    /**
     * Update filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Name invalidName = new Name("Skibidi");

        UpdateCommand updateCommand = new UpdateCommand(invalidName,
                new UpdatePersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(updateCommand, model, Messages.MESSAGE_INVALID_PERSON_UPDATE);
    }

    @Test
    public void equals() {
        final UpdateCommand standardCommand = new UpdateCommand(new Name(VALID_NAME_AMY), DESC_AMY);

        // same values -> returns true
        UpdatePersonDescriptor copyDescriptor = new UpdatePersonDescriptor(DESC_AMY);
        UpdateCommand commandWithSameValues = new UpdateCommand(new Name(VALID_NAME_AMY), copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different names -> returns false
        assertFalse(standardCommand.equals(new UpdateCommand(new Name(VALID_NAME_BOB), DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new UpdateCommand(new Name(VALID_NAME_AMY), DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Name name = new Name("Test name");
        UpdatePersonDescriptor updatePersonDescriptor = new UpdatePersonDescriptor();
        UpdateCommand updateCommand = new UpdateCommand(name, updatePersonDescriptor);
        String expected = UpdateCommand.class.getCanonicalName() + "{name=" + name + ", updatePersonDescriptor="
                + updatePersonDescriptor + "}";
        assertEquals(expected, updateCommand.toString());
    }

}
