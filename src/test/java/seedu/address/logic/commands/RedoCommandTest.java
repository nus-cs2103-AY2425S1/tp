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
import seedu.address.testutil.TypicalPersons;

public class RedoCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_redoAfterUndo_success() throws CommandException {
        // Add a person, commit, and then undo
        model.addPerson(TypicalPersons.MICHAEL);
        model.commitAddressBook();
        model.undoAddressBook();

        // Redo the undone command and verify success
        RedoCommand redoCommand = new RedoCommand();
        CommandResult result = redoCommand.execute(model);

        expectedModel.addPerson(TypicalPersons.MICHAEL);
        assertEquals(RedoCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertEquals(model.getAddressBook(), expectedModel.getAddressBook());
    }

    @Test
    public void execute_noCommandToRedo_throwsCommandException() {
        RedoCommand redoCommand = new RedoCommand();

        assertThrows(CommandException.class, () -> redoCommand.execute(model));
    }
}
