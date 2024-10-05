package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Vendor in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Vendor extends Contact {
    private Service service;
    public Vendor(Name name, Phone phone, Email email, Address address, Service service, Set<Tag> tags) {
        super(name, phone, email, address, tags);
        requireAllNonNull(service);
        this.service = service;
    }
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(this.getName(), this.getPhone(), this.getEmail(),
                this.getAddress(), this.service, this.getTags());
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
                .toString();
    }

}
