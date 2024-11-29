package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_BLANK_FIELD;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Nickname;
import seedu.address.model.contact.Role;
import seedu.address.model.contact.StudentStatus;
import seedu.address.model.contact.TelegramHandle;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {
    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_NAME_FIELD_CANNOT_BLANK = "Name " + MESSAGE_BLANK_FIELD;
    public static final String MESSAGE_EMAIL_FIELD_CANNOT_BLANK = "Email " + MESSAGE_BLANK_FIELD;
    public static final String MESSAGE_TELEGRAM_HANDLE_FILED_CANNOT_BLANK =
            "Telegram Handle " + MESSAGE_BLANK_FIELD;
    public static final String MESSAGE_STUDENT_STATUS_FIELD_CANNOT_BLANK =
            "Student Status " + MESSAGE_BLANK_FIELD;
    public static final String MESSAGE_ROLE_FIELD_CANNOT_BLANK = "Role " + MESSAGE_BLANK_FIELD;

    public static final String MESSAGE_INVALID_NAME_FIELD = "Invalid Name!\n" + Name.MESSAGE_CONSTRAINTS;
    public static final String MESSAGE_INVALID_EMAIL_FIELD = "Invalid Email!\n" + Email.MESSAGE_CONSTRAINTS;
    public static final String MESSAGE_INVALID_TELEGRAM_HANDLE_FIELD =
            "Invalid Telegram Handle!\n" + TelegramHandle.MESSAGE_CONSTRAINTS;
    public static final String MESSAGE_INVALID_STUDENT_STATUS_FIELD =
            "Invalid Student Status!\n" + StudentStatus.MESSAGE_CONSTRAINTS;
    public static final String MESSAGE_INVALID_ROLE_FIELD = "Invalid Role!\n" + Role.MESSAGE_CONSTRAINTS;

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
        if (trimmedName.isEmpty()) {
            throw new ParseException(MESSAGE_NAME_FIELD_CANNOT_BLANK);
        }
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(MESSAGE_INVALID_NAME_FIELD);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String telegramHandle} into a {@code TelegramHandle}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code telegramHandle} is invalid.
     */
    public static TelegramHandle parseTelegramHandle(String telegramHandle) throws ParseException {
        requireNonNull(telegramHandle);
        String trimmedTelegramHandle = telegramHandle.trim();
        if (trimmedTelegramHandle.isEmpty()) {
            throw new ParseException(MESSAGE_TELEGRAM_HANDLE_FILED_CANNOT_BLANK);
        }
        if (!TelegramHandle.isValidTelegramHandle(trimmedTelegramHandle)) {
            throw new ParseException(MESSAGE_INVALID_TELEGRAM_HANDLE_FIELD);
        }
        return new TelegramHandle(trimmedTelegramHandle);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static StudentStatus parseStudentStatus(String studentStatus) throws ParseException {
        requireNonNull(studentStatus);
        String trimmedStudentStatus = studentStatus.trim();
        if (trimmedStudentStatus.isEmpty()) {
            throw new ParseException(MESSAGE_STUDENT_STATUS_FIELD_CANNOT_BLANK);
        }
        if (!StudentStatus.isValidStudentStatus(trimmedStudentStatus)) {
            throw new ParseException(MESSAGE_INVALID_STUDENT_STATUS_FIELD);
        }
        return new StudentStatus(trimmedStudentStatus);
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
        if (trimmedEmail.isEmpty()) {
            throw new ParseException(MESSAGE_EMAIL_FIELD_CANNOT_BLANK);
        }
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(MESSAGE_INVALID_EMAIL_FIELD);
        }
        return new Email(trimmedEmail);
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
        if (trimmedRole.isEmpty()) {
            throw new ParseException(MESSAGE_ROLE_FIELD_CANNOT_BLANK);
        }
        if (!Role.isValidRoleName(trimmedRole)) {
            throw new ParseException(MESSAGE_INVALID_ROLE_FIELD);
        }
        return new Role(trimmedRole);
    }

    /**
     * Parses {@code Collection<String> roles} into a {@code Set<Role>}.
     */
    public static Set<Role> parseRoles(Collection<String> roles) throws ParseException {
        Set<Role> roleSet = new TreeSet<>();
        for (String roleName : roles) {
            roleSet.add(parseRole(roleName));
        }
        return roleSet;
    }

    /**
     * Parses a {@code String nickname} into a {@code Nickname}.
     * Leading and trailing whitespaces will be trimmed.*
     */
    public static Nickname parseNickname(String nickname) { // Parse Exception was never thrown
        requireNonNull(nickname);
        String trimmedNickname = nickname.trim();
        return new Nickname(trimmedNickname);
    }

    /**
     * Checks if the number entered is a valid integer.
     * @param args the preamble
     * @return true if it is an integer containing 32 bits (i.e. -2147483648 to 2147483647), otherwise false
     */
    public static boolean isInteger(String args) {
        try {
            Integer.parseInt(args); // can pass zero and negative integers as well
            return true;
        } catch (Exception exp) {
            return false;
        }
    }
}
