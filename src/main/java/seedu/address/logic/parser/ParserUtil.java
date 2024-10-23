package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_EMPTY_INPUT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DATE;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DATE_RANGE;
import static seedu.address.logic.Messages.MESSAGE_INVALID_NUMBER_OF_INPUTS;
import static seedu.address.logic.Messages.MESSAGE_INVALID_WITH_SPACES;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Attendance;
import seedu.address.model.person.Email;
import seedu.address.model.person.Fees;
import seedu.address.model.person.Name;
import seedu.address.model.person.Payment;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.tutorial.Tutorial;

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
     * Parses a {@code String payment} into an {@code Payment}.
     * Leading and trailing whitespaces will be trimmed
     */
    public static Payment parsePayment(String payment) throws ParseException {
        String trimmedPayment = payment.trim();
        if (!Payment.isValidPayment(trimmedPayment)) {
            throw new ParseException(Payment.MESSAGE_CONSTRAINTS);
        }
        return new Payment(trimmedPayment);
    }

    /**
     * Parses a {@code String fee} into an {@code Fees}.
     * Leading and trailing whitespaces will be trimmed, all characters are converted to lower case
     */
    public static Fees parseFees(String payment) throws ParseException {
        String trimmedFees = payment.trim().toLowerCase();
        if (!Fees.isValidFees(trimmedFees)) {
            throw new ParseException(Fees.MESSAGE_CONSTRAINTS);
        }
        return new Fees(trimmedFees);
    }

    /**
     * Parses a {@code String attendance} into an {@code Attendance}.
     * Leading and trailing whitespaces will be trimmed
     */
    public static Attendance parseAttendance(String attendance) throws ParseException {
        String trimmedAttendance = attendance.trim();
        if (!Attendance.isValidAttendance(trimmedAttendance)) {
            throw new ParseException(Attendance.MESSAGE_CONSTRAINTS);
        }
        LocalDate attendanceDate = LocalDate.parse(trimmedAttendance, Attendance.VALID_DATE_FORMAT);
        return new Attendance(attendanceDate);
    }

    /**
     * Parses a {@code String attendance} into an {@code Attendance}.
     * Leading and trailing whitespaces will be trimmed
     */
    public static Tutorial parseTutorial(String subject) throws ParseException {
        requireNonNull(subject);
        String trimmedSubject = subject.trim();
        if (!Tutorial.isValidTutorial(trimmedSubject)) {
            throw new ParseException(Tutorial.MESSAGE_CONSTRAINTS);
        }
        return new Tutorial(trimmedSubject);
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
     * Parses a string with multiple words from the find command, and check that it is not an empty string.
     *
     * @param searchString The search string.
     * @return The trimmed search string.
     * @throws ParseException If the search string is empty.
     */
    public static String parseMultipleWordsFromFindCommand(String searchString) throws ParseException {
        requireNonNull(searchString);
        String trimmedArgs = searchString.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_EMPTY_INPUT, FindCommand.MESSAGE_USAGE));
        }
        return trimmedArgs;
    }

    /**
     * Parses a single word from the find command, and checks that it does not contain spaces.
     *
     * @param searchString The search string.
     * @return The trimmed search string.
     * @throws ParseException if the search string contains spaces.
     */
    public static String parseSingleWordFromFindCommand(String searchString) throws ParseException {
        String trimmedArgs = parseMultipleWordsFromFindCommand(searchString);
        if (trimmedArgs.contains(" ")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_WITH_SPACES, FindCommand.MESSAGE_USAGE));
        }
        return trimmedArgs;
    }

    public static LocalDate[] parseAttendanceDate(String searchString) throws ParseException {
        String trimmedString = parseMultipleWordsFromFindCommand(searchString);
        String[] attendanceDates = validateAndSplitDateString(trimmedString);

        LocalDate startDate = parseDate(attendanceDates[0]);
        LocalDate endDate = parseDate(attendanceDates[1]);

        if (startDate.isAfter(endDate)) {
            throw new ParseException(String.format(MESSAGE_INVALID_DATE_RANGE, FindCommand.MESSAGE_USAGE));
        }

        return new LocalDate[]{startDate, endDate};
    }

    public static String[] validateAndSplitDateString(String dateInput) throws ParseException {
        String[] dateParts = dateInput.split(":");
        if (dateParts.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        return dateParts;
    }

    public static LocalDate parseDate(String dateString) throws ParseException {
        try {
            return LocalDate.parse(parseSingleWordFromFindCommand(dateString), Attendance.VALID_DATE_FORMAT);
        } catch (DateTimeParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_DATE, FindCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses a boolean value.
     *
     * @param booleanString The boolean string.
     * @return The parsed boolean value.
     * @throws ParseException if the search string is not a valid boolean.
     */
    public static boolean parseBoolean(String booleanString) throws ParseException {
        String trimmedArgs = parseSingleWordFromFindCommand(booleanString);
        if (!StringUtil.isBooleanValue(trimmedArgs)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        return Boolean.parseBoolean(trimmedArgs);
    }
}
