package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.assignment.Deadline;
import seedu.address.model.assignment.Grade;
import seedu.address.model.assignment.Status;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.student.StudentNumber;
import seedu.address.model.student.TutorialGroup;
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
     * Parses a {@code String tutorialGroup} into a {@code TutorialGroup}.
     */
    public static TutorialGroup parseTutorialGroup(String tutorialGroup) throws ParseException {
        requireNonNull(tutorialGroup);
        String trimmedTutorialGroup = tutorialGroup.trim();
        if (!TutorialGroup.isValidTutorialGroup(trimmedTutorialGroup)) {
            throw new ParseException(TutorialGroup.MESSAGE_CONSTRAINTS);
        }
        return new TutorialGroup(trimmedTutorialGroup);
    }

    /**
     * Parses a {@code String studentNumber} into a {@code StudentNumber}.
     */
    public static StudentNumber parseStudentNumber(String studentNumber) throws ParseException {
        requireNonNull(studentNumber);
        String trimmedStudentNumber = studentNumber.trim();
        if (!StudentNumber.isValidStudentNumber(trimmedStudentNumber)) {
            throw new ParseException(StudentNumber.MESSAGE_CONSTRAINTS);
        }
        return new StudentNumber(trimmedStudentNumber);
    }

    /**
     * Parses a {@code String date} into a {@code LocalDate} object.
     * The date is expected to be in the format 'YYYY-MM-DD'.
     *
     * @param date The date string to be parsed.
     * @return A {@code LocalDate} object representing the parsed date.
     * @throws ParseException If the input string does not follow the 'YYYY-MM-DD' format or is null.
     */
    public static LocalDate parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        try {
            System.out.println(trimmedDate);
            return LocalDate.parse(trimmedDate, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            throw new ParseException("Invalid date format. Please use YYYY-MM-DD.");
        }
    }

    /**
     * Parses a {@code String status} into an {@code Attendance} object.
     * The status is expected to be either 'present' or 'absent'.
     *
     * @param status The attendance status string to be parsed.
     * @return An {@code Attendance} object representing the parsed attendance status.
     * @throws ParseException If the input string is not 'present' or 'absent', or is null.
     */

    public static Attendance parseAttendance(String status) throws ParseException {
        requireNonNull(status);
        String trimmedStatus = status.trim();
        if (!Attendance.isValidAttendance(trimmedStatus)) {
            throw new ParseException(Attendance.MESSAGE_CONSTRAINTS);
        }
        return new Attendance(trimmedStatus);
    }

    /**
     * Parses the given {@code String} of assignment name and returns an {@code AssignmentName} object.
     *
     * @param assignmentName The assignment name to parse.
     * @return The parsed {@code AssignmentName}.
     * @throws ParseException If the given assignment name is invalid.
     */
    public static AssignmentName parseAssignmentName(String assignmentName) throws ParseException {
        requireNonNull(assignmentName);
        if (!AssignmentName.isValidName(assignmentName)) {
            throw new ParseException(AssignmentName.MESSAGE_CONSTRAINTS);
        }
        return new AssignmentName(assignmentName);
    }

    /**
     * Parses the given {@code String} of date and returns a {@code Deadline} object.
     *
     * @param date The date string to parse.
     * @return The parsed {@code Deadline}.
     * @throws ParseException If the given date is invalid.
     */
    public static Deadline parseDeadline(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!Deadline.isValidDeadline(trimmedDate)) {
            throw new ParseException(Deadline.MESSAGE_CONSTRAINTS);
        }
        return new Deadline(trimmedDate);
    }

    /**
     * Parses the given {@code String} of status and returns a {@code Status} object.
     *
     * @param status The status string to parse.
     * @return The parsed {@code Status}.
     * @throws ParseException If the given status is invalid.
     */
    public static Status parseStatus(String status) throws ParseException {
        requireNonNull(status);
        String trimmedStatus = status.trim();
        if (!Status.isValidStatus(trimmedStatus)) {
            throw new ParseException(Status.MESSAGE_CONSTRAINTS);
        }
        return new Status(trimmedStatus);
    }

    /**
     * Parses the given list of {@code String} statuses and returns a list of {@code Status} objects.
     * Pads the list to be exactly the given length
     *
     * @param statuses The list of status strings to parse.
     * @param requiredLength The exact length of the output list
     * @return A list of parsed {@code Status} objects.
     * @throws ParseException If any of the given statuses are invalid.
     */
    public static List<Status> parseStatuses(List<String> statuses, int requiredLength) throws ParseException {
        requireNonNull(statuses);
        Status[] statusList = new Status[requiredLength];
        for (int i = 0; i < requiredLength; i++) {
            if (i < statuses.size()) {
                statusList[i] = parseStatus(statuses.get(i));
            } else {
                statusList[i] = Status.getDefault();
            }
        }
        return Arrays.stream(statusList).toList();
    }

    /**
     * Parses the given {@code String} of grade and returns a {@code Grade} object.
     *
     * @param grade The grade string to parse.
     * @return The parsed {@code Grade}.
     * @throws ParseException If the given grade is invalid.
     */
    public static Grade parseGrade(String grade) throws ParseException {
        requireNonNull(grade);
        String trimmedGrade = grade.trim();
        if (!Grade.isValidGrade(trimmedGrade)) {
            throw new ParseException(Grade.MESSAGE_CONSTRAINTS);
        }
        return new Grade(trimmedGrade);
    }
}
