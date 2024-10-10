package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_ID;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;

/**
 * Adds a person to the address book.
 */
public class AssignCommand extends Command {

    public static final String COMMAND_WORD = "assign";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns a project to a person. "
            + "Parameters: "
            + PREFIX_PROJECT_ID + "PROJECT ID "
            + PREFIX_EMPLOYEE_ID + "EMPLOYEE ID\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PROJECT_ID + "123 "
            + PREFIX_EMPLOYEE_ID + "456";

    public static final String MESSAGE_SUCCESS = "Project %1$s assigned to person %1$s";
    public static final String MESSAGE_DUPLICATE_ASSIGNMENT = "This assignment already exists in the address book";

    private final Assignment toAssign;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AssignCommand(Assignment assignment) {
        requireNonNull(assignment);
        toAssign = assignment;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasAssignment(toAssign)) {
            throw new CommandException(MESSAGE_DUPLICATE_ASSIGNMENT);
        }

        model.addAssignment(toAssign);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAssign)));
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
        return toAssign.equals(otherAssignCommand.toAssign);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAssign", toAssign)
                .toString();
    }
}
