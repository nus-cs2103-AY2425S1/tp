package seedu.address.model.person;

import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Teacher in the address book.
 * Inherits from the Person class and includes an additional gender field.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Teacher extends Person {

    private final Gender gender;

    /**
     * Every field must be present and not null.
     *
     * @param name
     * @param gender
     * @param phone
     * @param email
     * @param address
     * @param tags
     */
    public Teacher(Name name, Gender gender, Phone phone, Email email, Address address, Set<Tag> tags) {
        super(name, phone, email, address, tags);
        this.gender = gender;
    }

    /**
     * Returns true if both Teachers have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Teacher)) {
            return false;
        }

        Teacher otherTeacher = (Teacher) other;
        return getName().equals(otherTeacher.getName())
            && getPhone().equals(otherTeacher.getPhone())
            && getEmail().equals(otherTeacher.getEmail())
            && getAddress().equals(otherTeacher.getAddress())
            && getTags().equals(otherTeacher.getTags());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("Role", "Teacher")
            .add("name", getName())
            .add("gender", gender)
            .add("phone", getPhone())
            .add("email", getEmail())
            .add("address", getAddress())
            .add("tags", getTags())
            .toString();
    }

}
