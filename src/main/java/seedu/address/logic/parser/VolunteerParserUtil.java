package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.volunteer.Date;
import seedu.address.model.volunteer.Email;
import seedu.address.model.volunteer.Name;
import seedu.address.model.volunteer.Phone;


/**
 * Contains utility methods used for parsing strings in volunteer-related classes.
 */
public class VolunteerParserUtil {

    public static final String MESSAGE_CONSTRAINTS = "Volunteer parsing error";
    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses a given string as a {@code Name} and returns it after validation.
     *
     * @param name The name string to be parsed.
     * @return A valid {@code Name} object.
     * @throws ParseException If the given name does not conform to the name constraints.
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
     * Parses a given string as a {@code Phone} and returns it after validation.
     *
     * @param phone The phone number string to be parsed.
     * @return A valid {@code Phone} object.
     * @throws ParseException If the given phone number does not conform to the phone constraints.
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
     * Parses a given string as a {@code Date} and returns it after validation.
     *
     * @param date The date string to be parsed.
     * @return A valid {@code Date} object.
     * @throws ParseException If the given date does not conform to the date constraints.
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!Date.isValidDate(trimmedDate)) {
            throw new ParseException(Date.MESSAGE_CONSTRAINTS);
        }
        return new Date(trimmedDate);
    }

    /**
     * Parses a given string as an {@code Email} and returns it after validation.
     *
     * @param email The email string to be parsed.
     * @return A valid {@code Email} object.
     * @throws ParseException If the given email does not conform to the email constraints.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }
}
