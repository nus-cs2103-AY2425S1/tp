package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.editcommands.EditGroupCommand;
import seedu.address.logic.commands.editcommands.EditGroupCommand.EditGroupDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;


public class EditGroupCommandTest {
    private Model model;
    private Group originalGroup;
    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        originalGroup = new Group(new GroupName("Original Group"));
        model.addGroup(originalGroup);
    }
    @Test
    public void execute_groupNameSpecified_success() {
        Group editedGroup = new Group(new GroupName("Updated Group"),
            originalGroup.getStudents(), originalGroup.getTasks());
        EditGroupDescriptor descriptor = new EditGroupDescriptor();
        descriptor.setGroupName(new GroupName("Updated Group"));

        EditGroupCommand editGroupCommand = new EditGroupCommand(originalGroup.getGroupName(), descriptor);

        String expectedMessage = String.format(EditGroupCommand.MESSAGE_EDIT_GROUP_SUCCESS,
            Messages.format(editedGroup));

        Model expectedModel = new ModelManager();
        expectedModel.addGroup(editedGroup);

        assertCommandSuccess(editGroupCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_duplicateGroup_throwsCommandException() {
        Group anotherGroup = new Group(new GroupName("Another Group"));
        model.addGroup(anotherGroup);

        EditGroupDescriptor descriptorDuplicate = new EditGroupDescriptor();
        descriptorDuplicate.setGroupName(new GroupName("Original Group"));

        EditGroupCommand editGroupCommand = new EditGroupCommand(anotherGroup.getGroupName(), descriptorDuplicate);
        assertThrows(CommandException.class, () -> editGroupCommand.execute(model),
            EditGroupCommand.MESSAGE_DUPLICATE_GROUP);
    }
    @Test
    public void execute_groupNotFound_failure() {
        EditGroupDescriptor descriptor = new EditGroupDescriptor();
        descriptor.setGroupName(new GroupName("Nonexistent Group"));

        EditGroupCommand editGroupCommand = new EditGroupCommand(new GroupName("Nonexistent Group"), descriptor);

        assertCommandFailure(editGroupCommand, model, Messages.MESSAGE_GROUP_NAME_NOT_FOUND);
    }
    @Test
    public void equals() {
        final EditGroupCommand standardCommand = new EditGroupCommand(new GroupName("Original Group"),
            new EditGroupDescriptor());

        // same values -> returns true
        EditGroupDescriptor copyDescriptor = new EditGroupDescriptor(new EditGroupDescriptor());
        EditGroupCommand commandWithSameValues = new EditGroupCommand(new GroupName("Original Group"), copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditGroupCommand(new GroupName("Updated Group"),
            new EditGroupDescriptor())));
    }


}
