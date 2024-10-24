package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Email;
import seedu.address.model.person.InterviewScore;
import seedu.address.model.person.Job;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.skill.Skill;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Job("Software Engineer"), new Phone("87438807"),
                    new Email("alexyeoh@example.com"),
                    getSkillSet("python", "react js"), new InterviewScore("5"), getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Job("Software Engineer"), new Phone("99272758"),
                    new Email("berniceyu@example.com"),
                    getSkillSet("python", "java"), new InterviewScore("6.5"), getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Job("Frontend Developer"), new Phone("93210283"),
                    new Email("charlotte@example.com"),
                    getSkillSet("java" , "C sharp"), new InterviewScore("5.0"), getTagSet("neighbours")),
            new Person(new Name("David Li"), new Job("UIUX Developer"), new Phone("91031282"),
                    new Email("lidavid@example.com"),
                    getSkillSet("javascript" , "html", "css"), new InterviewScore("10.0"), getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Job("Backend Developer"), new Phone("92492021"),
                    new Email("irfan@example.com"),
                    getSkillSet("java", "swift"), new InterviewScore("0"), getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Job("Devops Engineer"), new Phone("92624417"),
                    new Email("royb@example.com"),
                    getSkillSet("java", "python"), new InterviewScore("9.8"), getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
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
