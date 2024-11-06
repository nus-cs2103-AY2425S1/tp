package spleetwaise.transaction.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import spleetwaise.address.logic.Messages;
import spleetwaise.address.model.person.Person;
import spleetwaise.address.model.person.Phone;
import spleetwaise.commons.core.index.Index;
import spleetwaise.commons.logic.parser.BaseParserUtil;
import spleetwaise.commons.logic.parser.exceptions.ParseException;
import spleetwaise.commons.model.CommonModelManager;
import spleetwaise.transaction.model.filterpredicate.AmountSignFilterPredicate;
import spleetwaise.transaction.model.transaction.Amount;
import spleetwaise.transaction.model.transaction.Category;
import spleetwaise.transaction.model.transaction.Date;
import spleetwaise.transaction.model.transaction.Description;
import spleetwaise.transaction.model.transaction.Status;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil extends BaseParserUtil {
    public static final String MESSAGE_PHONE_NUMBER_IS_UNKNOWN = "Phone number is unknown.";

    /**
     * Parses a {@code String amount} into a {@code Amount}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code amount} is invalid.
     */
    public static Amount parseAmount(String amount) throws ParseException {
        requireNonNull(amount);
        amount = amount.trim();
        if (!Amount.isValidAmount(amount)) {
            throw new ParseException(Amount.MESSAGE_CONSTRAINTS);
        }
        return new Amount(amount);
    }

    /**
     * Parses a {@code String description} into a {@code Description}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static Description parseDescription(String description) throws ParseException {
        requireNonNull(description);
        description = description.trim();
        if (!Description.isValidDescription(description)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(description);
    }

    /**
     * Parses a {@code String date} into a {@code Date}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        date = date.trim();
        if (!Date.isValidDate(date)) {
            throw new ParseException(Date.MESSAGE_CONSTRAINTS);
        }
        return new Date(date);
    }

    /**
     * Parses a {@code String status} into a {@code Status}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code status} is invalid.
     */
    public static Status parseStatus(String status) throws ParseException {
        requireNonNull(status);
        status = status.trim();
        if (!Status.isValidStatus(status)) {
            throw new ParseException(Status.MESSAGE_CONSTRAINTS);
        }
        return new Status(status);
    }

    /**
     * Parses a {@code String catStr} into a {@code Category} that represents the category. Leading and trailing
     * whitespaces will be trimmed and capitalized
     */
    public static Category parseCategory(String catStr) throws ParseException {
        requireNonNull(catStr);
        String trimmedCategory = catStr.trim().toUpperCase();
        if (!Category.isValidCatName(trimmedCategory)) {
            throw new ParseException(Category.MESSAGE_CONSTRAINTS);
        }
        return new Category(trimmedCategory);
    }

    /**
     * Parses {@code Collection<String> Category} into a {@code Set<Category>}.
     */
    public static Set<Category> parseCategories(Collection<String> categoryStrs) throws ParseException {
        requireNonNull(categoryStrs);
        final Set<Category> categories = new HashSet<>();
        for (String categoryStr : categoryStrs) {
            categories.add(parseCategory(categoryStr));
        }
        return categories;
    }

    /**
     * Finds the corresponding Person who has the provided phone number.
     *
     * @param phone The phone to search using.
     * @return A Person who has the specified phone.
     * @throws ParseException No person was found with the phone.
     */
    public static Person getPersonFromPhone(Phone phone) throws ParseException {
        requireNonNull(phone);
        Optional<Person> p = CommonModelManager.getInstance().getPersonByPhone(phone);
        if (p.isEmpty()) {
            throw new ParseException(MESSAGE_PHONE_NUMBER_IS_UNKNOWN);
        }
        return p.get();
    }

    /**
     * Retrieves a {@code Person} from the filtered person list at the specified {@code Index}.
     *
     * @param index The {@code Index} of the person to retrieve from the filtered address book list.
     * @return The {@code Person} at the specified index in the filtered person list.
     * @throws ParseException If the specified index is out of bounds for the filtered person list.
     */
    public static Person getPersonByFilteredPersonListIndex(Index index)
            throws ParseException {
        requireNonNull(index);
        List<Person> lastShownList = CommonModelManager.getInstance().getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new ParseException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        return lastShownList.get(index.getZeroBased());
    }

    /**
     * Parses a {@code String amountSign} into a {@code AmountSignFilterPredicate}.
     *
     * @throws ParseException if the given {@code amountSign} is invalid.
     */
    public static AmountSignFilterPredicate parseAmountSign(String amountSign) throws ParseException {
        requireNonNull(amountSign);
        amountSign = amountSign.trim();
        if (!AmountSignFilterPredicate.isValidSign(amountSign)) {
            throw new ParseException(AmountSignFilterPredicate.MESSAGE_CONSTRAINTS);
        }
        return new AmountSignFilterPredicate(amountSign);
    }
}
