package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PREFIX;
import static seedu.address.logic.Messages.MESSAGE_MAXLEADINGZEROS;
import static seedu.address.logic.Messages.MESSAGE_OVERFLOW_INDEX;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.Group;
import seedu.address.model.list.GroupList;
import seedu.address.model.person.Email;
import seedu.address.model.person.Major;
import seedu.address.model.person.Name;
import seedu.address.model.person.StudentId;
import seedu.address.model.person.Year;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Error: Index is not a single non-zero unsigned integer.";

    /**
     * Parses the given one-based index string into an {@code Index} object.
     * The index string is validated to ensure it represents a valid non-zero unsigned integer.
     * Various checks are performed to ensure that the index is properly formatted.
     * If any validation fails, a {@code ParseException} is thrown with an appropriate error message.
     *
     * @param oneBasedIndex The user input string representing the one-based index.
     * @return An {@code Index} object representing the parsed index.
     * @throws ParseException If the index is invalid due to reasons such as non-numeric characters,
     *                        excessive leading zeros, integer overflow, or zero value.
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();

        if (StringUtil.verifyNotNumber(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        if (!StringUtil.verifyNotExcessiveLeadingZeros(trimmedIndex)) {
            throw new ParseException(MESSAGE_MAXLEADINGZEROS);
        }
        if (!StringUtil.verifyNotIntOverflow(trimmedIndex)) {
            throw new ParseException(MESSAGE_OVERFLOW_INDEX);
        }
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
     * Parses a {@code String studentId} into a {@code StudentId}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code studentId} is invalid.
     */
    public static StudentId parseStudentId(String studentId) throws ParseException {
        requireNonNull(studentId);
        String trimmedStudentId = studentId.trim();

        if (!StudentId.isValidStudentId(trimmedStudentId)) {
            handleAdditionalPrefix(trimmedStudentId, StudentId.MESSAGE_CONSTRAINTS);

            throw new ParseException(StudentId.MESSAGE_CONSTRAINTS);
        }

        return new StudentId(trimmedStudentId);
    }

    /**
     * Parses a {@code String major} into a {@code Major}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code major} is invalid.
     */
    public static Major parseMajor(String major) throws ParseException {
        requireNonNull(major);
        String trimmedMajor = major.trim();
        if (!Major.isValidMajor(trimmedMajor)) {
            throw new ParseException(Major.MESSAGE_CONSTRAINTS);
        }
        return Major.makeMajor(trimmedMajor);
    }

    /**
     * Parses a {@code String major} into a {@code Major} but allows empty major to be supplied.
     *
     * @throws ParseException if the given {@code major} is invalid or not empty.
     */
    public static Major parseOptionalMajor(String major) throws ParseException {
        requireNonNull(major);
        String trimmedMajor = major.trim();

        if (trimmedMajor.isEmpty()) {
            return Major.makeMajor(trimmedMajor);
        }

        return parseMajor(trimmedMajor);
    }

    /**
     * Parses a {@code String netId} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code netId} is invalid.
     */
    public static Email parseNetId(String netId) throws ParseException {
        requireNonNull(netId);
        String trimmedNetId = netId.trim();

        if (!Email.isValidNetId(trimmedNetId)) {
            handleAdditionalPrefix(trimmedNetId, Email.MESSAGE_CONSTRAINTS);

            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }

        return Email.makeEmail(trimmedNetId + Email.DOMAIN);
    }

    /**
     * Parses a {@code String netId} into an {@code Email} but allows empty netid to be supplied.
     *
     * @throws ParseException if the given {@code netId} is invalid or not empty.
     */
    public static Email parseOptionalNetId(String netId) throws ParseException {
        requireNonNull(netId);
        String trimmedNetId = netId.trim();

        if (trimmedNetId.isEmpty()) {
            return Email.makeEmail(trimmedNetId);
        }

        return parseNetId(trimmedNetId);
    }

    /**
     * Parses a {@code String year} into a {@code Year}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code year} is invalid.
     */
    public static Year parseYear(String year) throws ParseException {
        requireNonNull(year);
        String cleanedYear = removeLeadingZeroes(year.trim());

        if (!Year.isValidYear(cleanedYear)) {
            handleAdditionalPrefix(cleanedYear, Year.MESSAGE_CONSTRAINTS);

            throw new ParseException(Year.MESSAGE_CONSTRAINTS);
        }
        return Year.makeYear(cleanedYear);
    }

    /**
     * Parses a {@code String year} into a {@code Year} but allows empty year to be supplied.
     *
     * @throws ParseException if the given {@code year} is invalid or not empty.
     */
    public static Year parseOptionalYear(String year) throws ParseException {
        requireNonNull(year);
        String trimmedYear = year.trim();

        if (trimmedYear.isEmpty()) {
            return Year.makeYear(trimmedYear);
        }

        return parseYear(trimmedYear);
    }

    /**
     * Parses a {@code String group} into a {@code Group}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code group} is invalid.
     */
    public static Group parseGroup(String group) throws ParseException {
        requireNonNull(group);
        String trimmedGroup = group.trim();
        if (!Group.isValidGroupName(trimmedGroup)) {
            throw new ParseException(Group.MESSAGE_CONSTRAINTS);
        }
        return new Group(trimmedGroup);
    }

    /**
     * Parses {@code Collection<String> groups} into a {@code GroupList}.
     */
    public static GroupList parseGroups(Collection<String> groups) throws ParseException {
        requireNonNull(groups);
        final GroupList groupList = new GroupList();
        for (String groupName : groups) {
            groupList.addGroup(parseGroup(groupName));
        }
        return groupList;
    }

    private static String removeLeadingZeroes(String trimmedYear) {
        Pattern yearFormat = Pattern.compile("(?<leadingZeroes>0+)(?<year>[1-9]\\d*)");
        Matcher matcher = yearFormat.matcher(trimmedYear);

        if (matcher.matches()) {
            return matcher.group("year");
        }

        return trimmedYear;
    }

    private static void handleAdditionalPrefix(String input, String errorMsg) throws ParseException {
        if (input.contains("/")) {
            throw new ParseException(errorMsg + "\n"
                    + String.format(MESSAGE_INVALID_PREFIX, AddCommand.SUPPORTED_PREFIXES));
        }
    }
}
