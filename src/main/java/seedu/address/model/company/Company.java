package seedu.address.model.company;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.common.Address;
import seedu.address.model.common.Name;
import seedu.address.model.person.Phone;

/**
 * Represents a company in the address book.
 */
public class Company {
    private final Name name;
    private final Address address;
    private final BillingDate billingDate;
    private final Phone phone;

    /**
     * Every field must be present and not null.
     */
    public Company(Name name, Address address, BillingDate billingDate, Phone phone) {
        requireAllNonNull(name, address, billingDate, phone);
        this.name = name;
        this.address = address;
        this.billingDate = billingDate;
        this.phone = phone;
    }

    public Name getName() {
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
     * Checks only company names for equality,
     * used for detecting duplicates.
     * @param otherCompany Company to be compared with.
     * @return true is companies have the same name.
     */
    public boolean isSameCompany(Company otherCompany) {
        if (otherCompany == this) {
            return true;
        }

        return otherCompany != null
                && otherCompany.name.isSameName(this.name);
    }

    /**
     * Checks for equality.
     * For 2 companies, checks every field for equality.
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
        return this.name.equals(otherCompany.name)
                && this.address.equals(otherCompany.address)
                && this.billingDate.equals(otherCompany.billingDate)
                && this.phone.equals(otherCompany.phone);
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
