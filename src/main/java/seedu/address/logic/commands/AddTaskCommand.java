package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESCRIPTION;

import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
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
 * Adds a task to a student in the address book.
 */
public class AddTaskCommand extends Command {

    public static final String COMMAND_WORD = "addtask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to a student. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_TASK_DESCRIPTION + "TASK DESCRIPTION "
            + PREFIX_TASK_DEADLINE + "TASK DEADLINE (YYYY-MM-DD)";

    public static final String MESSAGE_SUCCESS = "Added task: %1$s for Student %2$s by %3$s";
    public static final String MESSAGE_STUDENT_NOT_FOUND = "This student does not exist in the address book.";
    public static final String MESSAGE_DUPLICATE_TASK = "Task already exists in the student's task list!";

    private final Name name;
    private final Task taskToAdd;

    /**
     * Creates an AddTaskCommand to add the specified {@code Task} to a {@code Student}.
     */
    public AddTaskCommand(Name name, Task task) {
        requireNonNull(name);
        requireNonNull(task);
        this.name = name;
        this.taskToAdd = task;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Student targetStudent = model.getStudentByName(name);
        if (targetStudent == null) {
            throw new CommandException(MESSAGE_STUDENT_NOT_FOUND);
        }

        if (targetStudent.getTaskList().contains(taskToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        Student updatedStudent = createUpdatedStudent(targetStudent, taskToAdd);

        model.setStudent(targetStudent, updatedStudent);
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                taskToAdd.getTaskDescription(), targetStudent.getName(), taskToAdd.getTaskDeadline()),
                UiState.DETAILS);
    }

    /**
     * Creates a new Copy of {@code targetStudent} that contains {@code taskToAdd}
     * @param targetStudent the {@code Student} to be updated
     * @param taskToAdd the {@code Task} to be added into the targetStudent
     * @return
     */
    private static Student createUpdatedStudent(Student targetStudent, Task taskToAdd) {
        Name name = targetStudent.getName();
        Phone phone = targetStudent.getPhone();
        EmergencyContact emergencyContact = targetStudent.getEmergencyContact();
        Address address = targetStudent.getAddress();
        Note note = targetStudent.getNote();
        Set<Subject> subjects = targetStudent.getSubjects();
        Level level = targetStudent.getLevel();
        TaskList taskList = targetStudent.getTaskList().copy();
        taskList.add(taskToAdd);
        Set<LessonTime> lessonTimes = targetStudent.getLessonTimes();
        return new Student(name, phone, emergencyContact,
                address, note, subjects, level, taskList, lessonTimes);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddTaskCommand)) {
            return false;
        }

        AddTaskCommand otherAddTaskCommand = (AddTaskCommand) other;
        return name.equals(otherAddTaskCommand.name)
                && taskToAdd.equals(otherAddTaskCommand.taskToAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("taskToAdd", taskToAdd)
                .toString();
    }
}
