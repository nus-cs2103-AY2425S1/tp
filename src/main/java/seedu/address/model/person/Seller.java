package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.tag.Tag;

/**
 * Represents a Seller in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Seller extends Person {

    /**
     * Creates a Seller with the specified details.
     *
     * @param name The name of the seller.
     * @param phone The phone number of the seller.
     * @param email The email address of the seller.
     * @param tags The tags associated with the seller.
     * @param appointment The appointment associated with the seller.
     * @param property The property associated with the seller.
     */

    public Seller(Name name, Phone phone, Email email, Set<Tag> tags, Appointment appointment, Property property) {
        super(name, phone, email, tags, appointment, property);
    }

    @Override
    public String getRole() {
        return "seller";
    }

    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * Checks if the seller has a property listing.
     *
     * @return true if the seller has a property listed, false otherwise.
     */
    public boolean hasListing() {
        return getProperty() != null && !getProperty().toString().isEmpty();
    }
}
