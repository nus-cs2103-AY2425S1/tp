package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING_TITLE;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.DeleteMeetingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.meeting.MeetingDate;
import seedu.address.model.meeting.MeetingTitle;

/**
 * Parses input arguments and creates a new DeleteMeetingCommand object
 */
public class DeleteMeetingCommandParser implements Parser<DeleteMeetingCommand> {
    private static final Logger logger = LogsCenter.getLogger(DeleteMeetingCommandParser.class);
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteMeetingCommand
     * and returns a DeleteMeetingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteMeetingCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MEETING_TITLE, PREFIX_MEETING_DATE);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_MEETING_TITLE, PREFIX_MEETING_DATE);
        if (ParserUtil.hasExcessToken(args, PREFIX_MEETING_TITLE, PREFIX_MEETING_DATE)) {
            logger.warning("Excess prefixes.");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteMeetingCommand.MESSAGE_USAGE));
        }
        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_MEETING_TITLE, PREFIX_MEETING_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteMeetingCommand.MESSAGE_USAGE));
        }


        MeetingTitle meetingTitle = ParserUtil.parseMeetingTitle(argMultimap.getValue(PREFIX_MEETING_TITLE).get());
        MeetingDate meetingDate = ParserUtil.parseMeetingDate(argMultimap.getValue(PREFIX_MEETING_DATE).get());

        return new DeleteMeetingCommand(meetingTitle, meetingDate);
    }
}
