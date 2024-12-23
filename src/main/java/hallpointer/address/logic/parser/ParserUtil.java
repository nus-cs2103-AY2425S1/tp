package hallpointer.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import hallpointer.address.commons.core.index.Index;
import hallpointer.address.commons.util.StringUtil;
import hallpointer.address.logic.parser.exceptions.ParseException;
import hallpointer.address.model.member.Name;
import hallpointer.address.model.member.Room;
import hallpointer.address.model.member.Telegram;
import hallpointer.address.model.point.Point;
import hallpointer.address.model.session.SessionDate;
import hallpointer.address.model.session.SessionName;
import hallpointer.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index must be a non-zero unsigned integer.";

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
     * Parses a {@code String telegram} into a {@code Telegram}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code telegram} is invalid.
     */
    public static Telegram parseTelegram(String telegram) throws ParseException {
        requireNonNull(telegram);
        String trimmedTelegram = telegram.trim();
        if (!Telegram.isValidTelegram(trimmedTelegram)) {
            throw new ParseException(Telegram.MESSAGE_CONSTRAINTS);
        }
        return new Telegram(trimmedTelegram);
    }

    /**
     * Parses a {@code String room} into an {@code Room}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code room} is invalid.
     */
    public static Room parseRoom(String room) throws ParseException {
        requireNonNull(room);
        String trimmedRoom = room.trim();
        if (!Room.isValidRoom(trimmedRoom)) {
            throw new ParseException(Room.MESSAGE_CONSTRAINTS);
        }
        return new Room(trimmedRoom);
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
     *
     * @throws ParseException if any {@code tag} in the {@code tags} collection are invalid.
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
     * Parses a {@code String name} into a {@code SessionName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static SessionName parseSessionName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!SessionName.isValidSessionName(trimmedName)) {
            throw new ParseException(SessionName.MESSAGE_CONSTRAINTS);
        }
        return new SessionName(trimmedName);
    }

    /**
     * Parses a {@code String date} into a {@code SessionDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static SessionDate parseSessionDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!SessionDate.isValidDate(trimmedDate)) {
            throw new ParseException(SessionDate.MESSAGE_CONSTRAINTS);
        }
        return new SessionDate(trimmedDate);
    }

    /**
     * Parses a {@code String points} into an {@code Points}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code points} is invalid.
     */
    public static Point parsePoints(String points) throws ParseException {
        requireNonNull(points);
        String trimmedPoints = points.trim();
        if (!Point.isValidPoints(trimmedPoints)) {
            throw new ParseException(Point.MESSAGE_CONSTRAINTS);
        }
        return new Point(trimmedPoints);
    }

    /**
     * Parses {@code Collection<String> indices} into a {@code Set<Index>}.
     *
     * @throws ParseException if any {@code tag} in the {@code tags} collection are invalid.
     */
    public static Set<Index> parseIndices(Collection<String> indices) throws ParseException {
        requireNonNull(indices);
        final Set<Index> indexSet = new HashSet<>();
        for (String index : indices) {
            String trimmedIndex = index.trim();
            indexSet.add(parseIndex(trimmedIndex));
        }
        return indexSet;
    }
}
