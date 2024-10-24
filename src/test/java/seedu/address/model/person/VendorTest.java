package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BUDGET_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalVendors.AMY;
import static seedu.address.testutil.TypicalVendors.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.VendorBuilder;

public class VendorTest {
    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Vendor vendor = new VendorBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> vendor.getTags().remove(0));
    }

    @Test
    public void isSameVendor() {
        // same object -> returns true
        assertTrue(AMY.isSamePerson(AMY));

        // null -> returns false
        assertFalse(AMY.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Vendor editedAlice = new VendorBuilder(AMY).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withBudget(VALID_BUDGET_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(AMY.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new VendorBuilder(AMY).withName(VALID_NAME_BOB).build();
        assertFalse(AMY.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns true
        Vendor editedBob = new VendorBuilder(AMY).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new VendorBuilder(AMY).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSamePerson(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Vendor aliceCopy = new VendorBuilder(AMY).build();
        assertTrue(AMY.equals(aliceCopy));

        // same object -> returns true
        assertTrue(AMY.equals(AMY));

        // null -> returns false
        assertFalse(AMY.equals(null));

        // different type -> returns false
        assertFalse(AMY.equals(5));

        // different person -> returns false
        assertFalse(AMY.equals(BOB));

        // different name -> returns false
        Vendor editedAlice = new VendorBuilder(AMY).withName(VALID_NAME_BOB).build();
        assertFalse(AMY.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new VendorBuilder(AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(AMY.equals(editedAlice));

        // different email -> returns false
        editedAlice = new VendorBuilder(AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(AMY.equals(editedAlice));

        // different address -> returns false
        editedAlice = new VendorBuilder(AMY).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(AMY.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new VendorBuilder(AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(AMY.equals(editedAlice));

        // different company -> returns false
        editedAlice = new VendorBuilder(AMY).withCompany(VALID_COMPANY_BOB).build();
        assertFalse(AMY.equals(editedAlice));

        // different company -> returns false
        editedAlice = new VendorBuilder(AMY).withBudget(VALID_BUDGET_BOB).build();
        assertFalse(AMY.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Vendor.class.getCanonicalName() + "{name=" + AMY.getName() + ", phone="
                + AMY.getPhone() + ", email=" + AMY.getEmail() + ", address="
                + AMY.getAddress() + ", tags=" + AMY.getTags() + ", company="
                + AMY.getCompany() + ", budget=" + AMY.getBudget() + "}";
        assertEquals(expected, AMY.toString());
    }
}
