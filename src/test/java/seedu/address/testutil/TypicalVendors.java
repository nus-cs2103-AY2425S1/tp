package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RSVP_ACCEPTED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RSVP_PENDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Guest;
import seedu.address.model.person.Person;
import seedu.address.model.person.Vendor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalVendors {

    public static final Vendor ALICE = new VendorBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends").withCompany("hello").build();
    public static final Vendor BOB = new VendorBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .withCompany(VALID_COMPANY_BOB).build();

    public static final Guest AVA = new GuestBuilder().withName("Ava Johnson")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withEmail("ava.johnson@example.com")
            .withPhone("94351253")
            .withRsvp("ACCEPTED")
            .withTags("friends").build();
    public static final Guest BRIAN = new GuestBuilder().withName("Brian Lee")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("brian.lee@example.com")
            .withPhone("98765432")
            .withRsvp("PENDING")
            .withTags("owesMoney", "friends").build();
    public static final Guest CAM = new GuestBuilder().withName("Cameron White")
            .withPhone("95352563")
            .withEmail("cameron.white@example.com")
            .withRsvp("DECLINED")
            .withAddress("wall street").build();
    public static final Guest DAVID = new GuestBuilder().withName("David Brown")
            .withPhone("87652533")
            .withEmail("david.brown@example.com")
            .withAddress("10th street")
            .withRsvp("ACCEPTED")
            .withTags("friends").build();
    public static final Guest ELLA = new GuestBuilder().withName("Ella Thompson")
            .withPhone("9482224")
            .withEmail("ella.thompson@example.com")
            .withRsvp("PENDING")
            .withAddress("michegan ave").build();
    public static final Guest FAITH = new GuestBuilder().withName("Faith Garcia")
            .withPhone("9482427")
            .withEmail("faith.garcia@example.com")
            .withRsvp("DECLINED")
            .withAddress("little tokyo").build();
    public static final Guest GAVIN = new GuestBuilder().withName("Gavin Kim")
            .withPhone("9482442")
            .withEmail("gavin.kim@example.com")
            .withRsvp("ACCEPTED")
            .withAddress("4th street").build();


    // Manually added
    public static final Guest HOON = new GuestBuilder().withName("Hoon Meier")
            .withPhone("8482424")
            .withEmail("stefan@example.com")
            .withAddress("little india")
            .withRsvp("ACCEPTED").build();
    public static final Guest IDA = new GuestBuilder().withName("Ida Mueller")
            .withPhone("8482131")
            .withEmail("hans@example.com")
            .withAddress("chicago ave")
            .withRsvp("PENDING").build();

    // Manually added - Guest's details found in {@code CommandTestUtil}
    public static final Guest AMY = new GuestBuilder().withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY)
            .withRsvp(VALID_RSVP_ACCEPTED)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Guest BOB = new GuestBuilder().withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB)
            .withAddress(VALID_ADDRESS_BOB)
            .withRsvp(VALID_RSVP_PENDING)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalGuests() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical guests.
     */
    public static AddressBook getTypicalAddressBookWithGuests() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalGuests()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Guest> getTypicalGuests() {
        return new ArrayList<>(Arrays.asList(AVA, BRIAN, CAM, DAVID, ELLA, GAVIN));
    }
}
