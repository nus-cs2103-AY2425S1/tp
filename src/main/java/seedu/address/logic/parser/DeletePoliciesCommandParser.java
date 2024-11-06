package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;

import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeletePoliciesCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.policy.PolicyType;


/**
 * Parses input arguments and creates a new DeletePoliciesCommand object.
 */
public class DeletePoliciesCommandParser implements Parser<DeletePoliciesCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeletePoliciesCommand
     * and returns a DeletePoliciesCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public DeletePoliciesCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_POLICY_TYPE);

        if (!(argMultimap.getValue(PREFIX_POLICY_TYPE).isPresent())
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeletePoliciesCommand.MESSAGE_USAGE));
        }
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble()); //index is the preamble
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePoliciesCommand.MESSAGE_USAGE), ive);
        }
        Set<PolicyType> policyTypes = ParserUtil.parsePolicyTypes(argMultimap.getAllValues(PREFIX_POLICY_TYPE));
        return new DeletePoliciesCommand(index, policyTypes);
    }
}
