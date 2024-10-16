package seedu.address.model.person;

import seedu.address.model.tag.Tag;

import java.util.Set;

public class Tutor extends Person {
    public Tutor(Name name, Phone phone, Email email, Address address, Hours hours, Set<Tag> tags) {
        super(name, phone, email, address, hours, tags);
    }

    @Override
    public boolean isTutor() {
        return true;
    }

    @Override
    public boolean isTutee() {
        return false;
    }

    @Override
    public boolean equals(Object other) {

        if (other == this) {
            return true;
        }

        // instanceof handles nulls
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

    @Override
    public String toString() {
        return "Tutor: " + super.toString();
    }

}
