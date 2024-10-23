package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.BookingIsOnDate;

/**
 * Displays all persons with a booking on the specified day.
 */
public class BookingsCommand extends Command {
    public static final String COMMAND_WORD = "bookings";

    private final LocalDate date;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all record with appointments on the specified date "
            + "where dates are in the format dd/mm/yy\n"
            + "Parameters: DATE\n"
            + "Examples:\n"
            + COMMAND_WORD + " 23/10/2024\n";

    public BookingsCommand(LocalDate date) {
        this.date = date;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(new BookingIsOnDate(date));
        return new CommandResult(
                String.format(Messages.MESSAGE_NUMBER_OF_BOOKINGS, model.getFilteredPersonList().size(), date));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BookingsCommand)) {
            return false;
        }

        BookingsCommand otherCommand = (BookingsCommand) other;
        return date.equals(otherCommand.date);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("date", date)
                .toString();
    }
}
