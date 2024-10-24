package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventDescription;
import seedu.address.model.event.EventDuration;
import seedu.address.model.event.EventName;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
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
     * Parses a {@code String eventName} into an {@code EventName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code eventName} is invalid.
     */
    public static EventName parseEventName(String eventName) throws ParseException {
        requireNonNull(eventName);
        String trimmedEventName = eventName.trim();
        if (!EventName.isValidName(trimmedEventName)) {
            throw new ParseException(EventName.MESSAGE_CONSTRAINTS);
        }
        return new EventName(trimmedEventName);
    }

    /**
     * Parses a {@code String eventDescription} into an {@code EventDescription}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code eventDescription} is invalid.
     */
    public static EventDescription parseEventDescription(String eventDescription) throws ParseException {
        requireNonNull(eventDescription);
        String trimmedEventDescription = eventDescription.trim();
        if (!EventDescription.isValidDescription(trimmedEventDescription)) {
            throw new ParseException(EventDescription.MESSAGE_CONSTRAINTS);
        }
        return new EventDescription(trimmedEventDescription);
    }

    /**
     * Parses a {@code String eventStartDate and String eventEndDate} into an {@code EventDuration}.
     *
     * @throws ParseException if the given {@code eventStartDate} or {@code eventEndDate} is invalid.
     */
    public static EventDuration parseEventDuration(String eventStartDate, String eventEndDate) throws ParseException {
        requireAllNonNull(eventStartDate, eventEndDate);
        String trimmedEventStartDate = eventStartDate.trim();
        String trimmedEventEndDate = eventEndDate.trim();
        LocalDate start;
        LocalDate end;
        try {
            start = LocalDate.parse(trimmedEventStartDate);
            end = LocalDate.parse(trimmedEventEndDate);
        } catch (DateTimeParseException exception) {
            throw new ParseException(EventDuration.MESSAGE_CONSTRAINTS);
        }
        if (!EventDuration.isValidDuration(start, end)) {
            throw new ParseException(EventDuration.MESSAGE_CONSTRAINTS);
        }
        return new EventDuration(start, end);
    }

    /**
     * Parses the given {@code String} representing a file name.
     *
     * This method trims any leading or trailing spaces from the file name and checks if the file name is valid.
     * A file name is considered invalid if it is empty.
     * If the file name is invalid, a {@code ParseException} is thrown.
     *
     * @param fileName The file name to be parsed and validated.
     * @return The trimmed file name if it is valid.
     * @throws ParseException if the file name is empty or invalid.
     */
    public static String parseFileName(String fileName) throws ParseException {
        requireNonNull(fileName);
        String trimmedFileName = fileName.trim();
        if (fileName.isEmpty()) {
            throw new ParseException(null);
        }
        return trimmedFileName;
    }

    /**
     * Parses a String containing the field to search and keywords,
     * into a String representing the field to search.
     */
    public static String parseField(String fieldAndKeywords) {
        requireNonNull(fieldAndKeywords);
        String field = fieldAndKeywords.split(" ")[0].trim();
        return field;
    }

    /**
     * Parses a List containing keyword arguments,
     * into a List containing the keywords.
     */
    public static List<String> parseSearchKeywords(List<String> keywordArgument) {
        requireNonNull(keywordArgument);
        List<String> keywords = Arrays.asList(keywordArgument.get(0).split(" "));
        return keywords;
    }
}
