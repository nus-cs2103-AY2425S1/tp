package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_INPUT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import seedu.address.logic.commands.TrackCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.CategoryContainsKeywordPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class TrackCommandParser implements Parser<TrackCommand> {
    private static final Set<String> PREDEFINED_CATEGORIES = new HashSet<>(Arrays.asList("student", "company"));

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public TrackCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        // Checks for empty input
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TrackCommand.MESSAGE_USAGE));
        }

        // Splits by whitespace to handle multiple words input
        String[] words = trimmedArgs.split("\\s+");
        String category = words[0].toLowerCase(Locale.ROOT);

        // Checks if the provided category to track is in multiple words or not from the predefined list
        if (words.length != 1 || !PREDEFINED_CATEGORIES.contains(category)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_INPUT, TrackCommand.MESSAGE_INVALID_INPUT_ERROR));
        }

        return new TrackCommand(new CategoryContainsKeywordPredicate(category));
    }

}
