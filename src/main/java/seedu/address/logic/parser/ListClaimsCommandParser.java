package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ListClaimsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.policy.PolicyType;

/**
 * Parses input arguments and creates a new {@code ListClaimsCommand} object.
 */
public class ListClaimsCommandParser implements Parser<ListClaimsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code ListClaimsCommand}
     * and returns a {@code ListClaimsCommand} object for execution.
     *
     * @param args The user input string containing the arguments.
     * @return The constructed {@code ListClaimsCommand}.
     * @throws ParseException If the input does not conform to the expected format.
     */
    public ListClaimsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_POLICY_TYPE);

        Index clientIndex;
        try {
            clientIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListClaimsCommand.MESSAGE_USAGE),
                    pe);
        }

        if (argMultimap.getValue(PREFIX_POLICY_TYPE).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListClaimsCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_POLICY_TYPE);

        PolicyType policyType;
        policyType = ParserUtil.parsePolicyType(argMultimap.getValue(PREFIX_POLICY_TYPE).get());

        return new ListClaimsCommand(clientIndex, policyType);
    }
}
