package seedu.eventfulnus.logic.parser;

import static seedu.eventfulnus.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eventfulnus.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.eventfulnus.logic.parser.CliSyntax.PREFIX_PARTICIPANT;
import static seedu.eventfulnus.logic.parser.CliSyntax.PREFIX_SPORT;
import static seedu.eventfulnus.logic.parser.CliSyntax.PREFIX_TEAM;
import static seedu.eventfulnus.logic.parser.CliSyntax.PREFIX_VENUE;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import javafx.util.Pair;
import seedu.eventfulnus.logic.commands.AddEventCommand;
import seedu.eventfulnus.logic.parser.exceptions.ParseException;
import seedu.eventfulnus.model.event.Event;
import seedu.eventfulnus.model.event.Venue;
import seedu.eventfulnus.model.person.Person;
import seedu.eventfulnus.model.person.role.Faculty;
import seedu.eventfulnus.model.person.role.athlete.Sport;

/**
 * Parses input arguments and creates a new AddEventCommand object.
 */
public class AddEventCommandParser implements Parser<AddEventCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddEventCommand
     * and returns an AddEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SPORT, PREFIX_TEAM,
                PREFIX_VENUE, PREFIX_DATE, PREFIX_PARTICIPANT);

        if (!arePrefixesPresent(argMultimap, PREFIX_SPORT, PREFIX_TEAM, PREFIX_VENUE, PREFIX_DATE)
            || !argMultimap.getPreamble().isEmpty() || args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SPORT, PREFIX_VENUE, PREFIX_DATE);

        Sport sport = ParserUtil.parseSport(argMultimap.getValue(PREFIX_SPORT).get());

        Pair<Faculty, Faculty> teams = ParserUtil.parseTeams(argMultimap.getAllValues(PREFIX_TEAM));

        Venue venue = ParserUtil.parseVenue(argMultimap.getValue(PREFIX_VENUE).get());

        LocalDateTime dateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATE).get());


        Set<Person> participants = new HashSet<>(ParserUtil.parseDefaultParticipants(sport, teams));
        participants.addAll(ParserUtil.parseParticipants(argMultimap.getAllValues(PREFIX_PARTICIPANT)));

        Event event = new Event(sport, teams, venue, dateTime, participants);

        return new AddEventCommand(event);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
