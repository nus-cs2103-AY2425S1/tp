package spleetwaise.transaction.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import spleetwaise.address.logic.parser.exceptions.ParseException;
import spleetwaise.address.model.ReadOnlyAddressBook;
import spleetwaise.address.model.person.Person;
import spleetwaise.address.model.person.Phone;
import spleetwaise.commons.model.CommonModel;
import spleetwaise.transaction.model.ReadOnlyTransactionBook;
import spleetwaise.transaction.model.transaction.Amount;
import spleetwaise.transaction.model.transaction.Category;
import spleetwaise.transaction.model.transaction.Date;
import spleetwaise.transaction.model.transaction.Description;
import spleetwaise.transaction.model.transaction.Transaction;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

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
     * Parses a {@code String indexStr} into a {@code int}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code indexStr} is invalid.
     */
    public static int parseIndex(String indexStr) throws ParseException {
        requireNonNull(indexStr);
        indexStr = indexStr.trim();
        int index;
        try {
            index = Integer.parseInt(indexStr);
        } catch (NumberFormatException e) {
            throw new ParseException("Index is not in integer form");
        }

        return index;
    }

    /**
     * Parses a {@code String catStr} into a {@code Category} that represents the category.
     * Leading and trailing whitespaces will be trimmed and capitalized
     */
    public static Category parseCategory(String catStr) throws ParseException {
        requireNonNull(catStr);
        String trimmedCategory = catStr.trim().toUpperCase();
        if (!Category.isValidTagName(trimmedCategory)) {
            throw new ParseException(Category.MESSAGE_CONSTRAINTS);
        }
        return new Category(trimmedCategory);
    }

    /**
     * Parses {@code Collection<String> Category} into a {@code Set<Category>}.
     */
    public static Set<Category> parseTags(Collection<String> categoryStrs) throws ParseException {
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
        ReadOnlyAddressBook ab = CommonModel.getInstance().getAddressBook();
        ObservableList<Person> personList = ab.getPersonList();
        FilteredList<Person> filteredPersonList = personList.filtered((p) -> p.getPhone().equals(phone));
        if (filteredPersonList.isEmpty()) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return filteredPersonList.get(0);
    }

    public static Transaction getTransactionFromIndex(int index) throws ParseException {
        index--;
        ReadOnlyTransactionBook tb = CommonModel.getInstance().getTransactionBook();
        ObservableList<Transaction> transactionList = tb.getTransactionList();
        if (index < 0 || index >= transactionList.size()) {
            throw new ParseException("Invalid index");
        }
        return transactionList.get(index);
    }


}
