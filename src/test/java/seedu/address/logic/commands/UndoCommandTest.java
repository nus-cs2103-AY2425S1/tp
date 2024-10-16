package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    public void execute_noUndoAvailable_failure() {
        // Setup model with no undoable actions
        model = new ModelManager(new AddressBook(), new UserPrefs());
        UndoCommand undoCommand = new UndoCommand();

        CommandResult result = undoCommand.execute(model);

        // Verify that the failure message is returned
        assertEquals(UndoCommand.MESSAGE_FAILURE, result.getFeedbackToUser());
    }
    @Test
    public void execute_multipleUndo_success() throws Exception {
        // Add two people to the model
        Person person1 = new PersonBuilder().withName("Alice").build();
        Person person2 = new PersonBuilder().withName("Bob").build();

        model.addPerson(person1);
        model.addPerson(person2);

        expectedModel.addPerson(person1);
        expectedModel.addPerson(person2);

        // Ensure multiple undos are possible
        assertTrue(model.canUndoAddressBook());

        // Undo the last action (adding Bob)
        CommandResult result1 = new UndoCommand().execute(model);
        expectedModel.undoAddressBook();
        assertEquals(UndoCommand.MESSAGE_SUCCESS, result1.getFeedbackToUser());
        assertEquals(expectedModel, model);

        // Undo the second action (adding Alice)
        CommandResult result2 = new UndoCommand().execute(model);
        expectedModel.undoAddressBook();
        assertEquals(UndoCommand.MESSAGE_SUCCESS, result2.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }


}
