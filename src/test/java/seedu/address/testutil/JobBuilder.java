package seedu.address.testutil;

import static seedu.address.testutil.TypicalCompanies.NUS;

import seedu.address.model.company.Company;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobCompany;
import seedu.address.model.job.JobDescription;
import seedu.address.model.job.JobName;
import seedu.address.model.job.JobRequirements;
import seedu.address.model.job.JobSalary;

/**
 * A utility class to help with building Job objects.
 */
public class JobBuilder {

    public static final String DEFAULT_JOB_NAME = "Software Engineer";
    public static final Company DEFAULT_COMPANY = NUS;
    public static final String DEFAULT_JOB_SALARY = "5000";
    public static final String DEFAULT_JOB_REQUIREMENTS = "Java, Python, C++";
    public static final String DEFAULT_JOB_DESCRIPTION = "Develop software solutions";
    private JobName jobName;
    private JobCompany jobCompany;
    private JobSalary jobSalary;
    private JobRequirements jobRequirements;
    private JobDescription jobDescription;
    /**
     * Constructs a new JobBuilder with the default values.
     */
    public JobBuilder() {
        jobName = new JobName(DEFAULT_JOB_NAME);
        jobCompany = new JobCompany(DEFAULT_COMPANY.toString());
        jobSalary = new JobSalary(DEFAULT_JOB_SALARY);
        jobRequirements = new JobRequirements(DEFAULT_JOB_REQUIREMENTS);
        jobDescription = new JobDescription(DEFAULT_JOB_DESCRIPTION);
    }

    /**
     * Sets the {@code JobName} of the {@code Job} that we are building.
     */
    public JobBuilder withJobName(String name) {
        this.jobName = new JobName(name);
        return this;
    }

    /**
     * Sets the {@code JobCompany} of the {@code Job} that we are building.
     */
    public JobBuilder withCompany(Company company) {
        this.jobCompany = new JobCompany(company.toString());
        return this;
    }

    /**
     * Builds a Job with the initialized attributes.
     *
     * @return Job object.
     */
    public Job build() {
        return new Job(jobName, jobCompany, jobSalary, jobRequirements, jobDescription);
    }
}
