package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.Name;
import seedu.address.model.student.StudentId;
import seedu.address.model.student.TutorialId;
import seedu.address.model.tut.TutDate;
import seedu.address.model.tut.TutName;
import seedu.address.model.tut.Tutorial;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HHmm";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     *
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
     * Parses a {@code String tutName} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tutName} is invalid.
     */
    public static TutName parseTutName(String tutName) throws ParseException {
        requireNonNull(tutName);
        String trimmedName = tutName.trim();
        if (!TutName.isValidTutName(trimmedName)) {
            throw new ParseException(Tutorial.MESSAGE_NAME_CONSTRAINTS);
        }
        return new TutName(trimmedName);
    }


    /**
     * Parses a {@code String studentId} into a {@code StudentId}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code studentId} is invalid.
     */
    public static StudentId parseStudentId(String studentId) throws ParseException {
        requireNonNull(studentId);
        String trimmedStudentId = studentId.trim();
        if (!StudentId.isValidStudentId(trimmedStudentId)) {
            throw new ParseException(StudentId.MESSAGE_CONSTRAINTS);
        }
        return new StudentId(trimmedStudentId);
    }

    /**
     * Parses a {@code String tutorialClass} into a {@code TutorialClass}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tutorialClass} is invalid.
     */
    public static TutorialId parseTutorialId(String tutorialId) throws ParseException {
        requireNonNull(tutorialId);
        String trimmedTutorialId = tutorialId.trim();
        if (!TutorialId.isValidTutorialClass(trimmedTutorialId)) {
            throw new ParseException(TutorialId.MESSAGE_CONSTRAINTS);
        }
        return TutorialId.of(trimmedTutorialId);
    }

    /**
     * Parses a date string in the format "dd/MM/yyyy" and converts it to a {@link TutDate} object.
     *
     * @param date The date string in the format "dd/MM/yyyy".
     * @return A {@link Date} object representing the parsed date.
     * @throws ParseException If the date string is not in the correct format or cannot be parsed.
     */
    public static Date parseDate(String date) throws ParseException {
        try {
            return new SimpleDateFormat("yyyy/MM/dd").parse(date);
        } catch (java.text.ParseException e) {
            throw new ParseException(TutDate.MESSAGE_CONSTRAINTS);
        }
    }


    /**
     * Parses a due date string into a {@link LocalDateTime} object.
     *
     * @param dueDateString String representing due date.
     * @return {@link LocalDateTime} object with given due date.
     * @throws ParseException if the given string is invalid.
     */
    public static LocalDateTime parseDueDate(String dueDateString) throws ParseException {
        requireNonNull(dueDateString);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        try {
            return LocalDateTime.parse(dueDateString, formatter);
        } catch (DateTimeParseException e) {
            throw new ParseException("Failed to parse date time: " + e.getMessage());
        }
    }
}
