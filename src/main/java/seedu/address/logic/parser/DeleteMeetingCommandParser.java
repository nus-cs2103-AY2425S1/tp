package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING_TITLE;

import java.util.logging.Logger;
import java.util.stream.Stream;

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
        if (hasExcessToken(args, PREFIX_MEETING_TITLE, PREFIX_MEETING_DATE)) {
            logger.warning("Excess prefixes.");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteMeetingCommand.MESSAGE_USAGE));
        }
        if (!arePrefixesPresent(argMultimap, PREFIX_MEETING_TITLE, PREFIX_MEETING_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteMeetingCommand.MESSAGE_USAGE));
        }


        MeetingTitle meetingTitle = ParserUtil.parseMeetingTitle(argMultimap.getValue(PREFIX_MEETING_TITLE).get());
        MeetingDate meetingDate = ParserUtil.parseMeetingDate(argMultimap.getValue(PREFIX_MEETING_DATE).get());

        return new DeleteMeetingCommand(meetingTitle, meetingDate);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
    /**
     * Returns true if number of tokens in args string exceeds specified prefixes.
     */
    private boolean hasExcessToken(String args, Prefix... prefixes) {
        String[] splits = args.trim().split("\\s(?=\\S+/)");
        if (splits[0].equals("/")) {
            return false;
        }
        return splits.length > prefixes.length;
    }
}
