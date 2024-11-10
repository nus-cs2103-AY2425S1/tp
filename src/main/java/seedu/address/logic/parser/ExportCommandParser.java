// Originally implemented by teammate @frymash, code was reused to take in different formats
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.ExportCommand.Format;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input commands and creates a new ExportCommand object.
 */
public class ExportCommandParser implements Parser<ExportCommand> {
    private static final Pattern EXPORT_COMMAND_FORMAT = Pattern.compile("format\\\\(?<format>\\S+)");
    private static final ArrayList<String> SUPPORTED_FORMATS = new ArrayList<>(List.of("csv", "txt"));

    /**
     * Parses the given {@code String} of arguments in the context of the ExportCommand
     * and returns an ExportCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public ExportCommand parse(String args) throws ParseException {
        requireNonNull(args);
        final Matcher matcher = EXPORT_COMMAND_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }

        // Extract the format (e.g., "csv") from the input
        String format = matcher.group("format");

        if (!SUPPORTED_FORMATS.contains(format)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }
        Format formatType = ExportCommand.matchFormat(format);

        if (formatType == null) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }
        return new ExportCommand(formatType);
    }
}
