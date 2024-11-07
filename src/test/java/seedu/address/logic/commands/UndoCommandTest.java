package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.executeDataModifyingCommand;
import static seedu.address.logic.commands.CommandTestUtil.executeNonDataModifyingCommand;
import static seedu.address.model.VersionedAddressBook.MESSAGE_NO_MORE_HISTORY;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.edit.EditCommand;
import seedu.address.logic.commands.edit.EditCommand.EditPersonDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

public class UndoCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_nothingToUndo_throwsCommandException() {
        String expectedMessage = MESSAGE_NO_MORE_HISTORY;
        assertCommandFailure(new UndoCommand(), model, expectedMessage);
    }

    @Test
    public void execute_oneAddCommandHistory_success() throws Exception {
        String expectedMessage = UndoCommand.MESSAGE_SUCCESS;
        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);
        executeDataModifyingCommand(addCommand, model);
        assertCommandSuccess(new UndoCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_oneEditCommandHistory_success() throws Exception {
        String expectedMessage = UndoCommand.MESSAGE_SUCCESS;
        EditPersonDescriptor editDescriptor = new EditPersonDescriptorBuilder().build();
        EditCommand editCommand = new EditCommand(Index.fromOneBased(1),
            editDescriptor);
        executeDataModifyingCommand(editCommand, model);
        assertCommandSuccess(new UndoCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_oneDeleteCommandHistory_success() throws Exception {
        String expectedMessage = UndoCommand.MESSAGE_SUCCESS;
        DeleteCommand deleteCommand = new DeleteCommand(new Index[]{Index.fromOneBased(1)});
        executeDataModifyingCommand(deleteCommand, model);
        assertCommandSuccess(new UndoCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_oneClearCommandHistory_success() throws Exception {
        String expectedMessage = UndoCommand.MESSAGE_SUCCESS;
        ClearCommand clearCommand = new ClearCommand();
        executeDataModifyingCommand(clearCommand, model);
        assertCommandSuccess(new UndoCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_oneListCommandHistory_success() throws Exception {
        String expectedMessage = MESSAGE_NO_MORE_HISTORY;
        ListCommand listCommand = new ListCommand();
        executeNonDataModifyingCommand(listCommand, model);
        assertCommandFailure(new UndoCommand(), model, expectedMessage);
    }

    @Test
    public void execute_twoCommands_success() throws Exception {
        String expectedMessage = UndoCommand.MESSAGE_SUCCESS;
        String errorMessage = MESSAGE_NO_MORE_HISTORY;

        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);
        executeDataModifyingCommand(addCommand, model);
        Model intermediateModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        DeleteCommand deleteCommand = new DeleteCommand(new Index[]{Index.fromOneBased(1)});
        executeDataModifyingCommand(deleteCommand, model);

        // One undo should revert the model back to the intermediate model
        assertCommandSuccess(new UndoCommand(), model, expectedMessage, intermediateModel);

        // Two undoes should revert the model back to the original state
        assertCommandSuccess(new UndoCommand(), model, expectedMessage, expectedModel);

        // Any further undoes should cause CommandException
        assertCommandFailure(new UndoCommand(), model, errorMessage);
    }
}
