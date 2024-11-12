package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.AggGradeCommand.MESSAGE_OPERATION_CONSTRAINTS;
import static seedu.address.logic.commands.AggGradeCommand.OPERATION_TRANSLATE;
import static seedu.address.model.person.Grade.MESSAGE_SCORE_CONSTRAINTS;
import static seedu.address.model.person.Grade.MESSAGE_WEIGHTAGE_CONSTRAINTS;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.AggGradeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Attendance;
import seedu.address.model.person.AttendanceList;
import seedu.address.model.person.Course;
import seedu.address.model.person.Email;
import seedu.address.model.person.Grade;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser
 * classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_FLOAT = "Value is not a valid float.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading
     * and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero
     *                        unsigned integer).
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
     * Parses a {@code String course} into an {@code Course}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code course} is invalid.
     */
    public static Course parseCourse(String course) throws ParseException {
        requireNonNull(course);
        String trimmedAddress = course.trim();
        if (!Course.isValidCourse(trimmedAddress)) {
            throw new ParseException(Course.MESSAGE_CONSTRAINTS);
        }
        return new Course(trimmedAddress);
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
     * Parses a {@code String date} into a {@code LocalDateTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static LocalDateTime parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim().replaceAll("  +", " ");
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern(AttendanceList.DATE_TIME_FORMAT)
                .parseDefaulting(ChronoField.ERA, 1)
                .toFormatter()
                .withResolverStyle(ResolverStyle.STRICT);
        try {
            return LocalDateTime.parse(trimmedDate, formatter);
        } catch (DateTimeParseException e) {
            if (e.getCause() != null) {
                throw new ParseException("Invalid date or time");
            } else {
                throw new ParseException("Date must be in the format: " + AttendanceList.DATE_TIME_FORMAT);
            }
        }
    }

    /**
     * Parses a {@code String attendance} into a {@code Attendance}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code attendance} is invalid.
     */
    public static Attendance parseAttendance(String attendance) throws ParseException {
        requireNonNull(attendance);
        String trimmedAttendance = attendance.trim();
        try {
            return Attendance.fromString(trimmedAttendance);
        } catch (IllegalArgumentException e) {
            throw new ParseException(Attendance.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String} into a {@code float}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code String} is not a valid float.
     */
    public static float parseFloat(String value) throws ParseException {
        requireNonNull(value);
        String trimmedValue = value.trim();
        try {
            return Float.parseFloat(trimmedValue);
        } catch (NumberFormatException e) {
            throw new ParseException(MESSAGE_INVALID_FLOAT);
        }
    }

    /**
     * Parses {@code String testName} into a valid test name.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the test name is invalid.
     */
    public static String parseTestName(String testName) throws ParseException {
        requireNonNull(testName);
        String trimmedTestName = testName.trim();
        if (!Grade.isValidTestName(trimmedTestName)) {
            throw new ParseException(Grade.MESSAGE_TEST_NAME_CONSTRAINTS);
        }
        return trimmedTestName;
    }

    /**
     * Parses {@code String value} into a valid score float
     * Leading and trailing whitespaces will be trimmed.
     * The score must be between 0 and 100 (inclusive) with a maximum of 2 decimal places.
     *
     * @param value The string value to be parsed.
     * @return The parsed score as a float.
     * @throws ParseException if the value is not a valid number, out of bounds, or has more than 2 decimal places.
     */
    public static float parseScore(String value) throws ParseException {
        requireNonNull(value);
        String trimmedValue = value.trim();

        try {
            float res = Float.parseFloat(trimmedValue);
            if (res < 0 || res > 100) {
                throw new ParseException(MESSAGE_SCORE_CONSTRAINTS);
            }

            // Check for a maximum of 2 decimal places
            if (trimmedValue.contains(".") && trimmedValue.split("\\.")[1].length() > 2) {
                throw new ParseException(MESSAGE_SCORE_CONSTRAINTS);
            }

            return res;
        } catch (NumberFormatException e) {
            throw new ParseException(MESSAGE_SCORE_CONSTRAINTS);
        }
    }

    /**
     * Parses {@code String value} into a valid weightage float.
     * Leading and trailing whitespaces will be trimmed.
     * The weightage must be greater than 0 and up to 100, with a maximum of 2 decimal places.
     *
     * @param value The string value to be parsed.
     * @return The parsed weightage as a float.
     * @throws ParseException if the value is not a valid number, out of bounds, or has more than 2 decimal places.
     */

    public static float parseWeightage(String value) throws ParseException {
        requireNonNull(value);
        String trimmedValue = value.trim();

        try {
            float res = Float.parseFloat(trimmedValue);
            if (res < 0 || res > 100) {
                throw new ParseException(MESSAGE_WEIGHTAGE_CONSTRAINTS);
            }

            // Check for a maximum of 2 decimal places
            if (trimmedValue.contains(".") && trimmedValue.split("\\.")[1].length() > 2) {
                throw new ParseException(MESSAGE_WEIGHTAGE_CONSTRAINTS);
            }

            return res;
        } catch (NumberFormatException e) {
            throw new ParseException(MESSAGE_WEIGHTAGE_CONSTRAINTS);
        }
    }

    /**
     * Parsers {@code String value} into a valid AggGrade operation.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if operation is invalid
     */
    public static AggGradeCommand.Operation parseAggGradeOperation(String value) throws ParseException {
        requireNonNull(value);
        String lowercaseTrimmedValue = value.trim().toLowerCase();

        if (!OPERATION_TRANSLATE.containsKey(lowercaseTrimmedValue)) {
            throw new ParseException(MESSAGE_OPERATION_CONSTRAINTS);
        }

        return OPERATION_TRANSLATE.get(lowercaseTrimmedValue);
    }
}
