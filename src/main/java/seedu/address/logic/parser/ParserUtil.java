package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Company;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Relation;
import seedu.address.model.person.Rsvp;
import seedu.address.model.person.predicates.AddressContainsKeywordsPredicate;
import seedu.address.model.person.predicates.CompanyContainsKeywordsPredicate;
import seedu.address.model.person.predicates.EmailContainsKeywordsPredicate;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicates.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.predicates.RsvpContainsKeywordsPredicate;
import seedu.address.model.person.predicates.TagContainsKeywordsPredicate;
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
     * Parses a {@code String rsvp} into an {@code Rsvp}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code rsvp} is invalid.
     */
    public static Rsvp parseRsvp(String rsvp) throws ParseException {
        if (rsvp == null) {
            return null;
        }
        String trimmedRsvp = rsvp.trim();
        if (!Rsvp.isValidRsvp(trimmedRsvp)) {
            throw new ParseException(Rsvp.MESSAGE_CONSTRAINTS);
        }
        return new Rsvp(trimmedRsvp);
    }

    /**
     * Parses a {@code String relation} into an {@code Relation}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code relation} is invalid.
     */
    public static Relation parseRelation(String relation) throws ParseException {
        if (relation == null) {
            return null;
        }
        String trimmedRelation = relation.trim();
        if (!Relation.isValidRelation(trimmedRelation)) {
            throw new ParseException(Relation.MESSAGE_CONSTRAINTS);
        }
        return new Relation(trimmedRelation);
    }

    /**
     * Parses a {@code String company} into an {@code Company}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Company parseCompany(String company) throws ParseException {
        if (company == null) {
            return null;
        }
        String trimmedCompany = company.trim();
        if (!Company.isValidCompany(trimmedCompany)) {
            throw new ParseException(Company.MESSAGE_CONSTRAINTS);
        }
        return new Company(trimmedCompany);
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
     * Parses a {@code String name} into a {@code NameContainsKeywordsPredicate}
     *
     * @throws ParseException If the name is invalid.
     */
    public static NameContainsKeywordsPredicate parseNamePredicate(String name) throws ParseException {
        requireNonNull(name);
        if (!Name.isValidName(name)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new NameContainsKeywordsPredicate(splitAndTrim(name));
    }

    /**
     * Parses a {@code String phone} into a {@code PhoneContainsKeywordsPredicate}
     *
     * @throws ParseException If the phone is invalid.
     */
    public static PhoneContainsKeywordsPredicate parsePhonePredicate(String phone) throws ParseException {
        requireNonNull(phone);
        if (!Phone.isValidPhone(phone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new PhoneContainsKeywordsPredicate(splitAndTrim(phone));
    }

    /**
     * Parses a {@code String email} into a {@code EmailContainsKeywordsPredicate}
     *
     * @throws ParseException If the email is invalid.
     */
    public static EmailContainsKeywordsPredicate parseEmailPredicate(String email) throws ParseException {
        requireNonNull(email);
        if (!Email.isValidEmail(email)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new EmailContainsKeywordsPredicate(splitAndTrim(email));
    }

    /**
     * Parses a {@code String address} into a {@code AddressContainsKeywordsPredicate}
     *
     * @throws ParseException If the address is invalid.
     */
    public static AddressContainsKeywordsPredicate parseAddressPredicate(String address) throws ParseException {
        requireNonNull(address);
        if (!Address.isValidAddress(address)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new AddressContainsKeywordsPredicate(splitAndTrim(address));
    }

    /**
     * Parses a {@code String rsvp} into a {@code RsvpContainsKeywordsPredicate}
     *
     * @throws ParseException If the rsvp is invalid.
     */
    public static RsvpContainsKeywordsPredicate parseRsvpPredicate(String rsvp) throws ParseException {
        requireNonNull(rsvp);
        if (!Rsvp.isValidRsvp(rsvp)) {
            throw new ParseException(Rsvp.MESSAGE_CONSTRAINTS);
        }
        return new RsvpContainsKeywordsPredicate(splitAndTrim(rsvp));
    }

    /**
     * Parses a {@code String company} into a {@code CompanyContainsKeywordsPredicate}
     *
     * @throws ParseException If the company is invalid.
     */
    public static CompanyContainsKeywordsPredicate parseCompanyPredicate(String company) throws ParseException {
        requireNonNull(company);
        if (!Company.isValidCompany(company)) {
            throw new ParseException(Company.MESSAGE_CONSTRAINTS);
        }
        return new CompanyContainsKeywordsPredicate(splitAndTrim(company));
    }

    /**
     * Parses a list of {@code String} representing tags and returns a {@code TagContainsKeywordsPredicate}
     *
     * @param tags The input list of strings to be parsed and validated as tags.
     * @return A {@code TagContainsKeywordsPredicate} that matches the given tag parts.
     * @throws ParseException If any tag is invalid.
     */
    public static TagContainsKeywordsPredicate parseTagPredicate(List<String> tags) throws ParseException {
        requireNonNull(tags);
        List<String> tagParts = tags.stream()
                .flatMap(tag -> Arrays.stream(tag.split(" ")))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
        if (tagParts.stream().anyMatch(part -> !Tag.isValidTagName(part))) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new TagContainsKeywordsPredicate(tagParts);
    }

    /**
     * Splits a string by spaces and trims each element.
     *
     * @param inputString The input string to be split and trimmed.
     * @return A {@code List<String>} containing the trimmed and non-empty elements.
     */
    private static List<String> splitAndTrim(String inputString) {
        return Arrays.stream(inputString.split(" "))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
}
