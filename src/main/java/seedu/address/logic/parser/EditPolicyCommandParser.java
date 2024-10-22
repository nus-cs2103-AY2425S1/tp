package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_EXPIRY_DATE;

import java.time.LocalDate;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditPolicyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyType;
import seedu.address.model.policy.Edit

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

        // Set optional fields (these fields can be edited)
        if (argMultimap.getValue(PREFIX_POLICY_PREMIUM_AMOUNT).isPresent()) {
            editPolicyDescriptor.setPremiumAmount(ParserUtil.parsePolicyAmount(argMultimap.getValue(PREFIX_POLICY_PREMIUM_AMOUNT).get()));
        }
        if (argMultimap.getValue(PREFIX_POLICY_COVERAGE_AMOUNT).isPresent()) {
            editPolicyDescriptor.setCoverageAmount(ParserUtil.parsePolicyAmount(argMultimap.getValue(PREFIX_POLICY_COVERAGE_AMOUNT).get()));
        }
        if (argMultimap.getValue(PREFIX_POLICY_EXPIRY_DATE).isPresent()) {
            editPolicyDescriptor.setExpiryDate(ParserUtil.parseExpiryDate(argMultimap.getValue(PREFIX_POLICY_EXPIRY_DATE).get()));
        }

        // Check if any field was edited
        if (!editPolicyDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditPolicyCommand.MESSAGE_NOT_EDITED);
        }

        return new EditPolicyCommand(index, editPolicyDescriptor);
    }
}

//public class AddPolicyCommandParser implements Parser<AddPolicyCommand> {
//    /**
//     * Parses the given {@code String} of arguments in the context of the {@code AddPolicyCommand}
//     * and returns an {@code AddPolicyCommand} object for execution.
//     *
//     * @throws ParseException if the user input does not conform to the expected format.
//     */
//    public AddPolicyCommand parse(String args) throws ParseException {
//        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_POLICY_TYPE,
//                PREFIX_POLICY_PREMIUM_AMOUNT, PREFIX_POLICY_COVERAGE_AMOUNT, PREFIX_POLICY_EXPIRY_DATE);
//
//        // Ensure there is at least one policy and that the preamble is not empty
//        if (!(argMultimap.getValue(PREFIX_POLICY_TYPE).isPresent()) || argMultimap.getPreamble().isEmpty()) {
//            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPolicyCommand.MESSAGE_USAGE));
//        }
//
//        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_POLICY_TYPE, PREFIX_POLICY_PREMIUM_AMOUNT,
//                PREFIX_POLICY_COVERAGE_AMOUNT, PREFIX_POLICY_EXPIRY_DATE);
//
//        Index index;
//        try {
//            // Parse the preamble as the index of the person
//            index = ParserUtil.parseIndex(argMultimap.getPreamble());
//        } catch (IllegalValueException ive) {
//            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
//                    AddPolicyCommand.MESSAGE_USAGE), ive);
//        }
//
//        assert argMultimap.getValue(PREFIX_POLICY_TYPE).isPresent() : "Expected value for 'pt/' but none found.";
//        PolicyType policyType = ParserUtil.parsePolicyType(
//                argMultimap.getValue(PREFIX_POLICY_TYPE).get());
//        double premiumAmount = ParserUtil.parsePolicyAmount(
//                argMultimap.getValue(PREFIX_POLICY_PREMIUM_AMOUNT).orElse(""));
//        double coverageAmount = ParserUtil.parsePolicyAmount(
//                argMultimap.getValue(PREFIX_POLICY_COVERAGE_AMOUNT).orElse(""));
//        LocalDate expiryDate = ParserUtil.parseExpiryDate(
//                argMultimap.getValue(PREFIX_POLICY_EXPIRY_DATE).orElse(""));
//
//        Policy policy = Policy.makePolicy(policyType, premiumAmount, coverageAmount, expiryDate);
//        return new AddPolicyCommand(index, policy);
//    }
//}

