package seedu.address.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.client.Client;
import seedu.address.model.client.Email;
import seedu.address.model.client.Name;
import seedu.address.model.client.Phone;
import seedu.address.model.rentalinformation.RentalInformation;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Client[] getSamplePersons() {
        return new Client[] {
            new Client(new Name("Alexander Tan"), new Phone("98765432"), new Email("alexandertan@example.com"),
                        getTagSet("Rich"), new ArrayList<>(Arrays.asList(
                            new RentalInformation("Blk 321 Ang Mo Kio Ave 3, #09-123", "01/04/2018",
                                "31/12/2024", "15", "2500", "7500", "Jackson;Yummi"),
                            new RentalInformation("Blk 112 Bishan Ave 5, #15-521", "01/01/2019",
                                "31/12/2026", "15", "2700", "8100", "Ryan Low;Matthew")
                ))
            ),
            new Client(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                        getTagSet("Rich", "Friendly"), new ArrayList<>(List.of(
                            new RentalInformation("Blk 998 East Coast Street 41, #07-231", "01/12/2020",
                                "30/11/2024", "15", "1600", "4800", "Jeremy")
                ))
            ),
            new Client(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                        getTagSet("Rich"), new ArrayList<>(List.of(
                            new RentalInformation("Blk 8 Hougang Ave 10, #11-2411", "01/12/2021",
                                "31/12/2025", "15", "1500", "4500", "Michelle")
                ))
            ),
            new Client(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                        getTagSet("Friendly")),
            new Client(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                        getTagSet("Colleagues")),
            new Client(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                        getTagSet("Colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Client sampleClient : getSamplePersons()) {
            sampleAb.addPerson(sampleClient);
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
