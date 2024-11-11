package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Unassigns an existing event from an existing person in the address book.
 */
public abstract class UnassignEventCommand extends Command {

    public static final String COMMAND_WORD = "unassign_event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unassigns an event from a person identified "
            + "by the index number used in the displayed person list.\n"
            + "Parameters: "
            + PREFIX_PERSON + "PERSON_INDEX (must be a positive integer) or PERSON_NAME (must start with an alphabet) "
            + PREFIX_EVENT + "EVENT_INDEX (must be a positive integer) or EVENT_NAME (must start with an alphabet) \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_PERSON + "1 " + PREFIX_EVENT + "CS2103T Project Meeting";

    public static final String MESSAGE_SUCCESS = "Removed Event '%1$s' from: %2$s";


    @Override
    public abstract CommandResult execute(Model model) throws CommandException;

    @Override
    public abstract boolean equals(Object other);

    @Override
    public abstract String toString();

}
