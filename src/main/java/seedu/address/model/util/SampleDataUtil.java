package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.company.BillingDate;
import seedu.address.model.company.Company;
import seedu.address.model.company.CompanyName;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobCompany;
import seedu.address.model.job.JobDescription;
import seedu.address.model.job.JobName;
import seedu.address.model.job.JobRequirements;
import seedu.address.model.job.JobSalary;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
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
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")
        ), new Person(
                new Name("Bernice Yu"),
                new Phone("99272758"),
                new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")
        ), new Person(
                new Name("Charlotte Oliveiro"),
                new Phone("93210283"),
                new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")
        ), new Person(
                new Name("David Li"),
                new Phone("91031282"),
                new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")
        ), new Person(
                new Name("Irfan Ibrahim"),
                new Phone("92492021"),
                new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")
        ), new Person(
                new Name("Roy Balakrishnan"),
                new Phone("92624417"),
                new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues")
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

    public static Company[] getSampleCompanies() {
        return new Company[]{new Company(
                new CompanyName("Google"),
                new Address("70 Pasir Panjang Rd, #03-71 Mapletree Business City II, Singapore 117371"),
                new BillingDate("1"),
                new Phone("65218000")
        ), new Company(
                new CompanyName("Meta"),
                new Address("9 Straits View, Marina One, Singapore 018937"),
                new BillingDate("2"),
                new Phone("12345678")
        ), new Company(
                new CompanyName("Amazon"),
                new Address("23 Church St, #10-01, Singapore 049481"),
                new BillingDate("3"),
                new Phone("67220300")
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
        for (Company sampleCompany : getSampleCompanies()) {
            sampleAb.addCompany(sampleCompany);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings).map(Tag::new).collect(Collectors.toSet());
    }

}
