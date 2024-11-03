package seedu.ddd.model.contact.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_TAG_1;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_TAG_2;
import static seedu.ddd.testutil.contact.TypicalContacts.ALICE;
import static seedu.ddd.testutil.contact.TypicalContacts.BENSON;
import static seedu.ddd.testutil.contact.TypicalContacts.CARL;
import static seedu.ddd.testutil.contact.TypicalContacts.VALID_CLIENT;

import org.junit.jupiter.api.Test;

import seedu.ddd.testutil.contact.ClientBuilder;

public class ClientTest {

    @Test
    public void isSameContact() {
        Client copied = new ClientBuilder(ALICE).build();
        assertTrue(ALICE.isSameContact(copied));

        assertFalse(CARL.isSameContact(copied));
        copied = new ClientBuilder(copied)
            .withName(CARL.getName().fullName)
            .build();
        assertTrue(CARL.isSameContact(copied));
    }

    @Test
    public void equals() {
        // different vendor
        assertNotEquals(ALICE, CARL);

        // different types
        assertNotEquals(ALICE, 1);
        assertNotEquals(ALICE, BENSON);

        // copied should be same
        Client copied = new ClientBuilder(ALICE).build();
        assertEquals(ALICE, copied);

        // different name
        copied = new ClientBuilder(ALICE)
            .withName(CARL.getName().fullName)
            .build();
        assertNotEquals(ALICE, copied);

        // different phone
        copied = new ClientBuilder(ALICE)
            .withPhone(CARL.getPhone().value)
            .build();
        assertNotEquals(ALICE, copied);

        // different email
        copied = new ClientBuilder(ALICE)
            .withEmail(CARL.getEmail().value)
            .build();
        assertNotEquals(ALICE, copied);

        // different address
        copied = new ClientBuilder(ALICE)
            .withAddress(CARL.getAddress().value)
            .build();
        assertNotEquals(ALICE, copied);

        // different service
        copied = new ClientBuilder(ALICE)
            .withTags(VALID_TAG_1, VALID_TAG_2)
            .build();
        assertNotEquals(ALICE, copied);
    }

    @Test
    public void toStringMethod() {
        String expectedClientString = Client.class.getCanonicalName() + "{name=" + VALID_CLIENT.getName()
                + ", phone=" + VALID_CLIENT.getPhone() + ", email=" + VALID_CLIENT.getEmail()
                + ", address=" + VALID_CLIENT.getAddress() + ", tags=" + VALID_CLIENT.getTags()
                + ", id=" + VALID_CLIENT.getId() + "}";
        assertEquals(expectedClientString, VALID_CLIENT.toString());
    }

}
