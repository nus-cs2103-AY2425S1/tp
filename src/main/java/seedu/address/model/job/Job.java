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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("company", company)
                .add("salary", salary)
                .add("requirements", requirements)
                .add("description", description)
                .toString();
    }
}
