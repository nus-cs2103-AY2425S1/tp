package seedu.address.model.job;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Job Listing in the address book.
 */
public class Job {
    private final String name;
    private final String company;
    private final String salary;
    private final String requirements;
    private final String description;

    /**
     * TODO: Implement data validation
     * Every field has no validation for now.
     */
    public Job(String name, String company, String salary, String requirements, String description) {
        this.name = name;
        this.company = company;
        this.salary = salary;
        this.requirements = requirements;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public String getSalary() {
        return salary;
    }

    public String getRequirements() {
        return requirements;
    }

    public String getDescription() {
        return description;
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
