package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUYER_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTALCODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLER_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.logging.Logger;

import seedu.address.logic.commands.AddMeetingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.Phone;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.MeetingDate;
import seedu.address.model.meeting.MeetingTitle;
import seedu.address.model.property.PostalCode;
import seedu.address.model.property.Type;

/**
 * Parses input arguments and creates a new AddMeetingCommand object
 */
public class AddMeetingCommandParser implements Parser<AddMeetingCommand> {
    private static final Logger logger = Logger.getLogger(AddMeetingCommandParser.class.getName());
    /**
     * Parses the given {@code String} of arguments in the context of the AddMeetingCommand
     * and returns an AddMeetingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddMeetingCommand parse(String args) throws ParseException {
        requireNonNull(args, "Arguments cannot be null.");

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MEETING_TITLE, PREFIX_MEETING_DATE, PREFIX_BUYER_PHONE,
                        PREFIX_SELLER_PHONE, PREFIX_TYPE, PREFIX_POSTALCODE);

        validateArgumentFormat(argMultimap, args);

        MeetingTitle meetingTitle = ParserUtil.parseMeetingTitle(argMultimap.getValue(PREFIX_MEETING_TITLE).get());
        MeetingDate meetingDate = ParserUtil.parseMeetingDate(argMultimap.getValue(PREFIX_MEETING_DATE).get());
        Phone buyerPhone = ParserUtil.parseClientPhone(argMultimap.getValue(PREFIX_BUYER_PHONE).get());
        Phone sellerPhone = ParserUtil.parseClientPhone(argMultimap.getValue(PREFIX_SELLER_PHONE).get());
        Type propertyType = ParserUtil.parseType(argMultimap.getValue(PREFIX_TYPE).get());
        PostalCode postalCode = ParserUtil.parsePostalCode(argMultimap.getValue(PREFIX_POSTALCODE).get());

        Meeting meeting = new Meeting(meetingTitle, meetingDate, buyerPhone, sellerPhone, propertyType,
                postalCode);
        logger.info("Parsed AddMeetingCommand with meeting: " + meeting);
        return new AddMeetingCommand(meeting);
    }

    /**
     * Validates the format of the arguments.
     *
     * @param argMultimap The tokenized arguments map.
     * @param args The original input arguments string.
     * @throws ParseException if format is invalid, including duplicate prefixes or excess tokens.
     */
    private void validateArgumentFormat(ArgumentMultimap argMultimap, String args) throws ParseException {
        requireNonNull(argMultimap, "ArgumentMultimap cannot be null.");

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_MEETING_TITLE, PREFIX_MEETING_DATE, PREFIX_BUYER_PHONE,
                PREFIX_SELLER_PHONE, PREFIX_TYPE, PREFIX_POSTALCODE);

        if (ParserUtil.hasExcessToken(args, PREFIX_MEETING_TITLE, PREFIX_MEETING_DATE, PREFIX_BUYER_PHONE,
                PREFIX_SELLER_PHONE, PREFIX_TYPE, PREFIX_POSTALCODE)) {
            logger.warning("Excess prefixes detected in input: " + args);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMeetingCommand.MESSAGE_USAGE));
        }

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_MEETING_TITLE, PREFIX_MEETING_DATE, PREFIX_BUYER_PHONE,
                PREFIX_SELLER_PHONE, PREFIX_TYPE, PREFIX_POSTALCODE)
                || !argMultimap.getPreamble().isEmpty()) {
            logger.warning("Missing required prefixes or unexpected preamble in input: " + args);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMeetingCommand.MESSAGE_USAGE));
        }
    }
}
