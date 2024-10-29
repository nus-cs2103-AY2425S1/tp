package seedu.address.logic.commands.editcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.VersionHistory;
import seedu.address.model.group.Group;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskName;

/**
 * Edits the details of an existing task in the address book for all groups having the task.
 */
public class EditTaskAllGroupCommand extends Command {

    public static final String COMMAND_WORD = "edit_task";
    public static final String COMMAND_WORD_ALIAS = "et";


    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_ALIAS
        + ": Edits the details of the task of all group with given task.\n"
        + "Field like task status should not be modified through this function."
        + "Parameters: "
        + PREFIX_INDEX + "INDEX(must be an integer) "
        + "[" + PREFIX_TASK_NAME + "TASK NAME] "
        + "[" + PREFIX_TASK_DEADLINE + "DEADLINE]\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_INDEX + "1 "
        + PREFIX_TASK_NAME + "Complete Assignment "
        + PREFIX_TASK_DEADLINE + "2024-12-12 1800";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "Duplicated task.";
    public static final String MESSAGE_INVALID_FILED_STATUS = "Task status should not be modified";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;
    private final EditTaskDescriptor editTaskDescriptor;

    /**
     * @param index of the task in the task list
     * @param editTaskDescriptor details to edit the task with
     */
    public EditTaskAllGroupCommand(Index index, EditTaskDescriptor editTaskDescriptor) {
        requireNonNull(index);
        requireNonNull(editTaskDescriptor);
        this.index = index;
        this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();
        List<Group> groups = model.getFilteredGroupList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }
        Task taskToEdit = lastShownList.get(index.getZeroBased());
        Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor);

        if (!taskToEdit.isSameTask(editedTask) && model.hasTask(editedTask)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        for (Group group : groups) {
            if (group.hasTask(taskToEdit)) {
                model.setTask(taskToEdit, editedTask, group);
                model.decreaseGroupWithTask(taskToEdit);
                System.out.println("find group alr");
                if (!model.hasTask(editedTask)) {
                    model.addTask(editedTask);
                } else {
                    model.increaseGroupWithTask(editedTask);
                }
                System.out.println("increment");
            }
        }
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS,
            Messages.format(editedTask)));
    }

    @Override
    public VersionHistory updateVersionHistory(VersionHistory versionHistory, Model model) throws CommandException {
        versionHistory.addVersion(model);
        return versionHistory;
    }

    /**
     * Creates and returns a {@code Student} with the details of {@code studentToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Task createEditedTask(Task taskToEdit, EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;

        TaskName updatedName = editTaskDescriptor.getTaskName().orElse(taskToEdit.getTaskName());
        Deadline updatedDeadline = editTaskDescriptor.getDeadline().orElse(taskToEdit.getDeadline());
        Status status = taskToEdit.getStatus();
        int numGroupHasTask = taskToEdit.getGroupsWithTask();
        return new Task(updatedName, updatedDeadline, status, numGroupHasTask);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditTaskAllGroupCommand)) {
            return false;
        }

        EditTaskAllGroupCommand otherEditCommand = (EditTaskAllGroupCommand) other;
        return index.equals(otherEditCommand.index)
            && editTaskDescriptor.equals(otherEditCommand.editTaskDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("index", index)
            .add("editPersonDescriptor", editTaskDescriptor)
            .toString();
    }

    /**
     * Stores the details to edit the student with. Each non-empty field value will replace the
     * corresponding field value of the student.
     */
    public static class EditTaskDescriptor {
        private TaskName taskName;
        private Deadline deadline;

        public EditTaskDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            setTaskName(toCopy.taskName);
            setDeadline(toCopy.deadline);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(taskName, deadline);
        }

        public void setTaskName(TaskName taskName) {
            this.taskName = taskName;
        }

        public Optional<TaskName> getTaskName() {
            return Optional.ofNullable(taskName);
        }
        public void setDeadline(Deadline deadline) {
            this.deadline = deadline;
        }
        public Optional<Deadline> getDeadline() {
            return Optional.ofNullable(deadline);
        }
        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditTaskDescriptor)) {
                return false;
            }

            EditTaskDescriptor otherEditTaskDescriptor = (EditTaskDescriptor) other;
            return Objects.equals(taskName, otherEditTaskDescriptor.taskName)
                && Objects.equals(deadline, otherEditTaskDescriptor.deadline);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                .add("taskName", taskName)
                .add("deadline", deadline)
                .toString();
        }
    }
}
