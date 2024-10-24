package spleetwaise.transaction.model.transaction;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import spleetwaise.address.commons.util.CollectionUtil;
import spleetwaise.address.model.person.Person;
import spleetwaise.commons.IdUtil;

/**
 * Represents a Transaction in the transaction book. Guarantees: details are present and not null, field values are
 * validated, immutable.
 */
public class Transaction {

    private final String id;
    private final Person person;
    private final Amount amount;
    private final Description description;
    private final Date date;
    private final Set<Category> categories = new HashSet<>();

    /**
     * Represents a Transaction in the transaction book.
     *
     * @param id          The id of this transaction.
     * @param person      The person involved in this transaction.
     * @param amount      The amount involved in this transaction.
     * @param description The description of the transaction.
     * @param date        The date the transaction has taken place.
     * @param categories  The categories the transaction has.
     */
    public Transaction(String id, Person person, Amount amount, Description description, Date date,
                       Set<Category> categories) {
        CollectionUtil.requireAllNonNull(person, amount, description, date, categories);
        this.id = id;
        this.person = person;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.categories.addAll(categories);
    }

    /**
     * Represents a Transaction in the transaction book.
     *
     * @param person      The person involved in this transaction.
     * @param amount      The amount involved in this transaction.
     * @param description The description of the transaction.
     * @param date        The date the transaction has taken place.
     * @param categories  The categories the transaction has.
     */
    public Transaction(Person person, Amount amount, Description description, Date date, Set<Category> categories) {
        this(IdUtil.getId(), person, amount, description, date, categories);
    }

    public String getId() {
        return id;
    }

    public Person getPerson() {
        return person;
    }

    public Amount getAmount() {
        return amount;
    }

    public Description getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    /**
     * Returns a boolean value if the transaction contains the category
     */
    public boolean containsCategory(Category category) {
        return categories.contains(category);
    }

    /**
     * Returns an immutable category set, which throws {@code UnsupportedOperationException} if modification is
     * attempted.
     */
    public Set<Category> getCategories() {
        return Collections.unmodifiableSet(categories);
    }

    /**
     * Returns true if both transactions have the same uuid.
     *
     * @param other The other object to compare
     * @return true of the object
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Transaction otherTransaction)) {
            return false;
        }
        return this.person.equals(otherTransaction.getPerson())
                && this.amount.equals(otherTransaction.getAmount())
                && this.description.equals(otherTransaction.getDescription())
                && this.date.equals(otherTransaction.getDate())
                && this.categories.equals(otherTransaction.getCategories());
    }

    /**
     * Returns true if both txns have the same id.
     */
    public boolean hasSameId(Transaction otherTxn) {
        requireNonNull(otherTxn);
        return otherTxn.getId().equals(getId());
    }

    /**
     * Returns true if txn has person with personId.
     */
    public boolean isByPersonId(String personId) {
        requireNonNull(personId);
        return this.person.getId().equals(personId);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    @Override
    public String toString() {
        return String.format("[%s] %s(%s): %s on %s for $%s with categories: %s", id, person.getName(),
                person.getPhone(), description, date, amount, categories
        );
    }
}
