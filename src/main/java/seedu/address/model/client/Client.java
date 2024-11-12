package seedu.address.model.client;

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
 * Represents a Client in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Client {

    // Identity fields
    private final Name name;
    private final Company company;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    private final List<Transaction> transactions;
    private double balance;

    /**
     * Every field must be present and not null.
     */
    public Client(Name name, Company company, Phone phone, Email email, Address address, Set<Tag> tags) {
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
    public Client(Name name, Company company, Phone phone, Email email, Address address, Set<Tag> tags,
                  List<Transaction> transactions) {
        requireAllNonNull(name, phone, email, address, tags, transactions);
        this.name = name;
        this.company = company;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.transactions = new ArrayList<>(transactions);
        this.balance = transactions.stream().map(Transaction::getAmount).reduce(0.0, Double::sum);
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

    public void removeTransaction(Transaction transaction) {
        this.transactions.remove(transaction);
    }

    public double getBalance() {
        return balance;
    }

    /**
     * Updates balance of a client. Used when a transaction is deleted.
     *
     * @param amount the amount the balance is supposed to change by.
     */
    public void updateBalance(double amount) {
        balance += amount;
    }

    /**
     * Checks if the transaction causes a double overflow.
     *
     * @param amount The amount of the transaction to be added / deleted.
     * @return whether the balance would overflow.
     */
    public boolean checkIsOverflow(double amount) {
        if (amount == Double.POSITIVE_INFINITY || amount == Double.NEGATIVE_INFINITY) {
            return true;
        }
        double updatedBalance = balance + amount;
        return (updatedBalance == Double.POSITIVE_INFINITY || updatedBalance == Double.NEGATIVE_INFINITY);
    }

    /**
     * Returns true if both clients have the same name and address
     * This defines a weaker notion of equality between two clients.
     */
    public boolean isSameClient(Client otherClient) {
        if (otherClient == this) {
            return true;
        }

        return otherClient != null
                && otherClient.getName().equals(getName())
                && otherClient.getAddress().equals(getAddress());
    }

    /**
     * Returns true if both clients have the same identity and data fields.
     * This defines a stronger notion of equality between two clients.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Client)) {
            return false;
        }

        Client otherClient = (Client) other;
        return name.equals(otherClient.name)
                && company.equals(otherClient.company)
                && phone.equals(otherClient.phone)
                && email.equals(otherClient.email)
                && address.equals(otherClient.address)
                && tags.equals(otherClient.tags);
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
