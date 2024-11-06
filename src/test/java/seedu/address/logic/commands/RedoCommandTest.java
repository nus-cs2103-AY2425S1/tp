package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class RedoCommandTest {

    private Model model;
    private Model expectedModel;
    private RedoCommand redoCommand;
    private Person newPerson1;
    private Person newPerson2;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        redoCommand = new RedoCommand();
        newPerson1 = new PersonBuilder().withName("New Person 1").withPhone("12345678")
                .withEmail("new1@example.com").withAddress("123, New Street").build();
        newPerson2 = new PersonBuilder().withName("New Person 2").withPhone("87654321")
                .withEmail("new2@example.com").withAddress("321, Old Street").build();
    }

    @Test
    public void execute_noCommandsToRedo_throwsCommandException() {
        assertThrows(CommandException.class, () -> redoCommand.execute(model));
    }

    @Test
    public void execute_singleRedo_success() throws Exception {
        model.addPerson(newPerson1);
        model.commitAddressBook();
        model.undoAddressBook();

        expectedModel.addPerson(newPerson1);
        expectedModel.commitAddressBook();
        expectedModel.undoAddressBook();

        model.redoAddressBook();
        expectedModel.redoAddressBook();

        assertEquals(expectedModel, model);
    }

    @Test
    public void execute_multipleRedos_success() throws Exception {
        model.addPerson(newPerson1);
        model.commitAddressBook();
        model.addPerson(newPerson2);
        model.commitAddressBook();
        model.undoAddressBook();
        model.undoAddressBook();

        expectedModel.addPerson(newPerson1);
        expectedModel.commitAddressBook();
        expectedModel.addPerson(newPerson2);
        expectedModel.commitAddressBook();
        expectedModel.undoAddressBook();
        expectedModel.undoAddressBook();

        model.redoAddressBook();
        model.redoAddressBook();
        expectedModel.redoAddressBook();
        expectedModel.redoAddressBook();

        assertEquals(expectedModel, model);
    }
}
