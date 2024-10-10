package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.stream.Stream;

import seedu.address.logic.commands.EventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Event;

/**
 * Parses input arguments and creates a new EventCommand object
 */
public class EventCommandParser implements Parser<EventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EventCommand
     * and returns an EventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_DATE);
        String name = argMultimap.getValue(PREFIX_NAME).get().trim();
        LocalDate date = LocalDate.parse(argMultimap.getValue(PREFIX_DATE).get().trim());

        Event event = new Event(name, date, new HashSet<>());

        return new EventCommand(event);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

