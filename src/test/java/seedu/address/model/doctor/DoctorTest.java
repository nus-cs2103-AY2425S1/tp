package seedu.address.model.doctor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalDoctors.ALICE;
import static seedu.address.testutil.TypicalDoctors.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.DoctorBuilder;

public class DoctorTest {

    @Test
    public void isSameDoctor() {
        // same object -> returns true
        assertTrue(ALICE.isSameDoctor(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameDoctor(null));

        // same name and phone number, all other attributes different -> returns true
        Doctor editedAlice = new DoctorBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withSpeciality("DifferentSpeciality")
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameDoctor(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new DoctorBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameDoctor(editedAlice));

        // different phone number, all other attributes same -> returns false
        editedAlice = new DoctorBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.isSameDoctor(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Doctor aliceCopy = new DoctorBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different doctor -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Doctor editedAlice = new DoctorBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new DoctorBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new DoctorBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new DoctorBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different speciality -> returns false
        editedAlice = new DoctorBuilder(ALICE).withSpeciality("DifferentSpeciality").build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new DoctorBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Doctor.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress()
                + ", speciality=" + ALICE.getSpeciality() + ", tags=" + ALICE.getTags() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
