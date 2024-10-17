package seedu.hireme.model.util;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.hireme.model.AddressBook;
import seedu.hireme.model.ReadOnlyAddressBook;
import seedu.hireme.model.internshipapplication.Company;
import seedu.hireme.model.internshipapplication.Date;
import seedu.hireme.model.internshipapplication.Email;
import seedu.hireme.model.internshipapplication.InternshipApplication;
import seedu.hireme.model.internshipapplication.Name;
import seedu.hireme.model.internshipapplication.Role;
import seedu.hireme.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static InternshipApplication[] getSampleInternships() {
        return new InternshipApplication[] { new InternshipApplication(new Company(
                        new Email("company1@mail.com"), new Name("Company 1")),
                        new Date(LocalDate.now()), new Role("role 1"))
        };
    }

    public static ReadOnlyAddressBook<InternshipApplication> getSampleAddressBook() {
        AddressBook<InternshipApplication> sampleAb = new AddressBook<>();
        for (InternshipApplication sampleInternship : getSampleInternships()) {
            sampleAb.addItem(sampleInternship);
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

}
