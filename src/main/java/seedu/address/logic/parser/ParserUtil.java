package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.ClientStatus;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.PaymentStatus;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProjectStatus;
import seedu.address.model.tag.Tag;


/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    private static final Logger logger = Logger.getLogger(ParserUtil.class.getName());

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
     * Parses a {@code String projectStatus} into a {@code ProjectStatus}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code projectStatus} is invalid.
     */
    public static ProjectStatus parseProjectStatus(String projectStatus) throws ParseException {
        requireNonNull(projectStatus);
        String trimmedProjectStatus = projectStatus.trim();
        if (!ProjectStatus.isValidProjectStatus(trimmedProjectStatus)) {
            throw new ParseException(ProjectStatus.MESSAGE_CONSTRAINTS);
        }
        return new ProjectStatus(trimmedProjectStatus);
    }

    /**
     * Parses a {@code String paymentStatus} into a {@code PaymentStatus}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code paymentStatus} is invalid.
     */
    public static PaymentStatus parsePaymentStatus(String paymentStatus) throws ParseException {
        requireNonNull(paymentStatus);
        String trimmedPaymentStatus = paymentStatus.trim();
        if (!PaymentStatus.isValidPaymentStatus(trimmedPaymentStatus)) {
            throw new ParseException(PaymentStatus.MESSAGE_CONSTRAINTS);
        }
        return new PaymentStatus(trimmedPaymentStatus);
    }
    /**
     * Parses a {@code String clientStatus} into a {@code clientStatus}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code clientStatus} is invalid.
     */
    public static ClientStatus parseClientStatus(String clientStatus) throws ParseException {
        requireNonNull(clientStatus);
        String trimmedClientStatus = clientStatus.trim();
        if (!ClientStatus.isValidClientStatus(trimmedClientStatus)) {
            throw new ParseException(ClientStatus.MESSAGE_CONSTRAINTS);
        }
        return new ClientStatus(trimmedClientStatus);
    }

    /**
     * Parses a {@code String deadline} into a {@code Deadline}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code deadline} is invalid.
     */
    public static Deadline parseDeadline(String deadline) throws ParseException {
        requireNonNull(deadline);
        String trimmedDeadline = deadline.trim();
        if (!Deadline.isValidDeadline(trimmedDeadline)) {
            logger.warning("Invalid deadline format detected: " + trimmedDeadline);
            throw new ParseException(Deadline.MESSAGE_CONSTRAINTS);
        }
        return new Deadline(trimmedDeadline);
    }

    /**
     * Returns the day given in a string representing a date
     */
    public static int getDay(String date) {
        // defensive programming
        Matcher datetimeMatcher = Deadline.PATTERN_DATE.matcher(date);

        if (datetimeMatcher.find()) {
            return Integer.parseInt(datetimeMatcher.group(1));
        } else {
            return 0; // will eventually throw an error
        }
    }

    /**
     * Returns the month given in a string representing a date
     */
    public static int getMonth(String date) {
        // defensive programming
        Matcher datetimeMatcher = Deadline.PATTERN_DATE.matcher(date);

        if (datetimeMatcher.find()) {
            return Integer.parseInt(datetimeMatcher.group(2));
        } else {
            return 0; // will eventually throw an error
        }
    }

    /**
     * Returns the year given in a string representing a date.
     * The year can either be a 2 digit number or a 4-digit number.
     * A 1- or 2-digit year is converted to a 4-digit year by adding 2000:
     *      Ex: 24 is considered as 2024, 9 is considered as 2009
     * A year with 4+ digits is kept as-is.
     * A 3-digit year is considered invalid.
     */
    public static int getYear(String date) {
        // defensive programming
        Matcher datetimeMatcher = Deadline.PATTERN_DATE.matcher(date);

        if (datetimeMatcher.find()) {
            int year = Integer.parseInt(datetimeMatcher.group(3));
            if (year >= 0 && year <= 99) {
                year += 2000;
            } else if (year < 10 || (year > 99 && year < 1000)) {
                return 0; // will eventually cause an error
            }

            return year;
        } else {
            return 0; // will eventually cause an error
        }
    }
}
