//package seedu.address.model.person;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_INDUSTRY_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_ID_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
//import static seedu.address.testutil.Assert.assertThrows;
//import static seedu.address.testutil.TypicalPersons.ALICE;
//import static seedu.address.testutil.TypicalPersons.BOB;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.address.model.person.company.Company;
//import seedu.address.model.person.student.Student;
//import seedu.address.testutil.CompanyBuilder;
//import seedu.address.testutil.StudentBuilder;
//
//public class PersonTest {
//
//    @Test
//    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
//        Student student = new StudentBuilder().build();
//        assertThrows(UnsupportedOperationException.class, () -> student.getTags().remove(0));
//    }
//
//    @Test
//    public void isSamePerson() {
//        // same object -> returns true
//        assertTrue(ALICE.isSamePerson(ALICE));
//
//        // null -> returns false
//        assertFalse(ALICE.isSamePerson(null));
//
//        // same name, all other attributes different -> returns true
//        Student editedAlice = new StudentBuilder((Student) ALICE).withPhone(VALID_PHONE_BOB)
//                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
//        assertTrue(ALICE.isSamePerson(editedAlice));
//
//        // different name, all other attributes same -> returns false
//        editedAlice = new StudentBuilder((Student) ALICE).withName(VALID_NAME_BOB).build();
//        assertFalse(ALICE.isSamePerson(editedAlice));
//
//        // name differs in case, all other attributes same -> returns false
//        Student editedBob = new StudentBuilder((Student) BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
//        assertFalse(BOB.isSamePerson(editedBob));
//
//        // name has trailing spaces, all other attributes same -> returns false
//        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
//        editedBob = new StudentBuilder((Student) BOB).withName(nameWithTrailingSpaces).build();
//        assertFalse(BOB.isSamePerson(editedBob));
//    }
//
//    @Test
//    public void equals() {
//        // same values -> returns true
//        Student aliceCopy = new StudentBuilder((Student) ALICE).build();
//        assertTrue(ALICE.equals(aliceCopy));
//
//        // same object -> returns true
//        assertTrue(ALICE.equals(ALICE));
//
//        // null -> returns false
//        assertFalse(ALICE.equals(null));
//
//        // different type -> returns false
//        assertFalse(ALICE.equals(5));
//
//        // different person -> returns false
//        assertFalse(ALICE.equals(BOB));
//
//        // different name -> returns false
//        Student editedAlice = new StudentBuilder((Student) ALICE).withName(VALID_NAME_BOB).build();
//        assertFalse(ALICE.equals(editedAlice));
//
//        // different phone -> returns false
//        editedAlice = new StudentBuilder((Student) ALICE).withPhone(VALID_PHONE_BOB).build();
//        assertFalse(ALICE.equals(editedAlice));
//
//        // different email -> returns false
//        editedAlice = new StudentBuilder((Student) ALICE).withEmail(VALID_EMAIL_BOB).build();
//        assertFalse(ALICE.equals(editedAlice));
//
//        // different address -> returns false
//        editedAlice = new StudentBuilder((Student) ALICE).withAddress(VALID_ADDRESS_BOB).build();
//        assertFalse(ALICE.equals(editedAlice));
//
//        // different tags -> returns false
//        editedAlice = new StudentBuilder((Student) ALICE).withTags(VALID_TAG_HUSBAND).build();
//        assertFalse(ALICE.equals(editedAlice));
//    }
//
//    @Test
//    public void toStringMethod() {
//        String expected = Student.class.getCanonicalName() + "{name="
//                + ALICE.getName() + ", studentID=" + ((Student) ALICE).getStudentID() + ", phone=" + ALICE.getPhone()
//                + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress()
//                + ", tags=" + ALICE.getTags() + "}";
//        assertEquals(expected, ALICE.toString());
//    }
//
//    // Add tests for Company
//
//    @Test
//    public void company_equals() {
//        // same values -> returns true
//        Company companyCopy = new CompanyBuilder((Company) BOB).build();
//        assertTrue(BOB.equals(companyCopy));
//
//        // same object -> returns true
//        assertTrue(BOB.equals(BOB));
//
//        // null -> returns false
//        assertFalse(BOB.equals(null));
//
//        // different type -> returns false
//        assertFalse(BOB.equals(5));
//
//        // different company -> returns false
//        Company editedBob = new CompanyBuilder().withName(VALID_NAME_BOB).withIndustry(VALID_INDUSTRY_BOB).build();
//        assertFalse(BOB.equals(editedBob));
//
//        // different industry -> returns false
//        editedBob = new CompanyBuilder((Company) BOB).withIndustry(VALID_INDUSTRY_BOB).build();
//        assertFalse(BOB.equals(editedBob));
//    }
//
//    @Test
//    public void company_toStringMethod() {
//        String expected = Company.class.getCanonicalName() + "{name="
//                + BOB.getName() + ", industry=" + ((Company) BOB).getIndustry() + ", phone=" + BOB.getPhone()
//                + ", email=" + BOB.getEmail() + ", address=" + BOB.getAddress()
//                + ", tags=" + BOB.getTags() + "}";
//        assertEquals(expected, BOB.toString());
//    }
//}
