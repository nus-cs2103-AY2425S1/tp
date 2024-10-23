package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_REVERSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SORT, PREFIX_REVERSE);

        String sortField = null;
        boolean isReverse = false;

        if (argMultimap.getValue(PREFIX_SORT).isPresent()) {
            sortField = argMultimap.getValue(PREFIX_SORT).get();
            if (!sortField.equals("name") && !sortField.equals("email")) {
                throw new ParseException(ListCommand.MESSAGE_INVALID_SORT_FIELD);
            }
        }

        if (argMultimap.getValue(PREFIX_REVERSE).isPresent()) {
            isReverse = true;
        }

        return new ListCommand(sortField, isReverse);
    }
}
