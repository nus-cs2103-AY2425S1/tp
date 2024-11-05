package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.tag.Tag;

/**
 * Represents a Seller in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Seller extends Person {

    private Role role;

    /**
     * Creates a Seller with the specified details.
     *
     * @param name The name of the seller.
     * @param phone The phone number of the seller.
     * @param email The email address of the seller.
     * @param tags The tags associated with the seller.
     * @param appointment The appointment associated with the seller.
     */
    public Seller(Name name, Phone phone, Email email, Set<Tag> tags,
                  Appointment appointment) {
        super(name, phone, email, tags, appointment);
        this.role = Role.SELLER;
    }

    /**
     * Creates a Seller with the specified details.
     *
     * @param name The name of the seller.
     * @param phone The phone number of the seller.
     * @param email The email address of the seller.
     * @param tags The tags associated with the seller.
     * @param appointment The appointment associated with the seller.
     * @param remark The remark associated with the seller.
     */
    public Seller(Name name, Phone phone, Email email, Set<Tag> tags,
                  Appointment appointment, String remark) {
        super(name, phone, email, tags, appointment, remark);
        this.role = Role.SELLER;
    }

    @Override
    public Role getRole() {
        return this.role;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
