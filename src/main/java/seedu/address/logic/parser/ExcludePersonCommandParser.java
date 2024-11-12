package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT_INDEX;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.searchmode.ExcludePersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new ExcludePersonCommand object
 */
public class ExcludePersonCommandParser implements Parser<ExcludePersonCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ExcludePersonCommand
     * and returns an ExcludePersonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ExcludePersonCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CONTACT_INDEX);

        if (!arePrefixesPresent(argMultimap, PREFIX_CONTACT_INDEX)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExcludePersonCommand.MESSAGE_USAGE));
        }

        Set<Index> excludedIndices = new HashSet<>();
        ParserUtil.parseStringOfIndices(excludedIndices, argMultimap.getValue(PREFIX_CONTACT_INDEX).orElse(null));
        return new ExcludePersonCommand(excludedIndices);
    }
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }


}
