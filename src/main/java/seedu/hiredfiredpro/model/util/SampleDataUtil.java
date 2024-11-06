package seedu.hiredfiredpro.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.hiredfiredpro.model.HiredFiredPro;
import seedu.hiredfiredpro.model.ReadOnlyHiredFiredPro;
import seedu.hiredfiredpro.model.person.Email;
import seedu.hiredfiredpro.model.person.InterviewScore;
import seedu.hiredfiredpro.model.person.Job;
import seedu.hiredfiredpro.model.person.Name;
import seedu.hiredfiredpro.model.person.Person;
import seedu.hiredfiredpro.model.person.Phone;
import seedu.hiredfiredpro.model.skill.Skill;
import seedu.hiredfiredpro.model.tag.Tag;

/**
 * Contains utility methods for populating {@code HiredFiredPro} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Job("Software Engineer"), new Phone("87438807"),
                    new Email("alexyeoh@example.com"),
                    getSkillSet("Python", "React js"), new InterviewScore("5"), getTagSet("Remote")),
            new Person(new Name("Bernice Yu"), new Job("Software Engineer"), new Phone("99272758"),
                    new Email("berniceyu@example.com"),
                    getSkillSet("Python", "Java"), new InterviewScore("6.5"), getTagSet("Contract", "Hybrid")),
            new Person(new Name("Charlotte Oliveiro"), new Job("Frontend Developer"), new Phone("93210283"),
                    new Email("charlotte@example.com"),
                    getSkillSet("Java" , "C sharp"), new InterviewScore("5.0"), getTagSet("Referral")),
            new Person(new Name("David Li"), new Job("UIUX Developer"), new Phone("91031282"),
                    new Email("lidavid@example.com"),
                    getSkillSet("Javascript" , "HTML", "CSS"), new InterviewScore("10.0"), getTagSet("Remote")),
            new Person(new Name("Irfan Ibrahim"), new Job("Backend Developer"), new Phone("92492021"),
                    new Email("irfan@example.com"),
                    getSkillSet("Java", "Swift"), new InterviewScore("0"), getTagSet("Hybrid")),
            new Person(new Name("Roy Balakrishnan"), new Job("Devops Engineer"), new Phone("92624417"),
                    new Email("royb@example.com"),
                    getSkillSet("Java", "Python"), new InterviewScore("9.8"), getTagSet("Contract"))
        };
    }

    public static ReadOnlyHiredFiredPro getSampleHiredFiredPro() {
        HiredFiredPro sampleAb = new HiredFiredPro();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a skill set containing the list of strings given.
     */
    public static Set<Skill> getSkillSet(String... strings) {
        return Arrays.stream(strings)
                .map(Skill::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
