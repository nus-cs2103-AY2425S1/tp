package seedu.address.testutil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.common.Name;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobCompany;
import seedu.address.model.job.JobDescription;
import seedu.address.model.job.JobSalary;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building Job objects.
 */
public class JobBuilder {

    public static final String DEFAULT_NAME = "Software Engineer";
    public static final String DEFAULT_COMPANY = "NUS";
    public static final String DEFAULT_SALARY = "5000";
    public static final String[] DEFAULT_REQUIREMENTS = {"Java", "Python", "C"};
    public static final String DEFAULT_DESCRIPTION = "Develop software solutions";

    private Name name;
    private JobCompany company;
    private JobSalary salary;
    private Set<Tag> requirements = new HashSet<>();
    private JobDescription description;

    /**
     * Creates a {@code JobBuilder} with the default details.
     */
    public JobBuilder() {
        name = new Name(DEFAULT_NAME);
        company = new JobCompany(DEFAULT_COMPANY);
        salary = new JobSalary(DEFAULT_SALARY);
        description = new JobDescription(DEFAULT_DESCRIPTION);
        requirements = Arrays.stream(DEFAULT_REQUIREMENTS).map(Tag::new).collect(Collectors.toSet());
    }

    /**
     * Initializes the JobBuilder with the data of {@code JobToCopy}.
     */
    public JobBuilder(Job jobToCopy) {
        name = jobToCopy.getName();
        company = jobToCopy.getCompany();
        salary = jobToCopy.getSalary();
        description = jobToCopy.getDescription();
        requirements = jobToCopy.getRequirements();
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
    public JobBuilder withRequirements(String... requirements) {
        this.requirements = Arrays.stream(requirements).map(Tag::new).collect(Collectors.toSet());
        return this;
    }

    /**
     * Sets the {@code JobDescription} of the {@code Job} that we are building.
     */
    public JobBuilder withDescription(String description) {
        this.description = new JobDescription(description);
        return this;
    }


    /**
     * Builds a Job with the initialized attributes.
     *
     * @return Job object.
     */
    public Job build() {
        return new Job(name, company, salary, description, requirements);
    }

}
