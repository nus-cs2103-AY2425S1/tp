package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Email;
import seedu.address.model.person.InterviewScore;
import seedu.address.model.person.Job;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.skill.Skill;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_JOB = "Software Engineer";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_SKILL = "python";
    public static final String DEFAULT_INTERVIEWSCORE = "6";
    public static final String DEFAULT_STATUS = "pending";

    private Name name;
    private Job job;
    private Phone phone;
    private Email email;
    private Set<Skill> skills;
    private InterviewScore interviewScore;
    private Set<Tag> tags;
    private String status;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        job = new Job(DEFAULT_JOB);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        skills = new HashSet<>();
        skills.add(new Skill(DEFAULT_SKILL));
        interviewScore = new InterviewScore(DEFAULT_INTERVIEWSCORE);
        tags = new HashSet<>();
        tags.add(new Tag(DEFAULT_STATUS)); // Add the "pending" tag by default
        status = DEFAULT_STATUS;
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        job = personToCopy.getJob();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        skills = new HashSet<>(personToCopy.getSkills());
        interviewScore = personToCopy.getInterviewScore();
        tags = new HashSet<>(personToCopy.getTags());
        status = personToCopy.getStatus();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Parses the {@code skills} into a {@code Set<Skill>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withSkills(String ... skills) {
        this.skills = SampleDataUtil.getSkillSet(skills);
        return this;
    }

    /**
     * Sets the {@code Job} of the {@code Person} that we are building.
     */
    public PersonBuilder withJob(String job) {
        this.job = new Job(job);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code InterviewScore} of the {@code Person} that we are building.
     */
    public PersonBuilder withInterviewScore(String interviewScore) {
        this.interviewScore = new InterviewScore(interviewScore);
        return this;
    }

    /**
     * Sets the {@code status} of the {@code Person} that we are building.
     */
    public Person build() {
        Person person = new Person(name, job, phone, email, skills, interviewScore, tags);
        person.setStatus(status);
        return person;
    }
}
