package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Date;
import seedu.address.model.event.Description;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Location;
import seedu.address.model.event.Time;


/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class EventParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index has to be a positive integer.";

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
    public static EventName parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!EventName.isValidName(trimmedName)) {
            throw new ParseException(EventName.MESSAGE_CONSTRAINTS);
        }
        return new EventName(trimmedName);
    }

    /**
     * Parses a given string as an {@code EventName} and returns it after validation.
     *
     * @param name The event name string to be parsed.
     * @return A valid {@code EventName} object.
     * @throws ParseException If the given name does not conform to the event name constraints.
     */
    public static EventName parseEventName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedEventName = name.trim();
        if (!EventName.isValidName(trimmedEventName)) {
            throw new ParseException(EventName.MESSAGE_CONSTRAINTS);
        }
        return new EventName(trimmedEventName);
    }

    /**
     * Parses a given string as a {@code Location} and returns it after validation.
     *
     * @param location The location string to be parsed.
     * @return A valid {@code Location} object.
     * @throws ParseException If the given location does not conform to the location constraints.
     */
    public static Location parseLocation(String location) throws ParseException {
        requireNonNull(location);
        String trimmedLocation = location.trim();
        if (!Location.isValidLocation(trimmedLocation)) {
            throw new ParseException(Location.MESSAGE_CONSTRAINTS);
        }
        return new Location(trimmedLocation);
    }

    /**
     * Parses a given string as a {@code Date} and returns it after validation.
     *
     * @param date The date string to be parsed.
     * @return A valid {@code Date} object.
     * @throws ParseException If the given date does not conform to the date constraints.
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!Date.isValidDate(trimmedDate)) {
            throw new ParseException(Date.MESSAGE_CONSTRAINTS);
        }
        return new Date(trimmedDate);
    }

    /**
     * Parses a given string as a {@code Time} and returns it after validation.
     *
     * @param time The time string to be parsed.
     * @return A valid {@code Time} object.
     * @throws ParseException If the given time does not conform to the time constraints.
     */
    public static Time parseTime(String time) throws ParseException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        if (!Time.isValidTime(trimmedTime)) {
            throw new ParseException(Time.MESSAGE_CONSTRAINTS);
        }
        return new Time(trimmedTime);
    }

    /**
     * Parses a {@code String description} into a {@code Description} object.
     *
     * @param description The string representing the description.
     * @return A {@code Description} object if the given string is valid.
     * @throws ParseException if the given string is not a valid description.
     */
    public static Description parseDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!Description.isValidDescription(trimmedDescription)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        if (trimmedDescription.isEmpty()) {
            throw new ParseException(Description.MESSAGE_DESCRIPTION_REMINDER);
        }
        return new Description(trimmedDescription);
    }

    /**
     * Ensures the search term matches EventName's validation and returns it after
     * @param searchTerm
     * @return searchTerm
     * @throws ParseException
     */
    public static String parseSearchTerm(String searchTerm) throws ParseException {
        requireNonNull(searchTerm);
        searchTerm = searchTerm.trim();

        if (!searchTerm.matches(EventName.VALIDATION_REGEX)) {
            throw new ParseException(EventName.MESSAGE_CONSTRAINTS);
        }

        return searchTerm;

    }

}
