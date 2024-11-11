package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_CRITERIA_FORMAT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Age;
import seedu.address.model.person.Email;
import seedu.address.model.person.Income;
import seedu.address.model.person.Name;
import seedu.address.model.person.Notes;
import seedu.address.model.person.Phone;
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
        // Remove consecutive whitespaces
        trimmedName = trimmedName.replaceAll("\\s+", " ");
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
        // Remove all whitespaces
        trimmedPhone = trimmedPhone.replaceAll("\\s+", "");
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
        // Remove consecutive whitespaces
        trimmedAddress = trimmedAddress.replaceAll("\\s+", " ");
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String notes} into a {@code Notes}.
    * Leading and trailing whitespaces will be trimmed.
    *
    * @throws ParseException if the given {@code notes} is invalid.
    */
    public static Notes parseNotes(String notes) throws ParseException {
        requireNonNull(notes);
        String trimmedNotes = notes.trim();
        if (!Notes.isValidNotes(trimmedNotes)) {
            throw new ParseException(Notes.MESSAGE_CONSTRAINTS);
        }
        return new Notes(trimmedNotes);
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
     * Parses {@code String criteria} into a {@code List<String>}.
     */
    public static List<String> parseCriteria(String criteria) throws ParseException {
        requireNonNull(criteria);
        final List<String> criteriaSet = new ArrayList<>();
        for (String criteriaString : criteria.split(" ")) {
            if (!criteriaString.isEmpty()) {
                criteriaSet.add(criteriaString);
            }
        }
        return criteriaSet;
    }

    /**
     * Parses the given {@code String} of age criteria and returns a list of valid age criteria.
     * A valid age criteria can only contain "<", ">", or numbers.
     * If it contains "<" or ">", there must only be a single instance of either of them,
     * and only as the first character. It cannot contain both.
     *
     * @param ageCriteria The string of age criteria to parse.
     * @return A list of valid age criteria.
     * @throws ParseException if any of the age criteria do not conform to the expected format.
     */
    public static List<String> parseAgeCriteria(String ageCriteria) throws ParseException {
        requireNonNull(ageCriteria);
        final List<String> ageCriteriaSet = new ArrayList<>();
        for (String ageCriteriaString : ageCriteria.split(" ")) {
            if (ageCriteriaString.isEmpty()) {
                continue;
            }
            if (ageCriteriaString.matches("^[<>]?\\d+$")) {
                ageCriteriaSet.add(ageCriteriaString);
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_CRITERIA_FORMAT,
                    "Age criteria can only contain \"<\", \">\", or numbers. "
                    + "If String contains \"<\" or \">\", there must only be a single instance of either of them, "
                    + "and only as the first character. It cannot contain both."
                ));
            }
        }
        return ageCriteriaSet;
    }

    /**
     * Parses a {@code String phoneCriteria} into a {@code List<String>} of phone criteria.
     * The phone criteria string is split by spaces, and each part is validated to match the pattern
     * of an optional leading '<' or '>' followed by one or more digits.
     *
     * @param phoneCriteria The string containing phone criteria to be parsed.
     * @return A list of valid phone criteria strings.
     * @throws ParseException If any part of the phone criteria string does not match the expected pattern.
     */
    public static List<String> parsePhoneCriteria(String phoneCriteria) throws ParseException {
        requireNonNull(phoneCriteria);
        final List<String> phoneCriteriaSet = new ArrayList<>();
        for (String phoneCriteriaString : phoneCriteria.split(" ")) {
            if (phoneCriteriaString.isEmpty()) {
                continue;
            }
            if (phoneCriteriaString.matches("\\+?[\\d\\-]*")) {
                phoneCriteriaSet.add(phoneCriteriaString);
            } else {
                throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
            }
        }
        return phoneCriteriaSet;
    }

    /**
     * Parses a {@code String incomeCriteria} into a {@code List<String>} of income criteria.
     * The income criteria string is split by spaces, and each part is validated to match the pattern
     * of valid income levels or their substrings.
     *
     * @param incomeCriteria The string containing income criteria to be parsed.
     * @return A list of valid income criteria strings.
     * @throws ParseException If any part of the income criteria string does not match the expected pattern.
     */
    public static List<String> parseIncomeCriteria(String incomeCriteria) throws ParseException {
        requireNonNull(incomeCriteria);
        final List<String> incomeCriteriaSet = new ArrayList<>();
        for (String incomeCriteriaString : incomeCriteria.split(" ")) {
            if (incomeCriteriaString.isEmpty()) {
                continue;
            }
            if (Income.isValidIncome(incomeCriteriaString)) {
                incomeCriteriaSet.add(incomeCriteriaString);
            } else {
                throw new ParseException(Income.MESSAGE_CONSTRAINTS);
            }
        }
        return incomeCriteriaSet;
    }

    /**
     * Parses a {@code String income} into an {@code Income}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code income} is invalid.
     */
    public static Income parseIncome(String income) throws ParseException {
        requireNonNull(income);
        String trimmedIncome = income.trim().toLowerCase();
        if (!Income.isValidIncome(trimmedIncome)) {
            throw new ParseException(Income.MESSAGE_CONSTRAINTS);
        }
        return new Income(trimmedIncome);
    }

    /**
     * Parses a {@code String age} into an {@code Age}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code age} is invalid.
     */
    public static Age parseAge(String age) throws ParseException {
        requireNonNull(age);
        String trimmedAge = age.trim();
        if (!Age.isValidAge(trimmedAge)) {
            throw new ParseException(Age.MESSAGE_CONSTRAINTS);
        }
        return new Age(trimmedAge);
    }
}
