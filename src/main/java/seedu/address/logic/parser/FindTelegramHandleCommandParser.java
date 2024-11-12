package seedu.address.logic.parser;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.FindTelegramHandleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.TelegramHandleContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new {@code FindTelegramHandleCommand} object.
 */
public class FindTelegramHandleCommandParser implements Parser<FindTelegramHandleCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindTelegramHandleCommand
     * and returns a FindTelegramHandleCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindTelegramHandleCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindTelegramHandleCommand.MESSAGE_USAGE));
        }

        List<String> keywordList = Arrays.asList(trimmedArgs.split("\\s+"));

        return new FindTelegramHandleCommand(new TelegramHandleContainsKeywordsPredicate(keywordList));
    }
}
