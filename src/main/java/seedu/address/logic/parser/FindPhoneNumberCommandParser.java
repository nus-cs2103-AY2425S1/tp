package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PHONE_NUMBER_KEYWORDS;

import java.util.Arrays;

import seedu.address.logic.commands.FindPhoneNumberCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PhoneNumberContainsKeywordPredicate;

/**
 * Parses input arguments and creates a new FindPhoneNumberCommand object
 */
public class FindPhoneNumberCommandParser implements Parser<FindPhoneNumberCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindPhoneNumberCommand
     * and returns a FindPhoneNumberCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     *      or if keywords are not all numbers
     */
    public FindPhoneNumberCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPhoneNumberCommand.MESSAGE_USAGE));
        }

        String[] phoneNumberKeywords = trimmedArgs.split("\\s+");
        for (String keyword : phoneNumberKeywords) {
            if (!keyword.matches("\\d+")) {
                throw new ParseException(MESSAGE_INVALID_PHONE_NUMBER_KEYWORDS);
            }
        }
        return new FindPhoneNumberCommand(new PhoneNumberContainsKeywordPredicate(Arrays.asList(phoneNumberKeywords)));
    }

}
