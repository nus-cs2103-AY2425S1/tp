package seedu.address.model.patient;

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
 * Represents a Patient in the contracts book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Patient extends Person {

    // Patient-specific data fields
    private final DateOfBirth dateOfBirth;
    private final Gender gender;

    /**
     * Every field must be present and not null.
     */
    public Patient(
        Name name,
        Phone phone,
        Email email,
        Address address,
        DateOfBirth dateOfBirth,
        Gender gender,
        Set<Tag> tags
    ) {
        super(name, phone, email, address, tags);

        requireAllNonNull(dateOfBirth, gender);
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public DateOfBirth getDateOfBirth() {
        return dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    /**
     * Returns true if both patients have the same name and phone number.
     * This defines a weaker notion of equality between two patients.
     */
    public boolean isSamePatient(Patient otherPatient) {
        if (otherPatient == this) {
            return true;
        }

        return otherPatient != null
                && otherPatient.getName().equals(getName())
                && otherPatient.getPhone().equals(getPhone());
    }

    /**
     * Returns true if both patients have the same identity and data fields.
     * This defines a stronger notion of equality between two patients.
     */
    @Override
    public boolean equals(Object other) {
        // Person identity and data fields must be the same
        if (!super.equals(other)) {
            return false;
        }

        Patient otherPatient = (Patient) other;
        return dateOfBirth.equals(otherPatient.dateOfBirth)
                && gender.equals(otherPatient.gender);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(
            super.getName(),
            super.getPhone(),
            super.getEmail(),
            super.getAddress(),
            dateOfBirth,
            gender,
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
                .add("dob", dateOfBirth)
                .add("gender", gender)
                .add("tags", super.getTags())
                .toString();
    }

}
