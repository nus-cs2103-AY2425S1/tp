package seedu.address.logic.commands.addcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_NAME;

import javafx.collections.ObservableList;
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

    public static final String COMMAND_WORD = "add_task_all";
    public static final String COMMAND_WORD_ALIAS = "ata";
    public static final int LIST_TASK_MARKER = 2;

    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_ALIAS
            + ": Adds a task to all groups.\n"
            + "Parameters: "
            + PREFIX_TASK_NAME + "TASK_NAME "
            + PREFIX_TASK_DEADLINE + "TASK_DATE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TASK_NAME + "Complete this task "
            + PREFIX_TASK_DEADLINE + "2024-01-01 1300 ";

    public static final String MESSAGE_SUCCESS = "Added task: %1$s";

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
        ObservableList<Group> groups = model.getFilteredGroupList();
        if (!model.hasTask(task)) {
            task.decreaseGroupWithTask();
            model.addTask(task);
        }
        for (Group g: groups) {
            if (!model.hasTaskInGroup(task, g)) {
                model.addTaskToGroup(task, g);
                model.increaseGroupWithTask(task);
            }
        }
        model.setMostRecentTaskDisplay(task);
        model.updateFilteredTaskList(x -> x.equals(task));
        model.setStateTasks();
        return new CommandResult(String.format(MESSAGE_SUCCESS, task.getTaskName().toString(),
                task.getTaskName().taskName), LIST_TASK_MARKER);
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
