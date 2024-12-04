package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Messages;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.From;
import seedu.address.model.appointment.To;
import seedu.address.model.listing.Address;
import seedu.address.model.listing.Area;
import seedu.address.model.listing.Price;
import seedu.address.model.listing.Region;
import seedu.address.model.name.Name;
import seedu.address.model.person.Email;
import seedu.address.model.person.Phone;
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
     * Parses {@code oneBasedIndex} and returns an {@code Index} object.
     * If parsing fails, throws a {@code ParseException} with a formatted message including the provided usage message.
     *
     * @param oneBasedIndex the one-based index as a string
     * @param usageMessage the usage message to include in the exception if parsing fails
     * @return the parsed {@code Index} object
     * @throws ParseException if the index cannot be parsed as a valid integer
     */
    public static Index parseIndexWithInvalidCommandFormatMessage(String oneBasedIndex,
            String usageMessage) throws ParseException {
        try {
            return parseIndex(oneBasedIndex);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    usageMessage));
        }
    }

    /**
     * Parses {@code oneBasedIndex} and returns an {@code Index} object for a listing.
     * Throws a {@code ParseException} if the index is not a non-zero unsigned integer.
     *
     * @param indexOneBased the one-based index as a string
     * @return the parsed {@code Index} object
     * @throws ParseException if the index is not a non-zero unsigned integer
     */
    public static Index getListingIndex(String indexOneBased) throws ParseException {
        if (!StringUtil.isNonZeroUnsignedInteger(indexOneBased)) {
            throw new ParseException(Messages.MESSAGE_INVALID_LISTING_DISPLAYED_INDEX);
        }
        return ParserUtil.parseIndex(indexOneBased);
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
     * Parses {@code Collection<String> names} into a {@code Set<Name>}.
     */
    public static Set<Name> parseNames(Collection<String> names) throws ParseException {
        requireNonNull(names);
        final Set<Name> nameSet = new HashSet<>();
        for (String n : names) {
            nameSet.add(parseName(n));
        }
        return nameSet;
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
     * Parses a {@code String date} into a {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        try {
            return new Date(trimmedDate);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }
    }

    /**
     * Parses a {@code String time} into a {@code From} start time.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code time} is invalid.
     */
    public static From parseFrom(String time) throws ParseException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        try {
            return new From(trimmedTime);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }
    }

    /**
     * Parses a {@code String time} into a {@code To} end time.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code time} is invalid.
     */
    public static To parseTo(String time) throws ParseException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        try {
            return new To(trimmedTime);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }
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
     * Parses {@code String price} into a {@code Price}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Price parsePrice(String price) throws ParseException {
        requireNonNull(price);
        String priceTrimmed = price.trim();
        if (!Price.isValidPrice(priceTrimmed)) {
            throw new ParseException(Price.MESSAGE_CONSTRAINTS);
        }
        BigDecimal decimalPrice = new BigDecimal(priceTrimmed);
        return new Price(priceTrimmed, decimalPrice);
    }

    /**
     * Parses {@code String area} into a {@code Area}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Area parseArea(String area) throws ParseException {
        requireNonNull(area);
        String areaTrimmed = area.trim();
        if (!Area.isValidArea(areaTrimmed)) {
            throw new ParseException(Area.MESSAGE_CONSTRAINTS);
        }
        return new Area(areaTrimmed);
    }

    /**
     * Parses {@code String region} into a {@code Area}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Region parseRegion(String region) throws ParseException {
        requireNonNull(region);
        String normalizedInput = region.trim().toUpperCase().replace(" ", "");
        for (Region r : Region.values()) {
            if (r.name().equals(normalizedInput)) {
                return r;
            }
        }
        throw new ParseException("Invalid region: " + region);
    }
}
