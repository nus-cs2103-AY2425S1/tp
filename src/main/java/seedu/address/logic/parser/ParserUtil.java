package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Event;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.TelegramUsername;
import seedu.address.model.role.Role;
import seedu.address.model.role.RoleHandler;
import seedu.address.model.role.exceptions.InvalidRoleException;

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
     * Parses the given {@code String telegramUsername} on add command and returns a {@code TelegramUsername} object.
     * Leading and trailing spaces will be trimmed.
     *
     * @param telegramUsername The username string to be parsed. Can be {@code null}.
     * @return A {@code TelegramUsername} object with the trimmed username.
     * @throws ParseException if the provided {@code telegramUsername} is invalid according to the
     *         {@code TelegramUsername#isValidUsername(String)} method.
     */
    public static TelegramUsername parseTeleOnAdd(String telegramUsername) throws ParseException {
        if (telegramUsername == null) {
            return new TelegramUsername(telegramUsername);
        }
        String trimmedTelegramUsername = telegramUsername.trim();
        if (!TelegramUsername.isValidUsername(trimmedTelegramUsername)) {
            throw new ParseException(TelegramUsername.MESSAGE_CONSTRAINTS);
        }
        return new TelegramUsername(trimmedTelegramUsername);
    }

    /**
     * Parses the given {@code String telegramUsername} on edit command and returns a {@code TelegramUsername} object.
     * Leading and trailing spaces will be trimmed. Difference between this parse and the one for add command is that
     * this parse method allows removal of telegram username when user input empty input.
     *
     * @param telegramUsername The username string to be parsed
     * @return A {@code TelegramUsername} object with the trimmed username.
     * @throws ParseException if the provided {@code telegramUsername} is invalid according to the
     *         {@code TelegramUsername#isValidUsername(String)} method.
     */
    public static TelegramUsername parseTeleOnEdit(String telegramUsername) throws ParseException {
        if (telegramUsername.isEmpty()) {
            return new TelegramUsername(null);
        }
        String trimmedTelegramUsername = telegramUsername.trim();
        if (!TelegramUsername.isValidUsername(trimmedTelegramUsername)) {
            throw new ParseException(TelegramUsername.MESSAGE_CONSTRAINTS);
        }
        return new TelegramUsername(trimmedTelegramUsername);
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
     * Parses a {@code String role} into a {@code Role}.
     */
    public static Role parseRole(String role) throws ParseException {
        requireNonNull(role);
        RoleHandler rh = new RoleHandler();
        String trimmedRole = role.trim();
        if (!RoleHandler.isValidRoleName(trimmedRole)) {
            throw new ParseException(RoleHandler.MESSAGE_CONSTRAINTS);
        }

        try {
            return RoleHandler.getRole(role);
        } catch (InvalidRoleException e) {
            throw new ParseException(RoleHandler.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses {@code Collection<String> roles} into a {@code Set<Role>}.
     * @param roles Collection of roles to be parsed
     * @return Set of roles to be added
     * @throws ParseException if one of the roles is invalid
     */
    public static Set<Role> parseRoles(Collection<String> roles) throws ParseException {
        requireNonNull(roles);
        final Set<Role> roleSet = new HashSet<>();
        for (String role : roles) {
            roleSet.add(parseRole(role));
        }
        return roleSet;
    }

    /**
     * Parses a {@code String event} into an {@code Event}.
     * Leading and trailing whitespace will be trimmed.
     *
     * @param event A valid event string to be parsed.
     * @return The {@code Event} object created from the given {@code String event}.
     * @throws ParseException If the given {@code event} string is invalid or doesn't match the constraints.
     *     The error message will follow the constraints defined by {@code Event#MESSAGE_CONSTRAINTS}.
     */
    public static Event parseEvent(String event) throws ParseException {
        requireNonNull(event);
        String trimmedEvent = event.trim();
        if (!Event.isValidEvent(trimmedEvent)) {
            throw new ParseException(Event.MESSAGE_CONSTRAINTS);
        }
        return new Event(trimmedEvent);
    }

    /**
     * Parses a string of comma-separated indices and adds them to the provided set.
     * Each index is trimmed of whitespace and validated to ensure it is a non-zero unsigned integer.
     *
     * @param indices The set to which the parsed indices will be added.
     *                It should not be null.
     * @param string The string containing comma-separated indices.
     *               If null, no action is taken.
     *
     * @throws ParseException If the string is not null and one or more indices
     *                        are invalid (not a non-zero unsigned integer) or
     *                        if there are duplicate indices in the string.
     */
    public static void parseStringOfIndices(Set<Index> indices, String string) throws ParseException {
        if (string == null) {
            return; // skip cuz tag is not part of input
        }

        String[] strings = string.split(",");

        for (String str : strings) {
            Index index;
            try {
                index = ParserUtil.parseIndex(str.trim());
            } catch (ParseException e) {
                throw new ParseException("One or more of the contact indices is not a non-zero unsigned integer.");
            }

            if (indices.contains(index)) {
                throw new ParseException("There cannot be duplicate indices for the same role e.g. a/1,1");
            }
            indices.add(index);
        }
    }
}
