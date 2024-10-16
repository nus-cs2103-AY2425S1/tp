package seedu.address.model.person;

import seedu.address.model.tag.Tag;

import java.util.Set;

public class Tutee extends Person {
    public Tutee(Name name, Phone phone, Email email, Address address, Hours hours, Set<Tag> tags) {
        super(name, phone, email, address, hours, tags);
    }

    @Override
    public boolean isTutor() {
        return false;
    }

    @Override
    public boolean isTutee() {
        return true;
    }

    @Override
    public boolean equals(Object other) {

        if (other == this) {
            return true;
        }

        // instanceof handles nulls
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

    @Override
    public String toString() {
        return "Tutee: " + super.toString();
    }

}