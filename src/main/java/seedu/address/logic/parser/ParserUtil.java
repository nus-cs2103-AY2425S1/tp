package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
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
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingDate;
import seedu.address.model.wedding.WeddingName;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_DATE = "Date is not in the correct format. "
            + "Please use the format DD/MM/YYYY.";

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
        if (!Name.isWithinCharLimit(trimmedName)) {
            throw new ParseException(Name.MESSAGE_LENGTH_LIMIT);
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
        if (!Address.isWithinCharLimit(trimmedAddress)) {
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
        if (!Email.isWithinCharLimit(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_EMAIL_LENGTH);
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
        if (!Tag.isWithinCharLimit(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CHAR_LIMIT);
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
     * Parses a {@code String weddingName} into a {@code WeddingName}.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if given weddingName is invalid
     */
    public static WeddingName parseWeddingName(String weddingName) throws ParseException {
        requireNonNull(weddingName);
        String trimmedName = weddingName.trim();
        if (!WeddingName.isValidWeddingName(trimmedName)) {
            throw new ParseException(WeddingName.MESSAGE_CONSTRAINTS);
        }
        if (!WeddingName.isWithinCharLimit(trimmedName)) {
            throw new ParseException(WeddingName.MESSAGE_LENGTH_LIMIT);
        }
        return new WeddingName(trimmedName);
    }

    /**
     * Parses a {@code String date} into a {@code WeddingDate}.
     * Leading and trailing whitespaces will be trimmed.
     * The expected format is "dd/MM/yyyy".
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static WeddingDate parseWeddingDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate parsedDate = LocalDate.parse(trimmedDate, formatter);
            if (!parsedDate.isAfter(LocalDate.now())) {
                throw new ParseException("Wedding date must be after current date.");
            }
            return new WeddingDate(parsedDate);
        } catch (DateTimeParseException e) {
            throw new ParseException(WeddingDate.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String personIndexString} into a {@code Set<Index>}
     * @param personIndexString
     * @return {@code Set<Index>}
     * @throws ParseException
     */

    public static Set<Index> parsePersonIndexString(String personIndexString) throws ParseException {
        Set<Index> personIndexSet;
        try {
            personIndexSet = Arrays.stream(personIndexString.split("\\s+"))
                    .map(Integer::parseInt)
                    .map(i -> Index.fromOneBased(i))
                    .collect(Collectors.toSet());

        } catch (IllegalArgumentException e) {
            throw new ParseException(Wedding.MESSAGE_CONSTRAINTS);
        }
        String[] indexArray = personIndexString.split("\\s+");
        Set<String> inputIndexSet = new HashSet<>(List.of(indexArray));
        if (inputIndexSet.size() != indexArray.length) {
            throw new ParseException("Duplicate contact indexes specified after " + PREFIX_CONTACT.getPrefix());
        }

        return personIndexSet;
    }

    /**
     * Parses a {@code ArrayList<Person> personArrayList} into a {@code String parsedPersonNames}
     * @param personArrayList
     * @return String
     */
    public static String parsePersonListToString(ArrayList<Person> personArrayList) {
        String parsedPersonNames = personArrayList.stream()
                .map(person -> person.getName().toString())
                .sorted()
                .reduce((name1, name2) -> name1 + ", " + name2)
                .orElse("");

        return parsedPersonNames;
    }
}
