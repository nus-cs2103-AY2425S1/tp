package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import seedu.address.logic.commands.exceptions.CommandException;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

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
            + PREFIX_DATE + "[REMARK]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DATE + "12 October 2024";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Remark: %2$s";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Date command not implemented yet";

    private final Index index;
    private final String date;

    /**
     * @param index of the person in the filtered person list to edit the remark
     * @param date of the person to be updated to
     */
    public DateCommand(Index index, String date) {
        requireAllNonNull(index, date);

        this.index = index;
        this.date = date;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(
                String.format(MESSAGE_ARGUMENTS, index.getOneBased(), date));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DateCommand)) {
            return false;
        }

        DateCommand e = (DateCommand) other;
        return index.equals(e.index)
                && date.equals(e.date);
    }
}
