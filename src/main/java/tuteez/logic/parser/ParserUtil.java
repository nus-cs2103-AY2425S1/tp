package tuteez.logic.parser;

import static java.util.Objects.requireNonNull;
import static tuteez.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tuteez.commons.core.index.Index;
import tuteez.commons.util.StringUtil;
import tuteez.logic.parser.exceptions.ParseException;
import tuteez.model.person.Address;
import tuteez.model.person.Email;
import tuteez.model.person.Name;
import tuteez.model.person.Phone;
import tuteez.model.person.TelegramUsername;
import tuteez.model.person.lesson.Day;
import tuteez.model.person.lesson.Lesson;
import tuteez.model.remark.Remark;
import tuteez.model.tag.Tag;

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
        if (address == null) {
            return new Address(address);
        }
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
        if (email == null) {
            return new Email(email);
        }
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
     * Parses the input {@code String username} into a {@code TelegramUsername}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param username the input telegram username, which can be null to represent an optional username.
     * @throws ParseException if the given {@code telegramUsername} is invalid.
     */
    public static TelegramUsername parseTelegramUsername(String username) throws ParseException {
        if (username == null) {
            return TelegramUsername.empty();
        }
        String trimmedUsername = username.trim();

        if (!TelegramUsername.isValidTelegramHandle(trimmedUsername)) {
            throw new ParseException(TelegramUsername.MESSAGE_CONSTRAINTS);
        }
        return TelegramUsername.of(trimmedUsername);
    }


    /**
     * Parses a {@code String lesson} into a {@code Lesson}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Lesson parseLesson(String lesson) throws ParseException {
        requireNonNull(lesson);
        String trimmedLesson = lesson.trim();
        String[] lessonDayTimeArr = trimmedLesson.split("\\s+", 2);

        if (lessonDayTimeArr.length != 2) {
            throw new ParseException(Lesson.MESSAGE_CONSTRAINTS);
        }

        String dayString = lessonDayTimeArr[0];
        String timeRange = lessonDayTimeArr[1];

        checkValidLessonDayInfo(dayString);
        checkValidLessonTimeInfo(timeRange);

        return new Lesson(trimmedLesson);
    }

    /**
     * Checks if the provided lesson day information is valid.
     *
     * <p>This method verifies that the specified day string corresponds to a valid day
     * in the {@code Day} enum. The day string is case-insensitive.</p>
     *
     * @param lessonDayString The string representation of the lesson day to be validated.
     * @throws ParseException If the provided day string is not a valid day.
     */
    public static void checkValidLessonDayInfo(String lessonDayString) throws ParseException {
        if (!Day.isValidDay(lessonDayString.toLowerCase())) {
            throw new ParseException(Lesson.MESSAGE_INVALID_LESSON_DAY);
        }
    }

    /**
     * Checks if the provided lesson time information is valid.
     *
     * <p>This method validates the time range format and ensures the start and end times
     * meet the specified constraints. Specifically, it checks that the lesson start time
     * is before the end time, that the lesson does not overflow into the next day, and
     * that both times are within valid bounds (e.g., 0000 to 2359).</p>
     *
     * @param lessonTimeString The string representation of the lesson time range in the format "HHmm-HHmm".
     * @throws ParseException If the time range format is invalid or if any time constraints are violated.
     */
    public static void checkValidLessonTimeInfo(String lessonTimeString) throws ParseException {
        if (!Lesson.isValidTimeRange(lessonTimeString)) {
            throw new ParseException(Lesson.MESSAGE_INVALID_LESSON_TIME);
        }

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
        String[] timeArr = lessonTimeString.split("-");
        LocalTime startTime = LocalTime.parse(timeArr[0], timeFormatter);
        LocalTime endTime = LocalTime.parse(timeArr[1], timeFormatter);

        if (!Lesson.isLessonStartAtValidTime(startTime)) {
            throw new ParseException(Lesson.MESSAGE_INVALID_LESSON_START_TIME);
        }

        if (!Lesson.isLessonEndAtValidTime(endTime)) {
            throw new ParseException(Lesson.MESSAGE_INVALID_LESSON_END_TIME);
        }

        if (!Lesson.isValidTimeOrder(startTime, endTime)) {
            throw new ParseException(Lesson.MESSAGE_INVALID_TIME_ORDER);
        }
    }

    /**
     * Parses {@code Collection<String> lessons} into a {@code List<Lesson>}.
     */
    public static List<Lesson> parseLessons(Collection<String> lessons) throws ParseException {
        requireNonNull(lessons);
        final List<Lesson> lessonList = new ArrayList<>();
        for (String lesson : lessons) {
            lessonList.add(parseLesson(lesson));
        }
        return lessonList;
    }

    /**
     * Parses a {@code String remark} into an {@code Remark}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code remark} is invalid.
     */
    public static Remark parseRemark(String remark) throws ParseException {
        requireNonNull(remark);
        String trimmedRemark = remark.trim();
        if (!Remark.isValidRemark(trimmedRemark)) {
            throw new ParseException(Remark.MESSAGE_CONSTRAINTS);
        }
        return new Remark(trimmedRemark);
    }

    /**
     * Validates that the provided {@code String args} is not empty or only whitespace.
     * If invalid, throws a {@code ParseException} with the provided {@code errorMessage}.
     *
     * @param args The args to validate.
     * @param errorMessage The error message to use in the exception if validation fails.
     * @throws ParseException if {@code args} is empty or contains only whitespace.
     */
    public static void validateNonEmptyArgs(String args, String errorMessage) throws ParseException {
        if (args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, errorMessage));
        }
    }

    /**
     * Validates that the specified {@code Prefix} exists in the given {@code ArgumentMultimap}.
     * If the prefix is missing, throws a {@code ParseException} with the provided {@code errorMessage}.
     *
     * @param argMultimap The map of arguments to check.
     * @param prefix The prefix to validate.
     * @param errorMessage The error message to use in the exception if validation fails.
     * @throws ParseException if the specified {@code prefix} is not present in {@code argMultimap}.
     */
    public static void validatePrefixExists(ArgumentMultimap argMultimap, Prefix prefix, String errorMessage)
            throws ParseException {
        if (!argMultimap.getValue(prefix).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, errorMessage));
        }
    }

}
