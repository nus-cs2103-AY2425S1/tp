package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTES_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTES_BOB;
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
import seedu.address.model.person.Notes;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;
/**
 * Contains integration tests (interaction with the Model) and unit tests for AddNotesCommand.
 */
public class AddNotesCommandTest {
    private static final String NOTES_STUB = "Some notes";
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addNotesUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withNotes(NOTES_STUB).build();
        AddNotesCommand notesCommand = new AddNotesCommand(INDEX_FIRST_PERSON,
                new Notes(editedPerson.getNotes().value));
        String expectedMessage = String.format(notesCommand.generateSuccessMessage(editedPerson));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);
        assertCommandSuccess(notesCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_deleteNotesUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withNotes("").build();
        AddNotesCommand notesCommand = new AddNotesCommand(INDEX_FIRST_PERSON,
                new Notes(editedPerson.getNotes().toString()));
        String expectedMessage = String.format(notesCommand.generateSuccessMessage(editedPerson));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);
        assertCommandSuccess(notesCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withNotes(NOTES_STUB).build();
        AddNotesCommand notesCommand = new AddNotesCommand(INDEX_FIRST_PERSON,
                new Notes(editedPerson.getNotes().value));
        String expectedMessage = String.format(notesCommand.generateSuccessMessage(editedPerson));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);
        assertCommandSuccess(notesCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddNotesCommand notesCommand = new AddNotesCommand(outOfBoundIndex, new Notes(VALID_NOTES_BOB));
        assertCommandFailure(notesCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
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
        AddNotesCommand notesCommand = new AddNotesCommand(outOfBoundIndex, new Notes(VALID_NOTES_BOB));

        assertCommandFailure(notesCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final AddNotesCommand standardCommand = new AddNotesCommand(INDEX_FIRST_PERSON, new Notes(VALID_NOTES_AMY));
        // same values -> returns true
        AddNotesCommand commandWithSameValues = new AddNotesCommand(INDEX_FIRST_PERSON, new Notes(VALID_NOTES_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        // different index -> returns false
        assertFalse(standardCommand.equals(new AddNotesCommand(INDEX_SECOND_PERSON, new Notes(VALID_NOTES_AMY))));
        // different Notes -> returns false
        assertFalse(standardCommand.equals(new AddNotesCommand(INDEX_FIRST_PERSON, new Notes(VALID_NOTES_BOB))));
    }
}
