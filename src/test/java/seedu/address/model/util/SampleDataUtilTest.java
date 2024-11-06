package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.model.util.SampleDataUtil.getTagSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.client.Client;
import seedu.address.model.client.Email;
import seedu.address.model.client.Name;
import seedu.address.model.client.Phone;
import seedu.address.model.rentalinformation.RentalInformation;

public class SampleDataUtilTest {
    @Test
    public void getSamplePersons_nil_listOfSampleClients() {
        Client[] clients = SampleDataUtil.getSamplePersons();
        Client[] myClients = new Client[]{
            new Client(new Name("Alexander Tan"), new Phone("98765432"), new Email("alexandertan@example.com"),
                        getTagSet("Rich"), new ArrayList<>(Arrays.asList(
                            new RentalInformation("Blk 321 Ang Mo Kio Ave 3, #09-123", "01/04/2018",
                                "31/12/2024", "15", "2500", "7500", "Jackson;Yummi"),
                            new RentalInformation("Blk 112 Bishan Ave 5, #15-521", "01/01/2019",
                                "31/12/2026", "15", "2700", "8100", "Jackson;Yummi")
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

        assertArrayEquals(myClients, clients);
    }

    @Test
    public void getSampleAddressBook_nil_addressBookContainingSampleClients() {
        ReadOnlyAddressBook addressBook = SampleDataUtil.getSampleAddressBook();
        AddressBook myAddressBook = new AddressBook();
        Client[] clients = SampleDataUtil.getSamplePersons();

        for (Client client : clients) {
            myAddressBook.addPerson(client);
        }

        assertEquals(myAddressBook, addressBook);
    }
}
