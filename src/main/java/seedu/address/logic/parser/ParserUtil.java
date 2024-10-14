package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.Address;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.Phone;
import seedu.address.model.student.PresentDates;
import seedu.address.model.student.StudentId;
import seedu.address.model.student.TutorialClass;
import seedu.address.model.tag.Tag;
import seedu.address.model.tut.Tut;
import seedu.address.model.tut.TutDate;

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
    public static Name parseTutName(String tutName) throws ParseException {
        requireNonNull(tutName);
        String trimmedName = tutName.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Tut.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Name(trimmedName);
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
    public static TutorialClass parseTutorialClass(String tutorialClass) throws ParseException {
        requireNonNull(tutorialClass);
        String trimmedTutorialClass = tutorialClass.trim();
        if (!TutorialClass.isValidTutorialClass(trimmedTutorialClass)) {
            throw new ParseException(TutorialClass.MESSAGE_CONSTRAINTS);
        }
        return new TutorialClass(trimmedTutorialClass);
    }

    /**
     * Parses a tutorial id and converts it into an integer.
     *
     * @param id String id of the tutorial.
     * @return an integer id of the tutorial.
     * @throws ParseException if the id is invalid.
     */
    public static TutorialClass parseTutIndex(String id) throws ParseException {
        requireNonNull(id);
        String trimmedId = id.trim();
        checkArgument(checkUsingIsDigitMethod(trimmedId), Tut.MESSAGE_ID_CONSTRAINTS);
        return new TutorialClass(trimmedId);
    }

    static boolean checkUsingIsDigitMethod(String input) {
        for (char c : input.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Parses a date string in the format "dd/MM/yyyy" and converts it to a {@link TutDate} object.
     *
     * @param date The date string in the format "dd/MM/yyyy".
     * @return A {@link TutDate} object representing the parsed date.
     * @throws ParseException If the date string is not in the correct format or cannot be parsed.
     */
    public static TutDate parseDate(String date) throws ParseException {
        try {
            Date d = new SimpleDateFormat("dd/MM/yyyy").parse(date);
            return new TutDate(d);
        } catch (java.text.ParseException e) {
            throw new ParseException(TutDate.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a collection of date strings and converts them into a {@link PresentDates} object.
     *
     * @param presentDates A collection of date strings in the format "dd/MM/yyyy".
     * @return A {@link PresentDates} object containing the parsed dates.
     * @throws ParseException If any of the date strings cannot be parsed.
     */
    public static PresentDates parseDates(Collection<String> presentDates) throws ParseException {
        requireNonNull(presentDates);
        final ArrayList<TutDate> dates = new ArrayList<>();
        for (String date : presentDates) {
            dates.add(parseDate(date));
        }
        return new PresentDates(dates);
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
