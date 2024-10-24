package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.eventcommands.FindEventCommand;
import seedu.address.logic.commands.personcommands.FindPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.types.common.EventNameContainsKeywordsPredicate;
import seedu.address.model.types.common.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<Command> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(ModelType modelType, String args) throws ParseException {
        if (modelType == ModelType.PERSON) {
            return parseForPerson(args);
        } else if (modelType == ModelType.EVENT) {
            return parseForEvent(args);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

    private FindPersonCommand parseForPerson(String args) throws ParseException {
        String trimmedArgs = args.trim();
        System.out.println(trimmedArgs);
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindPersonCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

    private FindEventCommand parseForEvent(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEventCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindEventCommand(new EventNameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
