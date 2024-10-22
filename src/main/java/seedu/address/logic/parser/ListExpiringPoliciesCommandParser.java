package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);

        int days = 30;

        // check if there is a preamble (which is where the days argument should be provided)
        String preamble = argMultimap.getPreamble();
        if (!preamble.isEmpty()) {
            try {
                days = Integer.parseInt(preamble.trim());
            } catch (NumberFormatException e) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ListExpiringPoliciesCommand.MESSAGE_USAGE));
            }

            if (days <= 0) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ListExpiringPoliciesCommand.MESSAGE_USAGE));
            }
        }

        return new ListExpiringPoliciesCommand(days);
    }
}
