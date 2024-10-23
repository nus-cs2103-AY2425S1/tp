package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_COVERAGE_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_EXPIRY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_PREMIUM_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditPolicyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.policy.EditPolicyDescriptor;
import seedu.address.model.policy.PolicyType;


/**
 * Parses input arguments and creates a new UpdatePolicyCommand object.
 */
public class EditPolicyCommandParser implements Parser<EditPolicyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UpdatePolicyCommand
     * and returns an UpdatePolicyCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public EditPolicyCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_POLICY_TYPE, PREFIX_POLICY_PREMIUM_AMOUNT,
                        PREFIX_POLICY_COVERAGE_AMOUNT, PREFIX_POLICY_EXPIRY_DATE);

        if (!(argMultimap.getValue(PREFIX_POLICY_TYPE).isPresent())
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPolicyCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_POLICY_TYPE, PREFIX_POLICY_PREMIUM_AMOUNT,
                PREFIX_POLICY_COVERAGE_AMOUNT, PREFIX_POLICY_EXPIRY_DATE);
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPolicyCommand.MESSAGE_USAGE), ive);
        }
        PolicyType policyType = ParserUtil.parsePolicyType(argMultimap.getValue(PREFIX_POLICY_TYPE).get());
        EditPolicyDescriptor editPolicyDescriptor = new EditPolicyDescriptor(policyType);

        if (argMultimap.getValue(PREFIX_POLICY_PREMIUM_AMOUNT).isPresent()) {
            editPolicyDescriptor.setPremiumAmount(ParserUtil.parsePremiumAmount(argMultimap
                    .getValue(PREFIX_POLICY_PREMIUM_AMOUNT).get()));
        }
        if (argMultimap.getValue(PREFIX_POLICY_COVERAGE_AMOUNT).isPresent()) {
            editPolicyDescriptor.setCoverageAmount(ParserUtil.parseCoverageAmount(argMultimap
                    .getValue(PREFIX_POLICY_COVERAGE_AMOUNT).get()));
        }
        if (argMultimap.getValue(PREFIX_POLICY_EXPIRY_DATE).isPresent()) {
            editPolicyDescriptor.setExpiryDate(ParserUtil.parseExpiryDate(argMultimap
                    .getValue(PREFIX_POLICY_EXPIRY_DATE).get()));
        }

        // Check if any field was edited
        if (!editPolicyDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditPolicyCommand.MESSAGE_NOT_EDITED);
        }

        return new EditPolicyCommand(index, editPolicyDescriptor);
    }
}


