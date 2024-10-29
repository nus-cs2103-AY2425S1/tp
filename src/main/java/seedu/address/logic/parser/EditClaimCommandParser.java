package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditClaimCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.claim.EditClaimDescriptor;
import seedu.address.model.policy.PolicyType;

public class EditClaimCommandParser implements Parser<EditClaimCommand> {

    public EditClaimCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_POLICY_TYPE, PREFIX_CLAIM_STATUS, PREFIX_CLAIM_DESC);

        if (!(argMultimap.getValue(PREFIX_POLICY_TYPE).isPresent())
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditClaimCommand.MESSAGE_USAGE));
        }

        Index personIndex;
        Index claimIndex;
        try {
            String[] indices = argMultimap.getPreamble().split("\\s+");
            if (indices.length < 2) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditClaimCommand.MESSAGE_USAGE));
            }
            personIndex = ParserUtil.parseIndex(indices[0]);
            claimIndex = ParserUtil.parseIndex(indices[1]);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditClaimCommand.MESSAGE_USAGE), ive);
        }

        PolicyType policyType = ParserUtil.parsePolicyType(argMultimap.getValue(PREFIX_POLICY_TYPE).get());
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
