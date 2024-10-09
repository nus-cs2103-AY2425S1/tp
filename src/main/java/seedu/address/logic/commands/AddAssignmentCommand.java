package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

public class AddAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "adda";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an assignment to a student. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_ASSIGNMENT + "ASSIGNMENT "
            + PREFIX_DEADLINE + "DEADLINE "
            + PREFIX_STATUS + "SUBMISSION STATUS "
            + PREFIX_STATUS + "GRADING STATUS "
            + PREFIX_GRADE + "GRADE "
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_ASSIGNMENT + "Math Quiz "
            + PREFIX_DEADLINE + "9/10/2024 "
            + PREFIX_STATUS + "N "
            + PREFIX_STATUS + "N "
            + PREFIX_GRADE + "NULL ";

    public static final String MESSAGE_SUCCESS = "New assignment added: %1$s";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }
}
