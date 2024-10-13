package seedu.address.model.util;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Internship[] getSampleInternships() {
        return new Internship[] {
                new Internship(new Company(
                        new Email("company1@mail.com"),new NameO("Company 1")),
                        new Date(LocalDate.now()), new Role("role 1"))
        };
    }

    public static ReadOnlyAddressBook<Internship> getSampleAddressBook() {
        AddressBook<Internship> sampleAb = new AddressBook<>();
        for (Internship sampleInternship : getSampleInternships()) {
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
