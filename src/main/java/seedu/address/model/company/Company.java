package seedu.address.model.company;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.person.Address;
import seedu.address.model.person.Phone;
import seedu.address.commons.util.ToStringBuilder;

import java.util.Objects;


public class Company {
    private final CompanyName name;
    private final Address address;
    private final BillingDate billingDate;
    private final Phone phone;

    /**
     * Every field must be present and not null.
     */
    public Company(CompanyName name, Address address, BillingDate billingDate, Phone phone) {
        requireAllNonNull(name, address, billingDate, phone);
        this.name = name;
        this.address = address;
        this.billingDate = billingDate;
        this.phone = phone;
    }

    public CompanyName getName() {
        return this.name;
    }

    public Address getAddress() {
        return this.address;
    }

    public BillingDate getBillingDate() {
        return this.billingDate;
    }

    public Phone getPhone() {
        return this.phone;
    }

    /**
     * Checks for equality.
     * For 2 companies, checks only their names for equality.
     *
     * @param other The object being equated to.
     * @return true if equal, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Company)) {
            return false;
        }

        Company otherCompany = (Company) other;
        return this.name.equals(otherCompany.name);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, address, billingDate, phone);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("address", address)
                .add("billing date", billingDate)
                .add("phone", phone)
                .toString();
    }
}
