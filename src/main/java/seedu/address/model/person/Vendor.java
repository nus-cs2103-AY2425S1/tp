package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;
import seedu.address.model.wedding.Wedding;

/**
 * Represents a Vendor in the WedLinker.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Vendor extends Person {

    /**
     * Every field must be present and not null.
     */
    // add the Task field here
    public Vendor(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Set<Wedding> weddings,
                  Set<Task> tasks) {
        super(name, phone, email, address, tags, weddings, tasks);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two vendors.
     */
    public boolean isSameVendor(Vendor otherVendor) {
        if (otherVendor == this) {
            return true;
        }
        // can add additional check to check if task is the same for both vendors
        return otherVendor != null
                && otherVendor.getName().equals(getName());
    }

    /**
     * Returns true if both vendors have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        return super.equals(other); // need to add equality check for tasks here? for vendors specifically
    }




}
