package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BOB;
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
import seedu.address.model.person.Date;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;




/**
 * Contains integration tests (interaction with the Model) and unit tests for DateCommand.
 */
public class DateCommandTest {
    private static final String DATE_STUB = "Some date";
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addDateUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withDate(DATE_STUB).build();
        DateCommand dateCommand = new DateCommand(INDEX_FIRST_PERSON, new Date(editedPerson.getDate().value));
        String expectedMessage = String.format(DateCommand.MESSAGE_ADD_DATE_SUCCESS, editedPerson);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);
        assertCommandSuccess(dateCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_deleteDateUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withDate("").build();
        DateCommand dateCommand = new DateCommand(INDEX_FIRST_PERSON,
                new Date(editedPerson.getDate().toString()));
        String expectedMessage = String.format(DateCommand.MESSAGE_DELETE_DATE_SUCCESS, editedPerson);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);
        assertCommandSuccess(dateCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withDate(DATE_STUB).build();
        DateCommand dateCommand = new DateCommand(INDEX_FIRST_PERSON, new Date(editedPerson.getDate().value));
        String expectedMessage = String.format(DateCommand.MESSAGE_ADD_DATE_SUCCESS, editedPerson);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);
        assertCommandSuccess(dateCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DateCommand dateCommand = new DateCommand(outOfBoundIndex, new Date(VALID_DATE_BOB));
        assertCommandFailure(dateCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
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
        DateCommand dateCommand = new DateCommand(outOfBoundIndex, new Date(VALID_DATE_BOB));

        assertCommandFailure(dateCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final DateCommand standardCommand = new DateCommand(INDEX_FIRST_PERSON, new Date(VALID_DATE_AMY));
        // same values -> returns true
        DateCommand commandWithSameValues = new DateCommand(INDEX_FIRST_PERSON, new Date(VALID_DATE_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        // different index -> returns false
        assertFalse(standardCommand.equals(new DateCommand(INDEX_SECOND_PERSON, new Date(VALID_DATE_AMY))));
        // different date -> returns false
        assertFalse(standardCommand.equals(new DateCommand(INDEX_FIRST_PERSON, new Date(VALID_DATE_BOB))));
    }
}
