package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.UpdatePolicyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.policy.PolicyMap;

/**
 * Parses input arguments and creates a new UpdatePolicyCommand object.
 */
public class UpdatePolicyCommandParser implements Parser<UpdatePolicyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UpdatePolicyCommand
     * and returns an UpdatePolicyCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public UpdatePolicyCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_POLICY_TYPE);

        if (!(argMultimap.getValue(PREFIX_POLICY_TYPE).isPresent())
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdatePolicyCommand.MESSAGE_USAGE));
        }
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdatePolicyCommand.MESSAGE_USAGE), ive);
        }

        PolicyMap policySet = ParserUtil.parsePolicies(argMultimap.getAllValues(PREFIX_POLICY_TYPE));
        return new UpdatePolicyCommand(index, policySet);
    }
}
