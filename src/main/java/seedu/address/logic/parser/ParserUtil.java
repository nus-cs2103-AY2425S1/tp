package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.buyer.Budget;
import seedu.address.model.buyer.Email;
import seedu.address.model.buyer.Phone;
import seedu.address.model.meetup.From;
import seedu.address.model.meetup.Info;
import seedu.address.model.meetup.Name;
import seedu.address.model.meetup.To;
import seedu.address.model.property.AskingPrice;
import seedu.address.model.property.LandlordName;
import seedu.address.model.property.Location;
import seedu.address.model.property.PropertyType;
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
    public static seedu.address.model.buyer.Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!seedu.address.model.buyer.Name.isValidName(trimmedName)) {
            throw new ParseException(seedu.address.model.buyer.Name.MESSAGE_CONSTRAINTS);
        }
        return new seedu.address.model.buyer.Name(trimmedName);
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
     * Parses a {@code String address} into an {@code Budget}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Budget parseBudget(String address) throws ParseException {
        requireNonNull(address);
        String trimmedBudget = address.trim();
        if (!Budget.isValidBudget(trimmedBudget)) {
            throw new ParseException(Budget.MESSAGE_CONSTRAINTS);
        }
        return new Budget(trimmedBudget);
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
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code meetUpName} is invalid.
     */
    public static Name parseMeetUpName(String meetUpName) throws ParseException {
        requireNonNull(meetUpName);
        String trimmedName = meetUpName.trim();
        if (!Name.isValidMeetUpName(trimmedName)) {
            throw new ParseException(seedu.address.model.meetup.Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code meetUpInfo} is invalid.
     */
    public static Info parseMeetUpInfo(String meetUpInfo) throws ParseException {
        requireNonNull(meetUpInfo);
        String trimmedInfo = meetUpInfo.trim();
        if (!Info.isValidMeetUpInfo(trimmedInfo)) {
            throw new ParseException(seedu.address.model.meetup.Info.MESSAGE_CONSTRAINTS);
        }
        return new Info(trimmedInfo);
    }

    /**
     * Parses a {@code String name} into a {@code From}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code meetUpFrom} is invalid.
     */
    public static From parseMeetUpFrom(String meetUpFrom) throws ParseException {
        requireNonNull(meetUpFrom);
        String trimmedFrom = meetUpFrom.trim();
        if (!From.isValidFrom(trimmedFrom)) {
            throw new ParseException(seedu.address.model.meetup.From.MESSAGE_CONSTRAINTS);
        }
        return new From(meetUpFrom);
    }

    /**
     * Parses a {@code String name} into a {@code From}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code meetUpFrom} is invalid.
     */
    public static To parseMeetUpTo(String meetUpTo) throws ParseException {
        requireNonNull(meetUpTo);
        String trimmedTo = meetUpTo.trim();
        if (!To.isValidTo(trimmedTo)) {
            throw new ParseException(seedu.address.model.meetup.To.MESSAGE_CONSTRAINTS);
        }
        return new To(meetUpTo);
    }

    /**
     * Parses a {@code String landlordName} into a {@code LandlordName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code LandlordName} is invalid.
     */
    public static LandlordName parseLandlordName(String landlordName) throws ParseException {
        requireNonNull(landlordName);
        String trimmedName = landlordName.trim();
        if (!LandlordName.isValidName(trimmedName)) {
            throw new ParseException(LandlordName.MESSAGE_CONSTRAINTS);
        }
        return new LandlordName(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Phone} is invalid.
     */
    public static seedu.address.model.property.Phone parsePropertyPhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!seedu.address.model.property.Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(seedu.address.model.property.Phone.MESSAGE_CONSTRAINTS);
        }
        return new seedu.address.model.property.Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String location} into a {@code Location}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code LandlordName} is invalid.
     */
    public static Location parseLocation(String location) throws ParseException {
        requireNonNull(location);
        String trimmedLocation = location.trim();
        if (!Location.isValidLocation(trimmedLocation)) {
            throw new ParseException(Location.MESSAGE_CONSTRAINTS);
        }
        return new Location(trimmedLocation);
    }

    /**
     * Parses a {@code String askingPrice} into a {@code AskingPrice}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code LandlordName} is invalid.
     */
    public static AskingPrice parseAskingPrice(String askingPrice) throws ParseException {
        requireNonNull(askingPrice);
        String trimmedPrice = askingPrice.trim();
        if (!AskingPrice.isValidPrice(trimmedPrice)) {
            throw new ParseException(AskingPrice.MESSAGE_CONSTRAINTS);
        }
        return new AskingPrice(trimmedPrice);
    }

    /**
     * Parses a {@code String propertyType} into a {@code PropertyType}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code LandlordName} is invalid.
     */
    public static PropertyType parsePropertyType(String propertyType) throws ParseException {
        requireNonNull(propertyType);
        String trimmedType = propertyType.trim();
        if (!PropertyType.isValidType(trimmedType)) {
            throw new ParseException(PropertyType.MESSAGE_CONSTRAINTS);
        }
        return new PropertyType(trimmedType);
    }
}
