package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class UndoCommandTest {
    private static final Integer FIRST_INDEX = 1;

    private Model testModel;
    private Model expectedModel;

    @BeforeEach
    public void setUp() throws CommandException {
        testModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Index index = Index.fromOneBased(FIRST_INDEX);
        DeleteCommand deleteCommand = new DeleteCommand(index);

        deleteCommand.execute(testModel);
        deleteCommand.execute(testModel);

        deleteCommand.execute(expectedModel);
        deleteCommand.execute(expectedModel);
    }

    @Test
    public void execute_afterSingleCommand_success() {
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), testModel, UndoCommand.MESSAGE_UNDO_SUCCESS, expectedModel);
    }

    @Test
    public void execute_multipleCommands_success() throws CommandException {
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), testModel, UndoCommand.MESSAGE_UNDO_SUCCESS, expectedModel);
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), testModel, UndoCommand.MESSAGE_UNDO_SUCCESS, expectedModel);
    }

    @Test
    public void execute_noFurtherActions_fail() {
        testModel.undoAddressBook();
        testModel.undoAddressBook();
        assertCommandFailure(new UndoCommand(), testModel, UndoCommand.MESSAGE_UNDO_FAILURE);
    }

}
