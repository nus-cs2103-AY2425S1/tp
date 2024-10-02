package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Remark;
import seedu.address.commons.core.Messages;

/**
 * Parses input arguments and creates a new RemarkCommand object.
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemarkCommand
     * and returns a RemarkCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemarkCommand parse(String args) throws ParseException {
        try {
            ArgumentMultimap argMultimap =
                    ArgumentTokenizer.tokenize(args, PREFIX_REMARK);

            Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
            String remarkValue = argMultimap.getValue(PREFIX_REMARK).orElse("");
            Remark remark = new Remark(remarkValue);
            return new RemarkCommand(index, remark);
        } catch (Exception e) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }
    }
}