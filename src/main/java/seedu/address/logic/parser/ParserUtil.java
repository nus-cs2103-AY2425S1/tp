package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.EmergencyContact;
import seedu.address.model.person.LessonTime;
import seedu.address.model.person.Level;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Subject;
import seedu.address.model.person.task.Task;
import seedu.address.model.person.task.TaskDeadline;
import seedu.address.model.person.task.TaskDescription;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

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
        String trimmedName = String.join(" ", name.trim().split("\\s+"));
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
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static EmergencyContact parseEmergencyContact(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new EmergencyContact(trimmedPhone);
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
     * Parses a {@code String note} into an {@code Note}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Note parseNote(String note) {
        requireNonNull(note);
        String trimmedNote = note.trim();

        return new Note(trimmedNote);
    }


    /**
     * Parses a {@code String level} into a {@code Level}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code level} is invalid.
     */
    public static Level parseLevel(String level) throws ParseException {
        requireNonNull(level);
        String trimmedLevel = level.trim();
        if (!Level.isValidLevelName(trimmedLevel)) {
            throw new ParseException(Level.MESSAGE_CONSTRAINTS);
        }
        return new Level(trimmedLevel);
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
        if (!Subject.isValidSubjectName(trimmedSubject)) {
            throw new ParseException(Subject.MESSAGE_CONSTRAINTS);
        }
        return new Subject(trimmedSubject);
    }

    /**
     * Parses {@code Collection<String> subjects} into a {@code Set<Subject>}.
     */
    public static Set<Subject> parseSubjects(Collection<String> subjects) throws ParseException {
        requireNonNull(subjects);
        final Set<Subject> subjectSet = new HashSet<>();
        for (String subjectName : subjects) {
            subjectSet.add(parseSubject(subjectName));
        }
        return subjectSet;
    }

    /**
     * Parses a {@code String subject} into a {@code Subject}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code subject} is invalid.
     */
    public static Subject parseSubjectByLevel(Level level, String subject) throws ParseException {
        requireNonNull(subject);
        String trimmedSubject = subject.trim();
        if (!Subject.isValidSubjectNameByLevel(level, trimmedSubject)) {
            throw new ParseException(Subject.MESSAGE_CONSTRAINTS);
        }
        return new Subject(trimmedSubject);
    }

    /**
     * Parses {@code Collection<String> subjects} into a {@code Set<Subject>}.
     */
    public static Set<Subject> parseSubjectsByLevel(Level level, Collection<String> subjects) throws ParseException {
        requireNonNull(level);
        requireNonNull(subjects);
        final Set<Subject> subjectSet = new HashSet<>();
        for (String subjectName : subjects) {
            subjectSet.add(parseSubjectByLevel(level, subjectName));
        }
        return subjectSet;
    }

    /**
     * Parses a {@code String desc} into an {@code TaskDescription}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code desc} is invalid.
     */
    public static TaskDescription parseTaskDescription(String desc) throws ParseException {
        requireNonNull(desc);
        String trimmedTDesc = desc.trim();
        if (!TaskDescription.isValidTaskDescription(trimmedTDesc)) {
            throw new ParseException(TaskDescription.MESSAGE_CONSTRAINTS);
        }
        return new TaskDescription(trimmedTDesc);
    }

    /**
     * Parses a {@code String deadline} into an {@code TaskDeadline}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code deadline} is invalid.
     */
    public static TaskDeadline parseTaskDeadline(String deadline) throws ParseException {
        requireNonNull(deadline);
        String trimmedTDeadline = deadline.trim();
        if (!TaskDeadline.isValidTaskDeadline(trimmedTDeadline)) {
            throw new ParseException(TaskDeadline.MESSAGE_CONSTRAINTS);
        }
        return new TaskDeadline(trimmedTDeadline);
    }

    /**
     * Parses a {@code String desc} and {@code String deadline} into a {@code Task}.
     */
    public static Task parseTask(String desc, String deadline) throws ParseException {
        requireNonNull(desc);
        requireNonNull(deadline);
        return new Task(parseTaskDescription(desc), parseTaskDeadline(deadline));
    }

    /**
     * Parses a {@code String lt} into a {@code LessonTime}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static LessonTime parseLessonTime(String lt) throws ParseException {
        requireNonNull(lt);
        String trimmedLt = lt.trim();
        if (!LessonTime.isValidLessonTime(trimmedLt)) {
            throw new ParseException(LessonTime.MESSAGE_CONSTRAINTS);
        }
        return new LessonTime(trimmedLt);
    }

    /**
     * Parses {@code Collection<String> lts} into a {@code Set<LessonTime>}.
     */
    public static Set<LessonTime> parseLessonTimes(Collection<String> lts) throws ParseException {
        requireNonNull(lts);
        final Set<LessonTime> lessonTimes = new HashSet<>();
        for (String lt : lts) {
            lessonTimes.add(parseLessonTime(lt));
        }
        return lessonTimes;
    }
}
