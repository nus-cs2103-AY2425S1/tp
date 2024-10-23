package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Email;
import seedu.address.model.person.InterviewScore;
import seedu.address.model.person.Job;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.skill.Skill;
import seedu.address.model.tag.Tag;

/**
 * Json-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String job;
    private final String phone;
    private final String email;
    private final List<JsonAdaptedSkill> skills = new ArrayList<>();
    private final String interviewScore;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final String status;


    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("job") String job,
                             @JsonProperty("phone") String phone, @JsonProperty("email") String email,
                             @JsonProperty("skilled") List<JsonAdaptedSkill> skilled,
                             @JsonProperty("interviewScore") String interviewScore,
                             @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
                             @JsonProperty("status") String status) {
        this.name = name;
        this.job = job;
        this.phone = phone;
        this.email = email;
        if (skilled != null) {
            skilled.addAll(skilled);
        }
        this.interviewScore = interviewScore;
        if (tagged != null) {
            tagged.addAll(tagged);
        }
        this.status = status;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        job = source.getJob().jobName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        skills.addAll(source.getSkills().stream()
                .map(JsonAdaptedSkill::new)
                .toList());
        interviewScore = source.getInterviewScore().interviewScore;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .toList());
        status = source.getStatus();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }
        final List<Skill> personSkills = new ArrayList<>();
        for (JsonAdaptedSkill skill : skills) {
            personSkills.add(skill.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (job == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Job.class.getSimpleName()));
        }
        if (!Job.isValidJob(job)) {
            throw new IllegalValueException(Job.MESSAGE_CONSTRAINTS);
        }
        final Job modelJob = new Job(job);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        final Set<Skill> modelSkills = new HashSet<>(personSkills);

        if (interviewScore == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    InterviewScore.class.getSimpleName()));
        }
        if (!InterviewScore.isValidInterviewScore(interviewScore)) {
            throw new IllegalValueException(InterviewScore.MESSAGE_CONSTRAINTS);
        }
        final InterviewScore modelInterviewScore = new InterviewScore(interviewScore);

        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Person(modelName, modelJob, modelPhone, modelEmail, modelSkills,
                modelInterviewScore, modelTags);

    }

}
