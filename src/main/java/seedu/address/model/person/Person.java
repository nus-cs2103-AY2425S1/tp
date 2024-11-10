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
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final DesiredRole desiredRole;
    private final Skills skills;
    private final Experience experience;
    private final Status status;
    private final Note note;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address,
                  DesiredRole desiredRole, Skills skills, Experience experience,
                  Status status, Note note, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, desiredRole, skills, experience, status, note, tags);

        assert name != null : "Name should not be null";
        assert phone != null : "Phone should not be null";
        assert email != null : "Email should not be null";
        assert address != null : "Address should not be null";
        assert desiredRole != null : "DesiredRole should not be null";
        assert skills != null : "Skills should not be null";
        assert experience != null : "Experience should not be null";
        assert status != null : "Status should not be null";
        assert note != null : "Note should not be null";
        assert tags != null : "Tags should not be null";

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.desiredRole = desiredRole;
        this.skills = skills;
        this.experience = experience;
        this.status = status;
        this.note = note;
        this.tags.addAll(tags);

        assert !tags.contains(null) : "Tags should not contain null values";
    }

    public Name getName() {
        return name;
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

    public DesiredRole getDesiredRole() {
        return desiredRole;
    }

    public Skills getSkills() {
        return skills;
    }

    public Experience getExperience() {
        return experience;
    }

    public Status getStatus() {
        return status;
    }

    public Note getNote() {
        return note;
    }

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
            && otherPerson.getName().equals(getName()) && otherPerson.getPhone().equals(getPhone());
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

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
            && phone.equals(otherPerson.phone)
            && email.equals(otherPerson.email)
            && address.equals(otherPerson.address)
            && desiredRole.equals(otherPerson.desiredRole)
            && skills.equals(otherPerson.skills)
            && experience.equals(otherPerson.experience)
            && status.equals(otherPerson.status)
            && note.equals(otherPerson.note)
            && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone, email, address, desiredRole, skills, experience, status, note, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("name", name)
            .add("phone", phone)
            .add("email", email)
            .add("address", address)
            .add("desiredRole", desiredRole)
            .add("skills", skills)
            .add("experience", experience)
            .add("status", status)
            .add("note", note)
            .add("tags", tags)
            .toString();
    }
}
