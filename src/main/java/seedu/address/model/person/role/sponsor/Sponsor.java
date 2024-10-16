package seedu.address.model.person.role.sponsor;

import seedu.address.model.person.role.Role;

/**
 * Represents a Sponsor role in the address book.
 */
public class Sponsor extends Role {
    public Sponsor(String sponsor) {
        super("Sponsor" + " - " + sponsor);
    }
}
