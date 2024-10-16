package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;

import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.GroupName;

/**
 * Adds a group to the address book.
 */
public class ListTaskCommand extends Command {
    public static final String COMMAND_WORD = "list_tasks";
    public static final int LIST_TASK_MARKER = 2;
    public static final int LIST_GROUP_TASK_MARKER = 3;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all tasks of a specified group. "
        + "Example: " + COMMAND_WORD
        + PREFIX_GROUP_NAME + "GROUP_NAME "
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_GROUP_NAME + "Group 1 ";

    public static final String MESSAGE_SUCCESS = "Listed all tasks for this group";
    public static final String MESSAGE_SUCCESS_ALL_TASKS = "Listed all tasks available";
    public static final String GROUP_NOT_FOUND = "Group not found!";

    private final Optional<GroupName> groupNameOptional;

    /**
     * Creates an ListTaskCommand to add the specified {@code Task}
     */
    public ListTaskCommand(GroupName groupName) {
        this.groupNameOptional = Optional.<GroupName>of(groupName);
    }

    /**
     * Creates an ListTaskCommand to add the specified {@code Task}
     */
    public ListTaskCommand() {
        this.groupNameOptional = Optional.empty();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (groupNameOptional.isPresent() && !model.containsGroupName(groupNameOptional.get())) {
            throw new CommandException(GROUP_NOT_FOUND);
        }
        if (groupNameOptional.isPresent()) {
            requireNonNull(model);
            model.updateFilteredGroupList(x -> x.getGroupName().equals(groupNameOptional.get()));
            model.setStateGroupTask();
            return new CommandResult(MESSAGE_SUCCESS, LIST_GROUP_TASK_MARKER);
        }
        model.setStateTasks();
        return new CommandResult(MESSAGE_SUCCESS_ALL_TASKS, LIST_TASK_MARKER);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .toString();
    }
}
