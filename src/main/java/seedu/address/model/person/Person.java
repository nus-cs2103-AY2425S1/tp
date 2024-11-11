package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

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
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.tags.addAll(tags);
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

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an immutable tag stream with order, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Stream<Tag> getOrderedTags() {
        return tags.stream().sorted(Comparator.comparing(tag -> tag.tagName));
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
     * Checks for duplicates in emails in the contact list.
     * @param otherPerson The Person object to compare against.
     * @return True if both persons have the same email address.
     */
    public boolean hasSameEmail(Person otherPerson) {
        return otherPerson != null && otherPerson.getEmail().equals(this.getEmail());
    }

    /**
     * Checks for duplicates in phone numbers in the contact list.
     * @param otherPerson The Person object to compare against.
     * @return True if both persons have the same phone number.
     */
    public boolean hasSamePhoneNumber(Person otherPerson) {
        return otherPerson != null && otherPerson.getPhone().equals(this.getPhone());
    }

    /**
     * Checks for duplicated contact information between Person instances.
     * This is to avoid adding duplicate contacts in the contact list.
     * Checked fields: name, phone, email.
     * @param otherPerson The Person object to compare against.
     * @return True if two contacts are considered as duplicates.
     */
    public boolean hasDuplicateInfo(Person otherPerson) {
        return this.isSamePerson(otherPerson)
                || this.hasSameEmail(otherPerson)
                || this.hasSamePhoneNumber(otherPerson);
    }

    /**
     * Checks whether the person has the tags with the same tag name.
     */
    public boolean hasTag(Tag t) {
        return this.getTags().contains(t);
    }

    /**
     * Removes a tag from a person.
     */
    public Person removeTag(Tag t) {
        Set<Tag> newTagSet = new HashSet<>();
        newTagSet.addAll(this.getTags());
        newTagSet.remove(t);
        return new Person(this.name, this.phone, this.email, newTagSet);
    }

    /**
     * Adds a tag into a person.
     * Assumes the person does not contain any tag in the new tag set.
     */
    public Person addTag(Set<? extends Tag> tagSet) {
        Set<Tag> newTagSet = new HashSet<>();
        newTagSet.addAll(this.getTags());
        newTagSet.addAll(tagSet);
        return new Person(this.name, this.phone, this.email, newTagSet);
    }

    /**
     * Overwrites the person's tag set.
     */
    public Person setAllTags(Set<Tag> tagSet) {
        return new Person(this.name, this.phone, this.email, tagSet);
    }

    /**
     * Returns true if person contain any tag in the new tag set.
     */
    public boolean containsDuplicateTag(Set<? extends Tag> tagSet) {
        for (Tag t : this.tags) {
            if (tagSet.contains(t)) {
                return true;
            }
        }
        return false;
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
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("tags", tags)
                .toString();
    }

}
