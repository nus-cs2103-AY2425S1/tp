package spleetwaise.transaction.model;

import java.util.function.Predicate;

import spleetwaise.address.commons.util.ToStringBuilder;
import spleetwaise.address.model.person.Person;
import spleetwaise.transaction.model.transaction.Amount;
import spleetwaise.transaction.model.transaction.Date;
import spleetwaise.transaction.model.transaction.Description;
import spleetwaise.transaction.model.transaction.Transaction;

/**
 * Tests that a {@code Transaction} matches any of the condition given.
 */
public class FilterCommandPredicate implements Predicate<Transaction> {

    private final Person contact;
    private final Amount amount;
    private final Description description;
    private final Date date;

    /**
     * Constructs a {@code FilterCommandPredicate} with the given contact, amount, description and date.
     *
     * @param contact     The person to filter the transaction by.
     * @param amount      The amount to filter the transaction by.
     * @param description The description to filter the transaction by.
     * @param date        The date to filter the transaction by.
     */
    public FilterCommandPredicate(Person contact, Amount amount, Description description, Date date) {
        if (contact == null && amount == null && description == null && date == null) {
            throw new NullPointerException();
        }

        this.contact = contact;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    @Override
    public boolean test(Transaction txn) {
        if (contact != null && !txn.getPerson().equals(contact)) {
            return false;
        }

        if (amount != null && !txn.getAmount().equals(amount)) {
            return false;
        }

        if (description != null
                && !txn.getDescription().toString().toLowerCase()
                .contains(description.toString().toLowerCase())) {
            return false;
        }

        if (date != null && !txn.getDate().equals(date)) {
            return false;
        }

        return true;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FilterCommandPredicate otherFilterCommandPredicate)) {
            return false;
        }

        return (contact == null ? otherFilterCommandPredicate.contact == null
                : contact.equals(otherFilterCommandPredicate.contact))
                && (amount == null ? otherFilterCommandPredicate.amount == null
                : amount.equals(otherFilterCommandPredicate.amount))
                && (description == null ? otherFilterCommandPredicate.description == null
                : description.equals(otherFilterCommandPredicate.description))
                && (date == null ? otherFilterCommandPredicate.date == null
                : date.equals(otherFilterCommandPredicate.date));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("contact", contact)
                .add("amount", amount)
                .add("description", description)
                .add("date", date)
                .toString();
    }

}
