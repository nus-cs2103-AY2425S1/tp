package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.exam.Exam;
import seedu.address.model.person.AbsentDate;
import seedu.address.model.person.AbsentReason;
import seedu.address.model.person.Address;
import seedu.address.model.person.EcName;
import seedu.address.model.person.EcNumber;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.RegisterNumber;
import seedu.address.model.person.Sex;
import seedu.address.model.person.StudentClass;
import seedu.address.model.submission.Submission;
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
     * Parses a {@code String registerNumber} into an {@code RegisterNumber}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code registerNumber} is invalid.
     */
    public static RegisterNumber parseRegisterNumber(String registerNumber) throws ParseException {
        requireNonNull(registerNumber);
        String trimmedRegisterNumber = registerNumber.trim();
        if (!RegisterNumber.isValidRegisterNumber(trimmedRegisterNumber)) {
            throw new ParseException(RegisterNumber.MESSAGE_CONSTRAINTS);
        }
        return new RegisterNumber(trimmedRegisterNumber);
    }

    /**
     * Parses a {@code String sex} into an {@code Sex}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code sex} is invalid.
     */
    public static Sex parseSex(String sex) throws ParseException {
        requireNonNull(sex);
        String trimmedSex = sex.trim();
        if (!Sex.isValidSex(trimmedSex)) {
            throw new ParseException(Sex.MESSAGE_CONSTRAINTS);
        }
        return new Sex(trimmedSex);
    }

    /**
     * Parses a {@code String studentClass} into an {@code Class}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code studentClass} is invalid.
     */
    public static StudentClass parseStudentClass(String studentClass) throws ParseException {
        requireNonNull(studentClass);
        String trimmedStudentClass = studentClass.trim();
        if (!StudentClass.isValidStudentClass(trimmedStudentClass)) {
            throw new ParseException(StudentClass.MESSAGE_CONSTRAINTS);
        }
        return new StudentClass(trimmedStudentClass);
    }

    /**
     * Parses a {@code String ecName} into a {@code EmergencyContactName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code ecName} is invalid.
     */
    public static EcName parseEmergencyContactName(String ecName) throws ParseException {
        requireNonNull(ecName);
        String trimmedEcName = ecName.trim();
        if (!EcName.isValidEcName(trimmedEcName)) {
            throw new ParseException(EcName.MESSAGE_CONSTRAINTS);
        }
        return new EcName(trimmedEcName);
    }

    /**
     * Parses a {@code String ecNumber} into a {@code EcNumber}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code EcNumber} is invalid.
     */
    public static EcNumber parseEcNumber(String ecNumber) throws ParseException {
        requireNonNull(ecNumber);
        String trimmedEmergencyPhone = ecNumber.trim();
        if (!EcNumber.isValidEcNumber(trimmedEmergencyPhone)) {
            throw new ParseException(EcNumber.MESSAGE_CONSTRAINTS);
        }
        return new EcNumber(trimmedEmergencyPhone);
    }

    /**
     * Parses a {@code String submission} into a {@code Submission}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code submission} is invalid.
     */
    public static Submission parseSubmission(String submission) throws ParseException {
        requireNonNull(submission);
        String trimmedSubmission = submission.trim();
        if (!Submission.isValidSubmissionName(trimmedSubmission)) {
            throw new ParseException(Submission.NAME_MESSAGE_CONSTRAINTS);
        }
        return new Submission(trimmedSubmission);
    }

    /**
     * Parses a {@code String submissionStatus} into a {@code String submissionStatus}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code submissionStatus} is invalid.
     */
    public static String parseSubmissionStatus(String submissionStatus) throws ParseException {
        requireNonNull(submissionStatus);
        String trimmedSubmissionStatus = submissionStatus.trim();
        if (!Submission.isValidSubmissionStatus(trimmedSubmissionStatus)) {
            throw new ParseException(Submission.STATUS_MESSAGE_CONSTRAINTS);
        }
        return trimmedSubmissionStatus;
    }

    /**
     * Parses a {@code String exam} into a {@code Exam}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code exam} is invalid.
     */
    public static Exam parseExam(String exam) throws ParseException {
        requireNonNull(exam);
        String trimmedExam = exam.trim();
        if (!Exam.isValidExamName(trimmedExam)) {
            throw new ParseException(Exam.NAME_MESSAGE_CONSTRAINTS);
        }
        return new Exam(trimmedExam);
    }

    /**
     * Parses a {@code String examScore} into a {@code String examScore}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code examScore} is invalid.
     */
    public static String parseExamScore(String examScore) throws ParseException {
        requireNonNull(examScore);
        String trimmedExamScore = examScore.trim();
        if (!Exam.isValidExamScore(trimmedExamScore)) {
            throw new ParseException(Exam.SCORE_MESSAGE_CONSTRAINTS);
        }
        return trimmedExamScore;
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
     * Parses a {@code String absentDate} into a {@code AbsentDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code absentDate} is invalid.
     */
    public static AbsentDate parseAbsentDate(String absentDate) throws ParseException {
        requireNonNull(absentDate);
        String trimmedAbsentDate = absentDate.trim();
        if (!AbsentDate.isValidAbsentDate(trimmedAbsentDate)) {
            throw new ParseException(AbsentDate.MESSAGE_CONSTRAINTS);
        }
        return new AbsentDate(trimmedAbsentDate);
    }

    /**
     * Parses a {@code String absentReason} into a {@code AbsentReason}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code absentReason} is invalid.
     */
    public static AbsentReason parseAbsentReason(String absentReason) throws ParseException {
        requireNonNull(absentReason);
        String trimmedAbsentReason = absentReason.trim();
        if (!AbsentReason.isValidAbsentReason(trimmedAbsentReason)) {
            throw new ParseException(AbsentReason.MESSAGE_CONSTRAINTS);
        }
        return new AbsentReason(trimmedAbsentReason);
    }

}
