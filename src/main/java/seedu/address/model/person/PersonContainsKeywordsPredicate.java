package seedu.address.model.person;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class PersonContainsKeywordsPredicate implements Predicate<Person> {
    private final String name;
    private final String phone;
    private final String email;
    private final String role;
    private final String major;
    private final String address;
    private final List<String> tags;

    /**
     * Constructs a Predicate with String values of each parameter.
     *
     * @param name Name of person in String.
     * @param phone Phone Number of person in String.
     * @param email Email of person in String.
     * @param role Role of person in String.
     * @param major Major of person in String.
     * @param tags Tags of person in List of String.
     */
    public PersonContainsKeywordsPredicate(String name, String phone, String email,
                                           String role, String major, String address, List<String> tags) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.role = role;
        this.major = major;
        this.address = address;
        this.tags = tags;
    }

    @Override
    public boolean test(Person person) {
        return (name == null || StringUtil.containsWordIgnoreCase(person.getName().fullName, name))
                && (phone == null || StringUtil.containsWordIgnoreCase(person.getPhone().value, phone))
                && (email == null || StringUtil.containsWordIgnoreCase(person.getEmail().value, email))
                && (role == null || StringUtil.containsWordIgnoreCase(person.getRole().toString(), role))
                && (major == null || StringUtil.containsWordIgnoreCase(person.getMajor().toString(), major))
                && (address == null || StringUtil.containsWordIgnoreCase(person.getAddress().value, address))
                && (tags == null
                || tags.isEmpty()
                || tags.stream().allMatch(tag -> person
                .getTags().stream()
                .anyMatch(personTag -> StringUtil.containsWordIgnoreCase(personTag.tagName, tag))));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonContainsKeywordsPredicate)) {
            return false;
        }

        PersonContainsKeywordsPredicate otherPredicate = (PersonContainsKeywordsPredicate) other;
        return Objects.equals(name, otherPredicate.name)
                && Objects.equals(phone, otherPredicate.phone)
                && Objects.equals(email, otherPredicate.email)
                && Objects.equals(role, otherPredicate.role)
                && Objects.equals(major, otherPredicate.major)
                && Objects.equals(address, otherPredicate.address)
                && Objects.equals(tags, otherPredicate.tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name != null ? name : "null")
                .add("phone", phone != null ? phone : "null")
                .add("email", email != null ? email : "null")
                .add("role", role != null ? role : "null")
                .add("major", major != null ? major : "null")
                .add("address", address != null ? address : "null")
                .add("tags", tags != null ? tags : "null")
                .toString();
    }
}
