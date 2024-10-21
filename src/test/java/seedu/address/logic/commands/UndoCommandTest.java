package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
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
        Person validPerson = new PersonBuilder().build();
        model.addPerson(validPerson);
        CommandResult result = new UndoCommand().execute(model);
        assertEquals(UndoCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertEquals(expectedModel, model);

    }
    @Test
    public void execute_undoEditPerson_success() throws CommandException {
        Person originalPerson = new PersonBuilder().build();
        Person editedPerson = new PersonBuilder().withName("Edited Name").build();
        model.addPerson(originalPerson);
        model.setPerson(originalPerson, editedPerson);
        expectedModel.addPerson(originalPerson);
        CommandResult result = new UndoCommand().execute(model);
        assertEquals(UndoCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    @Test
    public void execute_undoDeletePerson_success() throws CommandException {
        Person validPerson = new PersonBuilder().build();
        model.addPerson(validPerson);
        model.deletePerson(validPerson);
        expectedModel.addPerson(validPerson);
        CommandResult result = new UndoCommand().execute(model);
        assertEquals(UndoCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    @Test
    public void execute_undoWithoutPreviousCommand_failure() throws CommandException {
        CommandResult result = new UndoCommand().execute(model);
        assertEquals(UndoCommand.MESSAGE_FAILURE, result.getFeedbackToUser());
    }
}
