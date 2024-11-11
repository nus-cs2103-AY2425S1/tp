package seedu.ddd.model.contact.vendor;

import java.util.Objects;
import java.util.Set;

import seedu.ddd.commons.util.CollectionUtil;
import seedu.ddd.commons.util.ToStringBuilder;
import seedu.ddd.model.common.Id;
import seedu.ddd.model.common.Name;
import seedu.ddd.model.common.Tag;
import seedu.ddd.model.contact.common.Address;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.contact.common.Email;
import seedu.ddd.model.contact.common.Phone;

/**
 * Represents a Vendor in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Vendor extends Contact {
    private Service service;
    /**
     * Constructs a {@code Vendor}.
     *
     * @param name    A valid name.
     * @param phone   A valid phone number.
     * @param email   A valid email address.
     * @param address A valid address.
     * @param service A valid service.
     * @param tags    A set of tags associated with the client.
     * @param contactId      A valid id.
     */
    public Vendor(Name name, Phone phone, Email email, Address address, Service service, Set<Tag> tags,
            Id contactId) {
        super(name, phone, email, address, tags, contactId);
        CollectionUtil.requireAllNonNull(service);
        this.service = service;
    }

    public Service getService() {
        return service;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Vendor)) {
            return false;
        }

        Vendor otherVendor = (Vendor) other;
        return super.equals(otherVendor) && service.equals(otherVendor.service);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(this.getName(), this.getPhone(), this.getEmail(),
                this.getAddress(), this.service, this.getTags(), this.getId());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", this.getName())
                .add("phone", this.getPhone())
                .add("email", this.getEmail())
                .add("address", this.getAddress())
                .add("service", this.service)
                .add("tags", this.getTags())
                .add("id", this.getId())
                .toString();
    }
}
