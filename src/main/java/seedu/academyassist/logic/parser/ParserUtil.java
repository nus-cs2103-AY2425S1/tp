package seedu.academyassist.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.academyassist.commons.core.index.Index;
import seedu.academyassist.commons.util.StringUtil;
import seedu.academyassist.logic.parser.exceptions.ParseException;
import seedu.academyassist.model.filter.FilterParam;
import seedu.academyassist.model.person.Address;
import seedu.academyassist.model.person.Email;
import seedu.academyassist.model.person.Ic;
import seedu.academyassist.model.person.Name;
import seedu.academyassist.model.person.Phone;
import seedu.academyassist.model.person.StudentId;
import seedu.academyassist.model.person.Subject;
import seedu.academyassist.model.person.YearGroup;
import seedu.academyassist.model.sort.SortParam;

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
     * Parses a {@code String ic} into a {@code Ic}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code ic} is invalid.
     */
    public static Ic parseIc(String ic) throws ParseException {
        requireNonNull(ic);
        String trimmedIc = ic.trim();
        if (!Ic.isValidIc(trimmedIc)) {
            throw new ParseException(Ic.MESSAGE_CONSTRAINTS);
        }
        return new Ic(trimmedIc);
    }

    /**
     * Parses a {@code String subject} into a {@code Subject}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code subject} is invalid.
     */
    public static Subject parseSubject(String subject) throws ParseException {
        requireNonNull(subject);
        String trimmedSubject = subject.trim();
        if (!Subject.isValidSubject(trimmedSubject)) {
            throw new ParseException(Subject.MESSAGE_CONSTRAINTS);
        }
        return new Subject(trimmedSubject.toUpperCase());
    }

    /**
     * Parses {@code Collection<String> subjects} into a {@code Set<Subject>}.
     */
    public static Set<Subject> parseSubjects(Collection<String> subjects) throws ParseException {
        requireNonNull(subjects);
        if (subjects.isEmpty()) {
            throw new ParseException("Subjects cannot be empty");
        }
        final Set<Subject> subjectSet = new HashSet<>();
        for (String subjectName : subjects) {
            subjectSet.add(parseSubject(subjectName.toUpperCase()));
        }
        return subjectSet;
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
     * Parses a {@code String yearGroup} into a {@code YearGroup}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code yearGroup} is invalid.
     */
    public static YearGroup parseYearGroup(String yearGroup) throws ParseException {
        requireNonNull(yearGroup);
        String trimmedYearGroup = yearGroup.trim();
        if (!YearGroup.isValidYearGroup(trimmedYearGroup)) {
            throw new ParseException(YearGroup.MESSAGE_CONSTRAINTS);
        }
        return new YearGroup(trimmedYearGroup);
    }

    /**
     * Parses a {@code String studentId} into a {@code StudentId}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code studentId} is invalid.
     */
    public static StudentId parseStudentId(String studentId) throws ParseException {
        requireNonNull(studentId);
        String trimmedStudentId = studentId.trim();
        if (!StudentId.isValidStudentId(trimmedStudentId)) {
            throw new ParseException(StudentId.MESSAGE_CONSTRAINTS);
        }
        return new StudentId(trimmedStudentId);
    }

    /**
     * Parses {@code String sortParam} into a {@code SortParam}.
     */
    public static SortParam parseSortCommandParam(String sortParam) throws ParseException {
        requireNonNull(sortParam);
        String trimmedSortParam = sortParam.trim();
        if (!SortParam.isValidSortParam(trimmedSortParam)) {
            throw new ParseException(SortParam.MESSAGE_CONSTRAINTS);
        }

        return new SortParam(trimmedSortParam);
    }

    /**
     * Parses {@code String filterParam} into a {@code FilterParam}.
     */
    public static FilterParam parseFilterCommandParam(String filterParam) throws ParseException {
        requireNonNull(filterParam);
        String trimmedSortParam = filterParam.trim();
        if (!FilterParam.isValidFilterParam(trimmedSortParam)) {
            throw new ParseException(FilterParam.MESSAGE_CONSTRAINTS);
        }

        return new FilterParam(trimmedSortParam);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
