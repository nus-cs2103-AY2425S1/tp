package seedu.eventtory.testutil;

import static seedu.eventtory.logic.commands.CommandTestUtil.VALID_DESCRIPTION_AMY;
import static seedu.eventtory.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.eventtory.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.eventtory.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.eventtory.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.eventtory.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.eventtory.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.eventtory.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.eventtory.model.EventTory;
import seedu.eventtory.model.vendor.Vendor;

/**
 * A utility class containing a list of {@code Vendor} objects to be used in
 * tests.
 */
public class TypicalVendors {

    public static final Vendor ALICE = new VendorBuilder().withName("Alice Pauline")
            .withDescription("123, Jurong West Ave 6, #08-111")
            .withPhone("94351253")
            .withTags("friends").build();
    public static final Vendor BENSON = new VendorBuilder().withName("Benson Meier")
            .withDescription("311, Clementi Ave 2, #02-25")
            .withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Vendor CARL = new VendorBuilder().withName("Carl Kurz").withPhone("95352563")
            .withDescription("wall street").build();
    public static final Vendor DANIEL = new VendorBuilder().withName("Daniel Meier").withPhone("87652533")
            .withDescription("10th street").withTags("friends").build();
    public static final Vendor ELLE = new VendorBuilder().withName("Elle Meyer").withPhone("9482224")
            .withDescription("michegan ave").build();
    public static final Vendor FIONA = new VendorBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withDescription("little tokyo").build();
    public static final Vendor GEORGE = new VendorBuilder().withName("George Best").withPhone("9482442")
            .withDescription("4th street").build();

    // Manually added
    public static final Vendor HOON = new VendorBuilder().withName("Hoon Meier").withPhone("8482424")
            .withDescription("little india").build();
    public static final Vendor IDA = new VendorBuilder().withName("Ida Mueller").withPhone("8482131")
            .withDescription("chicago ave").build();

    // Manually added - Vendor's details found in {@code CommandTestUtil}
    public static final Vendor AMY = new VendorBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withDescription(VALID_DESCRIPTION_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Vendor BOB = new VendorBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withDescription(VALID_DESCRIPTION_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalVendors() {
    } // prevents instantiation

    /**
     * Returns an {@code EventTory} with all the typical vendors.
     */
    public static EventTory getTypicalEventTory() {
        EventTory et = new EventTory();
        for (Vendor vendor : getTypicalVendors()) {
            et.addVendor(vendor);
        }
        return et;
    }

    public static List<Vendor> getTypicalVendors() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
