package seedu.address.logic.commands;
import seedu.address.model.Model;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Changes the date of an existing person in the address book.
 */
public class DateCommand extends Command {
    public static final String COMMAND_WORD = "date";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the appointment date of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing date will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "r/ [DATE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "r/ 12 October 2024";
    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Date command not implemented yet";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
