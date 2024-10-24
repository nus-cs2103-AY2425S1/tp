package seedu.address.model.job;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.common.Name;
import seedu.address.model.tag.Tag;

/**
 * Represents a Job Listing in the address book.
 */
public class Job {
    private final Name name;
    private final JobCompany company;
    private final JobSalary salary;
    private final JobDescription description;
    private final Set<Tag> requirements = new HashSet<>();
    private final Set<String> matches = new HashSet<>();

    /**
     * TODO: Implement data validation
     * TODO: What is meant by this ah ^^?
     * Every field has no validation for now.
     */
    public Job(Name name, JobCompany company, JobSalary salary,
            JobDescription description, Set<Tag> requirements, Set<String> matches) {
        requireAllNonNull(name, company, salary, description);
        requireAllNonNull(requirements);
        requireAllNonNull(matches);
        this.name = name;
        this.company = company;
        this.salary = salary;
        this.description = description;
        this.requirements.addAll(requirements);
        this.matches.addAll(matches);
    }

    public Name getName() {
        return name;
    }

    public JobCompany getCompany() {
        return company;
    }

    public JobSalary getSalary() {
        return salary;
    }

    public JobDescription getDescription() {
        return description;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getRequirements() {
        return Collections.unmodifiableSet(requirements);
    }

    /**
     *  Returns true if both jobs have the same name and company.
     */
    public boolean isSameJob(Job otherJob) {
        if (otherJob == this) {
            return true;
        }

        return otherJob != null && otherJob.getName().equals(getName()) && otherJob.getCompany().equals(getCompany());
    }

    /**
     * Returns a {@code HashSet} representing associations between {@code Job} and {@code Person}, which can be empty.
     */
    public Set<String> getMatches() {
        return matches;
    }

    /**
     * Returns true if the contacts phone number is in matches.
     */
    public boolean hasMatched(String contactPhoneNumber) {
        return this.matches.contains(contactPhoneNumber);
    }

    /**
     * Returns a string that identify the Job object.
     */
    public String getIdentifier() {
        return company.toString() + "::" + name;
    }

    /**
     * Removes the association of a single person from the Job object.
     * Guaranteed to not throw an exception so long as the input is a {@code String} or {@code null}.
     */
    public void removeMatch(String identifier) {
        matches.remove(identifier);
    }

    /**
     * Returns true if both jobs have the same identity and data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Job)) {
            return false;
        }

        Job otherJob = (Job) other;
        return name.equals(otherJob.name)
                && company.equals(otherJob.company) && salary.equals(otherJob.salary)
                && requirements.equals(otherJob.requirements) && description.equals(otherJob.description)
                && matches.equals(otherJob.matches);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("name", name)
                                        .add("company", company)
                                        .add("salary", salary)
                                        .add("description", description)
                                        .add("requirements", requirements)
                                        .toString();
    }

}
