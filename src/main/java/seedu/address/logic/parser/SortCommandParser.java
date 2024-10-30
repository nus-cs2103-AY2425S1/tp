package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
/**
 * Parses input for sort and creates an instance of SortCommand
 */
public class SortCommandParser implements Parser<SortCommand> {
    //regex to filter the input to extract TAG name, comparison operator and TAG value
    private static final Pattern SORT_COMMAND_FORMAT =
            Pattern.compile("(?i)(t/(?<tag>\\S+)){1}\\s+(?<sortOrder>\\S+){1}");

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        final Matcher matcher = SORT_COMMAND_FORMAT.matcher(args.trim());

        if (!matcher.matches()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        // Extract the name and tag from the input
        String tag = matcher.group("tag");
        String sortOrder = matcher.group("sortOrder");

        if ((tag == null || tag.isEmpty())
                || (sortOrder == null || sortOrder.isEmpty() || (!sortOrder.equalsIgnoreCase("ASC")
                        && !sortOrder.equalsIgnoreCase("DESC")))) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        return new SortCommand(tag, sortOrder);
    }
}
