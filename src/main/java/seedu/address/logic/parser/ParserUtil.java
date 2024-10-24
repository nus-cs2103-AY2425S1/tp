package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Messages;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.StudentId;
import seedu.address.model.person.Tutorial;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_SORT_ORDER = "Sort order is not 1 or -1, or invalid field provided.";
    public static final String WILDCARD = "*"; // == 0 (one-based)
    public static final int WILDCARD_VALUE = 0;

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (trimmedIndex.equals(ParserUtil.WILDCARD)) {
            return Index.fromOneBased(ParserUtil.WILDCARD_VALUE);
        } else if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String sortOrder} into an {@code Integer} (1 or -1).
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code sortOrder} is not "1" or "-1".
     */
    public static Integer parseSortOrder(String sortOrder) throws ParseException {
        requireNonNull(sortOrder);

        String trimmedOrder = sortOrder.trim();
        if (trimmedOrder.equals("1") || trimmedOrder.equals("-1")) {
            return Integer.parseInt(trimmedOrder);
        }
        throw new ParseException(MESSAGE_INVALID_SORT_ORDER);
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
     * Parses a {@code String tutorial} into a {@code Tutorial}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tutorial} is invalid.
     */
    public static Tutorial parseTutorial(String tutorial) throws ParseException {
        requireNonNull(tutorial);
        String trimmedTutorial = tutorial.trim();
        try {
            Integer.parseInt(trimmedTutorial);
        } catch (NumberFormatException e) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    Tutorial.MESSAGE_INVALID_FORMAT));
        }
        if (!Tutorial.isValidTutorial(trimmedTutorial)) {
            throw new ParseException(Tutorial.MESSAGE_CONSTRAINTS);
        }
        return new Tutorial(trimmedTutorial);
    }

    /**
     * Parses a {@code String tutorialInput} into a list of {@code Tutorial} objects.
     * This method supports both single tutorials (e.g., "3") and multiple tutorials, either comma-separated
     * (e.g., "[3,4,5]") or ranges (e.g., "3-5").
     *
     * @param tutorialInput the string representing multiple tutorials or a range
     * @return a list of parsed {@code Tutorial} objects
     * @throws ParseException if the input format is invalid
     */
    public static List<Tutorial> parseTutorials(String tutorialInput) throws ParseException {
        requireNonNull(tutorialInput);
        List<Tutorial> tutorials = new ArrayList<>();

        // Check if it's a list of tutorials within brackets [3,4,5]
        if (tutorialInput.startsWith("[") && tutorialInput.endsWith("]")) {
            String content = tutorialInput.substring(1, tutorialInput.length() - 1).trim();
            String[] parts = content.split(",");
            for (String part : parts) {
                tutorials.add(parseTutorial(part.trim()));
            }
        } else if (tutorialInput.contains("-")) {
            String[] range = tutorialInput.split("-");
            if (range.length != 2) {
                throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                        Tutorial.MESSAGE_INVALID_FORMAT));
            }
            int start = Integer.parseInt(range[0].trim());
            int end = Integer.parseInt(range[1].trim());
            if (start > end) {
                throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                        Tutorial.MESSAGE_INVALID_FORMAT));
            }
            for (int i = start; i <= end; i++) {
                tutorials.add(parseTutorial(Integer.toString(i)));
            }
        } else {
            tutorials.add(parseTutorial(tutorialInput.trim()));
        }

        return tutorials;
    }

    /**
     * Parses a {@code String studentId} into a {@code StudentId}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code student ID} is invalid.
     */
    public static StudentId parseStudentId(String studentId) throws ParseException {
        requireNonNull(studentId);
        String trimmedId = studentId.trim();
        if (!StudentId.isValidStudentId(trimmedId)) {
            throw new ParseException(StudentId.MESSAGE_CONSTRAINTS);
        }
        return new StudentId(trimmedId);
    }
}
