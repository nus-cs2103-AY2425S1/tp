package seedu.address.model.util;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.internship.*;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static InternshipApplication[] getSampleInternships() {
        return new InternshipApplication[] {
                new InternshipApplication(new Company(
                        new Email("company1@mail.com"),new Name("Company 1")),
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
