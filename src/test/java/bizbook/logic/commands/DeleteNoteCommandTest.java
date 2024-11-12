package bizbook.logic.commands;

import static bizbook.logic.commands.CommandTestUtil.VALID_NOTE_HIGH_PROFILE_CLIENT;
import static bizbook.testutil.Assert.assertThrows;
import static bizbook.testutil.PersonBuilder.DEFAULT_ADDRESS;
import static bizbook.testutil.PersonBuilder.DEFAULT_EMAIL;
import static bizbook.testutil.PersonBuilder.DEFAULT_NAME;
import static bizbook.testutil.PersonBuilder.DEFAULT_PHONE;
import static bizbook.testutil.TypicalIndexes.INDEX_FIRST_NOTE;
import static bizbook.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static bizbook.testutil.TypicalIndexes.INDEX_OUTOFBOUND_NOTE;
import static bizbook.testutil.TypicalIndexes.INDEX_OUTOFBOUND_PERSON;
import static bizbook.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import bizbook.commons.core.index.Index;
import bizbook.logic.Messages;
import bizbook.logic.commands.exceptions.CommandException;
import bizbook.model.Model;
import bizbook.model.person.Person;
import bizbook.testutil.PersonBuilder;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DeleteNoteCommandTest {

    @Test
    public void constructor_nullFields_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteNoteCommand(null, null));
    }

    @Test
    public void execute_validNoteDeletion_deletionSuccessful() throws Exception {

        // Mock the model
        Model modelMock = mock(Model.class);

        // Create a valid person with a note and set up the model's person list
        Person validPerson = new PersonBuilder().withNotes(VALID_NOTE_HIGH_PROFILE_CLIENT).build();
        ObservableList<Person> personList = FXCollections.observableArrayList(validPerson);

        // Mock the behavior of modelMock to return personList when needed
        when(modelMock.getFilteredPersonList()).thenReturn(personList);

        // Mock focused person property
        ObjectProperty<Person> focusedPersonProperty = new SimpleObjectProperty<>(validPerson);
        when(modelMock.getFocusedPerson()).thenReturn(focusedPersonProperty);

        // Set up the person index and note index
        Index validPersonIndex = INDEX_FIRST_PERSON;
        Index validNoteIndex = INDEX_FIRST_PERSON;

        DeleteNoteCommand deleteNoteCommand = new DeleteNoteCommand(validPersonIndex, validNoteIndex);

        // Execute the deleteNotesCommand
        CommandResult commandResult = deleteNoteCommand.execute(modelMock);

        String expected = "Deleted note of Person: " + DEFAULT_NAME + "; Phone: " + DEFAULT_PHONE + "; Email: "
                + DEFAULT_EMAIL + "; Address: " + DEFAULT_ADDRESS + "; Tags: ; Notes: ";

        // Assert that the expected output is matched
        assertEquals(expected, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        // Mock the model
        Model modelMock = mock(Model.class);

        // Create a valid person with a note and set up the model's person list
        Person validPerson = new PersonBuilder().withNotes(VALID_NOTE_HIGH_PROFILE_CLIENT).build();
        ObservableList<Person> personList = FXCollections.observableArrayList(validPerson);

        // Mock the behavior of modelMock to return personList when needed
        when(modelMock.getFilteredPersonList()).thenReturn(personList);

        // Mock focused person property
        ObjectProperty<Person> focusedPersonProperty = new SimpleObjectProperty<>(validPerson);
        when(modelMock.getFocusedPerson()).thenReturn(focusedPersonProperty);

        // Set up a invalid person index and valid note index
        Index invalidPersonIndex = INDEX_OUTOFBOUND_PERSON;
        Index validNoteIndex = INDEX_FIRST_NOTE;

        DeleteNoteCommand deleteNoteCommand = new DeleteNoteCommand(invalidPersonIndex, validNoteIndex);

        // Assert that the expected error is thrown
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, () ->
                deleteNoteCommand.execute(modelMock));
    }

    @Test
    public void execute_invalidNoteIndex_throwsCommandException() {
        // Mock the model
        Model modelMock = mock(Model.class);

        // Create a valid person with a note and set up the model's person list
        Person validPerson = new PersonBuilder().withNotes(VALID_NOTE_HIGH_PROFILE_CLIENT).build();
        ObservableList<Person> personList = FXCollections.observableArrayList(validPerson);

        // Mock the behavior of modelMock to return personList when needed
        when(modelMock.getFilteredPersonList()).thenReturn(personList);

        // Mock focused person property
        ObjectProperty<Person> focusedPersonProperty = new SimpleObjectProperty<>(validPerson);
        when(modelMock.getFocusedPerson()).thenReturn(focusedPersonProperty);

        // Set up a valid person index and invalid note index
        Index validPersonIndex = INDEX_FIRST_PERSON;
        Index invalidNoteIndex = INDEX_OUTOFBOUND_NOTE;

        DeleteNoteCommand deleteNoteCommand = new DeleteNoteCommand(validPersonIndex, invalidNoteIndex);

        // Assert that the expected error is thrown
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_NOTE_INDEX, ()
                -> deleteNoteCommand.execute(modelMock));
    }

    @Test
    public void equals() {
        Index indexFirstPerson = INDEX_FIRST_PERSON;
        Index indexSecondPerson = INDEX_SECOND_PERSON;
        Index noteIndexFirst = INDEX_FIRST_PERSON;
        Index noteIndexSecond = INDEX_SECOND_PERSON;

        DeleteNoteCommand deleteFirstNoteCommand = new DeleteNoteCommand(indexFirstPerson, noteIndexFirst);
        DeleteNoteCommand deleteSecondNoteCommand = new DeleteNoteCommand(indexSecondPerson, noteIndexSecond);

        // same object -> returns true
        assertTrue(deleteFirstNoteCommand.equals(deleteFirstNoteCommand));

        // same values -> returns true
        DeleteNoteCommand deleteFirstNoteCommandCopy = new DeleteNoteCommand(indexFirstPerson, noteIndexFirst);
        assertTrue(deleteFirstNoteCommand.equals(deleteFirstNoteCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstNoteCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstNoteCommand.equals(null));

        // different person or note -> returns false
        assertFalse(deleteFirstNoteCommand.equals(deleteSecondNoteCommand));
    }
}
