package seedu.address.logic.commands.editcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GROUPS;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.student.Student;
import seedu.address.model.task.Task;

/**
 * Edits the details of an existing group in the address book.
 */
public class EditGroupCommand extends Command {

    public static final String COMMAND_WORD = "edit_grp";
    public static final String COMMAND_WORD_ALIAS = "eg";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the group "
        + "by the group name used in the displayed student list. "
        + "Existing values will be overwritten by the input values.\n"
        + "Parameters: INDEX (must be a positive integer) "
        + "[" + PREFIX_GROUP_NAME + "GROUP NAME]\n"
        + "Example: " + COMMAND_WORD
        + PREFIX_GROUP_NAME + "TEAM 1"
        + PREFIX_GROUP_NAME + "Update Group Name";

    public static final String MESSAGE_EDIT_GROUP_SUCCESS = "Edited Group: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_GROUP = "This group already exists in the address book.";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Exactly 2 group names (original and updated)"
        + "are required, but %d were provided.";

    private final GroupName groupName;
    private final EditGroupDescriptor editGroupDescriptor;

    /**
     * @param groupName               of the group to edit
     * @param editGroupDescriptor details to edit the student with
     */
    public EditGroupCommand(GroupName groupName, EditGroupDescriptor editGroupDescriptor) {
        requireNonNull(groupName);
        requireNonNull(editGroupDescriptor);

        this.groupName = groupName;
        this.editGroupDescriptor = new EditGroupDescriptor(editGroupDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.containsGroupName(groupName)) {
            throw new CommandException(Messages.MESSAGE_GROUP_NAME_NOT_FOUND);
        }
        Group groupToEdit = model.getGroupByName(groupName);
        Group editedGroup = createEditedGroup(groupToEdit, editGroupDescriptor);

        if (!groupToEdit.isSameGroup(editedGroup) && model.hasGroup(editedGroup)) {
            throw new CommandException(MESSAGE_DUPLICATE_GROUP);
        }
        if (model.containsGroupName(editedGroup.getGroupName())) {
            throw new CommandException(MESSAGE_DUPLICATE_GROUP);
        }

        model.setGroup(groupToEdit, editedGroup);
        model.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
        return new CommandResult(String.format(MESSAGE_EDIT_GROUP_SUCCESS, Messages.format(editedGroup)));
    }

    /**
     * Creates and returns a {@code Group} with the details of {@code groupToEdit}
     * edited with {@code editGroupDescriptor}.
     */
    private static Group createEditedGroup(Group groupToEdit, EditGroupDescriptor editGroupDescriptor) {
        assert groupToEdit != null;

        GroupName updatedName = editGroupDescriptor.getName().orElse(groupToEdit.getGroupName());
        Set<Student> students = groupToEdit.getStudents();
        Set<Task> tasks = groupToEdit.getTasks();
        return new Group(updatedName, students, tasks);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditGroupCommand)) {
            return false;
        }

        EditGroupCommand otherEditCommand = (EditGroupCommand) other;
        return groupName.equals(otherEditCommand.groupName)
            && editGroupDescriptor.equals(otherEditCommand.editGroupDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("groupName", groupName)
            .add("editPersonDescriptor", editGroupDescriptor)
            .toString();
    }

    /**
     * Stores the details to edit the group with. Each non-empty field value will replace the
     * corresponding field value of the group.
     */
    public static class EditGroupDescriptor {
        private GroupName groupName;

        public EditGroupDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditGroupDescriptor(EditGroupDescriptor toCopy) {
            setGroupName(toCopy.groupName);
        }

        public void setGroupName(GroupName name) {
            this.groupName = name;
        }

        public Optional<GroupName> getName() {
            return Optional.ofNullable(groupName);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            // instanceof handles nulls
            if (!(other instanceof EditGroupDescriptor)) {
                return false;
            }

            EditGroupDescriptor otherEditGroupDescriptor = (EditGroupDescriptor) other;
            return Objects.equals(groupName, otherEditGroupDescriptor.groupName);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                .add("groupName", groupName)
                .toString();
        }
    }
}
