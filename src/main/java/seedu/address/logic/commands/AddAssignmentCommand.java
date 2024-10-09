package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_MAX_SCORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NAME;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;

/**
 * Adds an Assignment to the app.
 */
public class AddAssignmentCommand extends Command {
    public static final String COMMAND_WORD = "add assignment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an Assignment to the app. "
            + "Parameters: "
            + PREFIX_ASSIGNMENT_NAME + "ASSIGNMENT NAME "
            + PREFIX_ASSIGNMENT_MAX_SCORE + "MAX SCORE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ASSIGNMENT_NAME + "Assignment 1"
            + PREFIX_ASSIGNMENT_MAX_SCORE + "100";
    public static final String MESSAGE_SUCCESS = "New assignment added: %1$s";
    public static final String MESSAGE_DUPLICATE_ASSIGNMENT = "This assignment already exists in the address book";
    private final Assignment toAdd;

    /**
     * Creates an AddAssignmentCommand to add the specified {@code Assignment}
     */
    public AddAssignmentCommand(Assignment assignment) {
        requireNonNull(assignment);
        toAdd = assignment;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.hasAssignment(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ASSIGNMENT);
        }
        model.addAssignment(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddAssignmentCommand)) {
            return false;
        }

        AddAssignmentCommand otherAddAssignmentCommand = (AddAssignmentCommand) other;
        return toAdd.equals(otherAddAssignmentCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
