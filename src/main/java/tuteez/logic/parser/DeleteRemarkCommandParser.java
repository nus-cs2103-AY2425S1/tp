package tuteez.logic.parser;

import static java.util.Objects.requireNonNull;
import static tuteez.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tuteez.logic.Messages.MESSAGE_INVALID_REMARK_INDEX_FORMAT;
import static tuteez.logic.Messages.MESSAGE_MISSING_REMARK_INDEX;
import static tuteez.logic.Messages.MESSAGE_MISSING_REMARK_INDEX_PREFIX;
import static tuteez.logic.parser.CliSyntax.PREFIX_REMARK_INDEX;
import static tuteez.logic.parser.ParserUtil.parsePersonIndex;
import static tuteez.logic.parser.ParserUtil.validateNonEmptyArgs;
import static tuteez.logic.parser.ParserUtil.validatePrefixExists;

import tuteez.commons.core.index.Index;
import tuteez.logic.commands.DeleteRemarkCommand;
import tuteez.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RemarkCommand object
 */
public class DeleteRemarkCommandParser implements Parser<DeleteRemarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemarkCommand
     * and returns a RemarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteRemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        validateNonEmptyArgs(args, DeleteRemarkCommand.MESSAGE_USAGE);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_REMARK_INDEX);
        validatePrefixExists(argMultimap, PREFIX_REMARK_INDEX, MESSAGE_MISSING_REMARK_INDEX_PREFIX);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_REMARK_INDEX);

        Index personIndex = parsePersonIndex(argMultimap);

        return createDeleteRemarkCommand(personIndex, argMultimap);
    }

    private DeleteRemarkCommand createDeleteRemarkCommand(Index personIndex, ArgumentMultimap argMultimap)
            throws ParseException {

        assert argMultimap.getValue(PREFIX_REMARK_INDEX).isPresent();

        String remarkIndexStr = argMultimap.getValue(PREFIX_REMARK_INDEX).get();

        if (remarkIndexStr.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MISSING_REMARK_INDEX));
        }

        Index index;

        try {
            index = ParserUtil.parseIndex(remarkIndexStr);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    String.format(MESSAGE_INVALID_REMARK_INDEX_FORMAT, remarkIndexStr)));
        }
        return new DeleteRemarkCommand(personIndex, index);
    }
}
