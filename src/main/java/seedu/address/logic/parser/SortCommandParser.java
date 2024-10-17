package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
/**
 * Parses input for filter and creates an instance of FilterCommand
 */
public class SortCommandParser implements Parser<SortCommand> {
    //regex to filter the input to extract NAME and/or TAG
    private static final Pattern SORT_COMMAND_FORMAT = Pattern.compile("(?i)(t/(?<tag>\\S+)){1}\\s+(?<operator>\\S+){1}\\s+(?<value>\\S+){1}");
    // private static final Pattern SORT_COMMAND_FORMAT = Pattern.compile("(?i)(n/(?<name>\\S+))?\\s*(t/(?<tag>\\S+))?");

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        final Matcher matcher = SORT_COMMAND_FORMAT.matcher(args.trim());

        if (!matcher.matches()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        // Extract the name and tag from the input
        // String name = matcher.group("name");
        String tag = matcher.group("tag");
        String operator = matcher.group("operator");
        String value = matcher.group("value");

        if ((tag == null || tag.isEmpty()) && (operator == null || operator.isEmpty()) && (value == null || value.isEmpty())) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        return new SortCommand(tag, operator, value);
    }
}
