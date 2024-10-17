package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.FindAddressCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AddressContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindAddressCommand object
 */
public class FindAddressCommandParser implements Parser<FindAddressCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindAddressCommand
     * and returns a FindAddressCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindAddressCommand parse(String args) throws ParseException {
        String addressKeywords = args.trim();
        if (addressKeywords.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindAddressCommand.MESSAGE_USAGE));
        }

        return new FindAddressCommand(new AddressContainsKeywordsPredicate(addressKeywords));
    }
}
