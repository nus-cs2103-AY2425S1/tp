package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_INDEX;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.task.Task;
import seedu.address.model.person.task.TaskDeadline;
import seedu.address.model.person.task.TaskDescription;
import seedu.address.model.person.task.TaskList;
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
            + "[" + PREFIX_TASK_DESCRIPTION + "TASK] "
            + "[" + PREFIX_TASK_DEADLINE + "DEADLINE] \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "Cristiano Ronaldo "
            + PREFIX_TASK_INDEX + "1 " + PREFIX_TASK_DESCRIPTION + "Handle MC";

    public static final String MESSAGE_UPDATE_TASK_SUCCESS = "Updated task: %1$s for Student %2$s by %3$s";
    public static final String MESSAGE_TASK_NOT_FOUND = "Task at index %1$d not found for %2$s";
    public static final String MESSAGE_NOT_UPDATED = "At least one field to be updated must be provided.";

    private final Name name;
    private final Index taskIndex;
    private final UpdateTaskDescriptor updateTaskDescriptor;

    /**
     * @param name of the student in the filtered person list to update
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
        List<Person> lastShownList = model.getFilteredPersonList();

        if (name.toString().isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_UPDATE);
        }

        Person personToUpdate = lastShownList.stream()
                .filter(person -> person.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new CommandException(Messages.MESSAGE_INVALID_PERSON_UPDATE));

        TaskList taskList = personToUpdate.getTaskList();
        if (taskIndex.getZeroBased() >= taskList.asUnmodifiableObservableList().size()
                || taskIndex.getOneBased() < 0) {
            throw new CommandException(String.format(MESSAGE_TASK_NOT_FOUND,
                    taskIndex.getOneBased(), personToUpdate.getName()));
        }

        Person updatedPerson = createUpdatedPerson(personToUpdate, taskIndex, updateTaskDescriptor);
        Task originalTask = personToUpdate.getTaskList().get(taskIndex.getZeroBased());

        model.setPerson(personToUpdate, updatedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        TaskDescription updatedTaskDescription = updateTaskDescriptor.getTaskDescription()
                .orElse(originalTask.getTaskDescription());
        TaskDeadline updatedTaskDeadline = updateTaskDescriptor.getTaskDeadline()
                .orElse(originalTask.getTaskDeadline());

        return new CommandResult(String.format(MESSAGE_UPDATE_TASK_SUCCESS, updatedTaskDescription,
                updatedPerson.getName(), updatedTaskDeadline), UiState.DETAILS);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToUpdate}
     * updated with {@code updateTaskDescriptor} at the specified task index.
     */
    private static Person createUpdatedPerson(Person personToUpdate, Index taskIndex,
                                              UpdateTaskDescriptor updateTaskDescriptor) {
        assert personToUpdate != null;

        Task originalTask = personToUpdate.getTaskList().get(taskIndex.getZeroBased());
        TaskDescription updatedTaskDescription = updateTaskDescriptor.getTaskDescription()
                .orElse(originalTask.getTaskDescription());
        TaskDeadline updatedTaskDeadline = updateTaskDescriptor.getTaskDeadline()
                .orElse(originalTask.getTaskDeadline());
        Task updatedTask = new Task(updatedTaskDescription, updatedTaskDeadline);
        TaskList updatedTaskList = personToUpdate.getTaskList().updateTask(taskIndex, updatedTask);

        return new Person(
                personToUpdate.getName(), personToUpdate.getPhone(), personToUpdate.getEmergencyContact(),
                personToUpdate.getAddress(), personToUpdate.getNote(),
                personToUpdate.getSubjects(), personToUpdate.getLevel(), updatedTaskList,
                personToUpdate.getLessonTimes());
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
