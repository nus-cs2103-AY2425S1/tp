package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.ListGroupCommand.LIST_GROUP_MARKER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;

import java.util.Optional;

/**
 * Adds a group to the address book.
 */
public class ListTaskCommand extends Command {

    private final Optional<Group> groupOptional;
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
        requireNonNull(model);
        model.updateFilteredGroupList(x -> x.getGroupName().equals(groupOptional.get().getGroupName()));
        model.setStateGroupTask();
        return new CommandResult(MESSAGE_SUCCESS, LIST_GROUP_TASK_MARKER);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .toString();
    }
}
