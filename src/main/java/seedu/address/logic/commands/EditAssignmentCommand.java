package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.assignment.AssignmentQuery;
import seedu.address.model.assignment.Grade;
import seedu.address.model.assignment.Status;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentNumber;

/**
 * Creates an EditAssignmentCommand to edit the specified {@code Assignment}
 */
public class EditAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "edita";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits an assignment for a student. "
            + "Parameters: "
            + PREFIX_NAME + "STUDENT_NAME "
            + PREFIX_ASSIGNMENT + "ASSIGNMENT "
            + PREFIX_DEADLINE + "DEADLINE (OPTIONAL) "
            + PREFIX_STATUS + "SUBMISSION STATUS (OPTIONAL) "
            + PREFIX_GRADE + "GRADE (OPTIONAL) "
            + PREFIX_STUDENT_NUMBER + "STUDENT NUMBER (OPTIONAL) "
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_ASSIGNMENT + "Math Quiz "
            + PREFIX_STATUS + "Y ";

    public static final String MESSAGE_SUCCESS = "Edited Assignment: %1$s from %2$s";
    public static final String MESSAGE_NO_STUDENT_FOUND = "No such student found!";
    public static final String MESSAGE_NO_ASSIGNMENT_FOUND = "No such assignment found!";
    public static final String MESSAGE_DUPLICATE_STUDENT = "There is more than 1 student of the same name \n"
            + "Their student numbers are as follows: %1$s \n"
            + MESSAGE_USAGE;

    public static final String MESSAGE_INVALID_ASSIGNMENT =
            "Assignment cannot be graded and not submitted at the same time! "
            + "Assignment is currently %s and %s";

    public final AssignmentName assignmentName;
    public final Name name;
    public final Optional<StudentNumber> studentNumber;
    public final AssignmentQuery assignmentQuery;

    private Assignment oldAssignment;
    private Student student;

    /**
     * Creates an EditAssignmentCommand to add the specified {@code Assignment}
     */
    public EditAssignmentCommand(Name name, AssignmentName assignmentName, AssignmentQuery assignmentQuery) {
        this.name = name;
        this.assignmentName = assignmentName;
        this.assignmentQuery = assignmentQuery;
        this.studentNumber = Optional.empty();
    }

    /**
     * Creates an EditAssignmentCommand to add the specified {@code Assignment}
     */
    public EditAssignmentCommand(Name name, AssignmentName assignmentName,
                                 AssignmentQuery assignmentQuery, StudentNumber studentNumber) {
        this.name = name;
        this.assignmentName = assignmentName;
        this.assignmentQuery = assignmentQuery;
        this.studentNumber = Optional.of(studentNumber);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Student> studentList = model.getAllStudentsByName(name);

        if (studentNumber.isPresent()) {
            List<Student> filteredStudentList = studentList.stream()
                    .filter(s -> s.getStudentNumber().equals(studentNumber.get()))
                    .toList();

            if (filteredStudentList.isEmpty()) {
                throw new CommandException(MESSAGE_NO_STUDENT_FOUND);
            }

            student = filteredStudentList.get(0);
        } else {
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
        }

        Assignment assignment = student.getAssignment(assignmentName);
        if (assignment == null) {
            throw new CommandException(MESSAGE_NO_ASSIGNMENT_FOUND);
        }

        Status status = assignmentQuery.querySubmissionStatus.orElse(assignment.getSubmissionStatus());
        Grade grade = assignmentQuery.queryGrade.orElse(assignment.getGrade());
        if ((!status.isSubmitted()) && grade.isGraded()) {
            throw new CommandException(String.format(MESSAGE_INVALID_ASSIGNMENT,
                    assignment.getSubmissionStatus().isSubmitted() ? "submitted" : "not submitted",
                    assignment.getGrade().isGraded() ? "graded" : "ungraded"));
        }

        oldAssignment = student.editAssignment(assignmentName, assignmentQuery);
        return new CommandResult(String.format(MESSAGE_SUCCESS, assignmentName, student.getName()));
    }

    @Override
    public boolean undo(Model model) {
        student.editAssignment(assignmentName, new AssignmentQuery(oldAssignment));
        return true;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditAssignmentCommand)) {
            return false;
        }

        EditAssignmentCommand otherCommand = (EditAssignmentCommand) other;
        return otherCommand.name.equals(this.name)
                && otherCommand.assignmentName.equals(this.assignmentName)
                && this.studentNumber.equals(otherCommand.studentNumber)
                && this.assignmentQuery.equals(otherCommand.assignmentQuery);
    }

}
