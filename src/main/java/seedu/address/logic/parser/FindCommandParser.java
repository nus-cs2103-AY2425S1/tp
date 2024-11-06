package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEHANDLE;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.TelegramHandle;
import seedu.address.model.person.TelegramHandleContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (trimmedArgs.startsWith(PREFIX_TELEHANDLE.getPrefix())) {
            String handleArgs = trimmedArgs.substring(PREFIX_TELEHANDLE.getPrefix().length()).trim();
            if (handleArgs.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            String[] handleKeywords = handleArgs.split("\\s+");
            for (String keyword : handleKeywords) {
                if (!TelegramHandle.isValidTelegramHandle(keyword)) {
                    throw new ParseException(TelegramHandle.MESSAGE_CONSTRAINTS);
                }
            }
            return new FindCommand(new TelegramHandleContainsKeywordsPredicate(Arrays.asList(handleKeywords)));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");
        for (String keyword : nameKeywords) {
            if (!Name.isValidName(keyword)) {
                throw new ParseException(Name.MESSAGE_CONSTRAINTS);
            }
        }
        return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
