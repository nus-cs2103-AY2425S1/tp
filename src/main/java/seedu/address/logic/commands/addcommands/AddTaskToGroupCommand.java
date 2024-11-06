package seedu.address.logic.commands.addcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.ListMarkers.LIST_GROUP_TASK_MARKER;
import static seedu.address.logic.Messages.MESSAGE_GROUP_NAME_NOT_FOUND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_NAME;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.VersionHistory;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskName;

/**
 * Adds a task to a group.
 */
public class AddTaskToGroupCommand extends Command {

    public static final String COMMAND_WORD = "add_t_g";
    public static final String COMMAND_WORD_ALIAS = "atg";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_ALIAS
        + ": Adds a task to a group identified by the group name used.\n"
        + "Parameters: "
        + PREFIX_TASK_NAME + "TASK_NAME "
        + PREFIX_TASK_DEADLINE + "TASK_DATE "
        + PREFIX_GROUP_NAME + "GROUP_NAME "
        + "[" + PREFIX_GROUP_NAME + "GROUP_NAME]...\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_TASK_NAME + "Complete this task "
        + PREFIX_TASK_DEADLINE + "2025-01-01 1300 "
        + PREFIX_GROUP_NAME + "CS2103T-T14-1";

    public static final String MESSAGE_SUCCESS = "Added task (%1$s) to the following groups:\n%2$s";

    public static final String MESSAGE_OVERDUE_WARNING = "WARNING: Task will be marked as overdue";

    public static final String MESSAGE_DUPLICATE_TASK_IN_GROUP = "This task is already in the group";

    public static final String MESSAGE_TASK_EXISTS_GLOBAL = "This task (%1$s) already exists.\n"
        + " Please use the add existing task command instead.";

    private final TaskName taskName;

    private final Deadline deadline;

    private final Set<GroupName> toAddInto;

    /**
     * Creates an AddStudentToGroupCommand to add the specified {@code Task} to the specified {@code Group}.
     */
    public AddTaskToGroupCommand(TaskName taskName, Deadline deadline, Set<GroupName> groupNames) {
        requireNonNull(taskName);
        requireNonNull(groupNames);
        this.taskName = taskName;
        this.deadline = deadline;
        this.toAddInto = groupNames;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Task task = new Task(taskName, deadline);
        if (model.hasTask(task)) {
            throw new CommandException(String.format(MESSAGE_TASK_EXISTS_GLOBAL, task.getTaskName().getTaskName()));
        }
        List<GroupName> groups = toAddInto.stream().toList();
        int lastIndex = groups.size() - 1;
        StringBuilder groupsAdded = new StringBuilder();

        for (GroupName groupName : groups) {
            if (!model.containsGroupName(groupName)) {
                throw new CommandException(MESSAGE_GROUP_NAME_NOT_FOUND);
            }
            Group group = model.getGroupByName(groupName);

            if (model.hasTaskInGroup(task, group)) {
                throw new CommandException(MESSAGE_DUPLICATE_TASK_IN_GROUP);
            }
            model.addTaskToGroup(task, group);
            if (!model.hasTask(task)) {
                model.addTask(task);
            } else {
                model.increaseGroupWithTask(task);
            }
            groupsAdded.append(groupName).append("\n");
        }
        model.setMostRecentGroupTaskDisplay(groups.get(lastIndex).getGroupName());
        model.updateFilteredGroupList(x -> x.getGroupName().equals(groups.get(lastIndex)));
        model.setStateGroupTask();
        ZoneId zid = ZoneId.of("Asia/Singapore");
        LocalDateTime currentTime = LocalDateTime.now(zid);
        if (deadline.getTime().isBefore(currentTime)) {
            return new CommandResult(String.format(MESSAGE_SUCCESS + "\n" + MESSAGE_OVERDUE_WARNING,
                task.getTaskName().getTaskName(), groupsAdded), LIST_GROUP_TASK_MARKER);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, task.getTaskName().getTaskName(),
            groupsAdded), LIST_GROUP_TASK_MARKER);
    }

    @Override
    public VersionHistory updateVersionHistory(VersionHistory versionHistory, Model model) throws CommandException {
        versionHistory.addVersion(model);
        return versionHistory;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddTaskToGroupCommand)) {
            return false;
        }

        AddTaskToGroupCommand otherAddTaskToGroupCommand = (AddTaskToGroupCommand) other;
        return taskName.equals(otherAddTaskToGroupCommand.taskName)
            && deadline.equals(otherAddTaskToGroupCommand.deadline)
            && toAddInto.equals(otherAddTaskToGroupCommand.toAddInto);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("taskName", taskName)
            .add("deadline", deadline)
            .add("toAddInto", toAddInto)
            .toString();
    }
}
