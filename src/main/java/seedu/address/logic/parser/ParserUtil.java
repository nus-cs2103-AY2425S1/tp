package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.game.Game;
import seedu.address.model.game.Role;
import seedu.address.model.game.SkillLevel;
import seedu.address.model.game.Username;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.preferredtime.PreferredTime;
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
     * Parses a {@code String game} into a {@code Game}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code game} is invalid.
     */
    public static Game parseGame(String game) throws ParseException {
        requireNonNull(game);
        String trimmedGame = game.trim();
        if (!Game.isValidGameName(trimmedGame)) {
            throw new ParseException(Game.MESSAGE_CONSTRAINTS);
        }
        return new Game(trimmedGame, new Username("tbc"), new SkillLevel("tbc"), new Role("tbc"), false);
    }

    /**
     * Parses a {@code String username} into a {@code Username}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code game} is invalid.
     */
    public static Username parseUsername(String username) throws ParseException {
        requireNonNull(username);
        String trimmedUsername = username.trim();
        if (!Username.isValidUsername(username)) {
            throw new ParseException(Username.MESSAGE_CONSTRAINTS);
        }
        return new Username(trimmedUsername);
    }

    /**
     * Parses a {@code String skillLevel} into a {@code SkillLevel}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code game} is invalid.
     */
    public static SkillLevel parseSkillLevel(String skillLevel) throws ParseException {
        requireNonNull(skillLevel);
        String trimmedSkillLevel = skillLevel.trim();
        if (!SkillLevel.isValidSkillLevel(skillLevel)) {
            throw new ParseException(Username.MESSAGE_CONSTRAINTS);
        }
        return new SkillLevel(trimmedSkillLevel);
    }

    /**
     * Parses a {@code String Role} into a {@code Role}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code game} is invalid.
     */
    public static Role parseRole(String role) throws ParseException {
        requireNonNull(role);
        String trimmedRole = role.trim();
        if (!Role.isValidRole(role)) {
            throw new ParseException(Username.MESSAGE_CONSTRAINTS);
        }
        return new Role(trimmedRole);
    }


    /**
     * Parses {@code Collection<String> games} into a {@code Set<Game>}.
     */
    public static Map<String, Game> parseGames(Collection<String> games) throws ParseException {
        requireNonNull(games);
        final Map<String, Game> gameMap = new HashMap<>();
        for (String gameName: games) {
            gameMap.put(gameName, parseGame(gameName));
        }
        return gameMap;
    }


    /**
     * Parses a {@code String preferredTime} into a {@code PreferredTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code preferredTime} is invalid.
     */
    public static PreferredTime parsePreferredTime(String preferredTime) throws ParseException {
        requireNonNull(preferredTime);
        String trimmedPreferredTime = preferredTime.trim();
        if (!PreferredTime.isValidPreferredTime(trimmedPreferredTime)) {
            throw new ParseException(PreferredTime.MESSAGE_CONSTRAINTS);
        }
        return new PreferredTime(trimmedPreferredTime);
    }

    /**
     * Parses {@code Collection<String> preferredTimes} into a {@code Set<PreferredTime>}.
     */
    public static Set<PreferredTime> parsePreferredTimes(Collection<String> preferredTimes) throws ParseException {
        requireNonNull(preferredTimes);
        final Set<PreferredTime> preferredTimeSet = new HashSet<>();
        for (String preferredTime : preferredTimes) {
            preferredTimeSet.add(parsePreferredTime(preferredTime));
        }
        return preferredTimeSet;
    }

}
