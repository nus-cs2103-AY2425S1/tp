package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindEmployeeCommand;
import seedu.address.logic.commands.FindPotentialCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

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

        String[] nameKeywords = trimmedArgs.split("\\s+");
        String typeOfPerson = nameKeywords[0];
        List<String> keywords = Arrays.asList(Arrays.copyOfRange(nameKeywords, 1, nameKeywords.length));
        if (keywords.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        } else if (typeOfPerson.equals(FindEmployeeCommand.ARGUMENT_WORD)) {
            return new FindEmployeeCommand(new NameContainsKeywordsPredicate(keywords));
        } else if (typeOfPerson.equals(FindPotentialCommand.ARGUMENT_WORD)) {
            return new FindPotentialCommand(new NameContainsKeywordsPredicate(keywords));
        } else if (typeOfPerson.equals(FindCommand.ARGUMENT_WORD)) {
            return new FindCommand(new NameContainsKeywordsPredicate(keywords));
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

}
