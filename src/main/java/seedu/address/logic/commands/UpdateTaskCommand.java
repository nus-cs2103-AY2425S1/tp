package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_INDEX;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.task.Task;
import seedu.address.model.student.task.TaskDeadline;
import seedu.address.model.student.task.TaskDescription;
import seedu.address.model.student.task.TaskList;
import seedu.address.ui.Ui.UiState;

/**
 * Updates the details of an existing task in a student's task list.
 */
public class UpdateTaskCommand extends Command {

    public static final String COMMAND_WORD = "updatetask";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Updates the details of an existing task in a student's task list at the specified task index. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_TASK_INDEX + "TASK_INDEX "
            + "[" + PREFIX_TASK_DESCRIPTION + "TASK_DESCRIPTION] "
            + "[" + PREFIX_TASK_DEADLINE + "TASK_DEADLINE (YYYY-MM-DD)] \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "Cristiano Ronaldo "
            + PREFIX_TASK_INDEX + "1 " + PREFIX_TASK_DESCRIPTION + "Handle MC";

    public static final String MESSAGE_UPDATE_TASK_SUCCESS = "Updated task: %1$s for Student %2$s by %3$s";
    public static final String MESSAGE_TASK_NOT_FOUND = "Task at index %1$d not found for %2$s";
    public static final String MESSAGE_NOT_UPDATED = "At least one field to be updated must be provided.";

    private final Name name;
    private final Index taskIndex;
    private final UpdateTaskDescriptor updateTaskDescriptor;

    /**
     * @param name of the student in the filtered student list to update
     * @param taskIndex index of the task in the student's task list
     * @param updateTaskDescriptor details to update the task with
     */
    public UpdateTaskCommand(Name name, Index taskIndex, UpdateTaskDescriptor updateTaskDescriptor) {
        requireNonNull(name);
        requireNonNull(updateTaskDescriptor);

        this.name = name;
        this.taskIndex = taskIndex;
        this.updateTaskDescriptor = new UpdateTaskDescriptor(updateTaskDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();

        if (name.toString().isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_UPDATE);
        }

        Student studentToUpdate = lastShownList.stream()
                .filter(student -> student.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new CommandException(Messages.MESSAGE_INVALID_STUDENT_UPDATE));

        TaskList taskList = studentToUpdate.getTaskList();
        if (taskIndex.getZeroBased() >= taskList.asUnmodifiableObservableList().size()
                || taskIndex.getOneBased() < 0) {
            throw new CommandException(String.format(MESSAGE_TASK_NOT_FOUND,
                    taskIndex.getOneBased(), studentToUpdate.getName()));
        }

        Student updatedStudent = createUpdatedStudent(studentToUpdate, taskIndex, updateTaskDescriptor);
        Task originalTask = studentToUpdate.getTaskList().get(taskIndex.getZeroBased());

        model.setStudent(studentToUpdate, updatedStudent);
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);

        TaskDescription updatedTaskDescription = updateTaskDescriptor.getTaskDescription()
                .orElse(originalTask.getTaskDescription());
        TaskDeadline updatedTaskDeadline = updateTaskDescriptor.getTaskDeadline()
                .orElse(originalTask.getTaskDeadline());

        return new CommandResult(String.format(MESSAGE_UPDATE_TASK_SUCCESS, updatedTaskDescription,
                updatedStudent.getName(), updatedTaskDeadline), UiState.DETAILS);
    }

    /**
     * Creates and returns a {@code Student} with the details of {@code studentToUpdate}
     * updated with {@code updateTaskDescriptor} at the specified task index.
     */
    private static Student createUpdatedStudent(Student studentToUpdate, Index taskIndex,
                                                UpdateTaskDescriptor updateTaskDescriptor) {
        assert studentToUpdate != null;

        Task originalTask = studentToUpdate.getTaskList().get(taskIndex.getZeroBased());
        TaskDescription updatedTaskDescription = updateTaskDescriptor.getTaskDescription()
                .orElse(originalTask.getTaskDescription());
        TaskDeadline updatedTaskDeadline = updateTaskDescriptor.getTaskDeadline()
                .orElse(originalTask.getTaskDeadline());
        Task updatedTask = new Task(updatedTaskDescription, updatedTaskDeadline);
        TaskList updatedTaskList = studentToUpdate.getTaskList().updateTask(taskIndex, updatedTask);

        return new Student(
                studentToUpdate.getName(), studentToUpdate.getPhone(), studentToUpdate.getEmergencyContact(),
                studentToUpdate.getAddress(), studentToUpdate.getNote(),
                studentToUpdate.getSubjects(), studentToUpdate.getLevel(), updatedTaskList,
                studentToUpdate.getLessonTimes());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpdateTaskCommand)) {
            return false;
        }

        UpdateTaskCommand otherUpdateTaskCommand = (UpdateTaskCommand) other;
        return name.equals(otherUpdateTaskCommand.name)
                && taskIndex.equals(otherUpdateTaskCommand.taskIndex)
                && updateTaskDescriptor.equals(otherUpdateTaskCommand.updateTaskDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("task index", taskIndex)
                .add("updateTaskDescriptor", updateTaskDescriptor)
                .toString();
    }

    /**
     * Stores the details to update the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class UpdateTaskDescriptor {
        private TaskDeadline taskDeadline;
        private TaskDescription taskDescription;

        public UpdateTaskDescriptor() {}

        /**
         * Copy constructor.
         */
        public UpdateTaskDescriptor(UpdateTaskDescriptor toCopy) {
            setTaskDescription(toCopy.taskDescription);
            setTaskDeadline(toCopy.taskDeadline);
        }

        /**
         * Returns true if at least one field is updated.
         */
        public boolean isAnyFieldUpdated() {
            return CollectionUtil.isAnyNonNull(taskDeadline, taskDescription);
        }

        public void setTaskDescription(TaskDescription taskDescription) {
            this.taskDescription = taskDescription;
        }

        public Optional<TaskDescription> getTaskDescription() {
            return Optional.ofNullable(taskDescription);
        }

        public void setTaskDeadline(TaskDeadline taskDeadline) {
            this.taskDeadline = taskDeadline;
        }

        public Optional<TaskDeadline> getTaskDeadline() {
            return Optional.ofNullable(taskDeadline);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof UpdateTaskDescriptor)) {
                return false;
            }

            UpdateTaskDescriptor otherDescriptor = (UpdateTaskDescriptor) other;
            return Objects.equals(taskDescription, otherDescriptor.taskDescription)
                    && Objects.equals(taskDeadline, otherDescriptor.taskDeadline);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("task description", taskDescription)
                    .add("task deadline", taskDeadline)
                    .toString();
        }
    }
}
