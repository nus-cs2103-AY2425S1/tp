package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_NAME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PaidCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new PaidCommand object.
 */
public class PaidCommandParser implements Parser<PaidCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PaidCommand
     * and returns a PaidCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PaidCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_POLICY_NAME);
        try {
            Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
            String policyName = argMultimap.getValue(PREFIX_POLICY_NAME).orElseThrow(() ->
                    new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PaidCommand.MESSAGE_USAGE)));
            return new PaidCommand(index, policyName);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PaidCommand.MESSAGE_USAGE), pe);
        }
    }
}
