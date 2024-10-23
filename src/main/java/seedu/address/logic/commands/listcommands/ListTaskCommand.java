package seedu.address.logic.commands.listcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_GROUP_NAME_NOT_FOUND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;

import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.VersionHistory;
import seedu.address.model.group.GroupName;

/**
 * Lists all the tasks in the address book or for a specified group.
 */
public class ListTaskCommand extends Command {
    public static final String COMMAND_WORD = "list_t";
    public static final String COMMAND_WORD_ALIAS = "lt";
    public static final int LIST_TASK_MARKER = 2;
    public static final int LIST_GROUP_TASK_MARKER = 3;

    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_ALIAS
        + ": Lists all tasks of a specified group.\n"
        + "PARAMETERS: " + COMMAND_WORD
        + PREFIX_GROUP_NAME + "GROUP_NAME "
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_GROUP_NAME + "Group 1 ";

    public static final String MESSAGE_SUCCESS = "Listed all tasks for this group";
    public static final String MESSAGE_SUCCESS_ALL_TASKS = "Listed all tasks available";

    private final Optional<GroupName> groupNameOptional;

    /**
     * Creates a ListTaskCommand to list all the tasks for a group with {@code groupName}.
     */
    public ListTaskCommand(GroupName groupName) {
        this.groupNameOptional = Optional.<GroupName>of(groupName);
    }

    /**
     * Creates a ListTaskCommand to list all the tasks in the address book.
     */
    public ListTaskCommand() {
        this.groupNameOptional = Optional.empty();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (groupNameOptional.isPresent() && !model.containsGroupName(groupNameOptional.get())) {
            throw new CommandException(MESSAGE_GROUP_NAME_NOT_FOUND);
        }
        if (groupNameOptional.isPresent()) {
            requireNonNull(model);
            model.updateFilteredGroupList(x -> x.getGroupName().equals(groupNameOptional.get()));
            model.setMostRecentGroupTaskDisplay(groupNameOptional.get().fullName);
            model.updateFilteredGroupList(x -> x.getGroupName().equals(groupNameOptional.get()));
            model.setStateGroupTask();
            return new CommandResult(MESSAGE_SUCCESS, LIST_GROUP_TASK_MARKER);
        }
        model.setStateTasks();
        model.updateFilteredTaskList(Model.PREDICATE_SHOW_ALL_TASKS); // added because it won't update after find task
        model.setMostRecentGroupTaskDisplay("");
        return new CommandResult(MESSAGE_SUCCESS_ALL_TASKS, LIST_TASK_MARKER);
    }

    @Override
    public VersionHistory updateVersionHistory(VersionHistory versionHistory, Model model) throws CommandException {
        return versionHistory;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .toString();
    }
}
