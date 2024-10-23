package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalVendors.ALISON;
import static seedu.address.testutil.TypicalVendors.BORIS;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.VendorBuilder;

// update the test cases with the task field once integrated
public class VendorTest {
    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Vendor vendor = new VendorBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> vendor.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALISON.isSamePerson(ALISON));

        // null -> returns false
        assertFalse(ALISON.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Vendor editedAlice = new VendorBuilder(ALISON).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALISON.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new VendorBuilder(ALISON).withName(VALID_NAME_BOB).build();
        assertFalse(ALISON.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Vendor editedBob = new VendorBuilder(BORIS).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BORIS.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new VendorBuilder(BORIS).withName(nameWithTrailingSpaces).build();
        assertFalse(BORIS.isSamePerson(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new VendorBuilder(ALISON).build();
        assertTrue(ALISON.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALISON.equals(ALISON));

        // null -> returns false
        assertFalse(ALISON.equals(null));

        // different type -> returns false
        assertFalse(ALISON.equals(5));

        // different person -> returns false
        assertFalse(ALISON.equals(BORIS));

        // different name -> returns false
        Vendor editedAlice = new VendorBuilder(ALISON).withName(VALID_NAME_BOB).build();
        assertFalse(ALISON.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new VendorBuilder(ALISON).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALISON.equals(editedAlice));

        // different email -> returns false
        editedAlice = new VendorBuilder(ALISON).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALISON.equals(editedAlice));

        // different address -> returns false
        editedAlice = new VendorBuilder(ALISON).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALISON.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new VendorBuilder(ALISON).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALISON.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Vendor.class.getCanonicalName() + "{name=" + ALISON.getName() + ", phone=" + ALISON.getPhone()
                + ", email=" + ALISON.getEmail() + ", address=" + ALISON.getAddress() + ", tags=" + ALISON.getTags()
                + ", weddings=" + ALISON.getWeddings() + "}";
        assertEquals(expected, ALISON.toString());
    }
}
