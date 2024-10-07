package seedu.address.model.job;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Job Listing in the address book.
 */
public class Job {
    private final JobName name;
    private final JobCompany company;
    private final JobSalary salary;
    private final JobRequirements requirements;
    private final JobDescription description;

    /**
     * TODO: Implement data validation
     * Every field has no validation for now.
     */
    public Job(JobName name, JobCompany company, JobSalary salary, JobRequirements requirements,
            JobDescription description) {
        this.name = name;
        this.company = company;
        this.salary = salary;
        this.requirements = requirements;
        this.description = description;
    }

    public JobName getName() {
        return name;
    }

    public JobCompany getCompany() {
        return company;
    }

    public JobSalary getSalary() {
        return salary;
    }

    public JobRequirements getRequirements() {
        return requirements;
    }

    public JobDescription getDescription() {
        return description;
    }

    /** Returns true if both jobs have the same name and company. */
    public boolean isSameJob(Job otherJob) {
        if (otherJob == this) {
            return true;
        }

        return otherJob != null && otherJob.getName().equals(getName()) && otherJob.getCompany().equals(getCompany());
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
        return name.equals(otherJob.name) && company.equals(otherJob.company) && salary.equals(otherJob.salary)
               && requirements.equals(otherJob.requirements) && description.equals(otherJob.description);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("name", name)
                                        .add("company", company)
                                        .add("salary", salary)
                                        .add("requirements", requirements)
                                        .add("description", description)
                                        .toString();
    }
}
