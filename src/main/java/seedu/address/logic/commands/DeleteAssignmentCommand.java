package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentNumber;


/**
 * Deletes an assignment.
 */
public class DeleteAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "deletea";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes an assignment for a student. "
            + "Parameters: "
            + PREFIX_NAME + "STUDENT_NAME "
            + PREFIX_ASSIGNMENT + "ASSIGNMENT "
            + PREFIX_STUDENT_NUMBER + "STUDENT NUMBER (OPTIONAL) "
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_ASSIGNMENT + "Math Quiz ";

    public static final String MESSAGE_SUCCESS = "Deleted Assignment: %1$s from %2$s";

    public static final String MESSAGE_NO_STUDENT_FOUND = "No such student found!";
    public static final String MESSAGE_NO_ASSIGNMENT_FOUND = "No such assignment found!";
    public static final String MESSAGE_DUPLICATE_STUDENT = "There is more than 1 student of the same name \n"
            + "Their student numbers are as follows: %1$s \n"
            + MESSAGE_USAGE;

    public final AssignmentName assignmentName;
    public final Name name;
    public final Optional<StudentNumber> studentNumber;

    private Assignment assignment;
    private Student student;

    /**
     * Creates an DeleteAssignmentCommand to add the specified {@code Assignment}
     */
    public DeleteAssignmentCommand(Name name, AssignmentName assignmentName) {
        this.name = name;
        this.assignmentName = assignmentName;
        this.studentNumber = Optional.empty();
    }

    /**
     * Creates an DeleteAssignmentCommand to add the specified {@code Assignment}
     */
    public DeleteAssignmentCommand(Name name, AssignmentName assignmentName, StudentNumber studentNumber) {
        this.name = name;
        this.assignmentName = assignmentName;
        this.studentNumber = Optional.of(studentNumber);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> studentList = model.getAllStudentsByName(name);

        if (this.studentNumber.isPresent()) {
            List<Student> filteredStudentList =
                    studentList.stream().filter(s -> s.getStudentNumber().equals(studentNumber.get())).toList();
            if (filteredStudentList.isEmpty()) {
                throw new CommandException(MESSAGE_NO_STUDENT_FOUND);
            }
            student = filteredStudentList.get(0);
            assignment = student.deleteAssignment(assignmentName);
            if (assignment == null) {
                throw new CommandException(MESSAGE_NO_ASSIGNMENT_FOUND);
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS, assignment.getAssignmentName(), student.getName()));
        }

        if (studentList.isEmpty()) {
            throw new CommandException(MESSAGE_NO_STUDENT_FOUND);
        }

        if (studentList.size() > 1) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_STUDENT,
                    studentList.stream()
                            .map(s -> s.getStudentNumber().toString())
                            .collect(Collectors.joining(", "))));
        }

        student = studentList.get(0);
        assignment = student.deleteAssignment(assignmentName);
        if (assignment == null) {
            throw new CommandException(MESSAGE_NO_ASSIGNMENT_FOUND);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, assignment.getAssignmentName(), student.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteAssignmentCommand)) {
            return false;
        }

        DeleteAssignmentCommand otherCommand = (DeleteAssignmentCommand) other;
        return otherCommand.name.equals(this.name)
                && otherCommand.assignmentName.equals(this.assignmentName)
                && this.studentNumber.equals(otherCommand.studentNumber);
    }

    @Override
    public boolean undo(Model model) {
        // The command will only have been executed if assignment was assigned a value
        if (assignment == null) {
            return false;
        }

        student.addAssignment(assignment);
        return true;
    }
}
