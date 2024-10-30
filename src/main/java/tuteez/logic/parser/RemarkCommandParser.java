package tuteez.logic.parser;

import static java.util.Objects.requireNonNull;
import static tuteez.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tuteez.logic.Messages.MESSAGE_INVALID_PERSON_INDEX_FORMAT;
import static tuteez.logic.Messages.MESSAGE_INVALID_REMARK_INDEX_FORMAT;
import static tuteez.logic.Messages.MESSAGE_MISSING_PERSON_INDEX;
import static tuteez.logic.Messages.MESSAGE_MISSING_REMARK_INDEX;
import static tuteez.logic.Messages.MESSAGE_REMARK_MISSING_COMMAND_TYPE;
import static tuteez.logic.Messages.MESSAGE_REMARK_MULTIPLE_OPERATIONS;
import static tuteez.logic.parser.CliSyntax.PREFIX_ADD_REMARK;
import static tuteez.logic.parser.CliSyntax.PREFIX_DELETE_REMARK;

import tuteez.commons.core.index.Index;
import tuteez.logic.commands.AddRemarkCommand;
import tuteez.logic.commands.DeleteRemarkCommand;
import tuteez.logic.commands.RemarkCommand;
import tuteez.logic.parser.exceptions.ParseException;
import tuteez.model.remark.Remark;

/**
 * Parses input arguments and creates a new RemarkCommand object
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemarkCommand
     * and returns a RemarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ADD_REMARK, PREFIX_DELETE_REMARK);

        validateBasicCommandFormat(args);
        validateOperationType(argMultimap);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ADD_REMARK, PREFIX_DELETE_REMARK);

        Index personIndex = parsePersonIndex(argMultimap);

        if (isAddRemarkCommand(argMultimap)) {
            return createAddRemarkCommand(personIndex, argMultimap);
        }

        if (isDeleteRemarkCommand(argMultimap)) {
            return createDeleteRemarkCommand(personIndex, argMultimap);
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_REMARK_MISSING_COMMAND_TYPE));
    }

    private void validateBasicCommandFormat(String args) throws ParseException {
        if (args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }
    }

    private void validateOperationType(ArgumentMultimap argMultimap) throws ParseException {
        boolean hasAddPrefix = argMultimap.getValue(PREFIX_ADD_REMARK).isPresent();
        boolean hasDeletePrefix = argMultimap.getValue(PREFIX_DELETE_REMARK).isPresent();

        if (hasAddPrefix && hasDeletePrefix) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_REMARK_MULTIPLE_OPERATIONS));
        }

        if (!hasAddPrefix && !hasDeletePrefix) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_REMARK_MISSING_COMMAND_TYPE));
        }
    }

    private Index parsePersonIndex(ArgumentMultimap argMultimap) throws ParseException {
        String preamble = argMultimap.getPreamble().trim();

        if (preamble.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MISSING_PERSON_INDEX));
        }

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_INVALID_PERSON_INDEX_FORMAT));
        }
        return index;
    }

    private AddRemarkCommand createAddRemarkCommand(Index personIndex, ArgumentMultimap argMultimap)
            throws ParseException {

        assert argMultimap.getValue(PREFIX_ADD_REMARK).isPresent();

        String remarkString = argMultimap.getValue(PREFIX_ADD_REMARK).get();

        Remark remark = ParserUtil.parseRemark(remarkString);

        return new AddRemarkCommand(personIndex, remark);
    }

    private DeleteRemarkCommand createDeleteRemarkCommand(Index personIndex, ArgumentMultimap argMultimap)
            throws ParseException {

        assert argMultimap.getValue(PREFIX_DELETE_REMARK).isPresent();

        String remarkIndexStr = argMultimap.getValue(PREFIX_DELETE_REMARK).get();

        if (remarkIndexStr.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MISSING_REMARK_INDEX));
        }

        Index index;

        try {
            index = ParserUtil.parseIndex(remarkIndexStr);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_INVALID_REMARK_INDEX_FORMAT));
        }
        return new DeleteRemarkCommand(personIndex, index);
    }

    private boolean isAddRemarkCommand(ArgumentMultimap argMultimap) {
        return argMultimap.getValue(PREFIX_ADD_REMARK).isPresent();
    }

    private boolean isDeleteRemarkCommand(ArgumentMultimap argMultimap) {
        return argMultimap.getValue(PREFIX_DELETE_REMARK).isPresent();
    }
}
