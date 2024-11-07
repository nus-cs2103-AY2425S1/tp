package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEXT_PAYMENT_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_START_DATE;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignPolicyCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Payment;
import seedu.address.model.person.Policy;


/**
 * Parses user input for assigning a policy to a client.
 */
public class AssignPolicyCommandParser implements Parser<AssignPolicyCommand> {

    /**
     * Parses the given {@code args} and returns an {@code AssignPolicyCommand} object.
     *
     * @param args The arguments provided by the user for the command.
     * @return An {@code AssignPolicyCommand} object containing the parsed index and policy.
     * @throws ParseException If the user input is not in the expected format, or if there are
     *                        duplicate prefixes or issues with parsing the index or policy.
     */
    @Override
    public AssignPolicyCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_POLICY_NAME, PREFIX_POLICY_START_DATE,
                        PREFIX_POLICY_END_DATE, PREFIX_NEXT_PAYMENT_DATE,
                        PREFIX_PAYMENT_AMOUNT);
        if (!arePrefixesPresent(argMultimap, PREFIX_POLICY_NAME, PREFIX_POLICY_START_DATE,
                PREFIX_POLICY_END_DATE, PREFIX_NEXT_PAYMENT_DATE, PREFIX_PAYMENT_AMOUNT)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AssignPolicyCommand.MESSAGE_USAGE));
        }
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AssignPolicyCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_POLICY_NAME, PREFIX_POLICY_START_DATE,
                PREFIX_POLICY_END_DATE, PREFIX_NEXT_PAYMENT_DATE,
                PREFIX_PAYMENT_AMOUNT);

        String nameString = ParserUtil.parseName(argMultimap.getValue(PREFIX_POLICY_NAME).get()).toString();
        String startDateString = argMultimap.getValue(PREFIX_POLICY_START_DATE).get();
        String endDateString = argMultimap.getValue(PREFIX_POLICY_END_DATE).get();
        String paydateString = argMultimap.getValue(PREFIX_NEXT_PAYMENT_DATE).get();
        String paymentAmount = argMultimap.getValue(PREFIX_PAYMENT_AMOUNT).get();
        String insurancePayment = paydateString + " " + paymentAmount;
        return checkAssignPolicyCommand(index, nameString, startDateString, endDateString, insurancePayment);
    }

    private AssignPolicyCommand checkAssignPolicyCommand(Index index, String nameString,
                                                         String startDateString, String endDateString,
                                                         String insurancePayment) throws ParseException {
        if (!Payment.isValidInsurancePayment(insurancePayment)) {
            throw new ParseException(Payment.MESSAGE_CONSTRAINTS);
        }
        try {
            Policy policy = new Policy(nameString, startDateString, endDateString, insurancePayment);
            return new AssignPolicyCommand(index, policy);
        } catch (CommandException e) {
            throw new ParseException(e.getMessage());
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
