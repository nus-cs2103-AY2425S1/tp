package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobCompany;
import seedu.address.model.job.JobDescription;
import seedu.address.model.job.JobName;
import seedu.address.model.job.JobRequirements;
import seedu.address.model.job.JobSalary;

/**
 * Jackson-friendly version of {@link Job}.
 */
class JsonAdaptedJob {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Job's %s field is missing!";

    private final String name;
    private final String company;
    private final String salary;
    private final String requirements;
    private final String description;

    /**
     * Constructs a {@code JsonAdaptedJob} with the given job details.
     */
    @JsonCreator
    public JsonAdaptedJob(@JsonProperty("name") String name, @JsonProperty("company") String company, @JsonProperty(
            "salary") String salary, @JsonProperty("requirements") String requirements,
            @JsonProperty("description") String description) {
        this.name = name;
        this.company = company;
        this.salary = salary;
        this.requirements = requirements;
        this.description = description;
    }

    /**
     * Converts a given {@code Job} into this class for Jackson use.
     */
    public JsonAdaptedJob(Job source) {
        name = source.getName().value;
        company = source.getCompany().value;
        salary = String.valueOf(source.getSalary().value);
        requirements = source.getRequirements().value;
        description = source.getDescription().value;
    }

    /**
     * Converts this Jackson-friendly adapted job object into the model's {@code Job} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted job.
     */
    public Job toModelType() throws IllegalValueException {

        // TODO: Input validation
        JobName modelJobName = new JobName(name);
        JobCompany modelJobCompany = new JobCompany(company);
        JobSalary modelJobSalary = new JobSalary(salary);
        JobRequirements modelJobRequirements = new JobRequirements(requirements);
        JobDescription modelJobDescription = new JobDescription(description);

        return new Job(modelJobName, modelJobCompany, modelJobSalary, modelJobRequirements, modelJobDescription);
    }

}
