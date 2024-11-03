package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;

import java.util.stream.Stream;

import seedu.address.logic.commands.UnmarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new UnmarkCommand object
 */
public class UnmarkCommandParser implements Parser<UnmarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the seedu.address.logic.commands.UnmarkCommand
     * and returns a seedu.address.logic.commands.UnmarkCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnmarkCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_WEEK);

        if (!arePrefixesPresent(argumentMultimap, PREFIX_NAME, PREFIX_WEEK)
                || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE)
            );
        }

        argumentMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_WEEK);

        Name name = ParserUtil.parseName(argumentMultimap.getValue(PREFIX_NAME).get());
        int week = ParserUtil.parseWeek(argumentMultimap.getValue(PREFIX_WEEK).get());

        return new UnmarkCommand(name, week);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
