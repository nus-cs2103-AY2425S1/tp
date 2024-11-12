package seedu.internbuddy.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.internbuddy.model.AddressBook;
import seedu.internbuddy.model.ReadOnlyAddressBook;
import seedu.internbuddy.model.application.AppStatus;
import seedu.internbuddy.model.application.Application;
import seedu.internbuddy.model.application.Description;
import seedu.internbuddy.model.company.Address;
import seedu.internbuddy.model.company.Company;
import seedu.internbuddy.model.company.Email;
import seedu.internbuddy.model.company.Phone;
import seedu.internbuddy.model.company.Status;
import seedu.internbuddy.model.company.StatusType;
import seedu.internbuddy.model.name.Name;
import seedu.internbuddy.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Company[] getSampleCompanies() {
        return new Company[] {
            new Company(new Name("Google"), new Phone("6502530000"), new Email("contact@google.com"),
                    new Address("1600 Amphitheatre Parkway, Mountain View, CA 94043"),
                    getTagSet("tech", "search"), new Status(StatusType.APPLIED),
                    getApplicationList(createApplication("SWE Intern",
                                    "Requires ReactJS and Express JS", "APPLIED"),
                            createApplication("QA Engineer", "Requires Java",
                                    "INTERVIEWED"))),
            new Company(new Name("Microsoft"), new Phone("4258828080"), new Email("contact@microsoft.com"),
                    new Address("One Microsoft Way, Redmond, WA 98052"),
                    getTagSet("tech", "software"), new Status(StatusType.APPLIED),
                    getApplicationList(createApplication("AI Engineer",
                            "Requires Python", "OA"))),
            new Company(new Name("Apple"), new Phone("4089961010"), new Email("contact@apple.com"),
                new Address("One Apple Park Way, Cupertino, CA 95014"),
                getTagSet("tech", "hardware"), new Status(StatusType.APPLIED),
                    getApplicationList(createApplication("UI UX Developer", "Requires Figma",
                            "REJECTED"))),
            new Company(new Name("Amazon"), new Phone("2062661000"), new Email("contact@amazon.com"),
                    new Address("410 Terry Ave N, Seattle, WA 98109"),
                    getTagSet("ecommerce", "cloud"), new Status(StatusType.INTERESTED), new ArrayList<>()),
            new Company(new Name("Facebook"), new Phone("6505434800"), new Email("contact@fb.com"),
                    new Address("1 Hacker Way, Menlo Park, CA 94025"),
                    getTagSet("social", "tech"), new Status(StatusType.CLOSED), new ArrayList<>()),
            new Company(new Name("Netflix"), new Phone("4085403700"), new Email("contact@netflix.com"),
                    new Address("100 Winchester Circle, Los Gatos, CA 95032"),
                    getTagSet("entertainment", "streaming"), new Status(StatusType.INTERESTED), new ArrayList<>())
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Company sampleCompany : getSampleCompanies()) {
            sampleAb.addCompany(sampleCompany);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns an application with a given description and status
     */
    public static Application createApplication(String name, String description, String appStatus) {
        return new Application(new Name(name), new Description(description), new AppStatus(appStatus));
    }

    /**
     * Returns an application list containing list of applications given.
     */
    public static List<Application> getApplicationList(Application... applications) {
        return Arrays.stream(applications).toList();
    }

}
