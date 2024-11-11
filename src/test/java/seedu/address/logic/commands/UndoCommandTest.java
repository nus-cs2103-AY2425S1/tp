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

public class UndoCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_undoAddPerson_success() throws CommandException {
        Person firstPerson = new PersonBuilder().withName("firstPerson").build();
        Person secondPerson = new PersonBuilder().withName("secondPerson").build();

        // Add the first person and commit the change
        model.addPerson(firstPerson);
        model.commitAddressBook();

        // Add the second person and commit the change
        model.addPerson(secondPerson);
        model.commitAddressBook();

        // Set up the expected model to match the state after undo
        expectedModel.addPerson(firstPerson);

        // Execute the undo command
        CommandResult result = new UndoCommand().execute(model);

        // Verify the results
        assertEquals(UndoCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    @Test
    public void execute_undoEditPerson_success() throws CommandException {
        Person originalPerson = new PersonBuilder().build();
        Person editedPerson = new PersonBuilder().withName("Edited Name").build();

        // Add the original person and commit the change
        model.addPerson(originalPerson);
        model.commitAddressBook();

        // Edit the person and commit the change
        model.setPerson(originalPerson, editedPerson);
        model.commitAddressBook();

        // Set up the expected model to match the state after undo
        expectedModel.addPerson(originalPerson);

        // Execute the undo command
        CommandResult result = new UndoCommand().execute(model);

        // Verify the results
        assertEquals(UndoCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }
    @Test
    public void execute_undoDeletePerson_success() throws CommandException {
        Person validPerson = new PersonBuilder().build();

        // Add the person and commit the change
        model.addPerson(validPerson);
        model.commitAddressBook();

        // Delete the person and commit the change
        model.deletePerson(validPerson);
        model.commitAddressBook();

        // Set up the expected model to match the state after undo
        expectedModel.addPerson(validPerson);

        // Execute the undo command
        CommandResult result = new UndoCommand().execute(model);

        // Verify the results
        assertEquals(UndoCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    @Test
    public void execute_undoWithoutPreviousCommand_failure() throws CommandException {
        CommandResult result = new UndoCommand().execute(model);
        assertEquals(UndoCommand.MESSAGE_FAILURE, result.getFeedbackToUser());
    }

    @Test
    public void execute_undoBeyondCapacity_failure() throws CommandException {
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

        // Attempt to undo beyond the capacity
        CommandResult result = new UndoCommand().execute(model);
        assertEquals(UndoCommand.MESSAGE_FAILURE, result.getFeedbackToUser());
    }
}
