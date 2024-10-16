package seedu.address.testutil;

import seedu.address.model.common.Name;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobCompany;
import seedu.address.model.job.JobDescription;
import seedu.address.model.job.JobRequirements;
import seedu.address.model.job.JobSalary;

/**
 * A utility class to help with building Job objects.
 */
public class JobBuilder {

    public static final String DEFAULT_NAME = "Waiter";
    public static final String DEFAULT_COMPANY = "Starbucks";
    public static final String DEFAULT_SALARY = "2500";
    public static final String DEFAULT_REQUIREMENTS = "Hands and feet";
    public static final String DEFAULT_DESCRIPTION =
            "At Starbucks, we are looking for someone who brings a lot to the table";

    private Name name;
    private JobCompany company;
    private JobSalary salary;
    private JobRequirements requirements;
    private JobDescription description;

    /**
     * Creates a {@code JobBuilder} with the default details.
     */
    public JobBuilder() {
        name = new Name(DEFAULT_NAME);
        company = new JobCompany(DEFAULT_COMPANY);
        salary = new JobSalary(DEFAULT_SALARY);
        requirements = new JobRequirements(DEFAULT_REQUIREMENTS);
        description = new JobDescription(DEFAULT_DESCRIPTION);
    }

    /**
     * Initializes the JobBuilder with the data of {@code JobToCopy}.
     */
    public JobBuilder(Job jobToCopy) {
        name = jobToCopy.getName();
        company = jobToCopy.getCompany();
        salary = jobToCopy.getSalary();
        requirements = jobToCopy.getRequirements();
        description = jobToCopy.getDescription();
    }

    /**
     * Sets the {@code Name} of the {@code Job} that we are building.
     */
    public JobBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code Company} into a {@code Set<Skill>} and set it to the {@code Job} that we are building.
     */
    public JobBuilder withCompany(String company) {
        this.company = new JobCompany(company);
        return this;
    }

    /**
     * Sets the {@code JobSalary} of the {@code Job} that we are building.
     */
    public JobBuilder withSalary(String role) {
        this.salary = new JobSalary(role);
        return this;
    }

    /**
     * Sets the {@code JobRequirements} of the {@code Job} that we are building.
     */
    public JobBuilder withRequirements(String requirements) {
        this.requirements = new JobRequirements(requirements);
        return this;
    }

    /**
     * Sets the {@code JobDescription} of the {@code Job} that we are building.
     */
    public JobBuilder withDescription(String description) {
        this.description = new JobDescription(description);
        return this;
    }

    public Job build() {
        return new Job(name, company, salary, requirements, description);
    }

}
