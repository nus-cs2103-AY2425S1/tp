package seedu.hireme.model.util;

import java.time.LocalDate;

import seedu.hireme.model.AddressBook;
import seedu.hireme.model.ReadOnlyAddressBook;
import seedu.hireme.model.internshipapplication.Company;
import seedu.hireme.model.internshipapplication.Date;
import seedu.hireme.model.internshipapplication.Email;
import seedu.hireme.model.internshipapplication.InternshipApplication;
import seedu.hireme.model.internshipapplication.Name;
import seedu.hireme.model.internshipapplication.Role;

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

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (InternshipApplication sampleInternship : getSampleInternships()) {
            sampleAb.addItem(sampleInternship);
        }
        return sampleAb;
    }

}
