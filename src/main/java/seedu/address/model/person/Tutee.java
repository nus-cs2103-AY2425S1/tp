package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Tutee in the application.
 */
public class Tutee extends Person {

    /**
     * Constructs a Tutee object.
     *
     * @param name The name of the Tutee.
     * @param phone The phone number of the Tutee.
     * @param email The email address of the Tutee.
     * @param address The residential address of the Tutee.
     * @param hours The hours the Tutee has been taught.
     * @param tags A set of tags associated with the Tutee.
     * @param subjects A set of subjects learned by the Tutee.
     */
    public Tutee(Name name, Phone phone, Email email, Address address, Hours hours, Set<Tag> tags,
                 Set<Subject> subjects) {
        super(name, phone, email, address, hours, tags, subjects);
    }

    public Tutee(int id, Name name, Phone phone, Email email, Address address, Hours hours, Set<Tag> tags,
                 Set<Subject> subjects) {
        super(id, name, phone, email, address, hours, tags, subjects);
    }

    /**
     * Returns {@code false} because this object represents a Tutee, not a Tutor.
     *
     * @return false.
     */
    @Override
    public boolean isTutor() {
        return false;
    }

    /**
     * Returns {@code true} because this object represents a Tutee.
     *
     * @return true.
     */
    @Override
    public boolean isTutee() {
        return true;
    }

    /**
     * Returns the role of the person as a string.
     *
     * @return "Tutee".
     */
    @Override
    public String getRole() {
        return "Tutee";
    }

    /**
     * Checks if this Tutee is equal to another object.
     * Two Tutees are considered equal if they have the same name, phone number, email, address, hours, and tags.
     *
     * @param other The other object to compare with.
     * @return {@code true} if this Tutee is equal to {@code other}, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Tutee)) {
            return false;
        }

        Tutee otherTutee = (Tutee) other;
        return this.getName().equals(otherTutee.getName())
                && this.getPhone().equals(otherTutee.getPhone())
                && this.getEmail().equals(otherTutee.getEmail())
                && this.getAddress().equals(otherTutee.getAddress())
                && this.getHours().equals(otherTutee.getHours())
                && this.getTags().equals(otherTutee.getTags());
    }

    /**
     * Returns a string representation of the Tutee.
     *
     * @return A string in the format "Tutee: " followed by the string representation of the superclass.
     */
    @Override
    public String toString() {
        return super.toString();
    }

}
