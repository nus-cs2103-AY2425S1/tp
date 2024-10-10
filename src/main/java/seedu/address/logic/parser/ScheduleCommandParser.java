package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Meeting;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class ScheduleCommandParser implements Parser<ScheduleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ScheduleCommand
     * and returns an ScheduleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ScheduleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_LOCATION, PREFIX_START_TIME, PREFIX_END_TIME);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE), pe);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_LOCATION, PREFIX_START_TIME, PREFIX_END_TIME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_LOCATION, PREFIX_START_TIME, PREFIX_END_TIME);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a, d MMMM yyyy");

        String startTimeString = argMultimap.getValue(PREFIX_START_TIME).get();
        LocalDateTime startTime;
        try {
            startTime = LocalDateTime.parse(startTimeString, formatter);
        } catch (DateTimeParseException e) {
            throw new ParseException("Invalid start time format. Please use the correct format: 'h:mm a, d MMMM yyyy'");
        }

        String endTimeString = argMultimap.getValue(PREFIX_END_TIME).get();
        LocalDateTime endTime;
        try {
            endTime = LocalDateTime.parse(endTimeString, formatter);
        } catch (DateTimeParseException e) {
            throw new ParseException("Invalid end time format. Please use the correct format: 'h:mm a, d MMMM yyyy'");
        }

        Meeting meeting = ParserUtil.parseMeeting(startTime, endTime,
                argMultimap.getValue(PREFIX_LOCATION).get());

        return new ScheduleCommand(index, meeting);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
