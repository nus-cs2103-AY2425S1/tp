package seedu.address.logic.parser;

import static seedu.address.commons.core.dateformatter.DateFormatter.MM_DD_YYYY_FORMATTER;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_COVERAGE_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_EXPIRY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_PREMIUM_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;

import java.time.LocalDate;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddPolicyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.policy.PolicySet;
import seedu.address.model.policy.PolicyType;

/**
 * Parses input arguments and creates a new AddPolicyCommand object.
 */
public class AddPolicyCommandParser implements Parser<AddPolicyCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code AddPolicyCommand}
     * and returns an {@code AddPolicyCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public AddPolicyCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_POLICY_TYPE,
                PREFIX_POLICY_PREMIUM_AMOUNT, PREFIX_POLICY_COVERAGE_AMOUNT, PREFIX_POLICY_EXPIRY_DATE);

        // Ensure there is at least one policy and that the preamble is not empty
        if (!(argMultimap.getValue(PREFIX_POLICY_TYPE).isPresent()) || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPolicyCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_POLICY_TYPE, PREFIX_POLICY_PREMIUM_AMOUNT,
                PREFIX_POLICY_COVERAGE_AMOUNT, PREFIX_POLICY_EXPIRY_DATE);

        Index index;
        try {
            // Parse the preamble as the index of the person
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddPolicyCommand.MESSAGE_USAGE), ive);
        }

        assert argMultimap.getValue(PREFIX_POLICY_TYPE).isPresent() : "Expected value for 'pt/' but none found.";
        PolicyType policyType = ParserUtil.parsePolicyType(argMultimap.getValue(PREFIX_POLICY_TYPE).get());
        try {
            double premiumAmount = argMultimap.getValue(PREFIX_POLICY_PREMIUM_AMOUNT)
                    .map(Double::parseDouble).orElse(-1.0);
            double coverageAmount = argMultimap.getValue(PREFIX_POLICY_COVERAGE_AMOUNT)
                    .map(Double::parseDouble).orElse(-1.0);
            LocalDate expiryDate = argMultimap.getValue(PREFIX_POLICY_EXPIRY_DATE)
                    .<LocalDate>map(date -> LocalDate.parse(date, MM_DD_YYYY_FORMATTER)).orElse(null);
        } catch (NumberFormatException e) {
            throw new ParseException(Policy.AMOUNT_MESSAGE_CONSTRAINTS);
        } catch (DateTimeParseException e) {
            throw new ParseException(Policy.)
        }

        return new AddPolicyCommand(index, policySet);
    }
}
