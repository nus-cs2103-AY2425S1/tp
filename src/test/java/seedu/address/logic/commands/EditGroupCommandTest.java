package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
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
    public void execute_duplicateGroup_throwsCommandException() {
        Group anotherGroup = new Group(new GroupName("Another Group"));
        model.addGroup(anotherGroup);

        EditGroupDescriptor descriptorDuplicate = new EditGroupDescriptor();
        descriptorDuplicate.setGroupName(new GroupName("Original Group"));

        EditGroupCommand editGroupCommand = new EditGroupCommand(Index.fromZeroBased(2),
            descriptorDuplicate);
        assertThrows(CommandException.class, () -> editGroupCommand.execute(model),
            EditGroupCommand.MESSAGE_DUPLICATE_GROUP);
    }

    @Test
    public void equals() {
        final EditGroupCommand standardCommand = new EditGroupCommand(Index.fromZeroBased(1),
            new EditGroupDescriptor());

        // same values -> returns true
        EditGroupDescriptor copyDescriptor = new EditGroupDescriptor(new EditGroupDescriptor());
        EditGroupCommand commandWithSameValues = new EditGroupCommand(Index.fromZeroBased(1), copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditGroupCommand(Index.fromZeroBased(2),
            new EditGroupDescriptor())));
    }


}
