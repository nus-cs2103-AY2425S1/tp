package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Remark;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index should be a non-zero unsigned integer"
            + " and cannot be blank.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.replaceAll("\\s+", "");
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
        if (name.length() > Name.MAXIMUM_NAME_LENGTH) {
            throw new ParseException(String.format(Name.MESSAGE_NAME_TOO_LONG, Name.MAXIMUM_NAME_LENGTH));
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
        if (trimmedPhone.length() > Phone.MAXIMUM_LENGTH) {
            throw new ParseException(String.format(Phone.MESSAGE_PHONE_TOO_LONG, Phone.MAXIMUM_LENGTH));
        }
        return new Phone(trimmedPhone);
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
        if (trimmedEmail.length() > Email.MAXIMUM_EMAIL_LENGTH) {
            throw new ParseException(String.format(Email.MESSAGE_EMAIL_TOO_LONG, Email.MAXIMUM_EMAIL_LENGTH));
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
        if (trimmedTag.length() > Tag.MAXIMUM_NAME_LENGTH) {
            throw new ParseException(String.format(Tag.MESSAGE_NAME_TOO_LONG, Tag.MAXIMUM_NAME_LENGTH));
        }
        return new Tag(trimmedTag);
    }
    /**
     * Parses a {@code String assignment name} into a {@code AssignmentName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code assignment name} is invalid.
     */
    public static AssignmentName parseAssignmentName(String assignmentName) throws ParseException {
        requireNonNull(assignmentName);
        String trimmedAssignmentName = assignmentName.trim();
        if (!Name.isValidName(trimmedAssignmentName)) {
            throw new ParseException(AssignmentName.MESSAGE_CONSTRAINTS);
        }
        if (trimmedAssignmentName.length() > AssignmentName.MAXIMUM_NAME_LENGTH) {
            throw new ParseException(String.format(AssignmentName.MESSAGE_NAME_TOO_LONG,
                    AssignmentName.MAXIMUM_NAME_LENGTH));
        }
        return new AssignmentName(assignmentName);
    }
    /**
     * Parses a {@code String max score} into a {@code int}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code max score} is invalid.
     */
    public static int parseMaxScore(String maxScore) throws ParseException {
        requireNonNull(maxScore);
        String trimmedMaxScore = maxScore.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedMaxScore)) {
            throw new ParseException(Assignment.MAX_SCORE_MESSAGE_CONSTRAINTS);
        }
        return Integer.parseInt(trimmedMaxScore);
    }
    /**
     * Parses a {@code String score} into a {@code int}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code score} is invalid.
     */
    public static int parseScore(String score) throws ParseException {
        requireNonNull(score);
        String trimmedScore = score.trim();
        if (!StringUtil.isUnsignedInteger(trimmedScore)) {
            throw new ParseException(Assignment.SCORE_MESSAGE_CONSTRAINTS);
        }
        return Integer.parseInt(trimmedScore);
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
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Remark parseRemark(String remark) throws ParseException {
        requireNonNull(remark);
        String trimmedRemark = remark.trim();
        if (!Remark.isValidRemarkName(trimmedRemark)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        if (trimmedRemark.length() > Remark.MAXIMUM_REMARK_LENGTH) {
            throw new ParseException(String.format(Remark.MESSAGE_REMARK_TOO_LONG, Remark.MAXIMUM_REMARK_LENGTH));
        }
        return new Remark(trimmedRemark);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
