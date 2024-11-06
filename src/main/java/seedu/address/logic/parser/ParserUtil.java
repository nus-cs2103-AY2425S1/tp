package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.Email;
import seedu.address.model.client.Name;
import seedu.address.model.client.Phone;
import seedu.address.model.rentalinformation.Address;
import seedu.address.model.rentalinformation.CustomerList;
import seedu.address.model.rentalinformation.Deposit;
import seedu.address.model.rentalinformation.MonthlyRent;
import seedu.address.model.rentalinformation.RentDueDate;
import seedu.address.model.rentalinformation.RentalDate;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    public static final String MESSAGE_INVALID_SORT_TYPE = "SortType is not either name or latest.";

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
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhoneForEdit(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }

        if (trimmedPhone.isEmpty()) {
            return new Phone();
        }

        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhoneAdd(String phone) throws ParseException {
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }

        if (trimmedPhone.isEmpty()) {
            return new Phone();
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
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static RentalDate parseRentalDate(String rentalDate) throws ParseException {
        requireNonNull(rentalDate);
        String trimmedRentalDate = rentalDate.trim();
        if (!RentalDate.isValidRentalDate(trimmedRentalDate) || !RentalDate.isValidDateTime(trimmedRentalDate)) {
            throw new ParseException(RentalDate.MESSAGE_CONSTRAINTS);
        }
        return new RentalDate(trimmedRentalDate);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static RentDueDate parseRentDueDate(String rentDueDate) throws ParseException {
        requireNonNull(rentDueDate);
        String trimmedRentDueDate = rentDueDate.trim();
        if (!RentDueDate.isValidDueDate(trimmedRentDueDate)) {
            throw new ParseException(RentDueDate.MESSAGE_CONSTRAINTS);
        }
        return new RentDueDate(trimmedRentDueDate);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static MonthlyRent parseMonthlyRent(String monthlyRent) throws ParseException {
        requireNonNull(monthlyRent);
        String trimmedMonthlyRent = monthlyRent.trim();
        if (!MonthlyRent.isValidMonthlyRent(trimmedMonthlyRent)) {
            throw new ParseException(MonthlyRent.MESSAGE_CONSTRAINTS);
        }
        return new MonthlyRent(trimmedMonthlyRent);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Deposit parseDeposit(String deposit) throws ParseException {
        requireNonNull(deposit);
        String trimmedDeposit = deposit.trim();
        if (!Deposit.isValidDeposit(trimmedDeposit)) {
            throw new ParseException(Deposit.MESSAGE_CONSTRAINTS);
        }
        return new Deposit(trimmedDeposit);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static CustomerList parseCustomerList(String customerList) throws ParseException {
        requireNonNull(customerList);
        String trimmedCustomerList = customerList.trim();
        if (!CustomerList.isValidCustomerList(trimmedCustomerList)) {
            throw new ParseException(CustomerList.MESSAGE_CONSTRAINTS);
        }
        return new CustomerList(trimmedCustomerList);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        String trimmedEmail = email.trim();
        if (!Email.isValidEmailForEdit(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }

        if (trimmedEmail.isEmpty()) {
            return new Email();
        }

        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmailAdd(String email) throws ParseException {
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }

        if (trimmedEmail.isEmpty()) {
            return new Email();
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
     * Parses {@code sortType} into an {@code String} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified sortType is invalid (not name or latest).
     */
    public static String parseSortType(String sortType) throws ParseException {
        String trimmedSortType = sortType.trim();
        if (!(trimmedSortType.equals("name") || trimmedSortType.equals("latest"))) {
            throw new ParseException(MESSAGE_INVALID_SORT_TYPE);
        }
        return trimmedSortType;
    }
}
