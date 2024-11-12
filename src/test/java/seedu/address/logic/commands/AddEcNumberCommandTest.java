package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ECNUMBER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ECNUMBER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ECNUMBER_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.EcNumber;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AddEcNumberCommand.
 */
public class AddEcNumberCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addEcNumberUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withEcNumber(VALID_ECNUMBER_AMY).build();

        AddEcNumberCommand addEcNumberCommand = new AddEcNumberCommand(INDEX_FIRST_PERSON,
                new EcNumber(editedPerson.getEcNumber().value));

        String expectedMessage = String.format(AddEcNumberCommand.MESSAGE_ADD_ECNUMBER_SUCCESS,
                editedPerson.getName(), editedPerson.getEcNumber());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        // EP: list is unfiltered when adding EcNumber successfully
        assertCommandSuccess(addEcNumberCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteEcNumberUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withEcNumber(VALID_ECNUMBER_EMPTY).build();

        AddEcNumberCommand addEcNumberCommand = new AddEcNumberCommand(INDEX_FIRST_PERSON,
                new EcNumber(editedPerson.getEcNumber().toString()));

        String expectedMessage = String.format(AddEcNumberCommand.MESSAGE_DELETE_ECNUMBER_SUCCESS,
                editedPerson.getName(), editedPerson.getEcNumber());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        // EP: list is unfiltered when deleting EcNumber successfully
        assertCommandSuccess(addEcNumberCommand, model, expectedMessage, expectedModel); // Boundary value
    }

    @Test
    public void execute_addEcNumberFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withEcNumber(VALID_ECNUMBER_AMY).build();

        AddEcNumberCommand addEcNumberCommand = new AddEcNumberCommand(INDEX_FIRST_PERSON,
                new EcNumber(editedPerson.getEcNumber().value));

        String expectedMessage = String.format(AddEcNumberCommand.MESSAGE_ADD_ECNUMBER_SUCCESS,
                editedPerson.getName(), editedPerson.getEcNumber());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        // EP: list is filtered when adding EcNumber successfully
        assertCommandSuccess(addEcNumberCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteEcNumberFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withEcNumber(VALID_ECNUMBER_EMPTY).build();

        AddEcNumberCommand addEcNumberCommand = new AddEcNumberCommand(INDEX_FIRST_PERSON,
                new EcNumber(editedPerson.getEcNumber().value));

        String expectedMessage = String.format(AddEcNumberCommand.MESSAGE_DELETE_ECNUMBER_SUCCESS,
                editedPerson.getName(), editedPerson.getEcNumber());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        // EP: list is filtered when deleting EcNumber successfully
        assertCommandSuccess(addEcNumberCommand, model, expectedMessage, expectedModel); // Boundary value
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddEcNumberCommand addEcNumberCommand = new AddEcNumberCommand(outOfBoundIndex,
                new EcNumber(VALID_ECNUMBER_BOB));

        // EP: invalid index
        assertCommandFailure(addEcNumberCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edits filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        AddEcNumberCommand addEcNumberCommand = new AddEcNumberCommand(outOfBoundIndex,
                new EcNumber(VALID_ECNUMBER_BOB));

        // EP: invalid index, boundary value: index is beyond filtered list but within address book
        assertCommandFailure(addEcNumberCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final AddEcNumberCommand standardCommand = new AddEcNumberCommand(
                INDEX_FIRST_PERSON, new EcNumber(VALID_ECNUMBER_AMY));

        // EP: same values -> returns true
        AddEcNumberCommand commandWithSameValues = new AddEcNumberCommand(
                INDEX_FIRST_PERSON, new EcNumber(VALID_ECNUMBER_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // EP: same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // EP: null -> returns false
        assertFalse(standardCommand.equals(null));

        // EP: different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // EP: different index -> returns false
        assertFalse(standardCommand.equals(new AddEcNumberCommand(
                INDEX_SECOND_PERSON, new EcNumber(VALID_ECNUMBER_AMY))));

        // EP: different EcNumber -> returns false
        assertFalse(standardCommand.equals(new AddEcNumberCommand(
                INDEX_FIRST_PERSON, new EcNumber(VALID_ECNUMBER_BOB))));

    }
}
