package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.person.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentNumber;

/**
 * Adds an assignment to a student.
 */
public class AddAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "adda";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an assignment to a student. "
            + "Parameters: "
            + PREFIX_NAME + "STUDENT_NAME "
            + PREFIX_STUDENT_NUMBER + "STUDENT NUMBER "
            + PREFIX_ASSIGNMENT + "ASSIGNMENT "
            + PREFIX_DEADLINE + "DEADLINE "
            + PREFIX_STATUS + "SUBMISSION STATUS (OPTIONAL) "
            + PREFIX_STATUS + "GRADING STATUS (OPTIONAL) "
            + PREFIX_GRADE + "GRADE (OPTIONAL) "
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Jane Doe "
            + PREFIX_ASSIGNMENT + "Math Quiz "
            + PREFIX_DEADLINE + "2024-10-09 "
            + PREFIX_STATUS + "N "
            + PREFIX_STATUS + "N "
            + PREFIX_GRADE + "NULL ";

    public static final String MESSAGE_SUCCESS = "New assignment added: %1$s to student %2$s";
    public static final String MESSAGE_NO_STUDENT_FOUND = "No such student found!";
    public static final String MESSAGE_DUPLICATE_ASSIGNMENT_FOUND = "There is already an existing assignment named "
            + "%1$s for student %2$s";

    public static final String MESSAGE_DUPLICATE_STUDENT = "There is more than 1 student of the same name \n"
            + "Their student numbers are as follows: %1$s \n"
            + MESSAGE_USAGE;

    public final Assignment assignment;
    public final Name name;
    public final Optional<StudentNumber> studentNumber;

    private Student student;

    /**
     * Creates an AddAssignmentCommand to add the specified {@code Assignment}
     */
    public AddAssignmentCommand(Name name, Assignment assignment) {
        requireAllNonNull(name, assignment);
        this.assignment = assignment;
        this.name = name;
        this.studentNumber = Optional.empty();
    }

    /**
     * Creates an AddAssignmentCommand to add the specified {@code Assignment}
     */
    public AddAssignmentCommand(Name name, Assignment assignment, StudentNumber studentNumber) {
        requireAllNonNull(name, assignment, studentNumber);
        this.assignment = assignment;
        this.name = name;
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
            if (!student.addAssignment(assignment)) {
                throw new CommandException(String.format(MESSAGE_DUPLICATE_ASSIGNMENT_FOUND,
                        assignment.getAssignmentName(), student.getName()));
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

        if (!student.addAssignment(assignment)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_ASSIGNMENT_FOUND,
                    assignment.getAssignmentName(), student.getName()));
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, assignment.getAssignmentName(), student.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddAssignmentCommand)) {
            return false;
        }

        AddAssignmentCommand otherCommand = (AddAssignmentCommand) other;
        return otherCommand.assignment.equals(this.assignment)
                && otherCommand.name.equals(this.name)
                && otherCommand.studentNumber.equals(this.studentNumber);
    }

    @Override
    public boolean undo(Model model) {
        student.deleteAssignment(assignment.getAssignmentName());
        return true;
    }
}
