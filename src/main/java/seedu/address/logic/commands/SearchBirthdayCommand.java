package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DATERANGE_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DATE_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_START_DATE_AFTER_END_DATE;
import static seedu.address.logic.Messages.MESSAGE_SUCCESS_SEARCH_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;

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
    public static final String COMMAND_WORD = "search " + PREFIX_BIRTHDAY;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Searches for clients whose birthdays are on the specified date or a specified date range.\n"
            + "Parameters: DATE (must be in yyyy-MM-dd format)\n"
            + "Example: " + COMMAND_WORD + " 2000-04-25\n"
            + "Example: " + COMMAND_WORD + " 2000-04-25 to 2000-05-25";

    private final String startDate;
    private final String endDate;

    /**
     * Creates a {@code SearchBirthdayCommand} to search for clients with the specified birthday {@code date}.
     *
     * @param dateInput The date in string format used to search for client birthdays.
     * @throws CommandException if the {@code date} format is invalid.
     */
    public SearchBirthdayCommand(String dateInput) throws CommandException {
        requireNonNull(dateInput);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // checks if the date input contains to (means it is a range)
        if (dateInput.contains("to")) {
            String [] dates = dateInput.split("to");
            // ensure dates contains 2 date
            if (dates.length != 2) {
                throw new CommandException(MESSAGE_INVALID_DATERANGE_FORMAT);
            }
            startDate = dates[0].trim();
            endDate = dates[1].trim();

            if (!isValidDate(startDate) || !isValidDate(endDate)) {
                throw new CommandException(MESSAGE_INVALID_DATE_FORMAT);
            }

            if (LocalDate.parse(startDate, formatter).isAfter(LocalDate.parse(endDate, formatter))) {
                throw new CommandException(MESSAGE_START_DATE_AFTER_END_DATE);
            }
        } else {
            String date = dateInput.trim();
            if (!isValidDate(date)) {
                throw new CommandException(MESSAGE_INVALID_DATE_FORMAT);
            }
            startDate = date;
            endDate = startDate;
        }

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        requireNonNull(model);
        Predicate<Person> predicate = person -> {
            Birthday birthday = person.getBirthday();

            if (birthday == null || birthday.value.isEmpty()) {
                return false;
            }
            String birthdayFormatted = birthday.toString();
            LocalDate personBirthday = LocalDate.parse(birthdayFormatted, formatter);
            LocalDate start = LocalDate.parse(startDate, formatter);
            LocalDate end = LocalDate.parse(endDate, formatter);
            return (!personBirthday.isBefore(start) && !personBirthday.isAfter(end));
        };

        model.updateFilteredPersonList(predicate);
        String dateRangeMessage;
        if (startDate.equals(endDate)) {
            dateRangeMessage = "on " + startDate;
        } else {
            dateRangeMessage = "from " + startDate + " to " + endDate;
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS_SEARCH_BIRTHDAY, dateRangeMessage));
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
        // This method made use of ChatGPT to ensure its correctness when comparing the Command object
        if (other == this) {
            return true;
        }

        if (!(other instanceof SearchBirthdayCommand)) {
            return false;
        }

        SearchBirthdayCommand otherCommand = (SearchBirthdayCommand) other;
        return this.startDate.equals(otherCommand.startDate) && this.endDate.equals(otherCommand.endDate);
    }
}
