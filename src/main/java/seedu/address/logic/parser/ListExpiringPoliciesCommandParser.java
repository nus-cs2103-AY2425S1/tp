package seedu.address.logic.parser;


import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAYS_FROM_EXPIRY;

import seedu.address.logic.commands.ListExpiringPoliciesCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListExpiringPoliciesCommand object
 */
public class ListExpiringPoliciesCommandParser implements Parser<ListExpiringPoliciesCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListExpiringPoliciesCommand
     * and returns a ListExpiringPoliciesCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListExpiringPoliciesCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DAYS_FROM_EXPIRY);

        int days = 30; // Default value

        // Check if the 'd/' prefix is provided
        if (argMultimap.getValue(PREFIX_DAYS_FROM_EXPIRY).isPresent()) {
            String daysString = argMultimap.getValue(PREFIX_DAYS_FROM_EXPIRY).get();
            try {
                days = Integer.parseInt(daysString);
            } catch (NumberFormatException e) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ListExpiringPoliciesCommand.MESSAGE_USAGE));
            }

            if (days <= 0) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ListExpiringPoliciesCommand.MESSAGE_USAGE));
            }
        }

        // Create and return the command with the parsed days or default
        return new ListExpiringPoliciesCommand(days);
    }
}
