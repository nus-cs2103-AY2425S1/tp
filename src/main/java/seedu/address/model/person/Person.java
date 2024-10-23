package seedu.address.model.person;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.appointment.Appointment;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Id id;
    private final Ward ward;
    private final Diagnosis diagnosis;
    private final Medication medication;
    private final Appointment appointment;
    private final Notes notes;
    /*
    private final Set<Tag> tags = new HashSet<>();
     */

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Id id, Ward ward, Diagnosis diagnosis, Medication medication, Notes notes) {
        this.name = name;
        this.id = id;
        this.ward = ward;
        this.diagnosis = diagnosis;
        this.medication = medication;
        this.appointment = null;
        this.notes = notes;
    }
    /**
     * Overloaded Constructor for Appointment instances
     */
    public Person(Name name, Id id, Ward ward, Diagnosis diagnosis, Medication medication,
                  Notes notes, Appointment appointment) {
        this.name = name;
        this.id = id;
        this.ward = ward;
        this.diagnosis = diagnosis;
        this.medication = medication;
        this.notes = notes;
        this.appointment = appointment;
    }

    public Id getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public Ward getWard() {
        return ward;
    }

    public Diagnosis getDiagnosis() {
        return diagnosis;
    }


    public Medication getMedication() {
        return medication;
    }

    public Notes getNotes() {
        return this.notes;
    }

    public Appointment getAppointment() {
        return this.appointment;
    }

    /*
    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    /*
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }
     */

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getId().equals(getId());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
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

        Person otherPerson = (Person) other;
        return id.equals(otherPerson.id)
                && name.equals(otherPerson.name)
                && ward.equals(otherPerson.ward)
                && diagnosis.equals(otherPerson.diagnosis)
                && medication.equals(otherPerson.medication);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, id, ward, diagnosis, medication);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("id", id)
                .add("ward", ward)
                .add("diagnosis", diagnosis)
                .add("medication", medication)
                .add("notes", notes)
                .toString();
    }

}
