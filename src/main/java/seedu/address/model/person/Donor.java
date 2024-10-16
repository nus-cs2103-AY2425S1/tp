package seedu.address.model.person;


import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Donor in the address book.
 * Inherits from Person and includes donated amount from the donor.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Donor extends Person {

    private final DonatedAmount donatedAmount;

    /**
     * Constructs a {@code Donor}.
     *
     * @param name    Name of the donor.
     * @param phone   Phone number of the donor.
     * @param email   Email address of the donor.
     * @param address Address of the donor.
     * @param tags    Tags associated with the donor.
     * @param donatedAmount   Hours contributed by the donor.
     */
    public Donor(Name name, Phone phone, Email email, Address address, Set<Tag> tags, DonatedAmount donatedAmount) {
        super(name, phone, email, address, tags);
        requireAllNonNull(donatedAmount);
        this.donatedAmount = donatedAmount;
    }

    public DonatedAmount getDonatedAmount() {
        return donatedAmount;
    }

    /**
     * Compares this Donor to another for equality.
     *
     * @param other The object to compare with.
     * @return True if both Donors have identical attributes.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof seedu.address.model.person.Donor)) {
            return false;
        }

        seedu.address.model.person.Donor otherDonor = (seedu.address.model.person.Donor) other;
        return name.equals(otherDonor.name)
                && phone.equals(otherDonor.phone)
                && email.equals(otherDonor.email)
                && address.equals(otherDonor.address)
                && tags.equals(otherDonor.tags)
                && donatedAmount.equals(otherDonor.donatedAmount);
    }

    /**
     * Returns a hash code for this Donor based on its attributes.
     *
     * @return Hash code of the donor.
     */
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, donatedAmount);
    }

    /**
     * Returns a string representation of this Donor.
     *
     * @return String representation of the donor.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .add("hours", donatedAmount)
                .toString();
    }

    public Role getRole() {
        return Role.DONOR;
    }

}
