package tutorease.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static tutorease.address.commons.util.DateTimeUtil.checkValidDateTime;
import static tutorease.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorease.address.logic.Messages.MISSING_PREFIX;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import tutorease.address.commons.core.LogsCenter;
import tutorease.address.commons.core.index.Index;
import tutorease.address.commons.util.StringUtil;
import tutorease.address.logic.parser.exceptions.ParseException;
import tutorease.address.model.lesson.EndDateTime;
import tutorease.address.model.lesson.Fee;
import tutorease.address.model.lesson.StartDateTime;
import tutorease.address.model.lesson.StudentId;
import tutorease.address.model.person.Address;
import tutorease.address.model.person.Email;
import tutorease.address.model.person.Name;
import tutorease.address.model.person.Phone;
import tutorease.address.model.person.Role;
import tutorease.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    private static Logger logger = LogsCenter.getLogger(ParserUtil.class);

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
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
        if (!Name.hasNoSlash(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS_NO_SLASHES);
        }
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
     * Parses a {@code String role} into an {@code Role}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Role parseRole(String role) throws ParseException {
        requireNonNull(role);
        String trimmedRole = role.trim();
        if (!Role.isValidRole(trimmedRole)) {
            throw new ParseException(Role.MESSAGE_CONSTRAINTS);
        }
        return new Role(trimmedRole);
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
     * Parses a {@code String studentId} into a {@code StudentId}.
     *
     * @param studentId The student ID to be parsed.
     * @return The parsed student ID.
     * @throws ParseException If the student ID is invalid.
     */
    public static StudentId parseStudentId(String studentId) throws ParseException {
        logger.log(Level.INFO, "Parsing student ID: " + studentId);
        requireNonNull(studentId);

        if (!StudentId.isValidStudentId(studentId)) {
            logger.log(Level.WARNING, "Student ID is invalid: " + studentId);
            throw new ParseException(StudentId.MESSAGE_CONSTRAINTS);
        }

        logger.log(Level.INFO, "Student ID is valid: " + studentId);
        return new StudentId(studentId);
    }

    /**
     * Parses a {@code String fee} into a {@code Fee}.
     *
     * @param fee The fee to be parsed.
     * @return The parsed fee.
     * @throws ParseException If the fee is invalid.
     */
    public static Fee parseFee(String fee) throws ParseException {
        logger.log(Level.INFO, "Parsing fee: " + fee);
        requireNonNull(fee);

        String trimmedFee = fee.trim();
        if (!Fee.isValidFee(trimmedFee)) {
            logger.log(Level.WARNING, "Fee is invalid: " + fee);
            throw new ParseException(Fee.MESSAGE_CONSTRAINTS);
        }

        logger.log(Level.INFO, "Fee is valid: " + fee);
        return new Fee(trimmedFee);
    }

    /**
     * Parses a {@code String startDateTime} into a {@code StartDateTime}.
     *
     * @param startDateTime The start date time to be parsed.
     * @return The parsed start date time.
     * @throws ParseException If the start date time is invalid.
     */
    public static StartDateTime parseStartDateTime(String startDateTime) throws ParseException {
        logger.log(Level.INFO, "Parsing start date time: " + startDateTime);
        requireNonNull(startDateTime);

        String trimmedStartDateTime = startDateTime.trim();
        checkValidDateTime(startDateTime);
        logger.log(Level.INFO, "Start date time is valid: " + startDateTime);
        return StartDateTime.createStartDateTime(trimmedStartDateTime);
    }

    /**
     * Parses a {@code StartDateTime} and a {@code String hoursToAdd} into an {@code EndDateTime}.
     *
     * @param startDateTime The start date time.
     * @param hoursToAdd    The number of hours to add to the start date time.
     * @return The parsed end date time.
     * @throws ParseException If the hours to add is invalid.
     */
    public static EndDateTime parseEndDateTime(StartDateTime startDateTime, String hoursToAdd) throws ParseException {
        logger.log(Level.INFO, "Parsing end date time: " + startDateTime + " + " + hoursToAdd + " hours");
        requireNonNull(startDateTime);
        requireNonNull(hoursToAdd);

        if (!EndDateTime.isValidHoursToAdd(hoursToAdd)) {
            logger.log(Level.WARNING, "Hours to add is invalid: " + hoursToAdd);
            throw new ParseException(EndDateTime.HOURS_MESSAGE_CONSTRAINTS);
        }

        logger.log(Level.INFO, "Hours to add is valid: " + hoursToAdd);
        return EndDateTime.createEndDateTime(startDateTime, hoursToAdd);
    }

    /**
     * Validates that the prefixes are present in the {@code ArgumentMultimap}.
     *
     * @param argumentMultimap The {@code ArgumentMultimap} to check.
     * @param usage            The usage message to display.
     * @param prefixes         The prefixes to check.
     * @throws ParseException If the prefixes are missing.
     */
    public static void validatePrefixesPresent(ArgumentMultimap argumentMultimap, String usage, Prefix... prefixes)
            throws ParseException {
        logger.log(Level.INFO, "Validating prefixes present: " + Arrays.toString(prefixes));
        checkMissingAllPrefixes(argumentMultimap, usage);
        findMissingPrefix(argumentMultimap, usage, prefixes);

        logger.log(Level.INFO, "All prefixes are present: " + Arrays.toString(prefixes));
        if (!argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, usage));
        }
    }

    /**
     * Finds the missing prefixes in the {@code ArgumentMultimap}.
     *
     * @param argumentMultimap The {@code ArgumentMultimap} to check.
     * @param usage            The usage message to display.
     * @param prefixes         The prefixes to check.
     * @throws ParseException If the prefixes are missing.
     */
    private static void findMissingPrefix(ArgumentMultimap argumentMultimap, String usage, Prefix[] prefixes)
            throws ParseException {
        logger.log(Level.INFO, "Finding missing prefixes: " + Arrays.toString(prefixes));

        for (Prefix prefix : prefixes) {
            if (!argumentMultimap.getValue(prefix).isPresent()) {
                logger.log(Level.WARNING, "Missing prefix: " + prefix);
                throw new ParseException(String.format(MISSING_PREFIX, prefix, usage));
            }
        }
    }

    /**
     * Checks if all prefixes are missing in the {@code ArgumentMultimap}.
     *
     * @param argumentMultimap The {@code ArgumentMultimap} to check.
     * @param usage            The usage message to display.
     * @throws ParseException If all prefixes are missing.
     */
    private static void checkMissingAllPrefixes(ArgumentMultimap argumentMultimap, String usage) throws ParseException {
        if (argumentMultimap.isMissingAllPrefix()) {
            logger.log(Level.WARNING, "All prefixes are missing");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, usage));
        }
    }
}
