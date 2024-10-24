package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Company company;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    private List<Transaction> transactions;
    private int balance;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Company company, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.company = company;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.transactions = new ArrayList<>();
        this.balance = 0;
    }
    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Company company, Phone phone, Email email, Address address, Set<Tag> tags,
                  List<Transaction> transactions) {
        requireAllNonNull(name, phone, email, address, tags, transactions);
        this.name = name;
        this.company = company;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.transactions = transactions;
        this.balance = transactions.stream().map(Transaction::getAmount).reduce(0, Integer::sum);
    }

    public Name getName() {
        return name;
    }

    public Company getCompany() {
        return company;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void updateTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public int getBalance() {
        return balance;
    }

    /**
     * Updates balance of a person. Used when a transaction is deleted.
     *
     * @param amount the amount the balance is supposed to change by.
     */
    public void updateBalance(int amount) {
        balance += amount;
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && company.equals(otherPerson.company)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, company, phone, email, address, tags, transactions);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("company", company)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .add("transactions", transactions)
                .toString();
    }

}
