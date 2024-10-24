package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.PaymentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Schedule;
import seedu.address.model.tag.Tag;

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
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String date} and {@code String note} into a {@code Schedule}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Schedule} is invalid.
     */
    public static Schedule parseSchedule(String dateTime, String note) throws ParseException {
        requireNonNull(dateTime);
        String trimmedDateTime = dateTime.trim();
        String trimmedNote = note.trim();

        Schedule.isValidDateTime(trimmedDateTime);
        return new Schedule(trimmedDateTime, trimmedNote);
    }

    /**
     * Parses a {@code String date} into a {@code Schedule} with an empty note.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Schedule} is invalid.
     */
    public static Schedule parseSchedule(String dateTime) throws ParseException {
        requireNonNull(dateTime);
        String trimmedDateTime = dateTime.trim();

        Schedule.isValidDateTime(trimmedDateTime);
        return new Schedule(trimmedDateTime, "");
    }

    /**
     * Parses {@code Collection<String> schedules} into a {@code Set<Schedule>}.
     */
    public static Set<Schedule> parseSchedules(Collection<String> dateTimes, Collection<String> notes)
            throws ParseException {
        requireNonNull(dateTimes);
        final Set<Schedule> scheduleSet = new HashSet<>();
        Iterator<String> dateTimeIterator = dateTimes.iterator();
        Iterator<String> noteIterator = notes.iterator();
        while (dateTimeIterator.hasNext()) {
            if (noteIterator.hasNext()) {
                scheduleSet.add(parseSchedule(dateTimeIterator.next(), noteIterator.next()));
            } else {
                scheduleSet.add(parseSchedule(dateTimeIterator.next(), ""));
            }
        }
        return scheduleSet;
    }


    /**
     * Parses a {@code String paidStatus} into a {@code boolean}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code paidStatus} is invalid.
     */
    public static boolean parsePaidStatus(String paidStatus) throws ParseException {
        requireNonNull(paidStatus);
        String trimmedStatus = paidStatus.trim().toLowerCase();
        if (trimmedStatus.equals("true") || trimmedStatus.equals("paid")) {
            return true;
        } else if (trimmedStatus.equals("false") || trimmedStatus.equals("unpaid")) {
            return false;
        } else {
            throw new ParseException(PaymentCommand.MESSAGE_PAYMENT_STATUS_INVALID);
        }
    }

}
