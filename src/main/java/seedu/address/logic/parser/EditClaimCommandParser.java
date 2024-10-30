package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditClaimCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.claim.EditClaimDescriptor;
import seedu.address.model.policy.PolicyType;

/**
 * Parses input arguments to create an EditClaimCommand.
 * This parser processes the user's command input, extracts the person and claim indices, and the fields to edit.
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
                ArgumentTokenizer.tokenize(args, PREFIX_POLICY_TYPE, PREFIX_CLAIM_INDEX, PREFIX_CLAIM_STATUS, PREFIX_CLAIM_DESC);

        if (argMultimap.getPreamble().isEmpty() || !argMultimap.getValue(PREFIX_POLICY_TYPE).isPresent()
                || !argMultimap.getValue(PREFIX_CLAIM_INDEX).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditClaimCommand.MESSAGE_USAGE));
        }

        Index personIndex = parsePersonIndex(argMultimap);
        Index claimIndex = parseClaimIndex(argMultimap);
        PolicyType policyType = ParserUtil.parsePolicyType(argMultimap.getValue(PREFIX_POLICY_TYPE).get());

        EditClaimDescriptor editClaimDescriptor = new EditClaimDescriptor();

        if (argMultimap.getValue(PREFIX_CLAIM_STATUS).isPresent()
                && !argMultimap.getValue(PREFIX_CLAIM_STATUS).get().trim().isEmpty()) {
            editClaimDescriptor.setStatus(ParserUtil.parseClaimStatus(argMultimap.getValue(PREFIX_CLAIM_STATUS).get()));
        }

        if (argMultimap.getValue(PREFIX_CLAIM_DESC).isPresent()
                && !argMultimap.getValue(PREFIX_CLAIM_DESC).get().trim().isEmpty()) {
            editClaimDescriptor.setDescription(argMultimap.getValue(PREFIX_CLAIM_DESC).get());
        }

        if (!editClaimDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditClaimCommand.MESSAGE_NOT_EDITED);
        }

        return new EditClaimCommand(personIndex, policyType, claimIndex, editClaimDescriptor);
    }

    /**
     * Parses the person index from the argument multimap.
     *
     * @param argMultimap Argument multimap containing parsed arguments.
     * @return Index of the person.
     * @throws ParseException if the person index is invalid.
     */
    private Index parsePersonIndex(ArgumentMultimap argMultimap) throws ParseException {
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

