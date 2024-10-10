package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;

/**
 * Changes the birthday of an existing person in the address book.
 */
public class BirthdayCommand extends Command {

    public static final String COMMAND_WORD = "birthday";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the birthday of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing birthday will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_BIRTHDAY + "[BIRTHDAY]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_BIRTHDAY + "2023-12-12.";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Birthday command not implemented yet";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}