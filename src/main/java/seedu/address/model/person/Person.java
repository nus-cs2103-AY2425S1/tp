package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.StudyGroupTag;

/**
 * Represents a Person in the address book. Guarantees: details are present and
 * not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Email email;

    // Data fields
    private final Gender gender;
    private final Age age;
    private final Detail detail;
    private final Set<StudyGroupTag> studyGroupTags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Email email, Gender gender, Age age, Detail detail, Set<StudyGroupTag> studyGroupTags) {
        requireAllNonNull(name, email, studyGroupTags);
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.detail = (detail != null) ? detail : new Detail("");
        this.studyGroupTags.addAll(studyGroupTags);
    }

    public Name getName() {
        return name;
    }

    public Email getEmail() {
        return email;
    }

    public Gender getGender() {
        return gender;
    }

    public Age getAge() {
        return age;
    }

    public Detail getDetail() {
        return detail;
    }

    /**
     * Returns an immutable study group tag set, which throws
     * {@code UnsupportedOperationException} if modification is attempted.
     */
    public Set<StudyGroupTag> getStudyGroupTags() {
        return Collections.unmodifiableSet(studyGroupTags);
    }

    /**
     * Returns true if both persons have the same email. This weaker notion of
     * equality between two persons allows researchers to differentiate participants
     * by email.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getEmail().equals(getEmail());
    }

    /**
     * Returns true if both persons have the same identity and data fields. This
     * defines a stronger notion of equality between two persons.
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
                && email.equals(otherPerson.email)
                && gender.equals(otherPerson.gender)
                && age.equals(otherPerson.age)
                && detail.equals(otherPerson.detail)
                && studyGroupTags.equals(otherPerson.studyGroupTags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, email, gender, age, detail, studyGroupTags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("email", email)
                .add("gender", gender)
                .add("age", age)
                .add("detail", detail)
                .add("study groups", studyGroupTags)
                .toString();
    }

}
