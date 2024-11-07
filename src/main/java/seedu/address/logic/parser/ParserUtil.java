package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.group.AddToGroupCommandParser.MEMBER_MESSAGE_CONSTRAINTS;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Date;
import seedu.address.model.person.DonatedAmount;
import seedu.address.model.person.Email;
import seedu.address.model.person.Hours;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;


/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Contains one or more indices that are not non-zero unsigned "
            + "integers.";
    public static final String MESSAGE_INVALID_INTERVAL_FORMAT = "Invalid range format. Expected format: a-b"
        + " (0 < a <= b)";
    public static final String MESSAGE_INVALID_INTERVAL = "Invalid range: start index must be less than or equal"
            + " to end index.";
    public static final String MESSAGE_EMPTY_INDICES = "INDICES should not be empty; at least one index or range "
            + "must be provided.";
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
     * Parses a range in the format "a-b" into a list of {@code Index} objects.
     *
     * @param range The range string to parse.
     * @return A list of {@code Index} objects from start to end of the range.
     * @throws ParseException If the format is invalid or bounds are incorrect.
     */
    public static List<Index> parseRange(String range) throws ParseException {
        String[] bounds = range.split("-");
        System.out.println(bounds.toString());
        // Validate that the range contains exactly two parts
        if (bounds.length != 2) {
            throw new ParseException(MESSAGE_INVALID_INTERVAL_FORMAT);
        }

        // Parse start and end indices
        Index startIndex = parseIndex(bounds[0]);
        Index endIndex = parseIndex(bounds[1]);

        // Validate that startIndex is less than or equal to endIndex
        if (startIndex.getOneBased() > endIndex.getOneBased()) {
            throw new ParseException(MESSAGE_INVALID_INTERVAL);
        }

        // Generate and return the list of indices from start to end
        return IntStream.rangeClosed(startIndex.getOneBased(), endIndex.getOneBased())
                .mapToObj(Index::fromOneBased)
                .collect(Collectors.toList());
    }

    /**
     * Parses a string containing indices and ranges (e.g., "1 2 3 5-9") into a list of {@code Index} objects.
     * Accepts both single indices and ranges separated by spaces.
     *
     * @param args The string containing indices and/or ranges to parse.
     * @return A list of {@code Index} objects representing the parsed indices and ranges.
     * @throws ParseException If any part of the input is not a valid index or range.
     */
    public static List<Index> parseIndices(String args) throws ParseException {
        requireNonNull(args);
        String trimmedIndices = args.trim();
        checkIndicesNotEmpty(trimmedIndices);
        try {
            return Arrays.stream(trimmedIndices.split(" "))
                    .flatMap(arg -> parseArgAsStream(arg))
                    .collect(Collectors.toList());
        } catch (RuntimeException e) {
            String causeMessage = e.getCause().getMessage();
            // Catch any ParseExceptions wrapped in RuntimeExceptions
            throw new ParseException(causeMessage, e);
        }
    }
    private static void checkIndicesNotEmpty(String indices) throws ParseException {
        if (indices.isEmpty()) {
            throw new ParseException(MESSAGE_EMPTY_INDICES);
        }
    }

    /**
     * Parses a single argument as either an interval (e.g., "5-9") or a single index (e.g., "3"),
     * returning a stream of {@code Index} objects. This allows for processing both individual indices
     * and ranges within a stream context.
     *
     * @param arg The argument string to parse as an index or interval.
     * @return A stream of {@code Index} objects parsed from the argument.
     * @throws RuntimeException wrapping a {@code ParseException} if the argument is invalid.
     */
    private static Stream<Index> parseArgAsStream(String arg) {
        try {
            if (arg.contains("-")) {
                return parseRange(arg).stream();
            } else {
                return Stream.of(parseIndex(arg));
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
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
     * Parses a {@code String role} into a {@code Role}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code role} is invalid.
     */
    public static Role parseRole(String role) throws ParseException {
        requireNonNull(role);
        String trimmedRole = role.trim();
        if (!Role.isValidRole(trimmedRole)) {
            throw new ParseException(Role.MESSAGE_CONSTRAINTS);
        }
        return Role.fromString(trimmedRole);
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
     * Parses a {@code String hours} into a {@code Hours}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code hours} is invalid.
     */
    public static Hours parseHours(String hours) throws ParseException {
        requireNonNull(hours);
        String trimmedHours = hours.trim();
        if (!Hours.isValidHours(trimmedHours)) {
            throw new ParseException(Hours.MESSAGE_CONSTRAINTS);
        }
        return new Hours(trimmedHours);
    }

    /**
     * Parses a {@code String amount} into a {@code DonatedAmount}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code amount} is invalid.
     */
    public static DonatedAmount parseDonatedAmount(String amount) throws ParseException {
        requireNonNull(amount);
        String trimmedAmount = amount.trim();
        if (!DonatedAmount.isValidAmount(trimmedAmount)) {
            throw new ParseException(DonatedAmount.MESSAGE_CONSTRAINTS);
        }
        return new DonatedAmount(trimmedAmount);
    }

    /**
     * Parses a {@code String amount} into a {@code DonatedAmount}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code amount} is invalid.
     */
    public static Date parsePartnershipEndDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!Date.isValidDate(trimmedDate)) {
            throw new ParseException(Date.MESSAGE_CONSTRAINTS);
        }
        return new Date(trimmedDate);
    }

    /**
     * Parses a {@code String sortOption} into a {@code SortOption}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given sortOption is invalid.
     */
    public static SortOption parseSortOption(String sortOption) throws ParseException {
        requireNonNull(sortOption);
        String trimmedOption = sortOption.trim();
        try {
            return SortOption.fromString(trimmedOption);
        } catch (IllegalArgumentException e) {
            throw new ParseException(SortOption.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String membersAsString} into a {@code List<Index>}.
     *
     * @throws ParseException if the given membersAsString is invalid.
     */
    public static List<Index> parseMembers(String membersAsString) throws ParseException {
        requireNonNull(membersAsString);
        List<Index> members;
        try {
            members = Arrays.stream(membersAsString.split(" ")).map(
                    i -> Index.fromOneBased(Integer.parseInt(i))).toList();
        } catch (PatternSyntaxException | NumberFormatException e) {
            throw new ParseException(MEMBER_MESSAGE_CONSTRAINTS);
        }
        return members;
    }
}
