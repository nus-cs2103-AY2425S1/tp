package tahub.contacts.logic.parser.person;

import static tahub.contacts.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import tahub.contacts.logic.commands.person.PersonFindCommand;
import tahub.contacts.logic.parser.Parser;
import tahub.contacts.logic.parser.exceptions.ParseException;
import tahub.contacts.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new PersonFindCommand object
 */
public class PersonFindCommandParser implements Parser<PersonFindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PersonFindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PersonFindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new PersonFindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
