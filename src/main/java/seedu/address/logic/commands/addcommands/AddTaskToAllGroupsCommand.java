package seedu.address.logic.commands.addcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.ListMarkers.LIST_TASK_MARKER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_NAME;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.VersionHistory;
import seedu.address.model.group.Group;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskName;

/**
 * Adds a task to all groups.
 */
public class AddTaskToAllGroupsCommand extends Command {

    public static final String COMMAND_WORD = "add_t";
    public static final String COMMAND_WORD_ALIAS = "at";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_ALIAS
        + ": Adds a task to all groups.\n"
        + "Parameters: "
        + PREFIX_TASK_NAME + "TASK_NAME "
        + PREFIX_TASK_DEADLINE + "TASK_DATE\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_TASK_NAME + "Complete this task "
        + PREFIX_TASK_DEADLINE + "2025-01-01 1300";

    public static final String MESSAGE_SUCCESS = "Added task: %1$s";
    public static final String MESSAGE_OVERDUE_WARNING = "WARNING: Task will be marked as overdue";
    public static final String NO_GROUPS = "There are currently no groups.";

    private final TaskName taskName;
    private final Deadline deadline;

    /**
     * Creates an AddTaskToAllGroupsCommand to add the specified {@code Task} to all groups.
     */
    public AddTaskToAllGroupsCommand(TaskName taskName, Deadline deadline) {
        requireNonNull(taskName);
        this.taskName = taskName;
        this.deadline = deadline;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Task task = new Task(taskName, deadline);
        List<Group> groups = model.getAddressBook().getGroupList();
        if (groups.size() == 0) {
            throw new CommandException(NO_GROUPS);
        }
        if (!model.hasTask(task)) {
            task.decreaseGroupWithTask();
            model.addTask(task);
        }
        for (Group g : groups) {
            if (!g.hasTask(task)) {
                model.addTaskToGroup(task, g);
                model.increaseGroupWithTask(task);
            }
        }
        model.setMostRecentTaskDisplay(task);
        model.updateFilteredTaskList(x -> x.equals(task));
        model.updateFilteredGroupList(Model.PREDICATE_SHOW_ALL_GROUPS);
        model.setStateTasks();
        ZoneId zid = ZoneId.of("Asia/Singapore");
        LocalDateTime currentTime = LocalDateTime.now(zid);
        if (deadline.getTime().isBefore(currentTime)) {
            return new CommandResult(String.format(MESSAGE_SUCCESS + "\n" + MESSAGE_OVERDUE_WARNING,
                task.getTaskName().toString()), LIST_TASK_MARKER);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, task.getTaskName().getTaskName(),
            task.getTaskName().getTaskName()), LIST_TASK_MARKER);
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

        AddTaskToAllGroupsCommand otherAddTaskToAllGroupsCommand = (AddTaskToAllGroupsCommand) other;
        return taskName.equals(otherAddTaskToAllGroupsCommand.taskName)
            && deadline.equals(otherAddTaskToAllGroupsCommand.deadline);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("taskName", taskName)
            .add("deadline", deadline)
            .toString();
    }
}
