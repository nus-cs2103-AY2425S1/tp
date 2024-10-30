package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AdvFilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
/**
 * Parses input for sort and creates an instance of SortCommand
 */
public class AdvFilterCommandParser implements Parser<AdvFilterCommand> {
    //regex to filter the input to extract TAG name, comparison operator and TAG value
    private static final Pattern ADVFILTER_COMMAND_FORMAT =
            Pattern.compile("(?i)(t\\\\(?<tag>\\S+))\\s+(?<operator>\\S+)\\s+(?<value>\\S+)");

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AdvFilterCommand parse(String args) throws ParseException {
        final Matcher matcher = ADVFILTER_COMMAND_FORMAT.matcher(args.trim());

        if (!matcher.matches()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AdvFilterCommand.MESSAGE_USAGE));
        }

        // Extract the name and tag from the input
        String tag = matcher.group("tag");
        String operator = matcher.group("operator");
        String value = matcher.group("value");

        if ((tag == null || tag.isEmpty())
                && (operator == null || operator.isEmpty()) && (value == null || value.isEmpty())) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AdvFilterCommand.MESSAGE_USAGE));
        }

        return new AdvFilterCommand(tag, operator, value);
    }
}
