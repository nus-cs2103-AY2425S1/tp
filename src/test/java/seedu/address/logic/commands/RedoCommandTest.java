package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.VersionedAddressBook;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class RedoCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_redoAddPerson_success() {
        Person validPerson = new PersonBuilder().build();

        // Add the person and commit the change
        model.addPerson(validPerson);
        model.commitAddressBook();

        // Undo the add
        model.undoAddressBook();

        // Set up the expected model to match the state after redo
        expectedModel.addPerson(validPerson);

        // Execute the redo command
        CommandResult result = new RedoCommand().execute(model);

        // Verify the results
        assertEquals(RedoCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }
    @Test
    public void execute_redoEditPerson_success() {
        Person originalPerson = new PersonBuilder().build();
        Person editedPerson = new PersonBuilder().withName("Edited Name").build();

        // Add the original person and commit the change
        model.addPerson(originalPerson);
        model.commitAddressBook();

        // Edit the person and commit the change
        model.setPerson(originalPerson, editedPerson);
        model.commitAddressBook();

        // Undo the edit
        model.undoAddressBook();

        // Set up the expected model to match the state after redo
        expectedModel.addPerson(originalPerson);
        expectedModel.setPerson(originalPerson, editedPerson);

        // Execute the redo command
        CommandResult result = new RedoCommand().execute(model);

        // Verify the results
        assertEquals(RedoCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }
    @Test
    public void execute_redoDeletePerson_success() {
        Person personToDelete = new PersonBuilder().build();

        // Add the person and commit the change
        model.addPerson(personToDelete);
        model.commitAddressBook();

        // Delete the person and commit the change
        model.deletePerson(personToDelete);
        model.commitAddressBook();

        // Undo the delete
        model.undoAddressBook();

        // Set up the expected model to match the state after redo
        expectedModel.addPerson(personToDelete);
        expectedModel.deletePerson(personToDelete);

        // Execute the redo command
        CommandResult result = new RedoCommand().execute(model);

        // Verify the results
        assertEquals(RedoCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }
    @Test
    public void execute_redoWithoutUndo_failure() {
        CommandResult result = new RedoCommand().execute(model);
        assertEquals(RedoCommand.MESSAGE_FAILURE, result.getFeedbackToUser());
    }

    @Test
    public void execute_redoBeyondCapacity_failure() throws CommandException {
        // Set up the initial state of the model
        VersionedAddressBook versionedAddressBook = (VersionedAddressBook) model.getVersionedAddressBook();
        for (int i = 0; i < versionedAddressBook.getCapacity(); i++) {
            Person person = new PersonBuilder().withName("Person " + i).build();
            model.addPerson(person);
        }

        // Undo the changes one by one until the first state
        for (int i = 0; i < versionedAddressBook.getCapacity() - 1; i++) {
            new UndoCommand().execute(model);
        }

        // Redo the changes one by one until the last state
        for (int i = 0; i < versionedAddressBook.getCapacity() - 1; i++) {
            new RedoCommand().execute(model);
        }

        // Attempt to redo beyond the capacity
        CommandResult result = new RedoCommand().execute(model);
        assertEquals(RedoCommand.MESSAGE_FAILURE, result.getFeedbackToUser());
    }
}
