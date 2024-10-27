package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteClaimsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.claim.ClaimStatus;
import seedu.address.model.policy.PolicyType;

/**
 * Parses input arguments and creates a new {@code DeleteClaimsCommand} object.
 */
public class DeleteClaimsCommandParser implements Parser<DeleteClaimsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code DeleteClaimsCommand}
     * and returns a {@code DeleteClaimsCommand} object for execution.
     *
     * @param args The user input string containing the arguments.
     * @return The constructed {@code DeleteClaimsCommand}.
     * @throws ParseException If the input does not conform to the expected format.
     */
    @Override
    public DeleteClaimsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_POLICY_TYPE, PREFIX_CLAIM_STATUS,
                PREFIX_CLAIM_DESC);
        Index personIndex = parsePersonIndex(argMultimap);
        validatePrefixes(argMultimap);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_POLICY_TYPE, PREFIX_CLAIM_STATUS, PREFIX_CLAIM_DESC);

        PolicyType policyType = parsePolicyType(argMultimap);
        ClaimStatus claimStatus = parseClaimStatus(argMultimap);
        String claimDescription = parseClaimDescription(argMultimap);

        return new DeleteClaimsCommand(personIndex, policyType, claimStatus, claimDescription);
    }

    /**
     * Parses the person index from the given argument multimap.
     *
     * @param argMultimap The argument multimap containing the parsed arguments.
     * @return The parsed person index.
     * @throws ParseException If the index is not a valid positive integer.
     */
    private Index parsePersonIndex(ArgumentMultimap argMultimap) throws ParseException {
        try {
            return ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteClaimsCommand.MESSAGE_USAGE),
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
                || argMultimap.getValue(PREFIX_CLAIM_STATUS).isEmpty()
                || argMultimap.getValue(PREFIX_CLAIM_DESC).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteClaimsCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses the policy type from the given argument multimap.
     *
     * @param argMultimap The argument multimap containing the parsed arguments.
     * @return The parsed policy type.
     * @throws ParseException If the policy type is invalid.
     */
    private PolicyType parsePolicyType(ArgumentMultimap argMultimap) throws ParseException {
        try {
            return ParserUtil.parsePolicyType(argMultimap.getValue(PREFIX_POLICY_TYPE).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteClaimsCommand.MESSAGE_USAGE),
                    pe);
        }
    }

    /**
     * Parses the claim status from the given argument multimap.
     *
     * @param argMultimap The argument multimap containing the parsed arguments.
     * @return The parsed claim status.
     * @throws ParseException If the claim status is invalid.
     */
    private ClaimStatus parseClaimStatus(ArgumentMultimap argMultimap) throws ParseException {
        try {
            return ParserUtil.parseClaimStatus(argMultimap.getValue(PREFIX_CLAIM_STATUS).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteClaimsCommand.MESSAGE_USAGE),
                    pe);
        }
    }

    /**
     * Parses the claim description from the given argument multimap.
     *
     * @param argMultimap The argument multimap containing the parsed arguments.
     * @return The parsed claim description.
     * @throws ParseException If the claim description is not present.
     */
    private String parseClaimDescription(ArgumentMultimap argMultimap) throws ParseException {
        return argMultimap.getValue(PREFIX_CLAIM_DESC).orElseThrow(() ->
                new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteClaimsCommand.MESSAGE_USAGE)));
    }

}
