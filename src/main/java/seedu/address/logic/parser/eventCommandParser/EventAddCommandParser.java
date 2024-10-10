package seedu.address.logic.parser.eventCommandParser;

import seedu.address.logic.commands.eventCommands.EventAddCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.EventParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Date;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Location;
import seedu.address.model.event.Time;

import java.util.stream.Stream;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.EVENT_PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.EVENT_PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.EVENT_PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.EVENT_PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.EVENT_PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.EVENT_PREFIX_START_TIME;

public class EventAddCommandParser {

    public EventAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, EVENT_PREFIX_NAME, EVENT_PREFIX_LOCATION, EVENT_PREFIX_DATE, EVENT_PREFIX_START_TIME, EVENT_PREFIX_END_TIME, EVENT_PREFIX_DESCRIPTION);

        if (!arePrefixesPresent(argMultimap, EVENT_PREFIX_NAME, EVENT_PREFIX_LOCATION, EVENT_PREFIX_DATE, EVENT_PREFIX_START_TIME, EVENT_PREFIX_END_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventAddCommand.MESSAGE_USAGE));
        }


        argMultimap.verifyNoDuplicatePrefixesFor(EVENT_PREFIX_NAME, EVENT_PREFIX_LOCATION, EVENT_PREFIX_DATE, EVENT_PREFIX_START_TIME, EVENT_PREFIX_END_TIME, EVENT_PREFIX_DESCRIPTION);
        EventName eventName = EventParserUtil.parseEventName(argMultimap.getValue(EVENT_PREFIX_NAME).get());
        Location location = EventParserUtil.parseLocation(argMultimap.getValue(EVENT_PREFIX_LOCATION).get());
        Date date = EventParserUtil.parseDate(argMultimap.getValue(EVENT_PREFIX_DATE).get());
        Time start_time = EventParserUtil.parseTime(argMultimap.getValue(EVENT_PREFIX_START_TIME).get());
        Time end_time = EventParserUtil.parseTime(argMultimap.getValue(EVENT_PREFIX_END_TIME).get());
        Description description = EventParserUtil.parseDescription(argMultimap.getValue(EVENT_PREFIX_DESCRIPTION).get());


        Event event;
        if (arePrefixesPresent(argMultimap, EVENT_PREFIX_DESCRIPTION)) {
//            Description description = EventParserUtil.parseDescription(argMultimap.getValue(EVENT_PREFIX_DESCRIPTION).get());
            event = new Event(eventName, location, date, start_time, end_time, description);
        } else {
            event = new Event(eventName, location, date, start_time, end_time);
        }

        return new EventAddCommand(event);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
