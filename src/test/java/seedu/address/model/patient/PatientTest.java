package seedu.address.model.patient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEX_BOB;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PatientBuilder;

public class PatientTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Patient patient = new PatientBuilder().build();
    }

    @Test
    public void isSamePatient() {
        // same object -> returns true
        assertTrue(ALICE.isSamePatient(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePatient(null));

        // same nric, all other attributes different -> returns True
        Patient editedAlice = new PatientBuilder(BOB).withNric(VALID_NRIC_ALICE).withSex(VALID_SEX_BOB)
                .withBirthdate(VALID_BIRTHDATE_BOB).build();
        assertTrue(ALICE.isSamePatient(editedAlice));

        // different nric, all other attributes same -> returns false
        editedAlice = new PatientBuilder(ALICE).withNric(VALID_NRIC_BOB).build();
        assertFalse(ALICE.isSamePatient(editedAlice));

        // name differs in case and nric same -> returns true
        Patient editedBob = new PatientBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSamePatient(editedBob));

        // name has trailing spaces, nric same -> returns true
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PatientBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertTrue(BOB.isSamePatient(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Patient aliceCopy = new PatientBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different patient -> returns false
        assertFalse(ALICE.equals(BOB));

        // different NRIC -> returns false
        Patient editedAlice = new PatientBuilder(ALICE).withNric(VALID_NRIC_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

    }

    @Test
    public void toStringMethod() {
        String expected = Patient.class.getCanonicalName() + "{name=" + ALICE.getName() + ", nric=" + ALICE.getNric()
                + ", sex=" + ALICE.getSex() + ", birthdate=" + ALICE.getBirthdate() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
