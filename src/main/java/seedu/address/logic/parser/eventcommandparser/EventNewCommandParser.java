package seedu.address.logic.parser.eventcommandparser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.EVENT_PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.EVENT_PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.EVENT_PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.EVENT_PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.EVENT_PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.EVENT_PREFIX_START_TIME;

import java.util.stream.Stream;

import seedu.address.logic.commands.eventcommands.EventNewCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.EventParserUtil;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Date;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Location;
import seedu.address.model.event.Time;
import seedu.address.model.event.exceptions.ChronologicalOrderException;

/**
 * Parses input arguments and creates a new {@code EventAddCommand} object.
 */
public class EventNewCommandParser implements Parser<EventNewCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code EventAddCommand}
     * and returns an {@code EventAddCommand} object for execution.
     *
     * @param args The input arguments for creating a new event.
     * @return A new {@code EventAddCommand} containing the parsed event data.
     * @throws ParseException If the input arguments do not conform to the expected format.
     */
    public EventNewCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, EVENT_PREFIX_NAME, EVENT_PREFIX_LOCATION, EVENT_PREFIX_DATE,
                        EVENT_PREFIX_START_TIME, EVENT_PREFIX_END_TIME, EVENT_PREFIX_DESCRIPTION);

        if (!arePrefixesPresent(argMultimap, EVENT_PREFIX_NAME, EVENT_PREFIX_LOCATION, EVENT_PREFIX_DATE,
                EVENT_PREFIX_START_TIME, EVENT_PREFIX_END_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventNewCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(EVENT_PREFIX_NAME, EVENT_PREFIX_LOCATION, EVENT_PREFIX_DATE,
                EVENT_PREFIX_START_TIME, EVENT_PREFIX_END_TIME, EVENT_PREFIX_DESCRIPTION);
        EventName eventName = EventParserUtil.parseEventName(argMultimap.getValue(EVENT_PREFIX_NAME).get());
        Location location = EventParserUtil.parseLocation(argMultimap.getValue(EVENT_PREFIX_LOCATION).get());
        Date date = EventParserUtil.parseDate(argMultimap.getValue(EVENT_PREFIX_DATE).get());
        Time startTime;
        try {
            startTime = EventParserUtil.parseTime(argMultimap.getValue(EVENT_PREFIX_START_TIME).get());
        } catch (ParseException e) {
            throw new ParseException("Start " + e.getMessage());
        }
        Time endTime;
        try {
            endTime = EventParserUtil.parseTime(argMultimap.getValue(EVENT_PREFIX_END_TIME).get());
        } catch (ParseException e) {
            throw new ParseException("End " + e.getMessage());
        }

        Event event;

        try {
            if (arePrefixesPresent(argMultimap, EVENT_PREFIX_DESCRIPTION)) {
                Description description = EventParserUtil.parseDescription(
                        argMultimap.getValue(EVENT_PREFIX_DESCRIPTION).get()
                );
                event = new Event(eventName, location, date, startTime, endTime, description);
            } else {
                event = new Event(eventName, location, date, startTime, endTime);
            }

            return new EventNewCommand(event);
        } catch (ChronologicalOrderException e) {
            throw new ParseException(e.getMessage());
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
