package keycontacts.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import keycontacts.commons.core.index.Index;
import keycontacts.commons.util.StringUtil;
import keycontacts.logic.parser.exceptions.ParseException;
import keycontacts.model.lesson.Date;
import keycontacts.model.lesson.Day;
import keycontacts.model.lesson.Time;
import keycontacts.model.pianopiece.PianoPiece;
import keycontacts.model.student.Address;
import keycontacts.model.student.GradeLevel;
import keycontacts.model.student.Group;
import keycontacts.model.student.Name;
import keycontacts.model.student.Phone;

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
     * Parses a {@code String gradeLevel} into a {@code GradeLevel}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code gradeLevel} is invalid.
     */
    public static GradeLevel parseGradeLevel(String gradeLevel) throws ParseException {
        requireNonNull(gradeLevel);
        String trimmedGradeLevel = gradeLevel.trim();
        if (!GradeLevel.isValidGradeLevel(trimmedGradeLevel)) {
            throw new ParseException(GradeLevel.MESSAGE_CONSTRAINTS);
        }

        return new GradeLevel(trimmedGradeLevel);
    }

    /**
     * Parses {@code Collection<String> pianoPieces} into a {@code Set<PianoPiece>}.
     */
    public static Set<PianoPiece> parsePianoPieces(Collection<String> pianoPieces) throws ParseException {
        requireNonNull(pianoPieces);
        final Set<PianoPiece> pianoPieceSet = new HashSet<>();
        for (String pianoPieceName : pianoPieces) {
            pianoPieceSet.add(parsePianoPiece(pianoPieceName));
        }
        return pianoPieceSet;
    }


    /**
     * Parses a {@code String pianoPiece} into a {@code PianoPiece}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code pianoPiece} is invalid.
     */
    public static PianoPiece parsePianoPiece(String pianoPiece) throws ParseException {
        requireNonNull(pianoPiece);
        String trimmedPianoPiece = pianoPiece.trim();
        if (!PianoPiece.isValidPianoPieceName(trimmedPianoPiece)) {
            throw new ParseException(PianoPiece.MESSAGE_CONSTRAINTS);
        }
        return new PianoPiece(trimmedPianoPiece);
    }

    /**
     * Parses a {@code String day} into a {@code Day}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code day} is invalid.
     */
    public static Day parseDay(String day) throws ParseException {
        requireNonNull(day);
        String trimmedDay = day.trim();
        if (!Day.isValidDay(trimmedDay)) {
            throw new ParseException(Day.MESSAGE_CONSTRAINTS);
        }
        return new Day(trimmedDay);
    }

    /**
     * Parses a {@code String time} into a {@code Time}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code time} is invalid.
     */
    public static Time parseTime(String time) throws ParseException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        if (!Time.isValidTime(trimmedTime)) {
            throw new ParseException(Time.MESSAGE_CONSTRAINTS);
        }
        return new Time(trimmedTime);
    }

    /**
     * Parses {@code String date} into a {@code Date}.
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!Date.isValidDate(trimmedDate)) {
            throw new ParseException(Date.MESSAGE_CONSTRAINTS);
        }
        return new Date(trimmedDate);
    }

    /**
     * Parses {@code String group} into a {@code Group}
     */
    public static Group parseGroup(String group) throws ParseException {
        requireNonNull(group);
        String trimmedGroup = group.trim();
        if (!Group.isValidGroupName(trimmedGroup)) {
            throw new ParseException(Group.MESSAGE_CONSTRAINTS);
        }
        return new Group(trimmedGroup);
    }
}
