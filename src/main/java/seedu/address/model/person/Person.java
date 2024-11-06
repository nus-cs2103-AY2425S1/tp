package seedu.address.model.person;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

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
    private final Address address;
    private final Fees fees;
    private final ClassId classId;
    private final Set<MonthPaid> monthsPaid = new HashSet<>();
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present.
     */
    public Person(Name name, Phone phone, Email email, Address address, Fees fees, ClassId classId,
                  Set<MonthPaid> monthsPaid, Set<Tag> tags) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.fees = fees;
        this.classId = classId;
        this.monthsPaid.addAll(monthsPaid);
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

    public Address getAddress() {
        return address;
    }

    public Fees getFees() {
        return fees;
    }

    public ClassId getClassId() {
        return classId;
    }


    /**
     * Returns an immutable monthPaid sortedset, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public SortedSet<MonthPaid> getMonthsPaid() {
        return Collections.unmodifiableSortedSet(new TreeSet<>(monthsPaid));
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
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns the string representation of {@code MonthsPaid} of this {@code Person}.
     * The string returned here is used for displaying on {@code PersonCode} and {@code info} pop-up window,
     * and for the {@code find} command.
     */
    public String getMonthsPaidToString() {
        return monthsPaid.stream()
                .map(monthPaid -> monthPaid.monthPaidValue)
                .reduce((curr, next) -> curr + ", " + next)
                .orElse("");
    }

    /**
     * Returns the string representation of {@code Tags} of this {@code Person}.
     * The string returned here is used for displaying in the window pop-up for the {@code info} command,
     * and for the {@code find} command.
     */
    public String getTagsToString() {
        return tags.stream()
                .map(tag -> tag.tagName)
                .reduce((curr, next) -> curr + ", " + next)
                .orElse("");
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
        return Objects.equals(name, otherPerson.name)
                && Objects.equals(phone, otherPerson.phone)
                && Objects.equals(email, otherPerson.email)
                && Objects.equals(address, otherPerson.address)
                && Objects.equals(fees, otherPerson.fees)
                && Objects.equals(classId, otherPerson.classId)
                && Objects.equals(tags, otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, fees, classId, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("fees", fees)
                .add("classId", classId)
                .add("monthsPaid", monthsPaid)
                .add("tags", tags)
                .toString();
    }

}
