package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddClaimCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.claim.Claim;
import seedu.address.model.claim.ClaimStatus;
import seedu.address.model.policy.PolicyType;

/**
 * Parses input arguments and creates a new AddClaimCommand object.
 */
public class AddClaimCommandParser implements Parser<AddClaimCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code AddClaimCommand}
     * and returns an {@code AddClaimCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public AddClaimCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_POLICY_TYPE, PREFIX_CLAIM_STATUS,
                PREFIX_CLAIM_DESC);

        if (!(argMultimap.getValue(PREFIX_CLAIM_STATUS).isPresent()) || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClaimCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_POLICY_TYPE, PREFIX_CLAIM_STATUS, PREFIX_CLAIM_DESC);

        Index personIndex;

        try {
            personIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClaimCommand.MESSAGE_USAGE), ive);
        }

        PolicyType policyType = ParserUtil.parsePolicyType(argMultimap.getValue(PREFIX_POLICY_TYPE).get());
        ClaimStatus claimStatus = ParserUtil.parseClaimStatus(argMultimap.getValue(PREFIX_CLAIM_STATUS).get());
        String claimDescription = argMultimap.getValue(PREFIX_CLAIM_DESC).get();
        Claim claim = new Claim(claimStatus, claimDescription);

        return new AddClaimCommand(personIndex, claim, policyType);
    }
}
