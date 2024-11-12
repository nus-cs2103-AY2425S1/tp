package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.common.Name;
import seedu.address.model.skill.Skill;

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
    private final Role role;
    private final Set<Skill> skills = new HashSet<>();
    private final Optional<String> match;

    /**
     * Every parameter must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Role role, Set<Skill> skills) {
        requireAllNonNull(name, phone, email, role, skills);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.role = role;
        this.skills.addAll(skills);
        this.match = Optional.empty();
    }

    /**
     * Creates a person with the matching job.
     */
    public Person(Name name, Phone phone, Email email, Role role, Set<Skill> skills, String match) {
        requireAllNonNull(name, phone, email, role, skills, match);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.role = role;
        this.skills.addAll(skills);
        this.match = Optional.of(match);
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

    public Role getRole() {
        return role;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Skill> getSkills() {
        return Collections.unmodifiableSet(skills);
    }

    /**
     * Returns a {@code String} representing a single association between {@code Job} and {@code Person} if it exists,
     * null otherwise.
     */
    public Optional<String> getMatch() {
        return match;
    }

    /**
     * Returns true if this person has any job matches, returns false otherwise.
     */
    public boolean isMatchPresent() {
        return match.isPresent();
    }

    /**
     * Checks if this person has matched with the specified job.
     *
     * @param jobIdentifier A string that uniquely identify a job.
     */
    public boolean hasMatched(String jobIdentifier) {
        return match.map(s -> s.equals(jobIdentifier)).orElse(false);
    }

    /**
     * Returns a string of the person's employment status to be used within a UI component.
     */
    public String getMatchToUiText() {
        if (!isMatchPresent()) {
            return "Unemployed";
        }
        String[] jobIdentifierComponents = match.get().split("::");

        // When this method is called, assert to check if match is in a valid format.
        assert(jobIdentifierComponents.length == 2);
        String companyName = jobIdentifierComponents[0];
        String jobName = jobIdentifierComponents[1];
        assert(!(companyName.isEmpty() || jobName.isEmpty()));

        return ("Employed @ " + companyName + " - " + jobName);
    }

    /**
     * Returns true if both persons have the same contact or email.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && (otherPerson.getEmail().equals(getEmail()) || otherPerson.getPhone().equals(getPhone()));
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
                && role.equals(otherPerson.role)
                && skills.equals(otherPerson.skills)
                && match.equals(otherPerson.match);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, role, skills, match);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("role", role)
                .add("skills", skills)
                .toString();
    }

}
