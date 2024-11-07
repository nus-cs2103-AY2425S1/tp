package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.common.Name;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobCompany;
import seedu.address.model.job.JobDescription;
import seedu.address.model.job.JobSalary;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Job}.
 */
class JsonAdaptedJob {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Job's %s field is missing!";

    private final String name;
    private final String company;
    private final String salary;
    private final String description;
    private final List<JsonAdaptedTag> requirements = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedJob} with the given job details.
     */
    @JsonCreator
    public JsonAdaptedJob(@JsonProperty("name") String name, @JsonProperty("company") String company,
                          @JsonProperty("salary") String salary, @JsonProperty("description") String description,
                          @JsonProperty("requirements") List<JsonAdaptedTag> requirements) {
        this.name = name;
        this.company = company;
        this.salary = salary;
        this.description = description;
        if (requirements != null) {
            this.requirements.addAll(requirements);
        }
    }

    /**
     * Converts a given {@code Job} into this class for Jackson use.
     */
    public JsonAdaptedJob(Job source) {
        name = source.getName().fullName;
        company = source.getCompany().fullName;
        salary = String.valueOf(source.getSalary().value);
        description = source.getDescription().value;
        requirements.addAll(source.getRequirements().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted job object into the model's {@code Job} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted job.
     */
    public Job toModelType() throws IllegalValueException {
        List<Tag> jobRequirements = new ArrayList<>();
        for (JsonAdaptedTag requirement : requirements) {
            jobRequirements.add(requirement.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (company == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    JobCompany.class.getSimpleName()));
        }
        if (!JobCompany.isValidCompany(company)) {
            throw new IllegalValueException(JobCompany.MESSAGE_CONSTRAINTS);
        }
        final JobCompany modelJobCompany = new JobCompany(company);

        if (salary == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    JobSalary.class.getSimpleName()));
        }
        if (!JobSalary.isValidSalary(salary)) {
            throw new IllegalValueException(JobSalary.MESSAGE_CONSTRAINTS);
        }
        final JobSalary modelJobSalary = new JobSalary(salary);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    JobDescription.class.getSimpleName()));
        }
        if (!JobDescription.isValidDescription(description)) {
            throw new IllegalValueException(JobDescription.MESSAGE_CONSTRAINTS);
        }
        final JobDescription modelJobDescription = new JobDescription(description);

        Set<Tag> modelJobRequirements = new HashSet<>(jobRequirements);

        return new Job(modelName, modelJobCompany, modelJobSalary, modelJobDescription, modelJobRequirements);
    }

}
