package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;

import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;

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

    private final Optional<Group> groupOptional;

    /**
     * Creates an ListTaskCommand to add the specified {@code Task}
     */
    public ListTaskCommand(Group group) {
        this.groupOptional = Optional.<Group>of(group);
    }

    /**
     * Creates an ListTaskCommand to add the specified {@code Task}
     */
    public ListTaskCommand() {
        this.groupOptional = Optional.empty();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (groupOptional.isPresent() && !model.containsGroupName(groupOptional.get().getGroupName())) {
            throw new CommandException(GROUP_NOT_FOUND);
        }
        if (groupOptional.isPresent()) {
            requireNonNull(model);
            model.updateFilteredGroupList(x -> x.getGroupName().equals(groupOptional.get().getGroupName()));
            model.setStateGroupTask();
            return new CommandResult(MESSAGE_SUCCESS, LIST_GROUP_TASK_MARKER);
        }
        return new CommandResult(MESSAGE_SUCCESS_ALL_TASKS, LIST_TASK_MARKER);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .toString();
    }
}
