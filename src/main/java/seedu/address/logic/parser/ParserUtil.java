package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.AppParameters;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Age;
import seedu.address.model.person.Detail;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.predicates.AgeContainsKeywordsPredicate;
import seedu.address.model.person.predicates.GenderMatchesKeywordsPredicate;
import seedu.address.model.tag.StudyGroupTag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_FILE_NAME = "File name should not be empty.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it.
     * Leading and trailing whitespaces will be
     * trimmed.
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
     * Parses {@code filePath} into an {@code File} and returns it. Leading
     * and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the specified filePath is invalid (empty string filePath or null filePath).
     */
    public static File parseFilePath(String filePath) throws ParseException {
        try {
            String trimmedFilePath = filePath.trim();
            if (trimmedFilePath.isEmpty()) {
                throw new ParseException(MESSAGE_INVALID_FILE_NAME);
            }
            File exportFile = new File(trimmedFilePath + ".txt");
            int i = 0;
            while (exportFile.exists()) {
                i++;
                exportFile = new File(trimmedFilePath + "(" + i + ").txt");
            }
            return exportFile;
        } catch (NullPointerException ioe) {
            throw new ParseException(MESSAGE_INVALID_FILE_NAME);
        }
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
     * Parses a {@code String gender} into a {@code Gender}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code gender} is invalid.
     */
    public static Gender parseGender(String gender) throws ParseException {
        requireNonNull(gender);
        String trimmedGender = gender.trim();
        if (!Gender.isValidGender(trimmedGender)) {
            throw new ParseException(Gender.MESSAGE_CONSTRAINTS);
        }
        return new Gender(trimmedGender);
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

    /**
     * Parses a {@code String detail} into a {@code Detail}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Detail parseDetail(String detail) {
        requireNonNull(detail);
        String trimmedDetail = detail.trim();
        return new Detail(trimmedDetail);
    }

    /**
     * Parses a {@code String studyGroup} into a {@code StudyGroupTag}.
     * Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code studyGroup} is invalid.
     */
    public static StudyGroupTag parseStudyGroup(String studyGroup) throws ParseException {
        requireNonNull(studyGroup);
        String trimmedStudyGroup = studyGroup.trim();
        if (!StudyGroupTag.isValidStudyGroupName(trimmedStudyGroup)) {
            throw new ParseException(StudyGroupTag.MESSAGE_CONSTRAINTS);
        }
        return new StudyGroupTag(trimmedStudyGroup);
    }

    /**
     * Parses {@code Collection<String> studyGroups} into a
     * {@code Set<StudyGroupTag>}.
     */
    public static Set<StudyGroupTag> parseStudyGroups(Collection<String> studyGroups)
            throws ParseException {
        requireNonNull(studyGroups);
        final Set<StudyGroupTag> studyGroupSet = new HashSet<>();
        for (String tagName : studyGroups) {
            studyGroupSet.add(parseStudyGroup(tagName));
        }
        return studyGroupSet;
    }

    /**
     * Parses input {@code String keywords} into a {@code Set<String>}.
     * Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code studyGroup} is invalid.
     */
    public static Set<String> parseKeywords(String keywords, Prefix prefix)
            throws ParseException {
        requireNonNull(keywords);
        String trimmedKeywords = keywords.trim();
        if (trimmedKeywords.length() == 0) {
            throw new ParseException(String.format(FindCommand.MESSAGE_EMPTY_CRITERIA, prefix));
        }
        return new HashSet<String>(new HashSet<>(Arrays.asList(trimmedKeywords.split("\\s+"))));
    }

    /**
     * Parses input {@code String agekeywords}
     * into a {@code Set<String>}.
     *
     * @throws ParseException if any of the given {@code keywords} is invalid.
     */
    public static Set<String> parseAgeKeywords(String ageKeywords, Prefix prefix)
            throws ParseException {
        Set<String> ageKeywordSet = parseKeywords(ageKeywords, prefix);
        for (String keyword : ageKeywordSet) {
            if (!AgeContainsKeywordsPredicate.isValidInput(keyword)) {
                throw new ParseException(AgeContainsKeywordsPredicate.MESSAGE_CONSTRAINTS);
            }
        }
        return ageKeywordSet;
    }

    /**
     * Parses input {@code String keywords}
     * into a {@code Set<String>}.
     *
     * @throws ParseException if any of the given {@code keywords} is invalid.
     */
    public static Set<String> parseGenderKeywords(String genderKeywords, Prefix prefix)
            throws ParseException {
        Set<String> genderKeywordSet = parseKeywords(genderKeywords, prefix);
        Logger logger = LogsCenter.getLogger(AppParameters.class);
        for (String keyword : genderKeywordSet) {
            logger.warning(keyword);
            if (!GenderMatchesKeywordsPredicate.isValidInput(keyword)) {
                throw new ParseException(GenderMatchesKeywordsPredicate.MESSAGE_CONSTRAINTS);
            }
        }
        return genderKeywordSet;
    }
}
