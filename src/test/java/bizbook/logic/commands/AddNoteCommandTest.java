package bizbook.logic.commands;

import static bizbook.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static bizbook.logic.commands.CommandTestUtil.VALID_NOTE_ALICE;
import static bizbook.logic.commands.CommandTestUtil.VALID_NOTE_BOB;
import static bizbook.logic.commands.CommandTestUtil.VALID_NOTE_HIGH_PROFILE_CLIENT;
import static bizbook.testutil.Assert.assertThrows;
import static bizbook.testutil.PersonBuilder.DEFAULT_ADDRESS;
import static bizbook.testutil.PersonBuilder.DEFAULT_EMAIL;
import static bizbook.testutil.PersonBuilder.DEFAULT_NAME;
import static bizbook.testutil.PersonBuilder.DEFAULT_PHONE;
import static bizbook.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static bizbook.testutil.TypicalIndexes.INDEX_OUTOFBOUND_PERSON;
import static bizbook.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static bizbook.testutil.TypicalNotes.TYPICAL_NOTE;
import static bizbook.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import bizbook.commons.core.index.Index;
import bizbook.logic.commands.exceptions.CommandException;
import bizbook.model.Model;
import bizbook.model.ModelManager;
import bizbook.model.UserPrefs;
import bizbook.model.person.Note;
import bizbook.model.person.Person;
import bizbook.testutil.PersonBuilder;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AddNoteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullFields_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddNoteCommand(null, null));
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

        AddNoteCommand addNoteCommand = new AddNoteCommand(validIndex, validNote);

        // Execute the addNotesCommand
        CommandResult commandResult = addNoteCommand.execute(modelMock);

        String expected = "Added note to Person: " + DEFAULT_NAME + "; Phone: " + DEFAULT_PHONE + "; Email: "
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

        AddNoteCommand addNoteCommand = new AddNoteCommand(validIndex, validNote);

        // Assert that the expected error is thrown
        assertThrows(CommandException.class, AddNoteCommand.DUPLICATE_MESSAGE_CONSTRAINTS, () ->
                addNoteCommand.execute(modelMock));
    }


    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        AddNoteCommand addNoteCommand = new AddNoteCommand(INDEX_OUTOFBOUND_PERSON,
                TYPICAL_NOTE);
        assertThrows(CommandException.class, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, () ->
                addNoteCommand.execute(model));
    }

    @Test
    public void equals() {
        Note noteAlice = new Note(VALID_NOTE_ALICE);
        Note noteBob = new Note(VALID_NOTE_BOB);
        Index indexFirstPerson = INDEX_FIRST_PERSON;
        Index indexSecondPerson = INDEX_SECOND_PERSON;

        AddNoteCommand addAliceNoteCommand = new AddNoteCommand(indexFirstPerson, noteAlice);
        AddNoteCommand addBobNoteCommand = new AddNoteCommand(indexSecondPerson, noteBob);

        // same object -> returns true
        assertTrue(addAliceNoteCommand.equals(addAliceNoteCommand));

        // same values -> returns true
        AddNoteCommand addAliceNoteCommandCopy = new AddNoteCommand(indexFirstPerson, noteAlice);
        assertTrue(addAliceNoteCommand.equals(addAliceNoteCommandCopy));

        // different types -> returns false
        assertFalse(addAliceNoteCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceNoteCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceNoteCommand.equals(addBobNoteCommand));
    }

}
