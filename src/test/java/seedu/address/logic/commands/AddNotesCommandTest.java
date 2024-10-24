package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_HIGH_PROFILE_CLIENT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.PersonBuilder.DEFAULT_ADDRESS;
import static seedu.address.testutil.PersonBuilder.DEFAULT_EMAIL;
import static seedu.address.testutil.PersonBuilder.DEFAULT_NAME;
import static seedu.address.testutil.PersonBuilder.DEFAULT_PHONE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class AddNotesCommandTest {

    @Test
    public void constructor_nullFields_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddNotesCommand(null, null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {

        // Mock the model
        Model modelMock = mock(Model.class);

        // Create a valid person and set up the model's person list
        Person validPerson = new PersonBuilder().withNotes().build();
        ObservableList<Person> personList = FXCollections.observableArrayList(validPerson);

        // Mock the behaviour of ModelMock to return personList when needed
        when(modelMock.getFilteredPersonList()).thenReturn(personList);

        // Create an ObjectProperty<Person> and mock getFocusedPerson to return it when needed
        ObjectProperty<Person> focusedPersonProperty = new SimpleObjectProperty<>(validPerson);
        when(modelMock.getFocusedPerson()).thenReturn(focusedPersonProperty);

        // Set up the valid note and index for use in execution
        Note validNote = new Note(VALID_NOTE_HIGH_PROFILE_CLIENT);
        Index validIndex = INDEX_FIRST_PERSON;

        AddNotesCommand addNotesCommand = new AddNotesCommand(validIndex, validNote);

        // Execute the addNotesCommand
        CommandResult commandResult = addNotesCommand.execute(modelMock);

        String expected = "Added notes to Person: " + DEFAULT_NAME + "; Phone: " + DEFAULT_PHONE + "; Email: "
                + DEFAULT_EMAIL + "; Address: " + DEFAULT_ADDRESS + "; Tags: ; Notes: ["
                + VALID_NOTE_HIGH_PROFILE_CLIENT + "]";

        // Assert that the expected output is matched
        assertEquals(expected, commandResult.getFeedbackToUser());

    }

    @Test
    public void execute_duplicateNote_throwsCommandException() {
        // Mock the model
        Model modelMock = mock(Model.class);

        // Create a valid person and set up the model's person list
        Person validPerson = new PersonBuilder().withNotes(VALID_NOTE_HIGH_PROFILE_CLIENT).build();
        ObservableList<Person> personList = FXCollections.observableArrayList(validPerson);

        // Mock the behaviour of ModelMock to return personList when needed
        when(modelMock.getFilteredPersonList()).thenReturn(personList);

        // Create an ObjectProperty<Person> and mock getFocusedPerson to return it when needed
        ObjectProperty<Person> focusedPersonProperty = new SimpleObjectProperty<>(validPerson);
        when(modelMock.getFocusedPerson()).thenReturn(focusedPersonProperty);

        // Set up the valid note and index for use in execution
        Note validNote = new Note(VALID_NOTE_HIGH_PROFILE_CLIENT);
        Index validIndex = INDEX_FIRST_PERSON;

        AddNotesCommand addNotesCommand = new AddNotesCommand(validIndex, validNote);

        // Assert that the expected error is thrown
        assertThrows(CommandException.class, AddNotesCommand.DUPLICATE_MESSAGE_CONSTRAINTS, () ->
                addNotesCommand.execute(modelMock));
    }

    @Test
    public void equals() {
        Note noteAlice = new Note(VALID_NOTE_ALICE);
        Note noteBob = new Note(VALID_NOTE_BOB);
        Index indexFirstPerson = INDEX_FIRST_PERSON;
        Index indexSecondPerson = INDEX_SECOND_PERSON;

        AddNotesCommand addAliceNoteCommand = new AddNotesCommand(indexFirstPerson, noteAlice);
        AddNotesCommand addBobNoteCommand = new AddNotesCommand(indexSecondPerson, noteBob);

        // same object -> returns true
        assertTrue(addAliceNoteCommand.equals(addAliceNoteCommand));

        // same values -> returns true
        AddNotesCommand addAliceNoteCommandCopy = new AddNotesCommand(indexFirstPerson, noteAlice);
        assertTrue(addAliceNoteCommand.equals(addAliceNoteCommandCopy));

        // different types -> returns false
        assertFalse(addAliceNoteCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceNoteCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceNoteCommand.equals(addBobNoteCommand));
    }

}
