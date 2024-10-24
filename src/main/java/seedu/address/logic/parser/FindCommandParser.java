package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashMap;
import java.util.Map;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object.
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_BIRTHDAY, PREFIX_TAG);

        Map<String, String> searchCriteria = new HashMap<>();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            searchCriteria.put("name", argMultimap.getValue(PREFIX_NAME).get());
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            searchCriteria.put("phone", argMultimap.getValue(PREFIX_PHONE).get());
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            searchCriteria.put("email", argMultimap.getValue(PREFIX_EMAIL).get());
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            searchCriteria.put("address", argMultimap.getValue(PREFIX_ADDRESS).get());
        }
        if (argMultimap.getValue(PREFIX_BIRTHDAY).isPresent()) {
            searchCriteria.put("birthday", argMultimap.getValue(PREFIX_BIRTHDAY).get());
        }
        if (argMultimap.getAllValues(PREFIX_TAG).size() > 0) {
            searchCriteria.put("tag", String.join(" ", argMultimap.getAllValues(PREFIX_TAG)));
        }

        if (searchCriteria.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(new PersonContainsKeywordsPredicate(searchCriteria));
    }
}

