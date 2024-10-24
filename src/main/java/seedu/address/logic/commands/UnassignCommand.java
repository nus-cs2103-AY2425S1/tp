package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_ID;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.AssignmentId;
import seedu.address.model.person.EmployeeId;
import seedu.address.model.project.ProjectId;
import seedu.address.ui.DisplayType;

/**
 * Unassign person from a project
 */
public class UnassignCommand extends Command {
    public static final String COMMAND_WORD = "unassign";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unassigns a person from a project. "
            + "\nParameters: "
            + PREFIX_ASSIGNMENT_ID + "ASSIGNMENT ID "
            + "or "
            + PREFIX_PROJECT_ID + "PROJECT ID "
            + PREFIX_EMPLOYEE_ID + "EMPLOYEE ID\n"
            + "Example:\n" + COMMAND_WORD + " "
            + PREFIX_ASSIGNMENT_ID + "1 \n"
            + COMMAND_WORD + " "
            + PREFIX_PROJECT_ID + "123 "
            + PREFIX_EMPLOYEE_ID + "456";

    public static final String MESSAGE_SUCCESS = "Person %1$s unassigned from project %2$s";
    public static final String MESSAGE_SUCCESS_ASSIGNMENT_ID = "Assignment %1$s is removed";
    public static final String MESSAGE_ASSIGNMENT_NOT_FOUND = "This assignment doesn't exists in the address book";

    private final AssignmentId assignmentId;
    private final ProjectId projectId;
    private final EmployeeId employeeId;

    /**
     * Creates an AssignCommand to add an {@code Assignment}
     */
    public UnassignCommand(ProjectId projectId, EmployeeId employeeId) {
        requireNonNull(projectId);
        requireNonNull(employeeId);
        this.projectId = projectId;
        this.employeeId = employeeId;
        this.assignmentId = null;
    }

    /**
     * Creates an AssignCommand to add an {@code Assignment}
     */
    public UnassignCommand(AssignmentId assignmentId) {
        requireNonNull(assignmentId);
        this.projectId = null;
        this.employeeId = null;
        this.assignmentId = assignmentId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (assignmentId != null && !model.hasAssignment(assignmentId)) {
            throw new CommandException(MESSAGE_ASSIGNMENT_NOT_FOUND);
        }

        if (projectId != null
                && employeeId != null
                && !model.hasAssignment(projectId, employeeId)) {
            throw new CommandException(MESSAGE_ASSIGNMENT_NOT_FOUND);
        }

        String commandMessage;
        if (assignmentId != null) {
            model.deleteAssignment(assignmentId);
            commandMessage = String.format(MESSAGE_SUCCESS_ASSIGNMENT_ID, assignmentId);
        } else {
            model.deleteAssignment(projectId, employeeId);
            commandMessage = String.format(MESSAGE_SUCCESS, employeeId, projectId);
        }
        return new CommandResult(commandMessage, DisplayType.ASSIGNMENT_LIST);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnassignCommand)) {
            return false;
        }

        UnassignCommand otherUnassignCommand = (UnassignCommand) other;
        return this.assignmentId != null && this.assignmentId.equals(otherUnassignCommand.assignmentId)
                || this.projectId != null && this.employeeId != null
                && this.projectId.equals(otherUnassignCommand.projectId)
                && this.employeeId.equals(otherUnassignCommand.employeeId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("assignmentId", assignmentId)
                .add("projectId", projectId)
                .add("employeeId", employeeId)
                .toString();
    }
}
