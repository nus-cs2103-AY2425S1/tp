package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static seedu.address.testutil.Assert.assertThrows;

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

        // Setup the valid note and index for use in execution
        Note validNote = new Note("High profile client.");
        Index validIndex = Index.fromZeroBased(0);

        AddNotesCommand addNotesCommand = new AddNotesCommand(validIndex, validNote);

        // Execute the addNotesCommand
        CommandResult commandResult = addNotesCommand.execute(modelMock);

        String expectedOutput = "Added notes to Person: Amy Bee; Phone: 85355255; Email: amy@gmail.com; Address: 123, "
                + "Jurong West Ave 6, #08-111; Tags: ; Notes: [High profile client.]";

        // Assert that the expected output is matched
        assertEquals(expectedOutput, commandResult.getFeedbackToUser());

    }

    @Test
    public void execute_duplicateNote_throwsCommandException() {
        // Mock the model
        Model modelMock = mock(Model.class);

        // Create a valid person and set up the model's person list
        Person validPerson = new PersonBuilder().withNotes("High profile client.").build();
        ObservableList<Person> personList = FXCollections.observableArrayList(validPerson);

        // Mock the behaviour of ModelMock to return personList when needed
        when(modelMock.getFilteredPersonList()).thenReturn(personList);

        // Create an ObjectProperty<Person> and mock getFocusedPerson to return it when needed
        ObjectProperty<Person> focusedPersonProperty = new SimpleObjectProperty<>(validPerson);
        when(modelMock.getFocusedPerson()).thenReturn(focusedPersonProperty);

        // Setup the valid note and index for use in execution
        Note validNote = new Note("High profile client.");
        Index validIndex = Index.fromZeroBased(0);

        AddNotesCommand addNotesCommand = new AddNotesCommand(validIndex, validNote);

        // Assert that the expected error is thrown
        assertThrows(CommandException.class, AddNotesCommand.DUPLICATE_MESSAGE_CONSTRAINTS, () ->
                addNotesCommand.execute(modelMock));
    }

    @Test
    public void equals() {
        Note noteAlice = new Note("Alice's note");
        Note noteBob = new Note("Bob's note");
        Index indexFirstPerson = Index.fromZeroBased(0);
        Index indexSecondPerson = Index.fromZeroBased(1);

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
