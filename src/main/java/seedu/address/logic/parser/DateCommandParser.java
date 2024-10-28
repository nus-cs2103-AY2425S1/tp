package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_EMAIL_DETAILS;
import static seedu.address.logic.Messages.MESSSAGE_INVALID_PHONE_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DateCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Date;


/**
 * Parses input arguments and creates a new {@code DateCommand} object
 */
public class DateCommandParser implements Parser<DateCommand> {
    private static final String DATE_PATTERN =
            "^([1-9]|[12][0-9]|3[01])/([1-9]|1[0-2])/\\d{4} ([01][0-9]|2[0-3])[0-5][0-9]$";
    private static final String FORMAT_PATTERN = "^\\d{1,2}/\\d{1,2}/\\d{4} \\d{4}$";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    /**
     * Parses the given {@code String} of arguments in the context of the {@code DateCommand}
     * and returns a {@code DateCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DateCommand parse(String args) throws ParseException {
        try {
            requireNonNull(args);
            ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                    PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_DATE);
            if (!areAnyPrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL)
                    && !areAnyPrefixesPresent(argMultimap, PREFIX_DATE)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DateCommand.MESSAGE_USAGE));
            }

            Optional<String> name = argMultimap.getValue(PREFIX_NAME);
            Optional<String> phone = argMultimap.getValue(PREFIX_PHONE);
            Optional<String> email = argMultimap.getValue(PREFIX_EMAIL);
            String dateString = argMultimap.getValue(PREFIX_DATE).orElse("");
            LocalDateTime date = parseDateString(dateString);

            // Validate the phone number format if present
            if (phone.isPresent() && !isValidPhone(phone.get())) {
                throw new ParseException(MESSSAGE_INVALID_PHONE_DETAILS);
            }

            // Validate the email format if present
            if (email.isPresent() && !isValidEmail(email.get())) {
                throw new ParseException(MESSAGE_INVALID_EMAIL_DETAILS);
            }

            return new DateCommand(name, phone, email, new Date(date));
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if at least one of the specified prefixes is present in the
     * given ArgumentMultimap.
     */
    private boolean areAnyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        for (Prefix prefix : prefixes) {
            if (argumentMultimap.getValue(prefix).isPresent()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validates phone number format.
     */
    private boolean isValidPhone(String phone) {
        return phone.matches("[3689]\\d{7}");
    }

    /**
     * Validates email format.
     */
    private boolean isValidEmail(String email) {
        return email.matches("^[\\w-\\.]+@[\\w-]+\\.[a-z]{2,3}$"); // Basic email validation
    }

    /**
     * Parses a date string into a {@code LocalDateTime} object.
     *
     * <p>This method checks if the provided date string matches the expected format and
     * validates the values of the date and time. The expected format is 'd/M/yyyy HHmm'.
     * For example, '2/12/2024 1800' represents 2nd December 2024 at 18:00 hours.</p>
     *
     * @param date The date string to be parsed.
     * @return A {@code LocalDateTime} object representing the parsed date and time.
     * @throws ParseException If the date format is invalid or if the date and time values are incorrect.
     */
    public LocalDateTime parseDateString(String date) throws ParseException {
        validateDateFormat(date);
        return parseDateTime(date);
    }

    /**
     * Validates the date format and checks if the values are correct.
     *
     * @param date The date string to validate.
     * @throws ParseException If the date format is invalid or if the date and time values are incorrect.
     */
    private void validateDateFormat(String date) throws ParseException {
        if (!date.matches(DATE_PATTERN)) {
            if (!date.matches(FORMAT_PATTERN)) {
                throw new ParseException("Invalid date format! Please use 'd/M/yyyy HHmm'. "
                        + "For example, '2/12/2024 1800'.");
            } else {
                throw new ParseException("Invalid date or time values! "
                        + "Ensure day, month, hour, and minute ranges are correct.");
            }
        }
    }


    /**
     * Parses the date string into a {@code LocalDateTime} object.
     *
     * @param date The date string to parse.
     * @return A {@code LocalDateTime} object representing the parsed date and time.
     * @throws ParseException If the date and time values are incorrect.
     */
    private LocalDateTime parseDateTime(String date) throws ParseException {
        String[] dateAndTime = date.split(" ");
        String[] dateParts = dateAndTime[0].split("/");

        int day = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[2]);

        // Check month-day combinations, including leap year validation
        if (month == 2) {
            if (day > 29 || (day == 29 && year % 4 != 0)) {
                throw new ParseException("Invalid date: February " + day + " is only valid in leap years.");
            }
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            if (day > 30) {
                throw new ParseException("Invalid date: " + Month.of(month) + " cannot have more than 30 days.");
            }
        }

        try {
            return LocalDateTime.parse(date, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new ParseException("Invalid date and time format! Please use the format 'd/M/yyyy HHmm'. "
                    + "For example, '2/12/2024 1800'. Ensure day, month, hour, and minute ranges are correct.");
        }
    }
}
