package seedu.address.logic.commands;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.TypicalGroups.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.deletecommands.DeleteGroupCommand;
import seedu.address.logic.parser.deletecommands.DeleteGroupCommandParser;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.Group;

//@@author gho7sie

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteGroupCommand}.
 */
public class DeleteGroupCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validGroupName_success() {
        Group groupToDelete = model.getFilteredGroupList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteGroupCommand deleteGroupCommand = new DeleteGroupCommand(groupToDelete.getGroupName());

        String expectedMessage = String.format(DeleteGroupCommand.MESSAGE_DELETE_GROUP_SUCCESS,
            Messages.format(groupToDelete), 0);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteGroup(groupToDelete);

        assertCommandSuccess(deleteGroupCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidGroupName_throwsCommandException() {
        assertParseFailure(new DeleteGroupCommandParser(), "not a group",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGroupCommand.MESSAGE_USAGE));
    }
}
