package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.HousingType;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PostalCode;
import seedu.address.model.person.Price;
import seedu.address.model.person.UnitNumber;
import seedu.address.model.statistics.AddressBookStatistics;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {
    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INCORRECT_INDEXES = "There should be 2 indexes separated by a space.\n"
            + "Indexes should be non-zero unsigned integers.";
    public static final int ARGUMENT_COUNT = 2;

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
     * Returns an {@code ArrayList<String>} of parameters consisting of
     * PersonIndex and propertyIndex from an argument String.
     */
    public static ArrayList<String> getParametersList(String args) {
        String[] parametersList = args.trim().split("\\s+");
        return new ArrayList<>(Arrays.asList(parametersList));
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
     * Checks if the number of property tags is valid.
     */
    public static boolean isValidNumberOfPropertyTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        return tags.size() <= 2;
    }

    /**
     * Checks if the length of property tags is valid.
     */
    public static boolean isValidLengthPropertyTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        for (String tagName : tags) {
            if (tagName.length() > 9) {
                return false;
            }
        }
        return true;
    }

    /**
     * Parses a {@code String housingType} into a {@code HousingType}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code housingType} is invalid.
     */
    public static HousingType parseHousingType(String housingType) throws ParseException {
        requireNonNull(housingType);
        String trimmedHousingType = housingType.trim();
        if (!HousingType.isValidHousingType(trimmedHousingType)) {
            throw new ParseException(HousingType.MESSAGE_CONSTRAINTS);
        }
        return HousingType.getHousingType(trimmedHousingType);
    }

    /**
     * Parses a {@code String sellingPrice} into a {@code SellingPrice}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code sellingPrice} is invalid.
     */
    public static Price parseSellingPrice(String sellingPrice) throws ParseException {
        requireNonNull(sellingPrice);
        String trimmedSellingPrice = sellingPrice.trim();
        int sellingPriceInt = 0;
        try {
            sellingPriceInt = Integer.parseInt(trimmedSellingPrice);
        } catch (NumberFormatException e) {
            throw new ParseException(Price.MESSAGE_CONSTRAINTS);
        }
        if (sellingPriceInt > 2000000000) {
            throw new ParseException(Price.MESSAGE_PRICE_TOO_HIGH);
        }
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedSellingPrice)) {
            throw new ParseException(Price.MESSAGE_CONSTRAINTS);
        }
        return new Price(trimmedSellingPrice);
    }

    /**
     * Parses a {@code String buyingPrice} into a {@code BuyingPrice}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code buyingPrice} is invalid.
     */
    public static Price parseBuyingPrice(String buyingPrice) throws ParseException {
        requireNonNull(buyingPrice);
        String trimmedBuyingPrice = buyingPrice.trim();
        int buyingPriceInt = 0;
        try {
            buyingPriceInt = Integer.parseInt(trimmedBuyingPrice);
        } catch (NumberFormatException e) {
            throw new ParseException(Price.MESSAGE_CONSTRAINTS);
        }
        if (buyingPriceInt > 2000000000) {
            throw new ParseException(Price.MESSAGE_PRICE_TOO_HIGH);
        }
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedBuyingPrice)) {
            throw new ParseException(Price.MESSAGE_CONSTRAINTS);
        }
        return new Price(trimmedBuyingPrice);
    }

    /**
     * Parses a {@code String postalCode} into a {@code PostalCode}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code postalCode} is invalid.
     */
    public static PostalCode parsePostalCode(String postalCode) throws ParseException {
        requireNonNull(postalCode);
        String trimmedPostalCode = postalCode.trim();
        if (!PostalCode.isValidPostalCode(trimmedPostalCode)) {
            throw new ParseException(PostalCode.MESSAGE_CONSTRAINTS);
        }
        return new PostalCode(trimmedPostalCode);
    }

    /**
     * Parses a {@code String unitNumber} into a {@code UnitNumber}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code unitNumber} is invalid.
     */
    public static UnitNumber parseUnitNumber(String unitNumber) throws ParseException {
        requireNonNull(unitNumber);
        String trimmedUnitNumber = unitNumber.trim();
        if (!UnitNumber.isValidUnitNumber(trimmedUnitNumber)) {
            throw new ParseException(UnitNumber.MESSAGE_CONSTRAINTS);
        }
        return new UnitNumber(trimmedUnitNumber);
    }

    /**
     * Parses the AddressBookStatistics and returns a String representation of the {@code AddressBookStatistics}.
     *
     * @param statistics The current {@code AddressBookStatistics} from the current {@code AddressBook}
     * @return A String representation of the {@code AddressBookStatistics}
     */
    public static String parseAddressBookStatistics(AddressBookStatistics statistics) {
        String message = "Total Persons: " + statistics.getTotalPersons()
                + "\nTotal Properties Bought: " + statistics.getTotalPropertiesBought()
                + "\nTotal Properties Sold: " + statistics.getTotalPropertiesSold()
                + "\nTotal Sales Revenue: " + statistics.getTotalSalesRevenue()
                + "\nTotal Purchase Expense: " + statistics.getTotalPurchaseExpense()
                + "\nUnique Number of Properties-to-Sell: "
                + statistics.getUniqueSellingPropertyList().getUniqueSellingPropertiesCount()
                + "\nUnique Number of Properties-to-Buy: "
                + statistics.getUniqueBuyingPropertyList().getUniqueBuyingPropertiesCount();
        return message;
    }

    /**
     * Parses the Commands and returns a String representation of the {@code Commands}.
     *
     * @param addressBook The list of commands AddressBook implements.
     * @return A String representation of the {@code Commands}
     */
    public static String parseCommandList(AddressBook addressBook) {
        String message = addressBook.getCommandList().toString();
        return message;
    }
}
