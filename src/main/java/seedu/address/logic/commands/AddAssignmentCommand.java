package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DUEDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;

/**
 * Command to add an assignment to the model.
 */
public class AddAssignmentCommand extends Command {
    public static final String COMMAND_WORD = "addAsg";
    public static final String SUCCESS_MESSAGE = "Assignment added successfully!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an assignment. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DUEDATE + "yyyy-MM-dd HHmm (Due date)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "2024-10-23 1230";
    public static final String MESSAGE_DUPLICATE_ASSIGNMENT = "This assignment already exists!";

    private final Assignment assignment;

    /**
     * Constructs an AddAssignmentCommand with the specified assignment.
     *
     * @param assignment The assignment to be added.
     */
    public AddAssignmentCommand(Assignment assignment) {
        this.assignment = assignment;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasAssignment(assignment)) {
            throw new CommandException(MESSAGE_DUPLICATE_ASSIGNMENT);
        }

        model.addAssignment(assignment);
        return new CommandResult(SUCCESS_MESSAGE + "\n" + assignment.toString());
    }
}
