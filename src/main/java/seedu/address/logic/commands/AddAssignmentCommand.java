package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.person.Name;
import seedu.address.model.student.Student;

/**
 * Adds an assignment to a student.
 */
public class AddAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "adda";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an assignment to a student. "
            + "Parameters: "
            + PREFIX_NAME + "STUDENT_NAME "
            + PREFIX_ASSIGNMENT + "ASSIGNMENT "
            + PREFIX_DEADLINE + "DEADLINE "
            + PREFIX_STATUS + "SUBMISSION STATUS (OPTIONAL)"
            + PREFIX_STATUS + "GRADING STATUS (OPTIONAL)"
            + PREFIX_GRADE + "GRADE (OPTIONAL)"
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

    public final Assignment assignment;
    public final Name name;

    /**
     * Creates an AddAssignmentCommand to add the specified {@code Assignment}
     */
    public AddAssignmentCommand(Name name, Assignment assignment) {
        requireAllNonNull(name, assignment);
        this.assignment = assignment;
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Student student = model.getStudentByName(name);

        if (student == null) {
            throw new CommandException(MESSAGE_NO_STUDENT_FOUND);
        }

        student.addAssignment(assignment);
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
                && otherCommand.name.equals(this.name);
    }
}
