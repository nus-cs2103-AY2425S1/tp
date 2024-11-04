package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Schedule;
import seedu.address.model.person.SocialMedia;
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
        if (!Name.isValidName(trimmedName) || trimmedName.length() > Name.MAX_LENGTH) {
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
        if (!Email.isValidEmail(trimmedEmail) || trimmedEmail.length() > Email.MAX_LENGTH) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String scheduleName},{@code String scheduleDate}, and {@code String scheduleTime}
     * into an {@code Schedule}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code scheduleName}, {@code scheduleDate}, or {@code scheduleTime}
     *      is invalid.
     */
    public static Schedule parseSchedule(String scheduleName, String scheduleDate, String scheduleTime)
            throws ParseException {
        requireAllNonNull(scheduleName, scheduleDate, scheduleTime);
        String trimmedScheduleName = scheduleName.trim();
        String trimmedScheduleDate = scheduleDate.trim();
        String trimmedScheduleTime = scheduleTime.trim();
        if (!Schedule.isValidName(trimmedScheduleName)) {
            throw new ParseException(Schedule.SCHEDULE_NAME_CONSTRAINTS);
        }

        if (!Schedule.isValidDate(trimmedScheduleDate)) {
            throw new ParseException(Schedule.DATE_CONSTRAINTS);
        }

        if (!Schedule.isValidTime(trimmedScheduleTime)) {
            throw new ParseException(Schedule.TIME_CONSTRAINTS);
        }
        return new Schedule(trimmedScheduleName, trimmedScheduleDate, trimmedScheduleTime);
    }

    /**
     * Parses a {@code String scheduleName},{@code String scheduleDate}, and {@code String scheduleTime}
     * into an {@code Schedule}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code scheduleName}, {@code scheduleDate}, or {@code scheduleTime}
     *      is invalid.
     */
    public static SocialMedia parseSocialMedia(String... socialMediaHandles)
            throws ParseException {
        SocialMedia.Platform[] platforms = {
            SocialMedia.Platform.CAROUSELL,
            SocialMedia.Platform.FACEBOOK,
            SocialMedia.Platform.INSTAGRAM
        };
        for (int i = 0; i < 3; i++) {
            if (socialMediaHandles[i].isEmpty()) {
                continue;
            }
            String trimmedHandle = socialMediaHandles[i].trim();
            if (!SocialMedia.isValidHandleName(trimmedHandle)) {
                throw new ParseException(SocialMedia.MESSAGE_CONSTRAINTS);
            }
            return new SocialMedia(trimmedHandle, platforms[i]);
        }
        return new SocialMedia(" ", SocialMedia.Platform.UNNAMED);
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
}
