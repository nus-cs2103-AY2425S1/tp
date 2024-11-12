package seedu.eventfulnus.model.person.role.sponsor;

import static java.util.Objects.requireNonNull;

import seedu.eventfulnus.model.person.role.Role;

/**
 * Represents a Sponsor role in the address book.
 */
public class Sponsor extends Role {
    /**
     * Creates a {@code Sponsor} with the given company name.
     */
    public Sponsor(String company) {
        super("Sponsor" + " - " + company);
        requireNonNull(company);
    }
}
