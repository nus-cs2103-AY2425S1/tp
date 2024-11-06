package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARTICIPANTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPORT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Venue;
import seedu.address.model.person.Person;
import seedu.address.model.person.role.athlete.SportString;

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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_SPORT, PREFIX_VENUE,
                PREFIX_PARTICIPANTS);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME) || !argMultimap.getPreamble().isEmpty()
                || args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME);
        EventName eventName = ParserUtil.parseEventName(argMultimap.getValue(PREFIX_NAME).get());
        SportString sport = ParserUtil.parseSportString(argMultimap.getValue(PREFIX_SPORT).get());
        Venue venue = ParserUtil.parseVenue(argMultimap.getValue(PREFIX_VENUE).get());
        Set<Person> participants = ParserUtil.parseParticipants(argMultimap.getAllValues(PREFIX_PARTICIPANTS));
        System.out.println(participants);

        Event event = new Event(eventName, sport, venue, participants);

        return new AddEventCommand(event);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
