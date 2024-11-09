package tuteez.logic.parser;

import static java.util.Objects.requireNonNull;
import static tuteez.logic.Messages.MESSAGE_MISSING_REMARK_PREFIX;
import static tuteez.logic.parser.CliSyntax.PREFIX_REMARK;
import static tuteez.logic.parser.ParserUtil.parsePersonIndex;
import static tuteez.logic.parser.ParserUtil.validateNonEmptyArgs;
import static tuteez.logic.parser.ParserUtil.validatePrefixExists;

import tuteez.commons.core.index.Index;
import tuteez.logic.commands.AddRemarkCommand;
import tuteez.logic.parser.exceptions.ParseException;
import tuteez.model.remark.Remark;

/**
 * Parses input arguments and creates a new RemarkCommand object
 */
public class AddRemarkCommandParser implements Parser<AddRemarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemarkCommand
     * and returns a RemarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddRemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_REMARK);

        validateNonEmptyArgs(args, AddRemarkCommand.MESSAGE_USAGE);
        validatePrefixExists(argMultimap, PREFIX_REMARK, MESSAGE_MISSING_REMARK_PREFIX);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_REMARK);

        Index personIndex = parsePersonIndex(argMultimap);

        return createAddRemarkCommand(personIndex, argMultimap);
    }

    private AddRemarkCommand createAddRemarkCommand(Index personIndex, ArgumentMultimap argMultimap)
            throws ParseException {

        assert argMultimap.getValue(PREFIX_REMARK).isPresent();

        String remarkString = argMultimap.getValue(PREFIX_REMARK).get();

        Remark remark = ParserUtil.parseRemark(remarkString);

        return new AddRemarkCommand(personIndex, remark);
    }
}
