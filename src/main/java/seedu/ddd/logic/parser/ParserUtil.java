package seedu.ddd.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.ddd.commons.core.index.Index;
import seedu.ddd.commons.util.StringUtil;
import seedu.ddd.logic.parser.exceptions.ParseException;
import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.client.Date;
import seedu.ddd.model.contact.common.Address;
import seedu.ddd.model.contact.common.Email;
import seedu.ddd.model.contact.common.Id;
import seedu.ddd.model.contact.common.Name;
import seedu.ddd.model.contact.common.Phone;
import seedu.ddd.model.contact.vendor.Service;
import seedu.ddd.model.contact.vendor.Vendor;
import seedu.ddd.model.event.Description;
import seedu.ddd.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    public static final String MESSAGE_INVALID_ID = "Id is not a non-zero unsigned integer.";

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
     * Parses a {@code String service} into a {@code Service}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code service} is invalid.
     */
    public static Service parseService(String service) throws ParseException {
        requireNonNull(service);
        String trimmedService = service.trim();
        if (!Service.isValidService(trimmedService)) {
            throw new ParseException(Service.MESSAGE_CONSTRAINTS);
        }
        return new Service(service);
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
        if (!Date.isValidDate(trimmedDate)) {
            throw new ParseException(Date.MESSAGE_CONSTRAINTS);
        }
        return new Date(trimmedDate);
    }

    /**
     * Parses a {@code String description} into a {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static Description parseDescription(String desc) throws ParseException {
        requireNonNull(desc);
        String trimmedDesc = desc.trim();
        if (!Description.isValidDescription(trimmedDesc)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(trimmedDesc);
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
     * Parses a {@code String Id} into a {@code Id}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code id} is invalid.
     */
    public static Id parseId(String id) throws ParseException {
        requireNonNull(id);
        if (!Id.isValidId(id)) {
            throw new ParseException(Id.MESSAGE_CONSTRAINTS);
        }
        return new Id(id);
    }

    /**
     * Parses {@code Collection<String> clientIds} into a {@code Set<Client>}.
     */
    public static Set<Id> parseIds(Collection<String> ids) throws ParseException {
        requireNonNull(ids);
        final Set<Id> idSet = new HashSet<>();
        for (String id : ids) {
            idSet.add(parseId(id));
        }
        return idSet;
    }
}
