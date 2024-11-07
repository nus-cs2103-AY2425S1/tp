package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_REVERSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT;

import java.util.List;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    public static final String MESSAGE_MULTIPLE_SORT_FIELDS = "Multiple sort fields are not allowed. "
            + "Please specify only one sort field.";
    public static final String MESSAGE_MULTIPLE_REVERSE = "Multiple reverse flags are not allowed.";
    public static final String MESSAGE_INVALID_FORMAT = "Invalid command format! \n%s";

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SORT, PREFIX_REVERSE);

        // Check for any non-prefixed arguments
        String trimmedArgs = args.trim();
        if (!trimmedArgs.isEmpty() && !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        // Check for multiple sort fields
        List<String> sortFields = argMultimap.getAllValues(PREFIX_SORT);
        if (sortFields.size() > 1) {
            throw new ParseException(MESSAGE_MULTIPLE_SORT_FIELDS);
        }

        // Check for multiple reverse flags
        List<String> reverseFlags = argMultimap.getAllValues(PREFIX_REVERSE);
        if (reverseFlags.size() > 1) {
            throw new ParseException(MESSAGE_MULTIPLE_REVERSE);
        }

        String sortField = null;
        boolean isReverse = false;

        // Process sort field if present
        if (!sortFields.isEmpty()) {
            sortField = sortFields.get(0);
            if (!sortField.equals("name") && !sortField.equals("email")
                && !sortField.equals("income") && !sortField.equals("age")) {
                throw new ParseException(ListCommand.MESSAGE_INVALID_SORT_FIELD);
            }
        }

        // Process reverse flag if present
        if (!reverseFlags.isEmpty()) {
            isReverse = true;
        }

        return new ListCommand(sortField, isReverse);
    }
}
