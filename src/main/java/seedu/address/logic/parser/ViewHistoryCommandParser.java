package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_TIME;
import seedu.address.logic.commands.ViewHistoryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Id;

/**
 * Parses input arguments and creates a new ViewHistoryCommand object.
 */
public class ViewHistoryCommandParser implements Parser<ViewHistoryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewHistoryCommand
     * and returns a ViewHistoryCommand object for execution.
     *
     * @param args the input arguments string.
     * @return a ViewHistoryCommand object.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    @Override
    public ViewHistoryCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);

        // Parse the patient ID from the preamble
        String patientIdString = argMultimap.getPreamble().trim();
        if (patientIdString.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ViewHistoryCommand.MESSAGE_USAGE));
        }

        // Parse the patient ID
        Id patientId = ParserUtil.parsePatientId(patientIdString);

        // Parse the optional LocalDateTime
        Optional<String> dateTimeString = argMultimap.getValue(PREFIX_DATE_TIME);
        Optional<LocalDateTime> dateTime = dateTimeString.isPresent()
                ? parseDateTime(dateTimeString.get())
                : Optional.empty();

        return new ViewHistoryCommand(patientId, dateTime);
    }

    /**
     * Parses a date-time string and returns an Optional containing the LocalDateTime.
     * If no valid date-time string is found, returns an empty Optional.
     *
     * @param dateTimeString the date-time string to parse.
     * @return an Optional containing the parsed LocalDateTime, or an empty Optional if no date-time is provided.
     */
    private Optional<LocalDateTime> parseDateTime(String dateTimeString) throws ParseException{
        try {
            if (dateTimeString != null && !dateTimeString.isEmpty()) {
                return Optional.of(LocalDateTime.parse(dateTimeString.trim()));
            }
        } catch (DateTimeParseException e) {
            // Handle invalid date-time format gracefully
            throw new ParseException("Invalid date-time format. Please use the format: yyyy/MM/dd HHmm.");
        }
        return Optional.empty(); // No date-time provided
    }
}
