package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddPolicyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.policy.PolicySet;

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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_POLICY_TYPE);

        // Ensure there is at least one policy and that the preamble is not empty
        if (!(argMultimap.getValue(PREFIX_POLICY_TYPE).isPresent()) || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPolicyCommand.MESSAGE_USAGE));
        }

        Index index;
        try {
            // Parse the preamble as the index of the person
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPolicyCommand.MESSAGE_USAGE), ive);
        }

        // Parse all policy types from the input and add them to a PolicySet
        PolicySet policySet = ParserUtil.parsePolicies(argMultimap.getAllValues(PREFIX_POLICY_TYPE));

        // Return the new AddPolicyCommand with the parsed index and policySet
        return new AddPolicyCommand(index, policySet);
    }
}
