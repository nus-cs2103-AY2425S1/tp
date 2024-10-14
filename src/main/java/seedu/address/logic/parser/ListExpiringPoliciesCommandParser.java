package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ListExpiringPoliciesCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListExpiringPoliciesCommand object
 * Currently ListExpiringPoliciesCommand has no arguments but this parser will support future
 * iterations when arguments are passed to ListExpiringPoliciesCommand to customise the time frame
 */
public class ListExpiringPoliciesCommandParser implements Parser<ListExpiringPoliciesCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListExpiringPoliciesCommand
     * and returns a ListExpiringPoliciesCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public ListExpiringPoliciesCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);

        // checks if input empty since no args
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListExpiringPoliciesCommand.MESSAGE_USAGE));
        }

        return new ListExpiringPoliciesCommand();
    }
}

