package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_INDEX;

import java.util.Optional;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.Address;
import seedu.address.model.student.EmergencyContact;
import seedu.address.model.student.LessonTime;
import seedu.address.model.student.Level;
import seedu.address.model.student.Name;
import seedu.address.model.student.Note;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Student;
import seedu.address.model.student.Subject;
import seedu.address.model.student.task.Task;
import seedu.address.model.student.task.TaskList;
import seedu.address.ui.Ui.UiState;

/**
 * Deletes a Task  from a Student identified using it's displayed index from the address book.
 */
public class DeleteTaskCommand extends Command {

    public static final String COMMAND_WORD = "deletetask";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the student and index number used in the displayed task list.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME (must be exact name of an existing student) "
            + PREFIX_TASK_INDEX + "INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_TASK_INDEX + "1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s for %2$s by %3$s";

    private final Name targetName;
    private final Index targetIndex;

    /**
     * Creates an DeleteTaskCommand to delete a task from specified User at the specified Displayed Index{@code Student}
     */
    public DeleteTaskCommand(Name targetName, Index targetIndex) {
        requireNonNull(targetName);
        requireNonNull(targetIndex);
        this.targetName = targetName;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Get the student we are looking for
        ObservableList<Student> studentList = model.getAddressBook().getStudentList();
        Optional<Student> optionalStudent = studentList.stream()
                .filter(x -> x.getName().equals(targetName))
                .findFirst();
        if (optionalStudent.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_NAME);
        }
        Student targetStudent = optionalStudent.get();

        // Get the Task to be deleted
        TaskList taskList = targetStudent.getTaskList();
        if (targetIndex.getZeroBased() >= taskList.asUnmodifiableObservableList().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        Task taskToDelete = taskList.get(targetIndex.getZeroBased());

        //Creates a new Student with the task removed
        Student updatedStudent = createUpdatedStudent(targetStudent, taskToDelete);
        model.setStudent(targetStudent, updatedStudent);
        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS,
                taskToDelete.getTaskDescription(),
                targetStudent.getName(),
                taskToDelete.getTaskDeadline()),
                UiState.DETAILS);
    }

    /**
     * Creates a new instance of Student with the task deleted
     * @param targetStudent the {@code Student} to be copied over
     * @param taskToDelete the {@code Task} to be removed
     * @return a new instance of the targetStudent with the taskToDelete removed
     */
    private static Student createUpdatedStudent(Student targetStudent, Task taskToDelete) {
        Name name = targetStudent.getName();
        Phone phone = targetStudent.getPhone();
        EmergencyContact emergencyContact = targetStudent.getEmergencyContact();
        Address address = targetStudent.getAddress();
        Note note = targetStudent.getNote();
        Set<Subject> subjects = targetStudent.getSubjects();
        Level level = targetStudent.getLevel();
        TaskList taskList = targetStudent.getTaskList().copy();
        taskList.remove(taskToDelete);
        Set<LessonTime> lessonTimes = targetStudent.getLessonTimes();
        return new Student(name, phone, emergencyContact,
                address, note, subjects, level, taskList, lessonTimes);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteTaskCommand)) {
            return false;
        }

        DeleteTaskCommand otherDeleteTaskCommand = (DeleteTaskCommand) other;
        boolean isSameTargetIndex = targetIndex.equals(otherDeleteTaskCommand.targetIndex);
        boolean isSameTargetStudent = targetName.equals(otherDeleteTaskCommand.targetName);
        return isSameTargetIndex && isSameTargetStudent;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetName", targetName)
                .add("targetIndex", targetIndex)
                .toString();
    }
}

