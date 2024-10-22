package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.model.util.SampleDataUtil.getTagSet;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.client.Client;
import seedu.address.model.client.Email;
import seedu.address.model.client.Name;
import seedu.address.model.client.Phone;

public class SampleDataUtilTest {
    @Test
    public void getSamplePersons_nil_listOfSampleClients() {
        Client[] clients = SampleDataUtil.getSamplePersons();
        Client[] myClients = new Client[] {
            new Client(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    getTagSet("friends")),
            new Client(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    getTagSet("colleagues", "friends")),
            new Client(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    getTagSet("neighbours")),
            new Client(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    getTagSet("family")),
            new Client(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    getTagSet("classmates")),
            new Client(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    getTagSet("colleagues"))
        };

        assertArrayEquals(myClients, clients);
    }

    @Test
    public void getSampleAddressBook_nil_addressbookContainingSampleClients() {
        ReadOnlyAddressBook addressBook = SampleDataUtil.getSampleAddressBook();
        AddressBook myAddressBook = new AddressBook();
        Client[] clients = SampleDataUtil.getSamplePersons();

        for (Client client : clients) {
            myAddressBook.addPerson(client);
        }

        assertEquals(myAddressBook, addressBook);
    }
}
