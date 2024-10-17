package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Partner in the address book.
 * Inherits from Person and includes the end date of partnership with NGO.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Partner extends Person {

    private final LocalDate partnershipEndDate;

    /**
     * Constructs a {@code Partner}.
     *
     * @param name Name of the partner.
     * @param phone Phone number of the partner.
     * @param email Email address of the partner.
     * @param address Address of the partner.
     * @param tags Tags associated with the partner.
     * @param partnershipEndDate Date when the partnership with the partner will end.
     */
    public Partner(Name name, Phone phone, Email email, Address address, Set<Tag> tags, LocalDate partnershipEndDate) {
        super(name, phone, email, address, tags);
        requireAllNonNull(partnershipEndDate);
        this.partnershipEndDate = partnershipEndDate;
    }

    public LocalDate getEndDate() {
        return partnershipEndDate;
    }

    /**
     * Compares this Partner to another for equality.
     *
     * @param other The object to compare with.
     * @return True if both Partners have identical attributes.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Partner)) {
            return false;
        }

        Partner otherPartner = (Partner) other;
        return name.equals(otherPartner.name)
                && phone.equals(otherPartner.phone)
                && email.equals(otherPartner.email)
                && address.equals(otherPartner.address)
                && tags.equals(otherPartner.tags)
                && partnershipEndDate.equals(otherPartner.partnershipEndDate);
    }

    /**
     * Returns a hash code for this Partner based on its attributes.
     *
     * @return Hash code of the partner.
     */
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, partnershipEndDate);
    }

    /**
     * Returns a string representation of this Partner.
     *
     * @return String representation of the partner.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .add("hours", partnershipEndDate)
                .toString();
    }
}
