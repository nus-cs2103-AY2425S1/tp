package seedu.ddd.model.contact.client;

import java.util.Set;

import seedu.ddd.commons.util.ToStringBuilder;
import seedu.ddd.model.common.Id;
import seedu.ddd.model.common.Name;
import seedu.ddd.model.common.Tag;
import seedu.ddd.model.contact.common.Address;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.contact.common.Email;
import seedu.ddd.model.contact.common.Phone;

/**
 * Represents a Client in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Client extends Contact {

    /**
     * Constructs a {@code Client}.
     *
     * @param name    A valid name.
     * @param phone   A valid phone number.
     * @param email   A valid email address.
     * @param address A valid address.
     * @param tags    A set of tags associated with the client.
     * @param contactId      A valid id.
     */
    public Client(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Id contactId) {
        super(name, phone, email, address, tags, contactId);
    }

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
        return super.equals(otherClient);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", this.getName())
                .add("phone", this.getPhone())
                .add("email", this.getEmail())
                .add("address", this.getAddress())
                .add("tags", this.getTags())
                .add("id", this.getId())
                .toString();
    }
}
