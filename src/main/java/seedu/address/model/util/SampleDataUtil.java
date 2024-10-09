package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobCompany;
import seedu.address.model.job.JobDescription;
import seedu.address.model.job.JobName;
import seedu.address.model.job.JobRequirements;
import seedu.address.model.job.JobSalary;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.skill.Skill;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[]{new Person(
                new Name("Alex Yeoh"),
                new Phone("87438807"),
                new Email("alexyeoh@example.com"),
                new Role("Software Engineer"),
                getSkillSet("Python", "C")
        ), new Person(
                new Name("Bernice Yu"),
                new Phone("99272758"),
                new Email("berniceyu@example.com"),
                new Role("Copywriter"),
                getSkillSet("wordpress", "MSword")
        ), new Person(
                new Name("Charlotte Oliveiro"),
                new Phone("93210283"),
                new Email("charlotte@example.com"),
                new Role("Teacher"),
                getSkillSet("math")
        ), new Person(
                new Name("David Li"),
                new Phone("91031282"),
                new Email("lidavid@example.com"),
                new Role("Data Scientist"),
                getSkillSet("R", "CUDA")
        ), new Person(
                new Name("Irfan Ibrahim"),
                new Phone("92492021"),
                new Email("irfan@example.com"),
                new Role("Machine Learning Engineer"),
                getSkillSet("CUDA", "Python")
        ), new Person(
                new Name("Roy Balakrishnan"),
                new Phone("92624417"),
                new Email("royb@example.com"),
                new Role("Bus driver"),
                getSkillSet("DrivingLicense")
        )};
    }

    public static Job[] getSampleJobs() {
        return new Job[]{new Job(
                new JobName("Software Engineer, Google Pay, Core"),
                new JobCompany("Google"),
                new JobSalary("100"),
                new JobRequirements(
                        "Experience with mobile development, 2 years of experience with software development"),
                new JobDescription(
                        "As a software engineer, you will work on a specific project critical to Google’s needs with "
                        + "opportunities to switch teams and projects as you and our fast-paced business grow and "
                        + "evolve. ")
        ), new Job(
                new JobName("Software Engineering Manager II, YouTube"),
                new JobCompany("YouTube"),
                new JobSalary("200"),
                new JobRequirements("8 years of experience with software development in one or more programming "
                                    + "languages (e.g., Python, C, C++, Java, JavaScript)"),
                new JobDescription(
                        "As a Software Engineering Manager you manage your project goals, contribute to product "
                        + "strategy and help develop your team. ")
        ), new Job(
                new JobName("Test Job"),
                new JobCompany("Test Company"),
                new JobSalary("300"),
                new JobRequirements(null),
                new JobDescription(null)
        )};
    }


    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        for (Job sampleJob : getSampleJobs()) {
            sampleAb.addJob(sampleJob);
        }
        return sampleAb;
    }

    /**
     * Returns a skill set containing the list of strings given.
     */
    public static Set<Skill> getSkillSet(String... strings) {
        return Arrays.stream(strings).map(Skill::new).collect(Collectors.toSet());
    }

}
