package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.StringUtil.capitalizeFirstLetter;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Hours;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Subject;
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
     * Parses {@code args} into an {@code Index} array and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer)
     *     or if the format was wrong.
     */
    public static Index[] parseIndices(String args) throws ParseException {
        String trimmedIndices = args.trim();
        String[] parts = trimmedIndices.split(" ");
        if (parts.length != 2) {
            throw new ParseException("no");
        }
        return new Index[]{parseIndex(parts[0]), parseIndex(parts[1])};
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
     * Parses a {@code String subject} into a {@code Subject} object.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param subject The subject name to be parsed.
     * @return A {@code Subject} object representing the parsed subject name.
     * @throws ParseException if the given {@code subject} is invalid.
     */
    public static Subject parseSubject(String subject) throws ParseException {
        requireNonNull(subject);
        String trimmedSubject = subject.trim();
        if (!Subject.isValidSubject(subject)) {
            throw new ParseException(Subject.MESSAGE_CONSTRAINTS);
        }
        return new Subject(capitalizeFirstLetter(trimmedSubject.toLowerCase()));
    }



    /**
     * Parses a collection of subject names into a {@code Set<Subject>}.
     * Each subject name in the collection will be parsed individually.
     *
     * @param subjects A collection of subject names to be parsed.
     * @return A {@code Set<Subject>} containing all the parsed subject names.
     * @throws ParseException if any of the given {@code subjects} are invalid.
     */
    public static Set<Subject> parseSubjects(Collection<String> subjects) throws ParseException {

        final Set<Subject> subjectSet = new HashSet<>();
        for (String subjectName : subjects) {
            subjectSet.add(parseSubject(subjectName));
        }
        return subjectSet;
    }

    /**
     * Parses a {@code String filepath}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code filepath} is invalid.
     */
    public static String parseFilepath(String filepath) throws ParseException {
        requireNonNull(filepath);
        String trimmedFilepath = filepath.trim();
        String transformedFilePath = trimmedFilepath.replaceFirst("^~", System.getProperty("user.home"));

        if (!new File(transformedFilePath).isFile()) {
            throw new ParseException("File does not exist!");
        }
        return transformedFilePath;
    }
}
