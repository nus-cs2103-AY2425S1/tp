package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteClaimCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.policy.PolicyType;

/**
 * Parses input arguments and creates a new {@code DeleteClaimCommand} object.
 */
public class DeleteClaimCommandParser implements Parser<DeleteClaimCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code DeleteClaimCommand}
     * and returns a {@code DeleteClaimCommand} object for execution.
     *
     * @param args The user input string containing the arguments.
     * @return The constructed {@code DeleteClaimCommand}.
     * @throws ParseException If the input does not conform to the expected format.
     */
    @Override
    public DeleteClaimCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_POLICY_TYPE, PREFIX_CLAIM_INDEX);
        Index clientIndex = parseClientIndex(argMultimap);
        validatePrefixes(argMultimap);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_POLICY_TYPE, PREFIX_CLAIM_INDEX);

        PolicyType policyType = ParserUtil.parsePolicyType(argMultimap.getValue(PREFIX_POLICY_TYPE).get());
        Index claimIndex = parseClaimIndex(argMultimap);

        return new DeleteClaimCommand(clientIndex, policyType, claimIndex);
    }

    /**
     * Parses the client index from the given argument multimap.
     *
     * @param argMultimap The argument multimap containing the parsed arguments.
     * @return The parsed client index.
     * @throws ParseException If the index is not a valid positive integer.
     */
    private Index parseClientIndex(ArgumentMultimap argMultimap) throws ParseException {
        try {
            return ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteClaimCommand.MESSAGE_USAGE),
                    pe);
        }
    }

    /**
     * Validates that all required prefixes are present in the argument multimap.
     *
     * @param argMultimap The argument multimap containing the parsed arguments.
     * @throws ParseException If any required prefix is missing.
     */
    private void validatePrefixes(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_POLICY_TYPE).isEmpty()
                || argMultimap.getValue(PREFIX_CLAIM_INDEX).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteClaimCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses the claim index from the given argument multimap.
     *
     * @param argMultimap The argument multimap containing the parsed arguments.
     * @return The parsed claim index.
     * @throws ParseException If the claim index is not a valid positive integer.
     */
    private Index parseClaimIndex(ArgumentMultimap argMultimap) throws ParseException {
        try {
            return ParserUtil.parseIndex(argMultimap.getValue(PREFIX_CLAIM_INDEX).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteClaimCommand.MESSAGE_USAGE),
                    pe);
        }
    }

}
