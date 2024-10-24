package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
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

    public static final String MESSAGE_DELETE_STUDENT_SUCCESS = "Deleted Student: ";

    public static final String MESSAGE_NONEXISTENT_STUDENT = "This student is not in your student list.";

    public static final String MESSAGE_DUPLICATE_STUDENT = "There is more than 1 student of the same name.\n"
            + "Their student numbers are as follows: %s" + "\n"
            + "Use the following command: " + COMMAND_WORD + " " + PREFIX_NAME + "%s "
            + PREFIX_STUDENT_NUMBER + "STUDENT NUMBER" + " to delete the student.";

    private final Name name;

    private final StudentNumber studentNumber;

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
        ObservableList<Student> studentList = model.getFilteredStudentList();
        ArrayList<Student> listToCheck = new ArrayList<>();

        for (Student student : studentList) {
            if (student.getName().equals(name)) {
                listToCheck.add(student);
            }
        }

        if (listToCheck.isEmpty()) {
            throw new CommandException(MESSAGE_NONEXISTENT_STUDENT);
        }

        // if there are multiple students with the same name, ensure that student number is provided
        if (listToCheck.size() > 1 && this.studentNumber == null) {
            StringBuilder duplicates = new StringBuilder();
            for (Student student : listToCheck) {
                duplicates.append(student.getStudentNumber());
                duplicates.append(" ");
            }
            String duplicateStudentNumbers = duplicates.toString();
            throw new CommandException(String.format(MESSAGE_DUPLICATE_STUDENT, duplicateStudentNumbers, name));
        }

        if (listToCheck.size() > 1) {
            for (Student student : listToCheck) {
                if (student.getStudentNumber().equals(studentNumber)) {
                    model.deleteStudent(student);
                    return new CommandResult(String.format(MESSAGE_DELETE_STUDENT_SUCCESS) + student.getName()
                            + " " + student.getStudentNumber());
                }
            }
            throw new CommandException(MESSAGE_NONEXISTENT_STUDENT);
        }

        // happy path if exactly 1 student with the name is found
        Student studentToDelete = model.getStudentByName(name);

        if (studentToDelete == null) {
            throw new CommandException(MESSAGE_NONEXISTENT_STUDENT);
        }

        // if student number is provided, check that it matches the student number of the student to delete
        if (!studentToDelete.getStudentNumber().equals(this.studentNumber) && this.studentNumber != null) {
            throw new CommandException(MESSAGE_NONEXISTENT_STUDENT);
        }

        model.deleteStudent(studentToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_STUDENT_SUCCESS) + studentToDelete.getName()
                + " " + studentToDelete.getStudentNumber());
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

        if (!(other instanceof DeleteStudentCommand)) {
            return false;
        }

        DeleteStudentCommand otherDeleteStudentCommand = (DeleteStudentCommand) other;
        return name.equals(otherDeleteStudentCommand.name)
                && ((studentNumber == null && otherDeleteStudentCommand.studentNumber == null)
                || (studentNumber != null && studentNumber.equals(otherDeleteStudentCommand.studentNumber)));
    }

    @Override
    public boolean undo(Model model) {
        return true;
    }
}
