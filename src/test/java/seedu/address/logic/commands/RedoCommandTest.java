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

public class RedoCommandTest {
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
        testModel.undoAddressBook();
        testModel.undoAddressBook();

        deleteCommand.execute(expectedModel);
        deleteCommand.execute(expectedModel);
        expectedModel.undoAddressBook();
        expectedModel.undoAddressBook();
    }

    @Test
    public void execute_afterUndo_success() {
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), testModel, RedoCommand.MESSAGE_REDO_SUCCESS, expectedModel);
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), testModel, RedoCommand.MESSAGE_REDO_SUCCESS, expectedModel);
    }

    @Test
    public void execute_noFurtherActions_fail() {
        testModel.redoAddressBook();
        testModel.redoAddressBook();
        assertCommandFailure(new RedoCommand(), testModel, RedoCommand.MESSAGE_REDO_FAILURE);
    }

}
