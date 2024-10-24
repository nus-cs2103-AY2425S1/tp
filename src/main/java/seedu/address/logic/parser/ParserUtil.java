package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.delivery.Cost;
import seedu.address.model.delivery.Eta;
import seedu.address.model.delivery.ItemName;
import seedu.address.model.delivery.Status;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
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
    public static List<Index> parseIndex(String oneBasedIndex) throws ParseException {
        String[] parts = oneBasedIndex.trim().split("\\s+"); // Split by spaces
        List<Index> indexList = new ArrayList<>();

        for (String part : parts) {
            if (!StringUtil.isNonZeroUnsignedInteger(part)) {
                throw new ParseException(MESSAGE_INVALID_INDEX);
            }
            indexList.add(Index.fromOneBased(Integer.parseInt(part.trim()))); // Convert to Index and add to list
        }
        return indexList;
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
     * Parses a {@code String role} into an {@code Role}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code role} is invalid.
     */
    public static Role parseRole(String role) throws ParseException {
        requireNonNull(role);
        String trimmedRole = role.trim();
        if (!Role.isValidRole(trimmedRole)) {
            throw new ParseException(Role.MESSAGE_CONSTRAINTS);
        }
        return new Role(trimmedRole);
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
     * Parses a {@code String eta} into an {@code Eta}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code eta} is invalid.
     */
    public static Eta parseEta(String eta) throws ParseException {
        requireNonNull(eta);
        String trimmedEta = eta.trim();
        if (!Eta.isValidEta(trimmedEta)) {
            throw new ParseException(Eta.MESSAGE_CONSTRAINTS);
        }
        return new Eta(trimmedEta);
    }

    /**
     * Parses a {@code String cost} into an {@code Cost}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code cost} is invalid.
     */
    public static Cost parseCost(String cost) throws ParseException {
        requireNonNull(cost);
        String trimmedCost = cost.trim();
        if (!Cost.isValidCost(trimmedCost)) {
            throw new ParseException(Cost.MESSAGE_CONSTRAINTS);
        }
        return new Cost(trimmedCost);
    }

    /**
     * Parses a {@code String itemId} into an {@code ItemName.java}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code itemId} is invalid.
     */
    public static ItemName parseItemName(String itemId) throws ParseException {
        requireNonNull(itemId);
        String trimmedName = itemId.trim();
        if (!ItemName.isValidItemName(trimmedName)) {
            throw new ParseException(ItemName.MESSAGE_CONSTRAINTS);
        }
        return new ItemName(trimmedName);
    }

    /**
     * Parses {@code Collection<String> items} into a {@code Set<ItemName>}.
     */
    public static Set<ItemName> parseItems(Collection<String> items) throws ParseException {
        requireNonNull(items);
        final Set<ItemName> itemSet = new HashSet<>();
        for (String itemName : items) {
            itemSet.add(parseItemName(itemName));
        }
        return itemSet;
    }

    /**
     * Parses a {@code String status} into an {@code Status.java}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code status} is invalid.
     */
    public static Status parseStatus(String status) throws ParseException {
        requireNonNull(status);
        String trimmedStatus = status.trim();
        if (!Status.isValidStatus(trimmedStatus)) {
            throw new ParseException(Status.MESSAGE_CONSTRAINTS);
        }
        return new Status(trimmedStatus);
    }

    /**
     * Parses a {@code String} delivery attribute to ensure it is a valid delivery attribute.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code String} delivery attribute is invalid.
     */
    public static String parseAttribute(String attribute) throws ParseException {
        requireNonNull(attribute);
        String trimmedAttribute = attribute.trim().toLowerCase(); // Make input case-insensitive.
        if (trimmedAttribute.equals("address")
            || trimmedAttribute.equals("cost")
            || trimmedAttribute.equals("date")
            || trimmedAttribute.equals("eta")
            || trimmedAttribute.equals("id")
            || trimmedAttribute.equals("status")) {
            return trimmedAttribute;
        } else {
            throw new ParseException(SortCommand.MESSAGE_UNKNOWN_ATTRIBUTE);
        }
    }
}
