package hallpointer.address.logic.parser;

import static hallpointer.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_DATE;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_POINTS;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_SESSION_NAME;

import java.util.HashSet;
import java.util.stream.Stream;

import hallpointer.address.logic.commands.AddSessionCommand;
import hallpointer.address.logic.parser.exceptions.ParseException;
import hallpointer.address.model.session.Date;
import hallpointer.address.model.session.Session;
import hallpointer.address.model.session.SessionName;

/**
 * Parses input arguments and creates a new AddSessionCommand object
 */
public class AddSessionCommandParser implements Parser<AddSessionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddSessionCommand
     * and returns an AddSessionCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddSessionCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SESSION_NAME, PREFIX_DATE, PREFIX_POINTS);

        if (!arePrefixesPresent(argMultimap, PREFIX_SESSION_NAME, PREFIX_DATE, PREFIX_POINTS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSessionCommand.MESSAGE_USAGE));
        }

        SessionName name = ParserUtil.parseSessionName(argMultimap.getValue(PREFIX_SESSION_NAME).get());
        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        int points = ParserUtil.parsePoints(argMultimap.getValue(PREFIX_POINTS).get());

        Session session = new Session(name, date, points, new HashSet<>());

        return new AddSessionCommand(session);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
