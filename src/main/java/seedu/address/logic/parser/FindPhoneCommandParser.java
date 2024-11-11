package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.FindPhoneCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PhoneBeginsWithKeywordPredicate;

/**
 * Parses input arguments and creates a new FindPhoneCommand object
 */
public class FindPhoneCommandParser implements Parser<FindPhoneCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindPhoneNumberCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindPhoneCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty() || !trimmedArgs.matches("\\d+")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPhoneCommand.MESSAGE_USAGE));
        }

        String[] phoneNumber = trimmedArgs.split("\\s+");

        if (phoneNumber.length != 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPhoneCommand.MESSAGE_USAGE));
        }

        return new FindPhoneCommand(new PhoneBeginsWithKeywordPredicate(phoneNumber[0]));
    }

}
