package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

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
    private final JobCode jobCode;
    private final Tag tag;
    private final Remark remark;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, JobCode jobCode, Tag tag, Remark remark) {
        requireAllNonNull(name, phone, email, jobCode, tag, remark);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.jobCode = jobCode;
        this.tag = tag;
        this.remark = remark;
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

    public JobCode getJobCode() {
        return jobCode;
    }

    public Tag getTag() {
        return tag;
    }

    public Remark getRemark() {
        return remark;
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        if (otherPerson == null) {
            return false;
        }

        return name.equals(otherPerson.name)
                && (phone.equals(otherPerson.phone) || email.equals(otherPerson.email));
    }

    /**
     * Returns true if both persons have the same name and email, or the same name and phone number.
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
                && (phone.equals(otherPerson.phone) || email.equals(otherPerson.email));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, jobCode, tag, remark);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("jobCode", jobCode)
                .add("tag", tag)
                .add("remark", remark)
                .toString();
    }

}
