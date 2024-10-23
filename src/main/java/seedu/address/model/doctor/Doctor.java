package seedu.address.model.doctor;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Represents a Doctor in the contacts book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Doctor extends Person {

    /**
     * Every field must be present and not null.
     */
    public Doctor(Name name, Phone phone, Email email, Address address, Speciality speciality, Set<Tag> tags) {
        super(name, phone, email, address, speciality, tags);
        requireAllNonNull(speciality);
    }

    /**
     * Returns true if both doctors have the same name and phone number.
     * This defines a weaker notion of equality between two doctors.
     */
    public boolean isSameDoctor(Doctor otherDoctor) {
        if (otherDoctor == this) {
            return true;
        }

        return otherDoctor != null
                && otherDoctor.getName().equals(getName())
                && otherDoctor.getPhone().equals(getPhone());
    }

    /**
     * Returns true if both doctors have the same identity and data fields.
     * This defines a stronger notion of equality between two doctors.
     */
    @Override
    public boolean equals(Object other) {
        // Person identity and data fields must be the same
        if (!super.equals(other)) {
            return false;
        }

        if (!(other instanceof Doctor)) {
            return false;
        }

        Doctor otherDoctor = (Doctor) other;
        return super.getSpeciality().equals(otherDoctor.getSpeciality());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(
            super.getName(),
            super.getPhone(),
            super.getEmail(),
            super.getAddress(),
            super.getSpeciality(),
            super.getTags()
        );
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", super.getName())
                .add("phone", super.getPhone())
                .add("email", super.getEmail())
                .add("address", super.getAddress())
                .add("speciality", super.getSpeciality())
                .add("tags", super.getTags())
                .toString();
    }

}
