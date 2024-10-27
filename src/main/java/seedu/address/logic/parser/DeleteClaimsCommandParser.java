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

        Index personIndex;
        try {
            personIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteClaimsCommand.MESSAGE_USAGE),
                    pe);
        }

        if (argMultimap.getValue(PREFIX_POLICY_TYPE).isEmpty()
                || argMultimap.getValue(PREFIX_CLAIM_STATUS).isEmpty()
                || argMultimap.getValue(PREFIX_CLAIM_DESC).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteClaimsCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_POLICY_TYPE, PREFIX_CLAIM_STATUS, PREFIX_CLAIM_DESC);

        PolicyType policyType;
        try {
            policyType = ParserUtil.parsePolicyType(argMultimap.getValue(PREFIX_POLICY_TYPE).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteClaimsCommand.MESSAGE_USAGE),
                    pe);
        }

        ClaimStatus claimStatus;
        try {
            claimStatus = ParserUtil.parseClaimStatus(argMultimap.getValue(PREFIX_CLAIM_STATUS).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteClaimsCommand.MESSAGE_USAGE),
                    pe);
        }

        String claimDescription = argMultimap.getValue(PREFIX_CLAIM_DESC).orElseThrow(() ->
                new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteClaimsCommand.MESSAGE_USAGE)));

        return new DeleteClaimsCommand(personIndex, policyType, claimStatus, claimDescription);
    }
}
