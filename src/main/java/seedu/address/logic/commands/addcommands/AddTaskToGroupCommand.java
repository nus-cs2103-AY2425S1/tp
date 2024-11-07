package seedu.address.logic.commands.addcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.ListMarkers.LIST_TASK_MARKER;
import static seedu.address.logic.Messages.MESSAGE_GROUP_NAME_NOT_FOUND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_NAME;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

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

    public static final String MESSAGE_SUCCESS = "Added task (%1$s) to the following group(s):%2$s";

    public static final String MESSAGE_OVERDUE_WARNING = "WARNING: Task will be marked as overdue";

    public static final String MESSAGE_TASK_EXISTS_GLOBAL = "This task (%1$s) already exists.\n"
        + " Please use the add existing task command instead.";

    public static final String MESSAGE_DUPLICATE_GROUP = "Duplicate group(s) entered, only 1 will be added:";

    private final TaskName taskName;

    private final Deadline deadline;

    private final List<GroupName> toAddInto;

    /**
     * Creates an AddStudentToGroupCommand to add the specified {@code Task} to the specified {@code Group}.
     */
    public AddTaskToGroupCommand(TaskName taskName, Deadline deadline, List<GroupName> groupNames) {
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
        String resultMessage = "";

        // create a stream consisting of all groups which are entered more than once by the user
        Stream<GroupName> checkForDuplicates = groups.stream().filter(x -> Collections.frequency(groups, x) > 1)
                .filter(y -> model.hasGroup(new Group(y)))
                .distinct();

        // count the number of duplicated groups entered
        long numDuplicates = groups.stream().filter(x -> Collections.frequency(groups, x) > 1)
                .filter(y -> model.hasGroup(new Group(y)))
                .distinct()
                .count();

        // create duplicate message for duplicate groups, if none will just return an empty string
        String duplicateMessage = checkForDuplicates.map(a -> a.getGroupName()).reduce(
                MESSAGE_DUPLICATE_GROUP, (x, y) -> x + "\n" + y);
        if (numDuplicates == 0) {
            duplicateMessage = "";
        }

        //  create a list without duplicate groups
        List<GroupName> noDuplicateGroupList = toAddInto.stream().distinct().toList();
        if (noDuplicateGroupList.size() > 1) {
            resultMessage += "\n";
        }

        int countModelDoesNotContainGroup = 0;
        String modelDoesNotContainGroup = MESSAGE_GROUP_NAME_NOT_FOUND;

        for (GroupName groupName : noDuplicateGroupList) {
            if (!model.containsGroupName(groupName)) {
                countModelDoesNotContainGroup++;
                modelDoesNotContainGroup += "\n" + groupName.getGroupName();
                toAddInto.removeAll(List.of(groupName));
                continue;
            }
            Group group = model.getGroupByName(groupName);

            model.addTaskToGroup(task, group);
            if (!model.hasTask(task)) {
                model.addTask(task);
            } else {
                model.increaseGroupWithTask(task);
            }
            resultMessage += groupName.getGroupName() + "\n";
        }
        if (countModelDoesNotContainGroup == noDuplicateGroupList.size()) {
            throw new CommandException(modelDoesNotContainGroup);
        }
        model.setMostRecentTaskDisplay(task);
        model.updateFilteredTaskList(x -> x.equals(task));
        model.setStateTasks();
        ZoneId zid = ZoneId.of("Asia/Singapore");
        LocalDateTime currentTime = LocalDateTime.now(zid);
        String finalOutput = "";
        if (numDuplicates > 0) {
            finalOutput = duplicateMessage + "\n" + String.format(MESSAGE_SUCCESS, task.getTaskName().getTaskName(),
                resultMessage);
        } else {
            finalOutput = String.format(MESSAGE_SUCCESS, task.getTaskName().getTaskName(), resultMessage);
        }
        if (countModelDoesNotContainGroup > 0) {
            finalOutput += modelDoesNotContainGroup + "\n";
        }
        if (deadline.getTime().isBefore(currentTime)) {
            return new CommandResult(finalOutput + MESSAGE_OVERDUE_WARNING, LIST_TASK_MARKER);
        }
        return new CommandResult(finalOutput, LIST_TASK_MARKER);
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
