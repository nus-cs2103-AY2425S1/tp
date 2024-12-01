package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated,
 * immutable.
 */
public class Doctor {

    // Identity fields
    private final DoctorName name;
    private final Phone phone;
    private final Email email;

    /**
     * Every field must be present and not null.
     */
    public Doctor(DoctorName name, Phone phone, Email email) {
        requireAllNonNull(name, phone, email);
        this.name = name;
        this.phone = phone;
        this.email = email;

    }

    /**
     * Returns the name of the doctor.
     *
     * @return Name of the doctor.
     */
    public DoctorName getName() {
        return this.name;
    }

    /**
     * Returns the phone number of the doctor.
     *
     * @return Phone number of the doctor.
     */
    public Phone getPhone() {
        return this.phone;
    }

    /**
     * Returns the email of the doctor.
     *
     * @return Email of the doctor.
     */
    public Email getEmail() {
        return this.email;
    }

    /**
     * Returns true if both doctors have the same name.
     * This defines a weaker notion of equality between two doctors.
     */
    public boolean isSamePerson(Doctor otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both doctors have the same identity and data fields.
     * This defines a stronger notion of equality between two doctors.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Doctor)) {
            return false;
        }

        Doctor otherDoctor = (Doctor) other;
        return name.equals(otherDoctor.name)
                && phone.equals(otherDoctor.phone)
                && email.equals(otherDoctor.email);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(getName(), getPhone(), getEmail());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .toString();
    }

}
