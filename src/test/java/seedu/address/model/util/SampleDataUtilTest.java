package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.util.SampleDataUtil.getRoleSet;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.StudentStatus;
import seedu.address.model.contact.TelegramHandle;
import seedu.address.model.tag.Nickname;
import seedu.address.model.tag.Role;

public class SampleDataUtilTest {
    private Contact[] sampleContacts = new Contact[] {
        new Contact(new Name("Alex Yeoh"), new TelegramHandle("87438807"), new Email("alexyeoh@example.com"),
                new StudentStatus("undergraduate 1"),
                getRoleSet("President"), new Nickname("")),
        new Contact(new Name("Bernice Yu"), new TelegramHandle("99272758"), new Email("berniceyu@example.com"),
                new StudentStatus("undergraduate 3"),
                getRoleSet("President", "Admin"), new Nickname("<nn space>")),
        new Contact(new Name("Charlotte Oliveiro"), new TelegramHandle("93210283"), new Email("charlotte@example.com"),
                new StudentStatus("masters"),
                getRoleSet("Marketing"), new Nickname("")),
        new Contact(new Name("David Li"), new TelegramHandle("91031282"), new Email("lidavid@example.com"),
                new StudentStatus("undergraduate 4"),
                getRoleSet("Admin"), new Nickname("<nn space>")),
        new Contact(new Name("Irfan Ibrahim"), new TelegramHandle("92492021"), new Email("irfan@example.com"),
                new StudentStatus("phd"),
                getRoleSet("Events (internal)"), new Nickname("")),
        new Contact(new Name("Roy Balakrishnan"), new TelegramHandle("92624417"), new Email("royb@example.com"),
                new StudentStatus("undergraduate 4"),
                getRoleSet("External Relations"), new Nickname(""))
    };

    @Test
    public void getRoleSet_success() {
        Role[] roles = {new Role("Admin"), new Role("Vice President")};

        assertTrue(List.of(roles).containsAll(getRoleSet("Admin", "Vice President")));
        assertTrue(List.of(roles).containsAll(getRoleSet("Vice President", "Admin")));
        assertTrue(List.of(roles).containsAll(getRoleSet("Vice President", "Admin", "Admin")));
    }

    @Test
    public void getSampleContacts_success() {
        assertTrue(List.of(sampleContacts).containsAll(List.of(SampleDataUtil.getSampleContacts())));
    }

    @Test
    public void getSampleAddressBook_success() {
        AddressBook sampleAb = new AddressBook();
        for (Contact sampleContact : sampleContacts) {
            sampleAb.addContact(sampleContact);
        }
        assertEquals(sampleAb, SampleDataUtil.getSampleAddressBook());
    }

}
