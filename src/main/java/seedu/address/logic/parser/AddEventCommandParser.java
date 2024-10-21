package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START_DATE;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDescription;
import seedu.address.model.event.EventDuration;
import seedu.address.model.event.EventName;

/**
 * Parses input arguments and creates a new AddEventCommand object
 */
public class AddEventCommandParser implements Parser<AddEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddEventCommand
     * and returns an AddEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultiMap =
                ArgumentTokenizer.tokenize(args, PREFIX_EVENT_NAME, PREFIX_EVENT_DESCRIPTION,
                        PREFIX_EVENT_START_DATE, PREFIX_EVENT_END_DATE);

        if (!arePrefixesPresent(argMultiMap, PREFIX_EVENT_NAME, PREFIX_EVENT_DESCRIPTION,
                PREFIX_EVENT_START_DATE, PREFIX_EVENT_END_DATE)
                || !argMultiMap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        argMultiMap.verifyNoDuplicatePrefixesFor(PREFIX_EVENT_NAME, PREFIX_EVENT_DESCRIPTION,
                PREFIX_EVENT_START_DATE, PREFIX_EVENT_END_DATE);
        EventName eventName = ParserUtil.parseEventName(argMultiMap.getValue(PREFIX_EVENT_NAME).get());
        EventDescription eventDescription =
                ParserUtil.parseEventDescription(argMultiMap.getValue(PREFIX_EVENT_DESCRIPTION).get());
        EventDuration eventDuration = ParserUtil.parseEventDuration(
                argMultiMap.getValue(PREFIX_EVENT_START_DATE).get(),
                argMultiMap.getValue(PREFIX_EVENT_END_DATE).get());

        Event event = new Event(eventName, eventDescription, eventDuration, -1);

        return new AddEventCommand(event);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
