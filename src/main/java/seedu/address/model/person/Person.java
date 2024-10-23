package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.skill.Skill;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {
    public static final Tag DEFAULT_TAG_PENDING = new Tag("pending");
    public static final Tag TAG_HIRED = new Tag("hired");
    public static final Tag TAG_REJECTED = new Tag("rejected");

    // Identity fields
    private final Name name;
    private final Job job;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Set<Skill> skills = new HashSet<>();
    private final InterviewScore interviewScore;
    private final Set<Tag> tags = new HashSet<>();

    // Status fields
    private String status;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Job job, Phone phone, Email email, Set<Skill> skills,
                  InterviewScore interviewScore, Set<Tag> tags) {
        requireAllNonNull(name, job, phone, email, skills, interviewScore, tags);
        this.name = name;
        this.job = job;
        this.phone = phone;
        this.email = email;
        this.skills.addAll(skills);
        this.interviewScore = interviewScore;
        this.tags.addAll(tags);
        if (!isHired() && !isRejected()) {
            this.tags.add(DEFAULT_TAG_PENDING);
        }
    }

    public Name getName() {
        return name;
    }

    public Job getJob() {
        return job;
    }
    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    /**
     * Returns an immutable skill set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Skill> getSkills() {
        return Collections.unmodifiableSet(skills);
    }

    public void addSkill(Skill skill) {
        skills.add(skill);
    }

    public void removeSkill(Skill skill) {
        skills.remove(skill);
    }

    public InterviewScore getInterviewScore() {
        return interviewScore;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Marks the person as hired.
     * Sets the status to "hired", removes the "rejected" and "pending" tags if present,
     * and adds the "hired" tag.
     */
    public void markAsHired() {
        this.status = "hired";
        removeTag(TAG_REJECTED);
        removeTag(DEFAULT_TAG_PENDING);
        addTag(TAG_HIRED);
    }


    public boolean isHired() {
        return tags.contains(TAG_HIRED);
    }

    /**
     * Marks the person as rejected.
     * Sets the status to "rejected", removes the "pending" and "hired" tags if present,
     * and adds the "rejected" tag.
     */
    public void markAsRejected() {
        this.status = "rejected";
        removeTag(DEFAULT_TAG_PENDING);
        removeTag(TAG_HIRED);
        addTag(TAG_REJECTED);
    }

    public boolean isRejected() {
        return tags.contains(TAG_REJECTED);
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
        if (!(other instanceof Person otherPerson)) {
            return false;
        }

        return name.equals(otherPerson.name)
                && job.equals(otherPerson.job)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && skills.equals(otherPerson.skills)
                && interviewScore.equals(otherPerson.interviewScore)
                && tags.equals(otherPerson.tags);
    }

    public boolean hasJobAndStatus(Name name, Job job) {
        return this.getName().isSameName(name) && this.getJob().isSameJob(job);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, job, phone, email, skills, interviewScore, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("job", job)
                .add("phone", phone)
                .add("email", email)
                .add("skills", skills)
                .add("interview score", interviewScore)
                .add("tags", tags)
                .toString();
    }

}
