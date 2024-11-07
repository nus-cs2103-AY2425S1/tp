package tuteez.logic.parser;

import static java.util.Objects.requireNonNull;
import static tuteez.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tuteez.logic.Messages.MESSAGE_INVALID_PERSON_INDEX_FORMAT;
import static tuteez.logic.Messages.MESSAGE_INVALID_REMARK_INDEX_FORMAT;
import static tuteez.logic.Messages.MESSAGE_MISSING_PERSON_INDEX;
import static tuteez.logic.Messages.MESSAGE_MISSING_REMARK_INDEX;
import static tuteez.logic.Messages.MESSAGE_MISSING_REMARK_INDEX_PREFIX;
import static tuteez.logic.parser.CliSyntax.PREFIX_REMARK_INDEX;

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
        validateBasicCommandFormat(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_REMARK_INDEX);
        validatePrefixExists(argMultimap);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_REMARK_INDEX);

        Index personIndex = parsePersonIndex(argMultimap);

        return createDeleteRemarkCommand(personIndex, argMultimap);
    }

    private void validateBasicCommandFormat(String args) throws ParseException {
        if (args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRemarkCommand.MESSAGE_USAGE));
        }
    }

    private void validatePrefixExists(ArgumentMultimap argMultimap) throws ParseException {
        if (!argMultimap.getValue(PREFIX_REMARK_INDEX).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_MISSING_REMARK_INDEX_PREFIX));
        }
    }

    private Index parsePersonIndex(ArgumentMultimap argMultimap) throws ParseException {
        String preamble = argMultimap.getPreamble().trim();

        if (preamble.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MISSING_PERSON_INDEX));
        }

        Index index;

        try {
            index = ParserUtil.parseIndex(preamble);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    String.format(MESSAGE_INVALID_PERSON_INDEX_FORMAT, preamble)));
        }
        return index;
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
