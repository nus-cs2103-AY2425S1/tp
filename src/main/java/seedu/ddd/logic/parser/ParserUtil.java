package seedu.ddd.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.ddd.logic.Messages.MESSAGE_INVALID_PREFIX_EMPTY_INPUT;
import static seedu.ddd.logic.Messages.getErrorMessageForPrefix;
import static seedu.ddd.logic.parser.CliFlags.FLAG_CLIENT;
import static seedu.ddd.logic.parser.CliFlags.FLAG_EVENT;
import static seedu.ddd.logic.parser.CliFlags.FLAG_VENDOR;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_SERVICE;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.ddd.logic.parser.CommandFlag.CLIENT;
import static seedu.ddd.logic.parser.CommandFlag.EVENT;
import static seedu.ddd.logic.parser.CommandFlag.VENDOR;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.ddd.commons.core.index.Index;
import seedu.ddd.commons.util.StringUtil;
import seedu.ddd.logic.parser.exceptions.ParseException;
import seedu.ddd.model.common.Id;
import seedu.ddd.model.common.Name;
import seedu.ddd.model.common.Tag;
import seedu.ddd.model.contact.common.Address;
import seedu.ddd.model.contact.common.Email;
import seedu.ddd.model.contact.common.Phone;
import seedu.ddd.model.contact.vendor.Service;
import seedu.ddd.model.event.common.Date;
import seedu.ddd.model.event.common.Description;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index provided should be a non-zero unsigned integer.";

    public static final String MESSAGE_INVALID_ID = "ID provided should be a non-negative unsigned integer.";

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
     * Parses {@code Collection<String> clientIds} into a {@code Set<Id>}.
     */
    public static Set<Id> parseIds(Collection<String> ids) throws ParseException {
        requireNonNull(ids);
        final Set<Id> contactIdSet = new HashSet<>();
        for (String id : ids) {
            contactIdSet.add(parseId(id));
        }
        return contactIdSet;
    }

    /**
     * Parses {@code ArgumentMultimap argMultimap} for flag(s), into a {@code CommandFlag}.
     */
    public static CommandFlag parseFlags(ArgumentMultimap argMultimap) throws ParseException {

        // Check if exactly one flag is present
        argMultimap.verifyNoExclusiveFlagsFor(FLAG_CLIENT, FLAG_VENDOR, FLAG_EVENT);

        if (argMultimap.getValue(FLAG_CLIENT).isPresent()) {
            return CLIENT;
        } else if (argMultimap.getValue(FLAG_VENDOR).isPresent()) {
            return VENDOR;
        } else if (argMultimap.getValue(FLAG_EVENT).isPresent()) {
            return EVENT;
        } else {
            return null;
        }
    }

    /**
     * Verifies that the specified ArgumentMultimap does not contain any illegal filters for a client.
     * Throws a ParseException if a forbidden filter (service, description, or date) is found when
     * the client flag (-c) is specified.
     *
     * @param argMultimap The ArgumentMultimap containing the parsed arguments.
     * @throws ParseException if the ArgumentMultimap contains filters for service, description, or date
     *         when the client flag (-c) is specified.
     */
    public static void verifyClientParser(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_SERVICE).isPresent()) {
            throw new ParseException(getErrorMessageForPrefix(PREFIX_SERVICE, FLAG_CLIENT));
        } else if (argMultimap.getValue(PREFIX_DESC).isPresent()) {
            throw new ParseException(getErrorMessageForPrefix(PREFIX_DESC, FLAG_CLIENT));
        } else if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            throw new ParseException(getErrorMessageForPrefix(PREFIX_DATE, FLAG_CLIENT));
        } else {
            // All other cases are valid
        }
    }

    /**
     * Verifies that the specified ArgumentMultimap does not contain any illegal filters for a vendor.
     * Throws a ParseException if a forbidden filter (description or date) is found when
     * the vendor flag (-v) is specified.
     *
     * @param argMultimap The ArgumentMultimap containing the parsed arguments.
     * @throws ParseException if the ArgumentMultimap contains filters for description or date
     *         when the vendor flag (-v) is specified.
     */
    public static void verifyVendorParser(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_DESC).isPresent()) {
            throw new ParseException(getErrorMessageForPrefix(PREFIX_DESC, FLAG_VENDOR));
        } else if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            throw new ParseException(getErrorMessageForPrefix(PREFIX_DATE, FLAG_VENDOR));
        } else {
            // All other cases are valid
        }
    }

    /**
     * Verifies that the specified ArgumentMultimap does not contain any illegal filters for an event.
     *
     * @param argMultimap The ArgumentMultimap containing the parsed arguments.
     * @throws ParseException if the ArgumentMultimap contains filters for address, phone, service, email,
     *         or tags when the event flag (-e) is specified.
     */
    public static void verifyEventParser(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            throw new ParseException(getErrorMessageForPrefix(PREFIX_ADDRESS, FLAG_EVENT));
        } else if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            throw new ParseException(getErrorMessageForPrefix(PREFIX_PHONE, FLAG_EVENT));
        } else if (argMultimap.getValue(PREFIX_SERVICE).isPresent()) {
            throw new ParseException(getErrorMessageForPrefix(PREFIX_SERVICE, FLAG_EVENT));
        } else if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            throw new ParseException(getErrorMessageForPrefix(PREFIX_EMAIL, FLAG_EVENT));
        } else if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            throw new ParseException(getErrorMessageForPrefix(PREFIX_TAG, FLAG_EVENT));
        } else {
            // All other cases are valid
        }
    }

    /**
     * Verifies that the user input following a specified prefix is not empty.
     * This method checks if an argument corresponding to a given prefix is present
     * in the provided ArgumentMultimap and ensures it is not empty.
     *
     * @param argMultimap The ArgumentMultimap containing the parsed arguments.
     * @param prefix The Prefix to look for in the ArgumentMultimap.
     * @return The trimmed argument string following the specified prefix.
     * @throws ParseException if there is no argument or the argument is empty after the prefix.
     */
    public static String verifyNoEmptyInput(ArgumentMultimap argMultimap, Prefix prefix)
            throws ParseException {
        String trimmedArgs = argMultimap.getValue(prefix).get().trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_PREFIX_EMPTY_INPUT, prefix));
        }
        return trimmedArgs;
    }
}
