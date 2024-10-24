package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
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
    private final Appointment appointment;
    private final Birthday birthday;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private List<Policy> policies = new ArrayList<>();


    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags,
                  Appointment appointment, Birthday birthday) {
        requireAllNonNull(name, phone, email, address, appointment, birthday);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.appointment = appointment;
        this.birthday = birthday;

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

    public Appointment getAppointment() {
        return appointment;
    }

    public Birthday getBirthday() {
        return birthday;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }


    /**
     * Assigns a policy to the list of policies.
     *
     * @param policy the policy to be assigned
     * @return true if the policy was successfully added, false if the policy already exists
     */
    public boolean assignPolicy(Policy policy) {
        for (Policy p : policies) {
            if (p.isSamePolicy(policy)) {
                return false;
            }
        }
        policies.add(policy);
        return true;

    }

    public void removePolicy(Policy policy) {
        policies.remove(policy);
    }

    /**
     * Returns a string representation of all assigned policies.
     *
     * @return a string containing the details of each policy, separated by new lines
     */
    public String getPoliciesString() {
        StringBuilder sb = new StringBuilder();
        int count = 1;
        for (Policy policy : policies) {
            sb.append(String.format("%s.", count));
            sb.append(policy.toString()).append("\n");
            count++;
        }
        return sb.toString();
    }



    /**
     * Returns a comparator that compares persons by name in lexicographic order.
     */
    public static Comparator<Person> getNameComparator() {
        return Comparator.comparing(person -> person.getName().fullName.toLowerCase());
    }

    /**
     * Returns a comparator that compares persons by appointment date in chronological order.
     */
    public static Comparator<Person> getAppointmentDateComparator() {
        return Comparator.comparing(person -> person.getAppointment().value);
    }

    /**
     * Returns a comparator that compares persons by birthday in chronological order.
     */
    public static Comparator<Person> getBirthdayComparator() {
        return Comparator.comparing(person -> person.getBirthday().value);
    }

    /**
     * Returns a comparator that compares persons by next payment date in chronological order.
     */
    public static Comparator<Person> getPayDateComparator() {
        return (person1, person2) -> {
            boolean person1HasPolicies = person1.getPolicies().size() > 0;
            boolean person2HasPolicies = person2.getPolicies().size() > 0;

            if (!person1HasPolicies && !person2HasPolicies) {
                return 0;
            } else if (!person1HasPolicies) {
                return 1;
            } else if (!person2HasPolicies) {
                return -1;
            } else {
                return person1.getPolicies().get(0).getPolicyPaymentDueDate()
                        .compareTo(person2.getPolicies().get(0).getPolicyPaymentDueDate());
            }
        };
    }

    public static Comparator<Person> getReversedPayDateComparator() {
        return (person1, person2) -> {
            boolean person1HasPolicies = person1.getReversedPolicies().size() > 0;
            boolean person2HasPolicies = person2.getReversedPolicies().size() > 0;

            if (!person1HasPolicies && !person2HasPolicies) {
                return 0;
            } else if (!person1HasPolicies) {
                return -1;
            } else if (!person2HasPolicies) {
                return 1;
            } else {
                return person1.getReversedPolicies().get(0).getPolicyPaymentDueDate()
                        .compareTo(person2.getReversedPolicies().get(0).getPolicyPaymentDueDate());
            }
        };
    }

    /**
     * Returns an immutable policy list, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<Policy> getPolicies() {
        policies.sort(Policy.getPolicyPaymentDueDateComparator());
        return Collections.unmodifiableList(policies);
    }

    public List<Policy> getReversedPolicies() {
        policies.sort(Policy.getPolicyPaymentDueDateComparator().reversed());
        return Collections.unmodifiableList(policies);
    }

    /**
     * Sets the policy list for this object.
     *
     * @param policies the list of {@code Policy} objects to be set. This list will replace
     *                 any existing policies.
     */
    public void setPolicies(List<Policy> policies) {
        this.policies = policies;
    }

    /**
     * Returns true if both persons have the same name and address.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName())
                && otherPerson.getAddress().equals(getAddress());
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
                && tags.equals(otherPerson.tags)
                && birthday.equals(otherPerson.birthday);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, birthday, appointment);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("birthday", birthday)
                .add("appointment", appointment)
                .add("tags", tags)
                .add("policies", policies)
                .toString();
    }

}
