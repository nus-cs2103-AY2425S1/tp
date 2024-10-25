package seedu.academyassist.logic.parser;

import static seedu.academyassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.academyassist.logic.parser.CliSyntax.PREFIX_SORT_PARAM;

import seedu.academyassist.logic.commands.SortCommand;
import seedu.academyassist.logic.parser.exceptions.ParseException;
import seedu.academyassist.model.sort.SortParam;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SORT_PARAM);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_SORT_PARAM)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        SortParam sortCommandParam = ParserUtil.parseSortCommandParam(argMultimap.getValue(PREFIX_SORT_PARAM).get());

        return new SortCommand(sortCommandParam);
    }

}
