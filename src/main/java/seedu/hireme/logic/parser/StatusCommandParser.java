package seedu.hireme.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.hireme.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.hireme.commons.core.index.Index;
import seedu.hireme.logic.commands.StatusCommand;
import seedu.hireme.logic.parser.exceptions.ParseException;
import seedu.hireme.model.internshipapplication.Status;

/**
 * Parses input arguments and creates a new {@code StatusCommand} object.
 */
public class StatusCommandParser implements Parser<StatusCommand> {

    private final Status status;

    /**
     * Constructs a {@code StatusCommandParser} with the specified status.
     *
     * @param status The status to set (e.g., PENDING, ACCEPTED, REJECTED).
     */
    public StatusCommandParser(Status status) {
        this.status = status;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the {@code StatusCommand}
     * and returns a {@code StatusCommand} object for execution.
     *
     * @param args The input arguments for parsing.
     * @return A {@code StatusCommand} object.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public StatusCommand parse(String args) throws ParseException {
        requireNonNull(args);
        try {
            // Parse index from the arguments
            Index index = ParserUtil.parseIndex(args.trim());

            // Ensure the status is one of the allowed values (PENDING, ACCEPTED, REJECTED)
            if (status == null || !(status == Status.PENDING || status == Status.ACCEPTED
                    || status == Status.REJECTED)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatusCommand.MESSAGE_USAGE));
            }

            return new StatusCommand(index, status);

        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatusCommand.MESSAGE_USAGE), pe);
        }
    }
}
