package seedu.address.logic.commands.addcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_GROUP_NAME_NOT_FOUND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_NAME;

import java.time.LocalDateTime;
import java.time.ZoneId;

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

    public static final String COMMAND_WORD = "add_task_grp";
    public static final String COMMAND_WORD_ALIAS = "atg";
    public static final int LIST_GROUP_TASK_MARKER = 3;

    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_ALIAS
        + ": Adds a task to a group.\n"
        + "Parameters: "
        + PREFIX_TASK_NAME + "TASK_NAME "
        + PREFIX_TASK_DEADLINE + "TASK_DATE "
        + PREFIX_GROUP_NAME + "GROUP_NAME\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_TASK_NAME + "Complete this task "
        + PREFIX_TASK_DEADLINE + "2024-01-01 1300 "
        + PREFIX_GROUP_NAME + "Team 1";

    public static final String MESSAGE_SUCCESS = "Added task: %1$s to %2$s";

    public static final String MESSAGE_OVERDUE_WARNING = "WARNING: Task will be marked as overdue";

    public static final String MESSAGE_DUPLICATE_TASK_IN_GROUP = "This task is already in the group";

    private final TaskName taskName;

    private final Deadline deadline;

    private final GroupName toAddInto;

    /**
     * Creates an AddStudentToGroupCommand to add the specified {@code Task} to the specified {@code Group}.
     */
    public AddTaskToGroupCommand(TaskName taskName, Deadline deadline, GroupName groupName) {
        requireNonNull(taskName);
        requireNonNull(groupName);
        this.taskName = taskName;
        this.deadline = deadline;
        this.toAddInto = groupName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.containsGroupName(toAddInto)) {
            throw new CommandException(MESSAGE_GROUP_NAME_NOT_FOUND);
        }

        Task task = new Task(taskName, deadline);
        Group group = model.getGroupByName(toAddInto);

        if (model.hasTaskInGroup(task, group)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK_IN_GROUP);
        }

        model.addTaskToGroup(task, group);
        if (!model.hasTask(task)) {
            model.addTask(task);
        } else {
            model.increaseGroupWithTask(task);
        }
        model.setMostRecentGroupTaskDisplay(group.getGroupName().fullName);
        model.updateFilteredGroupList(x -> x.getGroupName().equals(group.getGroupName()));
        model.setStateGroupTask();
        ZoneId zid = ZoneId.of("Asia/Singapore");
        LocalDateTime currentTime = LocalDateTime.now(zid);
        if (deadline.time.compareTo(currentTime) < 0) {
            return new CommandResult(String.format(MESSAGE_SUCCESS + "\n" + MESSAGE_OVERDUE_WARNING,
                    task.getTaskName().toString(), group.getGroupName().fullName), LIST_GROUP_TASK_MARKER);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, task.getTaskName().toString(),
            group.getGroupName().fullName), LIST_GROUP_TASK_MARKER);
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
