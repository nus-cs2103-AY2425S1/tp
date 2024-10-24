package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_ID;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentId;
import seedu.address.model.person.EmployeeId;
import seedu.address.model.person.Person;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectId;
import seedu.address.ui.DisplayType;

/**
 * Adds a person to the address book.
 */
public class AssignCommand extends Command {

    public static final String COMMAND_WORD = "assign";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns a project to a person. "
            + "Parameters: "
            + PREFIX_ASSIGNMENT_ID + "ASSIGNMENT ID "
            + PREFIX_PROJECT_ID + "PROJECT ID "
            + PREFIX_EMPLOYEE_ID + "EMPLOYEE ID\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ASSIGNMENT_ID + "1 "
            + PREFIX_PROJECT_ID + "123 "
            + PREFIX_EMPLOYEE_ID + "456";

    public static final String MESSAGE_SUCCESS = "Project %1$s assigned to person %1$s";
    public static final String MESSAGE_DUPLICATE_ASSIGNMENT = "This assignment already exists in the address book";

    private final AssignmentId assignmentId;
    private final ProjectId projectId;
    private final EmployeeId employeeId;

    /**
     * Creates an AssignCommand to add an {@code Assignment}
     */
    public AssignCommand(AssignmentId assignmentId, ProjectId projectId, EmployeeId employeeId) {
        requireNonNull(assignmentId);
        requireNonNull(projectId);
        requireNonNull(employeeId);
        this.assignmentId = assignmentId;
        this.projectId = projectId;
        this.employeeId = employeeId;
    }

    /**
     * Creates an AssignCommand to add an {@code Assignment}
     */
    public AssignCommand(Assignment assignment) {
        requireNonNull(assignment);
        this.assignmentId = assignment.getAssignmentId();
        this.projectId = assignment.getProject().getId();
        this.employeeId = assignment.getPerson().getEmployeeId();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasProjectId(projectId)) {
            throw new CommandException(MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
        } else if (!model.hasEmployeeId(employeeId)) {
            throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        List<Person> personList = model.getPersonList();
        Person person = personList.stream().filter(p -> p.getEmployeeId().equals(employeeId)).findFirst().get();

        List<Project> projectList = model.getProjectList();
        Project project = projectList.stream().filter(p -> p.getId().equals(projectId)).findFirst().get();

        Assignment toAssign = new Assignment(assignmentId, project, person);

        if (model.hasAssignment(toAssign)) {
            throw new CommandException(MESSAGE_DUPLICATE_ASSIGNMENT);
        }
        model.addAssignment(toAssign);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAssign)),
                DisplayType.ASSIGNMENT_LIST);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AssignCommand)) {
            return false;
        }

        AssignCommand otherAssignCommand = (AssignCommand) other;
        return assignmentId.equals(otherAssignCommand.assignmentId) && projectId.equals(otherAssignCommand.projectId)
                && employeeId.equals(otherAssignCommand.employeeId);
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
