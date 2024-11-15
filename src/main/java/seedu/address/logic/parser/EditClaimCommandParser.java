package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditClaimCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.claim.Claim;
import seedu.address.model.claim.EditClaimDescriptor;
import seedu.address.model.policy.PolicyType;

/**
 * Parses input arguments to create an EditClaimCommand.
 * This parser processes the user's command input, extracts the client and claim indices, and the fields to edit.
 * Ensures that mandatory fields like policy type and claim index are provided.
 */
public class EditClaimCommandParser implements Parser<EditClaimCommand> {

    /**
     * Parses the input arguments to create an EditClaimCommand.
     *
     * @param args User input arguments.
     * @return EditClaimCommand object representing the command to edit a claim.
     * @throws ParseException if the input format is incorrect or required fields are missing.
     */
    public EditClaimCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_POLICY_TYPE, PREFIX_CLAIM_INDEX,
                        PREFIX_CLAIM_STATUS, PREFIX_CLAIM_DESC);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_POLICY_TYPE, PREFIX_CLAIM_INDEX,
                PREFIX_CLAIM_STATUS, PREFIX_CLAIM_DESC);

        validateRequiredFields(argMultimap);

        Index clientIndex = parseClientIndex(argMultimap);
        Index claimIndex = parseClaimIndex(argMultimap);
        PolicyType policyType = ParserUtil.parsePolicyType(argMultimap.getValue(PREFIX_POLICY_TYPE).get());

        EditClaimDescriptor editClaimDescriptor = buildEditClaimDescriptor(argMultimap);

        if (!editClaimDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditClaimCommand.MESSAGE_NOT_EDITED);
        }

        return new EditClaimCommand(clientIndex, policyType, claimIndex, editClaimDescriptor);
    }

    /**
     * Validates that essential fields like preamble, policy type, and claim index are present.
     *
     * @param argMultimap Argument multimap containing parsed arguments.
     * @throws ParseException if any required field is missing.
     */
    private void validateRequiredFields(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getPreamble().isEmpty()
                || !argMultimap.getValue(PREFIX_POLICY_TYPE).isPresent()
                || !argMultimap.getValue(PREFIX_CLAIM_INDEX).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditClaimCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Constructs an EditClaimDescriptor based on available fields in the ArgumentMultimap.
     *
     * @param argMultimap Argument multimap containing parsed arguments.
     * @return EditClaimDescriptor containing the fields to edit.
     * @throws ParseException if any field parsing fails.
     */
    private EditClaimDescriptor buildEditClaimDescriptor(ArgumentMultimap argMultimap) throws ParseException {
        EditClaimDescriptor descriptor = new EditClaimDescriptor();

        if (argMultimap.getValue(PREFIX_CLAIM_STATUS).isPresent()
                && !argMultimap.getValue(PREFIX_CLAIM_STATUS).get().trim().isEmpty()) {
            descriptor.setStatus(ParserUtil.parseClaimStatus(argMultimap.getValue(PREFIX_CLAIM_STATUS).get()));
        }

        if (argMultimap.getValue(PREFIX_CLAIM_DESC).isPresent()
                && !argMultimap.getValue(PREFIX_CLAIM_DESC).get().trim().isEmpty()) {
            String desc = argMultimap.getValue(PREFIX_CLAIM_DESC).get();
            if (!Claim.isValidClaim(desc)) {
                throw new ParseException(Claim.MESSAGE_CONSTRAINTS);
            }
            descriptor.setDescription(desc);
        }
        return descriptor;
    }

    /**
     * Parses the client index from the argument multimap.
     *
     * @param argMultimap Argument multimap containing parsed arguments.
     * @return Index of the client.
     * @throws ParseException if the client index is invalid when not found.
     */
    private Index parseClientIndex(ArgumentMultimap argMultimap) throws ParseException {
        try {
            return ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditClaimCommand.MESSAGE_USAGE), ive);
        }
    }

    /**
     * Parses the claim index from the argument multimap.
     *
     * @param argMultimap Argument multimap containing parsed arguments.
     * @return Index of the claim.
     * @throws ParseException if the claim index is invalid.
     */
    private Index parseClaimIndex(ArgumentMultimap argMultimap) throws ParseException {
        try {
            return ParserUtil.parseIndex(argMultimap.getValue(PREFIX_CLAIM_INDEX).get());
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditClaimCommand.MESSAGE_USAGE), ive);
        }
    }
}

