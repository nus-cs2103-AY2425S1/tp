package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
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
    private final Schedule schedule;
    private final Set<Tag> tags = new HashSet<>();
    private SocialMedia socialMedia;

    /**
     * Every field must be present and not null.
     */
    public Person(
            Name name,
            Phone phone,
            Email email,
            Address address,
            Schedule schedule,
            SocialMedia socialMedia,
            Set<Tag> tags
    ) {
        requireAllNonNull(name, phone, email, address, schedule, socialMedia, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.schedule = schedule;
        this.tags.addAll(tags);
        this.socialMedia = socialMedia;
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

    public Schedule getSchedule() {
        return schedule;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public void setSocialMedia(SocialMedia socialMedia) {
        this.socialMedia = socialMedia;
    }

    public SocialMedia getSocialMedia() {
        return socialMedia;
    }

    public boolean hasSocialMedia() {
        return !socialMedia.getPlatform().equals(SocialMedia.Platform.UNNAMED);
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
     * Returns true if person already has a tag with the same name.
     */
    public boolean hasTag(String other) {
        Iterator<Tag> iterator = tags.iterator();
        boolean hasTag = false;
        while (iterator.hasNext()) {
            Tag tag = iterator.next();
            if (tag.getTagName().equals(other)) {
                hasTag = true;
            }
        }
        return hasTag;
    }

    /**
     * Returns true if person has all tags in others.
     */
    public boolean hasAllTags(Set<Tag> others) {
        boolean hasAll = true;
        Iterator<Tag> iterator = others.iterator();
        while (iterator.hasNext()) {
            Tag other = iterator.next();
            if (!this.hasTag(other.getTagName())) {
                hasAll = hasAll && false;
            }
        }
        return hasAll;
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
                && address.equals(otherPerson.address)
                && socialMedia.equals(otherPerson.socialMedia)
                && schedule.equals(otherPerson.schedule)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, schedule, tags, socialMedia);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("schedule", schedule)
                .add("tags", tags)
                .add("socialmedia", socialMedia)
                .toString();
    }

}
