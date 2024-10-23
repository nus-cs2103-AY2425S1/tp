package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEXT_PAYMENT_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_START_DATE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignPolicyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
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

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignPolicyCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_POLICY_NAME, PREFIX_POLICY_START_DATE,
                PREFIX_POLICY_END_DATE, PREFIX_NEXT_PAYMENT_DATE,
                PREFIX_PAYMENT_AMOUNT);
        try {
            Policy policy = ParserUtil.parsePolicy(args);
            return new AssignPolicyCommand(index, policy);
        } catch (ParseException pe) {
            throw new ParseException(pe.getMessage() + "\n" + AssignPolicyCommand.MESSAGE_USAGE);
        }
    }


}
