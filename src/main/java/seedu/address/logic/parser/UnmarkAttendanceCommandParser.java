package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnmarkAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ModelManager;

/**
 * Parses input arguments and creates a new UnmarkAttendanceCommand object
 */
public class UnmarkAttendanceCommandParser implements Parser<UnmarkAttendanceCommand> {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    /**
     * Parses the given {@code String} of arguments in the context of the UnmarkAttendanceCommand
     * and returns a UnmarkAttendanceCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnmarkAttendanceCommand parse(String args) throws ParseException {
        requireNonNull(args);
        if (args.trim().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkAttendanceCommand.MESSAGE_USAGE));
        }
        logger.info("Starting to parse unmark attendance command.");

        try {
            String[] parts = args.trim().split("\\s+");
            assert parts.length > 0 : "Parts array should not be empty";

            Index[] indexes = new Index[parts.length];

            for (int i = 0; i < parts.length; i++) {
                indexes[i] = ParserUtil.parseIndex(parts[i]);
            }

            logger.info("Unmark attendance command parsed successfully.");
            return new UnmarkAttendanceCommand(indexes);
        } catch (ParseException parseException) {
            logger.warning("Failed to parse unmark attendance command.");
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkAttendanceCommand.MESSAGE_USAGE),
                    parseException);
        }
    }
}
