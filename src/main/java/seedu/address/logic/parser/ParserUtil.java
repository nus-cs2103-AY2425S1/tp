package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_SYNTAX;
import static seedu.address.logic.Messages.MESSAGE_MISSING_REQUIRED_PREFIXES;
import static seedu.address.logic.Messages.MESSAGE_UNEXPECTED_PREAMBLE;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.product.ProductName;
import seedu.address.model.supplier.Address;
import seedu.address.model.supplier.Email;
import seedu.address.model.supplier.Name;
import seedu.address.model.supplier.Phone;
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
        return new Name(name);
    }


    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static ProductName parseProductName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!ProductName.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new ProductName(name);
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
            throw new ParseException(Email.getDetailedErrorMessage(email));
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
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static int parseInteger(String num) throws ParseException, NumberFormatException {
        // TODO: check if the number is a valid integer
        requireNonNull(num);
        String trimmedNum = num.trim();
        return Integer.parseInt(trimmedNum);
    }

    /**
     * Verifies the input for command parsers to ensure no duplicates, unexpected preamble content,
     * missing required prefixes, and no extra prefixes.
     *
     * @param argMultimap       ArgumentMultimap containing the parsed arguments.
     * @param requiredPrefixes  Required prefixes that must be present.
     * @param commandUsage      Command usage information to show in error messages.
     * @throws ParseException   If there is an issue with the input format.
     */
    public static void verifyInput(ArgumentMultimap argMultimap, Prefix[] requiredPrefixes, String commandUsage)
            throws ParseException {
        // Check if required prefixes are present
        if (!arePrefixesPresent(argMultimap, requiredPrefixes)) {
            throw new ParseException(String.format(MESSAGE_MISSING_REQUIRED_PREFIXES, commandUsage));
        }
        // Check for preamble content
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_UNEXPECTED_PREAMBLE, commandUsage));
        }
        // Check for extra prefixes
        if (hasExtraPrefixes(argMultimap)) {
            throw new ParseException(String.format(MESSAGE_INVALID_SYNTAX, commandUsage));
        }
    }

    // Helper methods for verifying presence and extra prefixes
    private static boolean arePrefixesPresent(ArgumentMultimap argMultimap, Prefix... prefixes) {
        return Arrays.stream(prefixes).allMatch(prefix -> argMultimap.getValue(prefix).isPresent());
    }

    private static boolean hasExtraPrefixes(ArgumentMultimap argMultimap) {
        Set<Prefix> presentPrefixes = argMultimap.getAllPrefixes();

        for (Prefix prefix : presentPrefixes) {
            List<String> values = argMultimap.getAllValues(prefix);

            // Check if any value contains an extra "/" which may indicate an extra prefix
            for (String value : values) {
                if (value.contains("/")) {
                    return true;
                }
            }
        }
        return false;
    }
}
