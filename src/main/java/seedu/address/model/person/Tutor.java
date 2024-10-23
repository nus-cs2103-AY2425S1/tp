package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.tag.Tag;


/**
 * Represents a Tutor in the application.
 * A Tutor is a Person who teaches others.
 */
public class Tutor extends Person {

    /**
     * Constructs a Tutor object.
     *
     * @param name The name of the Tutor.
     * @param phone The phone number of the Tutor.
     * @param email The email address of the Tutor.
     * @param address The residential address of the Tutor.
     * @param hours The hours the Tutor has taught.
     * @param tags A set of tags associated with the Tutor.
     * @param subjects A set of subjects taught by the Tutor.
     */
    public Tutor(Name name, Phone phone, Email email, Address address, Hours hours, Set<Tag> tags,
                 Set<Subject> subjects) {
        super(name, phone, email, address, hours, tags, subjects);
    }

    public Tutor(int id, Name name, Phone phone, Email email, Address address, Hours hours, Set<Tag> tags,
                 Set<Subject> subjects) {
        super(id, name, phone, email, address, hours, tags, subjects);
    }


    /**
     * Returns {@code true} because this object represents a Tutor.
     *
     * @return true.
     */
    @Override
    public boolean isTutor() {
        return true;
    }

    /**
     * Returns {@code false} because this object is a Tutor, not a Tutee.
     *
     * @return false.
     */
    @Override
    public boolean isTutee() {
        return false;
    }

    /**
     * Returns the role of the person as a string.
     *
     * @return "Tutor".
     */
    @Override
    public String getRole() {
        return "Tutor";
    }

    /**
     * Checks if this Tutor is equal to another object.
     * Two Tutors are considered equal if they have the same name, phone number, email, address, hours, and tags.
     *
     * @param other The other object to compare with.
     * @return {@code true} if this Tutor is equal to {@code other}, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Tutor)) {
            return false;
        }

        Tutor otherTutor = (Tutor) other;
        return this.getName().equals(otherTutor.getName())
                && this.getPhone().equals(otherTutor.getPhone())
                && this.getEmail().equals(otherTutor.getEmail())
                && this.getAddress().equals(otherTutor.getAddress())
                && this.getHours().equals(otherTutor.getHours())
                && this.getTags().equals(otherTutor.getTags());
    }

    /**
     * Returns a string representation of the Tutor.
     *
     * @return A string in the format "Tutor: " followed by the string representation of the superclass.
     */
    @Override
    public String toString() {
        return super.toString();
    }

}
