package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

class UndoCommandTest {

    private Model model;
    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
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


}
