package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.function.Predicate;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Person;

/**
 * Searches for clients whose birthdays are on the specified date.
 */
public class SearchBirthdayCommand extends Command {
    public static final String COMMAND_WORD = "search b/";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Searches for clients whose birthdays are on the specified date.\n"
            + "Parameters: DATE (must be in yyyy-MM-dd format)\n"
            + "Example: " + COMMAND_WORD + " 2000-04-25";

    public static final String MESSAGE_SUCCESS = "Listed all clients with birthdays on %s";
    public static final String MESSAGE_INVALID_DATE_FORMAT = "The date format is invalid. Please use yyyy-MM-dd format.";

    private final String date;

    public SearchBirthdayCommand(String date) throws CommandException {
        requireNonNull(date);
        if (!isValidDate(date)) {
            throw new CommandException(MESSAGE_INVALID_DATE_FORMAT);
        }
        this.date = date;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Predicate<Person> predicate = person -> {
            Birthday birthday = person.getBirthday();

            if (birthday == null) {
                return false;
            }
            String birthdayFormatted = birthday.toString();
            return birthdayFormatted.equals(date);
        };
        model.updateFilteredPersonList(predicate);
        return new CommandResult(String.format(MESSAGE_SUCCESS, date));
    }

    /**
     * Checks if the given date string is in the yyyy-MM-dd format.
     *
     * @param date The date string to check.
     * @return True if the date is in yyyy-MM-dd format, false otherwise.
     */
    private boolean isValidDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        // Short circuit if the same object
        if (other == this) {
            return true;
        }

        // Instance of handles nulls and type check
        if (!(other instanceof SearchBirthdayCommand)) {
            return false;
        }

        // Cast and compare the date attribute
        SearchBirthdayCommand otherCommand = (SearchBirthdayCommand) other;
        return this.date.equals(otherCommand.date);
    }
}
