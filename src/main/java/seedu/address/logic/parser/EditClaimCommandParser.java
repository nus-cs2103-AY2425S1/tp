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

public class EditClaimCommandParser implements Parser<EditClaimCommand> {

    public EditClaimCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_POLICY_TYPE, PREFIX_CLAIM_INDEX, PREFIX_CLAIM_STATUS, PREFIX_CLAIM_DESC);

        // Ensure person index is in the preamble and required fields are present
        if (argMultimap.getPreamble().isEmpty() || !argMultimap.getValue(PREFIX_POLICY_TYPE).isPresent()
                || !argMultimap.getValue(PREFIX_CLAIM_INDEX).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditClaimCommand.MESSAGE_USAGE));
        }

        // Parse the person index from the preamble
        Index personIndex;
        try {
            personIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditClaimCommand.MESSAGE_USAGE), ive);
        }

        // Parse required claim index from `c/`
        Index claimIndex;
        try {
            claimIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_CLAIM_INDEX).get());
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditClaimCommand.MESSAGE_USAGE), ive);
        }

        // Parse policy type
        PolicyType policyType = ParserUtil.parsePolicyType(argMultimap.getValue(PREFIX_POLICY_TYPE).get());

        // Create EditClaimDescriptor to hold optional fields
        EditClaimDescriptor editClaimDescriptor = new EditClaimDescriptor();
        if (argMultimap.getValue(PREFIX_CLAIM_STATUS).isPresent()) {
            editClaimDescriptor.setStatus(ParserUtil.parseClaimStatus(argMultimap.getValue(PREFIX_CLAIM_STATUS).get()));
        }
        if (argMultimap.getValue(PREFIX_CLAIM_DESC).isPresent()) {
            editClaimDescriptor.setDescription(argMultimap.getValue(PREFIX_CLAIM_DESC).get());
        }

        // Check if any field was edited
        if (!editClaimDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditClaimCommand.MESSAGE_NOT_EDITED);
        }

        return new EditClaimCommand(personIndex, policyType, claimIndex, editClaimDescriptor);
    }
}
