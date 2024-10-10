package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
//    private final Id id;
    private final Name name;
//    private final Ward ward;
//    private final Diagnosis diagnosis;
//    private final Medication medication;
//    private final Phone phone;
//    private final Email email;
//
//    // Data fields
//    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name){
//        this.id = id;
        this.name = name;
//        this.ward = ward;
//        this.diagnosis = diagnosis;
//        this.medication = medication;
    }
//    public Id getId() {
//        return id;
//    }

    public Name getName() {
        return name;
    }

//    public Ward getWard() {
//        return ward;
//    }
//
//    public Diagnosis getDiagnosis() {
//        return diagnosis;
//    }
//
//    public Medication getMedication() {
//        return medication;
//    }

//    public Phone getPhone() {
//        return phone;
//    }
//
//    public Email getEmail() {
//        return email;
//    }
//
//    public Address getAddress() {
//        return address;
//    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
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

//        Person otherPerson = (Person) other;
//        return id.equals(otherPerson.id)
//                && name.equals(otherPerson.name)
//                && ward.equals(otherPerson.ward)
//                && diagnosis.equals(otherPerson.diagnosis)
//                && medication.equals(otherPerson.medication);
        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name);
    }

//    @Override
//    public String toString() {
//        return new ToStringBuilder(this)
//                .add("id", id)
//                .add("name", name)
//                .add("ward", ward)
//                .add("diagnosis", diagnosis)
//                .add("medication", medication)
//                .toString();
//    }
@Override
public String toString() {
    return new ToStringBuilder(this)
            .add("name", name)
            .toString();
}

}
