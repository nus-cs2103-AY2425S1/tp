package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentNumber;

/**
 * Deletes a student from the system based on the student number provided.
 */
public class DeleteStudentCommand extends Command {
    public static final String COMMAND_WORD = "deletes";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the student identified by the student name used in the displayed student list.\n"
            + "Parameters: NAME (must be a valid student name) STUDENT NUMBER (optional,"
            + " must be a valid student number)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "Jerrell Lee " + PREFIX_STUDENT_NUMBER + "A1234567X";

    public static final String MESSAGE_DELETE_STUDENT_SUCCESS = "Deleted Student: %1$s %2$s";

    public static final String MESSAGE_NONEXISTENT_STUDENT = "This student is not in your student list.";

    public static final String MESSAGE_DUPLICATE_STUDENT = "There is more than 1 student of the same name.\n"
            + "Their student numbers are as follows: %s" + "\n"
            + "Use the following command: " + COMMAND_WORD + " " + PREFIX_NAME + "%s "
            + PREFIX_STUDENT_NUMBER + "STUDENT NUMBER" + " to delete the student.";

    private final Name name;
    private final StudentNumber studentNumber;
    private Student studentToDelete;
    private int index;

    /**
     * Constructs a DeleteStudentCommand to delete the specified student by student name.
     *
     * @param name The student name of the student to be deleted.
     */
    public DeleteStudentCommand(Name name) {
        requireNonNull(name);
        this.name = name;
        this.studentNumber = null;
    }

    /**
     * Constructs a DeleteStudentCommand to delete the specified student by student name and student number.
     *
     * @param name The student name of the student to be deleted.
     * @param studentNumber The student number of the student to be deleted.
     */
    public DeleteStudentCommand(Name name, StudentNumber studentNumber) {
        requireNonNull(name);
        this.name = name;
        this.studentNumber = studentNumber;
    }

    /**
     * Executes the command to delete a student from the system.
     *
     * @param model The model in which the student will be deleted.
     * @return A CommandResult indicating the success of the command.
     * @throws CommandException If the student number is invalid or the student does not exist in the list.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> listToCheck = model.getAllStudentsByName(name);

        if (listToCheck.isEmpty()) {
            throw new CommandException(MESSAGE_NONEXISTENT_STUDENT);
        }

        if (this.studentNumber != null) {
            studentToDelete = listToCheck.stream()
                        .filter(stu -> stu.getStudentNumber().equals(studentNumber))
                        .reduce((a, b) -> b)
                        .orElse(null);
            if (studentToDelete == null) {
                throw new CommandException(MESSAGE_NONEXISTENT_STUDENT);
            }
            index = model.deleteStudent(studentToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_STUDENT_SUCCESS, name, studentNumber));
        }

        if (listToCheck.size() > 1) {
            String duplicateStudentNumbers = listToCheck.stream()
                    .map(s -> s.getStudentNumber().toString())
                    .reduce((a, b) -> a + " " + b)
                    .orElse("");
            throw new CommandException(String.format(MESSAGE_DUPLICATE_STUDENT, duplicateStudentNumbers, name));
        }

        studentToDelete = listToCheck.get(0);
        index = model.deleteStudent(studentToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_STUDENT_SUCCESS, name,
                studentToDelete.getStudentNumber()));
    }

    /**
     * Checks whether this DeleteStudentCommand is equal to another object.
     *
     * @param other The object to compare to.
     * @return True if the object is equal to this command, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteStudentCommand otherCommand)) {
            return false;
        }

        return name.equals(otherCommand.name)
                && ((studentNumber == null && otherCommand.studentNumber == null)
                || (studentNumber != null && studentNumber.equals(otherCommand.studentNumber)));
    }

    @Override
    public boolean undo(Model model) {
        assert studentToDelete != null;
        model.addStudent(index, studentToDelete);
        return true;
    }
}
