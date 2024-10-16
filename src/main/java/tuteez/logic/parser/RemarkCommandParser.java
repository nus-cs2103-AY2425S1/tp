package tuteez.logic.parser;

import static tuteez.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tuteez.logic.parser.CliSyntax.PREFIX_ADD_REMARK;
import static tuteez.logic.parser.CliSyntax.PREFIX_DELETE_REMARK;

import tuteez.commons.core.index.Index;
import tuteez.logic.commands.AddRemarkCommand;
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
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemarkCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ADD_REMARK, PREFIX_DELETE_REMARK);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE), pe);
        }

        if (argMultimap.getValue(PREFIX_ADD_REMARK).isPresent()) {
            String remarkString = argMultimap.getValue(PREFIX_ADD_REMARK).get();
            return new AddRemarkCommand(index, new Remark(remarkString));
        }

        if (argMultimap.getValue(PREFIX_DELETE_REMARK).isPresent()) {
            // TO DO: add delete remark logic
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
    }
}
