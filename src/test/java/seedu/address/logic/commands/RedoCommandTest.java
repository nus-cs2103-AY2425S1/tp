package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.executeDataModifyingCommand;
import static seedu.address.logic.commands.CommandTestUtil.executeNonDataModifyingCommand;
import static seedu.address.model.VersionedAddressBook.MESSAGE_NO_MORE_UNDONE_STATES;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.edit.EditCommand;
import seedu.address.logic.commands.edit.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

public class RedoCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_nothingToRedo_throwsCommandException() throws CommandException {
        // Nothing done and 1 redo
        String expectedMessage = MESSAGE_NO_MORE_UNDONE_STATES;
        assertCommandFailure(new RedoCommand(), model, expectedMessage);

        // 1 add and 1 redo
        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);
        executeDataModifyingCommand(addCommand, model);
        assertCommandFailure(new RedoCommand(), model, expectedMessage);

        UndoCommand undoCommand = new UndoCommand();
        RedoCommand redoCommand = new RedoCommand();
        // 1 delete 1 undo 1 edit and 1 redo
        DeleteCommand deleteCommand = new DeleteCommand(new Index[]{Index.fromOneBased(1)});
        EditPersonDescriptor editDescriptor = new EditPersonDescriptorBuilder().build();
        EditCommand editCommand = new EditCommand(Index.fromOneBased(1),
            editDescriptor);

        executeDataModifyingCommand(deleteCommand, model);
        undoCommand.execute(model);
        executeDataModifyingCommand(editCommand, model);
        assertCommandFailure(new RedoCommand(), model, expectedMessage);

        // 1 clear, 1 undo and 2 redoes
        ClearCommand clearCommand = new ClearCommand();
        executeDataModifyingCommand(clearCommand, model);
        undoCommand.execute(model);
        redoCommand.execute(model);
        assertCommandFailure(new RedoCommand(), model, expectedMessage);
    }

    @Test
    public void execute_oneUndo_success() throws CommandException {
        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);
        EditPersonDescriptor editDescriptor = new EditPersonDescriptorBuilder().build();
        EditCommand editCommand = new EditCommand(Index.fromOneBased(1), editDescriptor);
        DeleteCommand deleteCommand = new DeleteCommand(new Index[]{Index.fromOneBased(1)});
        ClearCommand clearCommand = new ClearCommand();
        UndoCommand undoCommand = new UndoCommand();
        String expectedMessage = RedoCommand.MESSAGE_SUCCESS;

        // 1 add, 1 undo and 1 redo
        executeDataModifyingCommand(addCommand, model);
        Model modelAfterAdd = new ModelManager(model.getAddressBook(), new UserPrefs());
        undoCommand.execute(model);
        assertCommandSuccess(new RedoCommand(), model, expectedMessage, modelAfterAdd);

        // 1 edit, 1 undo and 1 redo
        executeDataModifyingCommand(editCommand, model);
        Model modelAfterEdit = new ModelManager(model.getAddressBook(), new UserPrefs());
        undoCommand.execute(model);
        assertCommandSuccess(new RedoCommand(), model, expectedMessage, modelAfterEdit);

        // 1 delete, 1 undo and 1 redo
        executeDataModifyingCommand(deleteCommand, model);
        Model modelAfterDelete = new ModelManager(model.getAddressBook(), new UserPrefs());
        undoCommand.execute(model);
        assertCommandSuccess(new RedoCommand(), model, expectedMessage, modelAfterDelete);

        // 1 clear, 1 undo and 1 redo
        executeDataModifyingCommand(clearCommand, model);
        Model modelAfterClear = new ModelManager(model.getAddressBook(), new UserPrefs());
        undoCommand.execute(model);
        assertCommandSuccess(new RedoCommand(), model, expectedMessage, modelAfterClear);
    }

    @Test
    public void execute_commandsWithNonDataModifyingOnesInBetween_success() throws CommandException {
        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);
        ListCommand listCommand = new ListCommand();
        UndoCommand undoCommand = new UndoCommand();
        String expectedMessage = RedoCommand.MESSAGE_SUCCESS;

        executeDataModifyingCommand(addCommand, model);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        executeNonDataModifyingCommand(listCommand, model);
        undoCommand.execute(model);
        assertCommandSuccess(new RedoCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_undoAndRedoTwice_success() throws CommandException {
        EditPersonDescriptor editDescriptor = new EditPersonDescriptorBuilder().build();
        EditCommand editCommand = new EditCommand(Index.fromOneBased(1),
            editDescriptor);
        UndoCommand undoCommand = new UndoCommand();
        RedoCommand redoCommand = new RedoCommand();

        executeDataModifyingCommand(editCommand, model);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        String expectedMessage = RedoCommand.MESSAGE_SUCCESS;

        undoCommand.execute(model);
        assertCommandSuccess(new RedoCommand(), model, expectedMessage, expectedModel);
        undoCommand.execute(model);
        assertCommandSuccess(new RedoCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_twoCommandsAndUndoTwiceAndRedoTwice_success() throws CommandException {
        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);
        DeleteCommand deleteCommand = new DeleteCommand(new Index[]{
            Index.fromOneBased(1)
        });
        UndoCommand undoCommand = new UndoCommand();

        executeDataModifyingCommand(addCommand, model);
        Model intermediateModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        executeDataModifyingCommand(deleteCommand, model);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        String expectedMessage = RedoCommand.MESSAGE_SUCCESS;

        undoCommand.execute(model);
        undoCommand.execute(model);
        assertCommandSuccess(new RedoCommand(), model, expectedMessage, intermediateModel);
        assertCommandSuccess(new RedoCommand(), model, expectedMessage, expectedModel);
    }
}
