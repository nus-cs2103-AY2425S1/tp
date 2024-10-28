package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateOfLastVisit;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmergencyContact;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces
     * will be trimmed.
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
     * Parses a {@code String name} into a {@code Name}. Leading and trailing whitespaces will be trimmed.
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
     * Parses a {@code String phone} into a {@code Phone}. Leading and trailing whitespaces will be trimmed.
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
     * Parses a {@code Optional<String> address} into an {@code Optional<Address>}. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Optional<Address> parseAddress(Optional<String> address) throws ParseException {
        requireNonNull(address);

        if (address.isEmpty() || address.get().trim().isEmpty()) {
            // if an address prefix was never entered by the user
            // or an empty string ("") was entered indicating no address
            return Optional.empty();
        }

        String trimmedAddress = address.get().trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return Optional.of(new Address(trimmedAddress));
    }

    /**
     * Parses a {@code Optional<String> email} into an {@code Optional<Email>}. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Optional<Email> parseEmail(Optional<String> email) throws ParseException {
        requireNonNull(email);

        if (email.isEmpty() || email.get().trim().isEmpty()) {
            // if an email prefix was never entered by the user
            // or an empty string ("") was entered indicating no email
            return Optional.empty();
        }
        String trimmedEmail = email.get().trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return Optional.of(new Email(trimmedEmail));
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}. Leading and trailing whitespaces will be trimmed.
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
     * Parses a {@code String dateOfLastVisit} into a {@code DateOfLastVisit}. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code dateOfLastVisit} is invalid.
     */
    public static Optional<DateOfLastVisit> parseDateOfLastVisit(Optional<String> dateOfLastVisit)
            throws ParseException {
        requireNonNull(dateOfLastVisit);

        if (dateOfLastVisit.isEmpty() || dateOfLastVisit.get().trim().isEmpty()) {
            // if dateOfLastVisit prefix was never entered by the user
            // or an empty string ("") was entered indicating no date of last visit
            return Optional.empty();
        }

        String trimmedDateOfLastVisit = dateOfLastVisit.get().trim();
        if (!DateOfLastVisit.isValidDateOfLastVisit(trimmedDateOfLastVisit)) {
            throw new ParseException(DateOfLastVisit.MESSAGE_CONSTRAINTS);
        }
        return Optional.of(new DateOfLastVisit(trimmedDateOfLastVisit));
    }

    /**
     * Parses a {@code Optional<String> emergencyContact} into an {@code Optional<EmergencyContact>}. Leading
     * and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code emergencyContact} is invalid.
     */
    public static Optional<EmergencyContact> parseEmergencyContact(Optional<String> emergencyContact)
            throws ParseException {
        requireNonNull(emergencyContact);

        if (emergencyContact.isEmpty() || emergencyContact.get().trim().isEmpty()) {
            // if emergencyContact prefix was never entered by the user
            // or an empty string ("") was entered indicating no emergency contact
            return Optional.empty();
        }

        String trimmedEmergencyContact = emergencyContact.get().trim();
        if (!EmergencyContact.isValidEmergencyContact(trimmedEmergencyContact)) {
            throw new ParseException(EmergencyContact.MESSAGE_CONSTRAINTS);
        }
        return Optional.of(new EmergencyContact(trimmedEmergencyContact));
    }

}
