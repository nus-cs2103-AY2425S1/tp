package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.Email;
import seedu.address.model.client.Name;
import seedu.address.model.client.Phone;
import seedu.address.model.meeting.MeetingDate;
import seedu.address.model.meeting.MeetingTitle;
import seedu.address.model.property.Ask;
import seedu.address.model.property.Bid;
import seedu.address.model.property.PostalCode;
import seedu.address.model.property.Type;
import seedu.address.model.property.Unit;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String postalCode} into a {@code postalCode}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code postalCode} is invalid.
     */
    public static PostalCode parsePostalCode(String postalCode) throws ParseException {
        requireNonNull(postalCode);
        String trimmedPostalCode = postalCode.trim();
        if (!PostalCode.isValidPostalCode(trimmedPostalCode)) {
            throw new ParseException(PostalCode.MESSAGE_CONSTRAINTS);
        }
        return new PostalCode(trimmedPostalCode);
    }

    /**
     * Parses a {@code String unitNumber} into a {@code unitNumber}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code unitNumber} is invalid.
     */
    public static Unit parseUnit(String unitNumber) throws ParseException {
        requireNonNull(unitNumber);
        String trimmedUnitNumber = unitNumber.trim();
        if (!Unit.isValidUnit(trimmedUnitNumber)) {
            throw new ParseException(Unit.MESSAGE_CONSTRAINTS);
        }
        return new Unit(trimmedUnitNumber);
    }

    /**
     * Parses a {@code String type} into a {@code Type}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Type} is invalid.
     */
    public static Type parseType(String type) throws ParseException {
        requireNonNull(type);
        String trimmedType = type.trim();
        if (!Type.isValidType(trimmedType)) {
            throw new ParseException(Type.MESSAGE_CONSTRAINTS);
        }
        return new Type(trimmedType);
    }

    /**
     * Parses a {@code String ask} into a {@code Ask}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Ask} is invalid.
     */
    public static Ask parseAsk(String ask) throws ParseException {
        requireNonNull(ask);
        String trimmedAsk = ask.trim();
        if (!Ask.isValidAsk(trimmedAsk)) {
            throw new ParseException(Ask.MESSAGE_CONSTRAINTS);
        }
        return new Ask(trimmedAsk);
    }

    /**
     * Parses a {@code String bid} into a {@code Bid}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Bid} is invalid.
     */
    public static Bid parseBid(String bid) throws ParseException {
        requireNonNull(bid);
        String trimmedBid = bid.trim();
        if (!Bid.isValidBid(trimmedBid)) {
            throw new ParseException(Bid.MESSAGE_CONSTRAINTS);
        }
        return new Bid(trimmedBid);
    }


    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static seedu.address.model.client.Name parseClientName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new seedu.address.model.client.Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parseClientPhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseClientEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String meetingTitle} into an {@code MeetingTitle}.
     * Titles should only contain alphanumeric characters and spaces, and it should not be blank.
     *
     * @throws ParseException if the given {@code meetingTitle} is invalid.
     */
    public static MeetingTitle parseMeetingTitle(String meetingTitle) throws ParseException {
        requireNonNull(meetingTitle);
        String trimmedMeetingTitle = meetingTitle.trim();
        if (!MeetingTitle.isValidMeetingTitle(trimmedMeetingTitle)) {
            throw new ParseException(MeetingTitle.MESSAGE_CONSTRAINTS);
        }
        return new MeetingTitle(trimmedMeetingTitle);
    }

    /**
     * Parses a {@code String meetingDate} into an {@code MeetingDate}.
     * Meeting dates should be in the format dd-MM-yyyy and must be a valid date.
     *
     * @throws ParseException if the given {@code meetingDate} is invalid.
     */
    public static MeetingDate parseMeetingDate(String meetingDate) throws ParseException {
        requireNonNull(meetingDate);
        String trimmedMeetingDate = meetingDate.trim();
        if (!MeetingDate.isValidMeetingDate(trimmedMeetingDate)) {
            throw new ParseException(MeetingDate.MESSAGE_CONSTRAINTS);
        }
        return new MeetingDate(trimmedMeetingDate);
    }
}
