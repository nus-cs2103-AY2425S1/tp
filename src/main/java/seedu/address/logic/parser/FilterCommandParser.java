package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses input for filter and creates an instance of FilterCommand
 */
public class FilterCommandParser implements Parser<FilterCommand> {
    //regex to filter the input to extract NAME and/or TAG
    private static final Pattern FILTER_COMMAND_FORMAT = Pattern.compile("(?i)(n/(?<name>\\S+))?\\s*(t/(?<tag>\\S+))?");
    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        final Matcher matcher = FILTER_COMMAND_FORMAT.matcher(args.trim());

        if (!matcher.matches()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        // Extract the name and tag from the input
        String name = matcher.group("name");
        String tag = matcher.group("tag");

        if ((name == null || name.isEmpty()) && (tag == null || tag.isEmpty())) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        return new FilterCommand(name, tag);
    }
}
