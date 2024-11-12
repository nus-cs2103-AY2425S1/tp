package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
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

class RedoCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_noRedoAvailable_throwsCommandException() {
        model = new ModelManager(new AddressBook(), new UserPrefs());
        RedoCommand redoCommand = new RedoCommand();

        // Verify that the exception is thrown when there are no redoable states
        CommandException exception = assertThrows(CommandException.class, () -> redoCommand.execute(model));
        assertEquals(RedoCommand.MESSAGE_FAILURE, exception.getMessage());
    }

    @Test
    public void execute_redoAvailable_success() throws Exception {
        Person uniquePerson = new PersonBuilder().withName("Unique Person").build();

        model.addPerson(uniquePerson);
        model.saveAddressBook();

        expectedModel.addPerson(uniquePerson);
        expectedModel.saveAddressBook();

        model.undoAddressBook();
        expectedModel.undoAddressBook();

        CommandResult result = new RedoCommand().execute(model);
        expectedModel.redoAddressBook();

        assertEquals(RedoCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    @Test
    public void execute_multipleRedo_success() throws Exception {
        Person person1 = new PersonBuilder().withPhone(VALID_PHONE_AMY).build();
        Person person2 = new PersonBuilder().withPhone(VALID_PHONE_BOB).build();

        model.addPerson(person1);
        model.addPerson(person2);
        model.saveAddressBook();

        expectedModel.addPerson(person1);
        expectedModel.addPerson(person2);
        expectedModel.saveAddressBook();

        model.undoAddressBook();
        expectedModel.undoAddressBook();
        model.undoAddressBook();
        expectedModel.undoAddressBook();

        assertTrue(model.canRedoAddressBook());

        CommandResult result1 = new RedoCommand().execute(model);
        expectedModel.redoAddressBook();
        assertEquals(RedoCommand.MESSAGE_SUCCESS, result1.getFeedbackToUser());
        assertEquals(expectedModel, model);

        CommandResult result2 = new RedoCommand().execute(model);
        expectedModel.redoAddressBook();
        assertEquals(RedoCommand.MESSAGE_SUCCESS, result2.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

}
