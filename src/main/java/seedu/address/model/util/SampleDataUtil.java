package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.exceptions.SampleDataLoadingException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.common.Address;
import seedu.address.model.common.Name;
import seedu.address.model.company.BillingDate;
import seedu.address.model.company.Company;
import seedu.address.model.company.exceptions.CompanyNotFoundException;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobCompany;
import seedu.address.model.job.JobDescription;
import seedu.address.model.job.JobSalary;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.skill.Skill;
import seedu.address.model.tag.Tag;

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
                new Name("Software Engineer, Google Pay, Core"),
                new JobCompany("Google"),
                new JobSalary("100"),
                new JobDescription(
                        "As a software engineer, you will work on a specific project critical to Google’s needs with "
                        + "opportunities to switch teams and projects as you and our fast-paced business grow and "
                        + "evolve."),
                getRequirements("Go", "Kubernetes", "Docker", "5YOE")
        ), new Job(
                new Name("Software Engineering Manager II, YouTube"),
                new JobCompany("YouTube"),
                new JobSalary("200"),
                new JobDescription(
                        "As a Software Engineering Manager you manage your project goals, contribute to product "
                        + "strategy and help develop your team."),
                getRequirements("Leadership", "AGILE", "SDLC", "CICD")
        ), new Job(
                new Name("Database Engineer"),
                new JobCompany("Amazon"),
                new JobSalary("300"),
                new JobDescription("Looking for someone to handle our web services."),
                getRequirements("SQL")
        )};
    }

    public static Company[] getSampleCompanies() {
        return new Company[]{new Company(
                new Name("Google"),
                new Address("70 Pasir Panjang Rd, #03-71 Mapletree Business City II, Singapore 117371"),
                new BillingDate("1"),
                new Phone("65218000")
        ), new Company(
                new Name("YouTube"),
                new Address("9 Straits View, Marina One, Singapore 018937"),
                new BillingDate("2"),
                new Phone("12345678")
        ), new Company(
                new Name("Starbucks"),
                new Address("230 Victoria Street 01-106 Bugis Junction, 188021"),
                new BillingDate("25"),
                new Phone("69101099")
        ), new Company(
                new Name("Amazon"),
                new Address("23 Church St, #10-01, Singapore 049481"),
                new BillingDate("3"),
                new Phone("67220300")
        )};
    }


    public static ReadOnlyAddressBook getSampleAddressBook() throws SampleDataLoadingException {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Company sampleCompany : getSampleCompanies()) {
                sampleAb.addCompany(sampleCompany);
            }
            for (Job sampleJob : getSampleJobs()) {
                sampleAb.addJob(sampleJob); // this can throw CompanyNotFoundException
            }
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }
            return sampleAb;
        } catch (CompanyNotFoundException e) {
            throw new SampleDataLoadingException(e);
        }
    }

    /**
     * Returns a skill set containing the list of strings given.
     */
    public static Set<Skill> getSkillSet(String... strings) {
        return Arrays.stream(strings).map(Skill::new).collect(Collectors.toSet());
    }

    /**
     * Returns a requirement set containing the list of strings given.
     */
    public static Set<Tag> getRequirements(String... strings) {
        return Arrays.stream(strings).map(Tag::new).collect(Collectors.toSet());
    }

}
