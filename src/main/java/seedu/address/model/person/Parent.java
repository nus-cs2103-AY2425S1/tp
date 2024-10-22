package seedu.address.model.person;

import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a parent in the address book
 */
public class Parent extends Person {
    private Student child = null;

    /**
     * Constructs a {@code Parent} with the given details.
     */
    public Parent(Name name, Phone phone, Email email, Address address, Student child, Set<Tag> tags) {
        super(name, phone, email, address, tags);
        this.child = child;
    }

    /**
     * Constructs a {@code Parent} with the given {@code Person} as a base.
     */
    public Parent(Person person, Student child) {
        super(person.getName(), person.getPhone(), person.getEmail(), person.getAddress(), person.getTags());
        this.child = child;
    }

    public void setChild(Student child) {
        this.child = child;
    }

    public Student getChild() {
        return child;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Parent)) {
            return false;
        }

        Parent otherParent = (Parent) other;
        return super.equals(otherParent) && getChild().equals(otherParent.getChild());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName(), this.getPhone(), this.getEmail(), this.getAddress(),
                this.getChild(), this.getTags());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", this.getName())
                .add("phone", this.getPhone())
                .add("email", this.getEmail())
                .add("address", this.getAddress())
                .add("child", this.getChild())
                .add("tags", this.getTags())
                .toString();
    }

    @Override
    public String toMessageString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Phone: ")
                .append(getPhone())
                .append("; Email: ")
                .append(getEmail())
                .append("; Address: ")
                .append(getAddress())
                .append("; Child: ")
                .append(getChild())
                .append("; Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
