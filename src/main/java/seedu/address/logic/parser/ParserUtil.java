package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.delivery.Cost;
import seedu.address.model.delivery.DateTime;
import seedu.address.model.delivery.DeliverySortBy;
import seedu.address.model.delivery.Quantity;
import seedu.address.model.delivery.SupplierIndex;
import seedu.address.model.person.Address;
import seedu.address.model.person.Company;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.SupplierStatus;
import seedu.address.model.product.Product;
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
     * Parses a {@code String company} into an {@code Company}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code company} is invalid.
     */
    public static Company parseCompany(String company) throws ParseException {
        requireNonNull(company);
        String trimmedCompany = company.trim();
        if (!Company.isValidCompany(trimmedCompany)) {
            throw new ParseException(Company.MESSAGE_CONSTRAINTS);
        }
        return new Company(trimmedCompany);
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
     * Parses a {@code String productName } into a {@code Product}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code productName} is invalid.
     */
    public static Product parseProduct(String productName) throws ParseException {
        requireNonNull(productName);
        String trimmedProductName = productName.trim();
        if (!Product.isValidProductName(trimmedProductName)) {
            throw new ParseException(Product.MESSAGE_CONSTRAINTS);
        }
        return new Product(trimmedProductName);
    }
    /**
     * Parses a {@code String statusString} into a {@code SupplierStatus}.
     * Leading and trailing whitespaces
     * will be trimmed.
     * @throws ParseException if the given {@code statusString} is invalid.
     */
    public static SupplierStatus parseSupplierStatus(String statusString) throws ParseException {
        requireNonNull(statusString);
        String trimmedStatus = statusString.trim();
        if (!SupplierStatus.isValidStatus(trimmedStatus)) {
            throw new ParseException(SupplierStatus.MESSAGE_CONSTRAINTS);
        }
        return new SupplierStatus(trimmedStatus);
    }

    /**
     * Parses {@code Collection<String> products} into a {@code Set<Product>}.
     */
    public static Set<Product> parseProducts(Collection<String> products) throws ParseException {
        requireNonNull(products);
        final Set<Product> productSet = new HashSet<>();
        for (String productName : products) {
            productSet.add(parseProduct(productName));
        }
        return productSet;
    }

    /**
     * Parses a {@code String dd-MM-yyyy HH:mm } into a {@code Time}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code dd-MM-yyyy HH:mm} is invalid.
     */
    public static DateTime parseDateTime(String dateTime) throws ParseException {
        requireNonNull(dateTime);
        String trimmedDateTime = dateTime.trim();
        if (!DateTime.isValidTime(trimmedDateTime)) {
            throw new ParseException(DateTime.MESSAGE_CONSTRAINTS);
        }
        return new DateTime(trimmedDateTime);
    }

    /**
     * Parses a {@code String supplierIndex } into a {@code int}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code supplierName} is invalid.
     */
    public static SupplierIndex parseSupplierIndex(String supplierIndex) throws ParseException {
        requireNonNull(supplierIndex);
        String trimmedSupplierIndex = supplierIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedSupplierIndex)
                || !SupplierIndex.isValidIndex(trimmedSupplierIndex)) {
            throw new ParseException(SupplierIndex.MESSAGE_CONSTRAINTS);
        }
        return new SupplierIndex(trimmedSupplierIndex);
    }

    /**
     * Parses a {@code String quantity } into a {@code Quantity}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code quantity} is invalid.
     */
    public static Quantity parseQuantity(String quantity) throws ParseException {
        requireNonNull(quantity);
        String trimmedQuantity = quantity.trim();
        if (!Quantity.isValidQuantity(trimmedQuantity)) {
            throw new ParseException(Quantity.MESSAGE_CONSTRAINTS);
        }
        return new Quantity(trimmedQuantity);
    }

    /**
     * Parses a {@code String cost } into a {@code Cost}.
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
     * Parses a {@code String sortOrder } into a {@code SortOrder}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code sortOrder} is invalid.
     */
    public static SortOrder parseSortOrder(String sortOrder) throws ParseException {
        requireNonNull(sortOrder);
        String trimmedSortOrder = sortOrder.trim();
        if (!(SortOrder.isValidSortOrder(trimmedSortOrder))) {
            throw new ParseException(SortOrder.MESSAGE_CONSTRAINTS);
        }
        return new SortOrder(trimmedSortOrder);
    }

    /**
     * Parses a {@code String sortBy } into a {@code SortBy}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code sortBy} is invalid.
     */
    public static DeliverySortBy parseDeliverySortBy(String sortBy) throws ParseException {
        requireNonNull(sortBy);
        String trimmedSortBy = sortBy.trim();
        if (!(DeliverySortBy.isValidSortBy(trimmedSortBy))) {
            throw new ParseException(DeliverySortBy.MESSAGE_CONSTRAINTS);
        }
        return new DeliverySortBy(trimmedSortBy);
    }
}
