package seedu.address.logic.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalPersons;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

public class UndoCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_undoOnce_success() throws CommandException {
        model.addPerson(TypicalPersons.MICHAEL);
        model.commitAddressBook();

        assertNotEquals(model.getAddressBook(), expectedModel.getAddressBook());

        UndoCommand undoCommand = new UndoCommand();
        CommandResult result = undoCommand.execute(model);

        assertEquals(UndoCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertEquals(model.getAddressBook(), expectedModel.getAddressBook());
    }

    @Test
    public void execute_noCommandToUndo_throwsCommandException() {
        UndoCommand undoCommand = new UndoCommand();

        assertThrows(CommandException.class, () -> undoCommand.execute(model));
    }
}
