package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated,
 * immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Nric nric;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Set<Role> roles = new HashSet<>();

    // Dependents
    private final Set<Nric> caregivers = new HashSet<>();
    private final Set<Nric> patients = new HashSet<>();

    private final Set<Appointment> appointments = new HashSet<>();


    private final List<Note> notes = new ArrayList<>();



    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Nric nric, Phone phone, Email email, Address address,
            Set<Tag> tags, Set<Role> roles) {
        requireAllNonNull(name, nric, phone, email, address, tags, roles);
        this.name = name;
        this.nric = nric;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.roles.addAll(roles);
    }

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Nric nric, Phone phone, Email email, Address address,
            Set<Tag> tags, Set<Role> roles, Set<Nric> caregivers, Set<Nric> patients) {
        requireAllNonNull(name, nric, phone, email, address, tags, roles);
        this.name = name;
        this.nric = nric;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.roles.addAll(roles);
        this.caregivers.addAll(caregivers);
        this.patients.addAll(patients);
    }

    /**
     * Constructor to make a deep copy of a Person object.
     */
    public Person(Person otherperson) {
        this.name = otherperson.getName();
        this.nric = otherperson.getNric();
        this.phone = otherperson.getPhone();
        this.email = otherperson.getEmail();
        this.address = otherperson.getAddress();
        this.tags.addAll(otherperson.getTags());
        this.roles.addAll(otherperson.getRoles());
        this.caregivers.addAll(otherperson.getCaregivers());
        this.patients.addAll(otherperson.getPatients());
    }

    public Name getName() {
        return name;
    }

    public Nric getNric() {
        return nric;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public boolean hasCaregiver(Nric caregiver) {
        return caregivers.contains(caregiver);
    }

    public boolean hasPatient(Nric patient) {
        return patients.contains(patient);
    }
    public boolean hasRole(Role role) {
        return this.roles.contains(role);
    }
    public boolean hasTag(Tag tag) {
        return this.tags.contains(tag);
    }

    /**
     * Returns true if {@code Name} of this {@code Person} contains {@code Name} of parameter
     */
    public boolean hasName(Name name) {
        return this.name.fullName.toUpperCase().contains(name.fullName.toUpperCase());
    }
    public boolean hasNric(Nric nric) {
        return this.nric.equals(nric);
    }
    public void addCaregiver(Nric caregiver) {
        caregivers.add(caregiver);
    }

    public void addPatient(Nric patient) {
        patients.add(patient);
    }

    /**
     * Removes the specified caregiver from the list of caregivers if the caregiver is present.
     *
     * @param caregiver The caregiver to be removed.
     */
    public void removeCaregiver(Nric caregiver) {
        if (this.hasCaregiver(caregiver)) {
            caregivers.remove(caregiver);
        }

    }

    /**
     * Removes the specified patient from the list of patients if the patient is present.
     *
     * @param patient The patient to be removed.
     */
    public void removePatient(Nric patient) {
        if (this.hasPatient(patient)) {
            patients.remove(patient);
        }
    }

    /**
     * Returns an immutable tag set, which throws
     * {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an immutable role set, which throws
     * {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Role> getRoles() {
        return Collections.unmodifiableSet(roles);
    }

    /**
     * Returns an immutable set of caregivers, which throws
     * {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Nric> getCaregivers() {
        return Collections.unmodifiableSet(caregivers);
    }

    /**
     * Returns an immutable set of patients, which throws
     * {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Nric> getPatients() {
        return Collections.unmodifiableSet(patients);
    }

    /**
     * Returns an immutable list of notes, which throws
     * {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<Note> getNotes() {
        return List.copyOf(notes);
    }

    /**
     * Adds a note to this person.
     *
     * @param note the note to add.
     */
    public void addNote(Note note) {
        requireNonNull(note);
        notes.add(note);
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
                && otherPerson.getNric().equals(getNric());
    }

    /**
     * Returns an immutable set of appointments, which throws
     * {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Appointment> getAppointments() {
        return Collections.unmodifiableSet(appointments);
    }

    /**
     * Adds an appointment to the list of appointments.
     *
     * @param appointment The appointment to be added.
     */
    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    /**
     * Returns true if the person has the specified appointment.
     * @param appointment The appointment to be checked.
     * @return true if the person has the specified appointment.
     */
    public boolean hasAppointment(Appointment appointment) {
        return appointments.contains(appointment);
    }

    /**
     * Removes the specified appointment from the list of appointments if the appointment is present.
     *
     * @param appointment The appointment to be removed.
     */
    public void removeAppointment(Appointment appointment) {
        appointments.remove(appointment);
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
        return name.equals(otherPerson.name)
                && nric.equals(otherPerson.nric)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, nric, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("nric", nric)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .add("roles", roles)
                .toString();
    }

}
