package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalPersons;

class UndoCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_noUndoAvailable_throwsCommandException() {
        model = new ModelManager(new AddressBook(), new UserPrefs());
        UndoCommand undoCommand = new UndoCommand();

        // Verify that the exception is thrown when there are no undoable states
        CommandException exception = assertThrows(CommandException.class, () -> undoCommand.execute(model));
        assertEquals(UndoCommand.MESSAGE_FAILURE, exception.getMessage());
    }

    @Test
    public void execute_undoAvailable_success() throws Exception {
        Person uniquePerson = new PersonBuilder().withName("Unique Person").build();

        model.addPerson(uniquePerson);
        model.saveAddressBook();

        expectedModel.addPerson(uniquePerson);
        expectedModel.saveAddressBook();

        CommandResult result = new UndoCommand().execute(model);
        expectedModel.undoAddressBook();

        assertEquals(UndoCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
        assert false;
    }

    @Test
    public void execute_multipleUndo_success() throws Exception {
        Person person1 = new PersonBuilder().withName("Alice").build();
        Person person2 = new PersonBuilder().withName("Bob").build();

        model.addPerson(person1);
        model.addPerson(person2);
        model.saveAddressBook();

        expectedModel.addPerson(person1);
        expectedModel.addPerson(person2);
        expectedModel.saveAddressBook();

        assertTrue(model.canUndoAddressBook());

        CommandResult result1 = new UndoCommand().execute(model);
        expectedModel.undoAddressBook();
        assertEquals(UndoCommand.MESSAGE_SUCCESS, result1.getFeedbackToUser());
        assertEquals(expectedModel, model);

        CommandResult result2 = new UndoCommand().execute(model);
        expectedModel.undoAddressBook();
        assertEquals(UndoCommand.MESSAGE_SUCCESS, result2.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

}
