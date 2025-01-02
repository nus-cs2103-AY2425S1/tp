package seedu.address.model.client;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.status.Status;
import seedu.address.model.tier.Tier;

/**
 * Represents a Client in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Client {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Job job;
    private final Income income;
    private final Tier tier;
    private final Remark remark;
    private final Status status;
    /**
     * Every field must be present and not null.
     */
    public Client(Name name, Phone phone, Email email, Address address, Job job, Income income,
                  Tier tier, Remark remark, Status status) {
        requireAllNonNull(name, phone, email, address, tier, remark, status);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.job = job;
        this.income = income;
        this.tier = tier;
        this.remark = remark;
        this.status = status;
    }

    public Name getName() {
        return name;
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
    public Job getJob() {
        return job;
    }
    public Income getIncome() {
        return income;
    }
    public Status getStatus() {
        return status;
    }

    /**
     * Returns an immutable tier, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Tier getTier() {
        return tier;
    }

    public Remark getRemark() {
        return remark;
    }

    /**
     * Returns true if both clients have the same name.
     * This defines a weaker notion of equality between two clients.
     */
    public boolean isSameClient(Client otherClient) {
        if (otherClient == this) {
            return true;
        }

        return otherClient != null
                && otherClient.getName().equals(getName())
                && otherClient.getPhone().equals(getPhone())
                && otherClient.getEmail().equals(getEmail());
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
        return isSameClient(otherClient);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, job, tier, remark, status);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("job", job)
                .add("income", income)
                .add("tier", tier)
                .add("remark", remark)
                .add("status", status)
                .toString();
    }

}
