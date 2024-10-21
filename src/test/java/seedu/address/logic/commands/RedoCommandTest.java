package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
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
        model.addPerson(validPerson);
        model.undoAddressBook();
        expectedModel.addPerson(validPerson);
        CommandResult result = new RedoCommand().execute(model);
        assertEquals(RedoCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }
    @Test
    public void execute_redoEditPerson_success() {
        Person originalPerson = new PersonBuilder().build();
        Person editedPerson = new PersonBuilder().withName("Edited Name").build();
        model.addPerson(originalPerson);
        model.setPerson(originalPerson, editedPerson);
        model.undoAddressBook();
        expectedModel.addPerson(editedPerson);
        CommandResult result = new RedoCommand().execute(model);
        assertEquals(RedoCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }
    @Test
    public void execute_redoDeletePerson_success() {
        Person validPerson = new PersonBuilder().build();
        model.addPerson(validPerson);
        model.deletePerson(validPerson);
        model.undoAddressBook();
        CommandResult result = new RedoCommand().execute(model);
        assertEquals(RedoCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }
    @Test
    public void execute_redoWithoutUndo_failure() {
        CommandResult result = new RedoCommand().execute(model);
        assertEquals(RedoCommand.MESSAGE_FAILURE, result.getFeedbackToUser());
    }
}
