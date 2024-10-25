package seedu.ddd.testutil.contact;

import static seedu.ddd.testutil.contact.TypicalContactFields.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.contact.vendor.Vendor;

/**
 * A utility class containing a list of {@code Contact} objects to be used in tests.
 */
public class TypicalContacts {
    public static final Client ALICE = new ClientBuilder()
            .withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends")
            .withId(1)
            .build();
    public static final Vendor BENSON = new VendorBuilder()
            .withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com")
            .withPhone("98765432")
            .withService("flower provider")
            .withTags("owesMoney", "friends")
            .withId(2)
            .build();
    public static final Client CARL = new ClientBuilder()
            .withName("Carl Kurz")
            .withPhone("95352563")
            .withEmail("heinz@example.com")
            .withAddress("wall street")
            .withId(3)
            .build();
    public static final Vendor DANIEL = new VendorBuilder()
            .withName("Daniel Meier")
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withAddress("10th street")
            .withService("Photography")
            .withTags("friends")
            .withId(4)
            .build();
    public static final Client ELLE = new ClientBuilder()
            .withName("Elle Meyer")
            .withPhone("9482224")
            .withEmail("werner@example.com")
            .withAddress("michegan ave")
            .withId(5)
            .build();
    public static final Client FIONA = new ClientBuilder()
            .withName("Fiona Kunz")
            .withPhone("9482427")
            .withEmail("lydia@example.com")
            .withAddress("little tokyo")
            .withId(6)
            .build();
    public static final Vendor GEORGE = new VendorBuilder()
            .withName("George Best")
            .withPhone("9482442")
            .withEmail("anna@example.com")
            .withAddress("4th street")
            .withService("catering")
            .withId(7)
            .build();

    // Manually added
    public static final Client HOON = new ClientBuilder()
            .withName("Hoon Meier")
            .withPhone("8482424")
            .withEmail("stefan@example.com")
            .withAddress("little india")
            .withId(8)
            .build();
    public static final Client IDA = new ClientBuilder()
            .withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com")
            .withAddress("chicago ave")
            .withId(9)
            .build();

    // Manually added - Contacts' details found in {@code CommandTestUtil}
    public static final Client VALID_CLIENT = new ClientBuilder()
            .withName(VALID_CLIENT_NAME)
            .withPhone(VALID_CLIENT_PHONE)
            .withEmail(VALID_CLIENT_EMAIL)
            .withAddress(VALID_CLIENT_ADDRESS)
            .withTags(VALID_TAG_1)
            .withId(VALID_CLIENT_ID)
            .build();
    public static final Vendor VALID_VENDOR = new VendorBuilder()
            .withName(VALID_VENDOR_NAME)
            .withPhone(VALID_VENDOR_PHONE)
            .withEmail(VALID_VENDOR_EMAIL)
            .withAddress(VALID_VENDOR_ADDRESS)
            .withService(VALID_VENDOR_SERVICE_1)
            .withTags(VALID_TAG_1, VALID_TAG_2)
            .withId(VALID_VENDOR_ID)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalContacts() {} // prevents instantiation

    public static List<Contact> getTypicalContacts() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
