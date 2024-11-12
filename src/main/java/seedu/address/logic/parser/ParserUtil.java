package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PriorityLevel;
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
     * Parses a {@code String priorityLevel} into a {@code PriorityLevel}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param priorityLevel The string to parse.
     * @return The parsed PriorityLevel object.
     * @throws ParseException if the given {@code priorityLevel} is invalid.
     */
    public static PriorityLevel parsePriorityLevel(String priorityLevel) throws ParseException {
        requireNonNull(priorityLevel);
        String trimmedPriorityLevel = priorityLevel.trim();

        if (trimmedPriorityLevel.isEmpty() || trimmedPriorityLevel.equalsIgnoreCase("delete")) {
            return new PriorityLevel(3); // default level if none provided or "delete" keyword used
        }

        if (trimmedPriorityLevel.isEmpty()) {
            return new PriorityLevel(3); // default level if none provided
        }
        int level;
        try {
            level = Integer.parseInt(trimmedPriorityLevel);
        } catch (NumberFormatException e) {
            throw new ParseException("Priority level must be a number.");
        }

        if (level < 1 || level > 3) {
            throw new ParseException("Invalid priority level. Please enter 1, 2, or 3.");
        }
        return new PriorityLevel(level);
    }

    /**
     * Parses a {@code String argument} to determine if it indicates a command to delete the priority level.
     * If the argument is "delete", it returns a default PriorityLevel of 3.
     *
     * @param argument The string argument to parse.
     * @return The PriorityLevel of 3 if the argument indicates deletion.
     * @throws ParseException if the argument is not "delete" or a valid priority level.
     */
    public static PriorityLevel parseDeletePriorityLevel(String argument) throws ParseException {
        requireNonNull(argument);
        String trimmedArgument = argument.trim();

        if (trimmedArgument.equalsIgnoreCase("deletelevel")) {
            return new PriorityLevel(3); // default priority level upon deletion
        }

        throw new ParseException("Invalid argument for deleting priority level. Expected 'deletelevel'.");
    }

}
