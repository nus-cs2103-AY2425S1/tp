package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OWED_AMOUNT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PAID_AMOUNT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHEDULE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_BOB;
import static seedu.address.testutil.TypicalStudents.ALICE;
import static seedu.address.testutil.TypicalStudents.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.StudentBuilder;

public class StudentTest {
    @Test
    public void isSameStudent() {
        // same object -> returns true
        assertTrue(ALICE.isSameStudent(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameStudent(null));

        // same name and phone, all other attributes different -> returns true
        Student editedAlice = new StudentBuilder(ALICE).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withSchedule(VALID_SCHEDULE_BOB)
                .withSubject(VALID_SUBJECT_BOB).withPaidAmount(VALID_PAID_AMOUNT_BOB)
                .withOwedAmount(VALID_OWED_AMOUNT_BOB).withRate(VALID_RATE_BOB).build();
        assertTrue(ALICE.isSameStudent(editedAlice));

        // same name, all other attributes different -> returns false
        editedAlice = new StudentBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withSchedule(VALID_SCHEDULE_BOB)
                .withSubject(VALID_SUBJECT_BOB).withPaidAmount(VALID_PAID_AMOUNT_BOB)
                .withOwedAmount(VALID_OWED_AMOUNT_BOB).withRate(VALID_RATE_BOB).build();
        assertFalse(ALICE.isSameStudent(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new StudentBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameStudent(editedAlice));

        // name differs in case, all other attributes same -> returns true
        Student editedBob = new StudentBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSameStudent(editedBob));

        // name has trailing spaces, all other attributes same -> returns true
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new StudentBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertTrue(BOB.isSameStudent(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Student aliceCopy = new StudentBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different student -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Student editedAlice = new StudentBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new StudentBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new StudentBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new StudentBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different schedule -> returns false
        editedAlice = new StudentBuilder(ALICE).withSchedule(VALID_SCHEDULE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different subject -> returns false
        editedAlice = new StudentBuilder(ALICE).withSubject(VALID_SUBJECT_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different rate -> returns false
        editedAlice = new StudentBuilder(ALICE).withRate(VALID_RATE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different owedAmount -> returns false
        editedAlice = new StudentBuilder(ALICE).withOwedAmount(VALID_OWED_AMOUNT_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different paidAmount -> returns false
        editedAlice = new StudentBuilder(ALICE).withPaidAmount(VALID_PAID_AMOUNT_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

    }

    @Test
    public void isClash_sameStudent_returnsFalse() {
        Student student = new StudentBuilder().build();
        assertFalse(student.isClash(student), "A student should not clash with themselves.");
    }

    @Test
    public void isClash_nullStudent_returnsFalse() {
        Student student = new StudentBuilder().build();
        assertFalse(student.isClash(null), "Clashing with a null student should return false.");
    }

    @Test
    public void isClash_noClash_returnsFalse() {
        Student studentA = new StudentBuilder().withSchedule("Monday-0900-1100").build();
        Student studentB = new StudentBuilder().withSchedule("Monday-1200-1300").build();
        assertFalse(studentA.isClash(studentB), "Students should not clash when their schedules do not overlap.");
    }

    @Test
    public void isClash_clash_returnsTrue() {
        Student studentA = new StudentBuilder().withSchedule("Monday-0900-1100").build();
        Student studentB = new StudentBuilder().withSchedule("Monday-1000-1200").build();
        assertTrue(studentA.isClash(studentB), "Students should clash when their schedules overlap.");
    }

    @Test
    public void isClash_partialOverlap_returnsTrue() {
        Student studentA = new StudentBuilder().withSchedule("Tuesday-1000-1200").build();
        Student studentB = new StudentBuilder().withSchedule("Tuesday-1100-1300").build();
        assertTrue(studentA.isClash(studentB), "Students should clash with partially overlapping schedules.");
    }

    @Test
    public void toStringMethod() {
        String expected = Student.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress()
                + ", schedule=" + ALICE.getSchedule() + ", subject=" + ALICE.getSubject()
                + ", rate=" + ALICE.getRate() + ", paidAmount=" + ALICE.getPaidAmount()
                + ", owedAmount=" + ALICE.getOwedAmount() + "}";
        assertEquals(expected, ALICE.toString());
    }

    @Test
    public void hashCodeTest() {
        // same attributes -> returns true
        Student sameAlice = new StudentBuilder(ALICE).build();

        assertEquals(ALICE.hashCode(), sameAlice.hashCode());

        // different attributes -> returns false
        Student differentPhone = new StudentBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        Student differentEmail = new StudentBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        Student differentAddress = new StudentBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        Student differentName = new StudentBuilder(ALICE).withName(VALID_NAME_BOB).build();
        Student differentStudent = new StudentBuilder(BOB).build();

        assertNotEquals(ALICE.hashCode(), differentPhone.hashCode());
        assertNotEquals(ALICE.hashCode(), differentEmail.hashCode());
        assertNotEquals(ALICE.hashCode(), differentAddress.hashCode());
        assertNotEquals(ALICE.hashCode(), differentName.hashCode());
        assertNotEquals(ALICE.hashCode(), differentStudent.hashCode());
    }
}
