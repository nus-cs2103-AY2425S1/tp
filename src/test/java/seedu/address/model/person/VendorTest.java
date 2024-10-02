package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class VendorTest {
    private static final Vendor VENDOR_ALICE = (Vendor) ALICE;
    private static final Vendor VENDOR_BOB = (Vendor) BOB;

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(VENDOR_BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(VENDOR_BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(VENDOR_BOB.isSamePerson(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(VENDOR_ALICE).build();
        assertTrue(VENDOR_ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(VENDOR_ALICE.equals(VENDOR_ALICE));

        // null -> returns false
        assertFalse(VENDOR_ALICE.equals(null));

        // different type -> returns false
        assertFalse(VENDOR_ALICE.equals(5));

        // different person -> returns false
        assertFalse(VENDOR_ALICE.equals(VENDOR_BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(VENDOR_ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(VENDOR_ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(VENDOR_ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(VENDOR_ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(VENDOR_ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(VENDOR_ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(VENDOR_ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(VENDOR_ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(VENDOR_ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(VENDOR_ALICE.equals(editedAlice));

        // different company -> returns false
        editedAlice = new PersonBuilder(VENDOR_ALICE).withCompany(VALID_COMPANY_BOB).build();
        assertFalse(VENDOR_ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + VENDOR_ALICE.getName() + ", phone="
                + VENDOR_ALICE.getPhone() + ", email=" + VENDOR_ALICE.getEmail() + ", address="
                + VENDOR_ALICE.getAddress() + ", tags=" + VENDOR_ALICE.getTags() + ", company="
                + VENDOR_ALICE.getCompany() + "}";
        assertEquals(expected, VENDOR_ALICE.toString());
    }
}
