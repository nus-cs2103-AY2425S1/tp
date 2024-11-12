package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalGroups.GOONERS;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;


public class DeleteGroupCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
    }

    @Test
    public void execute_validGroupName_success() {
        Group groupToDelete = GOONERS;
        GroupName groupName = groupToDelete.getGroupName();
        model.addGroup(groupToDelete); // Add the group to the model

        DeleteGroupCommand deleteGroupCommand = new DeleteGroupCommand(groupName);

        String expectedMessage = String.format(DeleteGroupCommand.MESSAGE_DELETE_GROUP_SUCCESS,
                groupToDelete.getGroupName());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteGroup(groupToDelete);

        assertCommandSuccess(deleteGroupCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_groupNotFound_throwsCommandException() {
        GroupName nonExistentGroupName = new GroupName("Non Existent Group");
        DeleteGroupCommand deleteGroupCommand = new DeleteGroupCommand(nonExistentGroupName);

        assertCommandFailure(deleteGroupCommand, model,
                String.format(DeleteGroupCommand.MESSAGE_GROUP_NOT_FOUND, nonExistentGroupName));
    }

    @Test
    public void equals() {
        GroupName groupName1 = new GroupName("Group 1");
        GroupName groupName2 = new GroupName("Group 2");
        DeleteGroupCommand deleteFirstCommand = new DeleteGroupCommand(groupName1);
        DeleteGroupCommand deleteSecondCommand = new DeleteGroupCommand(groupName2);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteGroupCommand deleteFirstCommandCopy = new DeleteGroupCommand(groupName1);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different group -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        GroupName groupName = new GroupName("Test Group");
        DeleteGroupCommand deleteGroupCommand = new DeleteGroupCommand(groupName);
        String expected = "DeleteGroupCommand[groupName=Test Group]";
        assertEquals(expected, deleteGroupCommand.toString());
    }
}
