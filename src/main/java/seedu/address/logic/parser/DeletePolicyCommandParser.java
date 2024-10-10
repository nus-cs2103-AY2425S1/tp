package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeletePolicyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.policy.PolicyMap;

/**
 * Parses input arguments and creates a new DeletePolicyCommand object.
 */
public class DeletePolicyCommandParser implements Parser<DeletePolicyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeletePolicyCommand
     * and returns a DeletePolicyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public DeletePolicyCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_POLICY_TYPE);

        if (!(argMultimap.getValue(PREFIX_POLICY_TYPE).isPresent())
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeletePolicyCommand.MESSAGE_USAGE));
        }
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble()); //index is the preamble
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePolicyCommand.MESSAGE_USAGE), ive);
        }
        PolicyMap policyDel = ParserUtil.parsePolicies(argMultimap.getAllValues(PREFIX_POLICY_TYPE));
        return new DeletePolicyCommand(index, policyDel);
    }
}
