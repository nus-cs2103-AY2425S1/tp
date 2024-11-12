package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REGISTER_NUMBER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REGISTER_NUMBER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEX_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_CLASS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonTest {

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

        // same name, all other attributes different -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withRegisterNumber(VALID_REGISTER_NUMBER_BOB).withSex(VALID_SEX_BOB)
                .withStudentClass(VALID_STUDENT_CLASS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // same class and register number -> returns true
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // name differs in case, same class and register number -> returns true
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSamePerson(editedBob));

        // same phone numbers, other attributes different -> return true
        Person diffPhoneBob = new PersonBuilder(BOB).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withRegisterNumber(VALID_REGISTER_NUMBER_BOB).withSex(VALID_SEX_BOB)
                .withStudentClass(VALID_STUDENT_CLASS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(AMY.isSamePerson(diffPhoneBob));

        // same emails -> return true
        Person diffEmailBob = new PersonBuilder(BOB).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_BOB).withRegisterNumber(VALID_REGISTER_NUMBER_BOB).withSex(VALID_SEX_BOB)
                .withStudentClass(VALID_STUDENT_CLASS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(AMY.isSamePerson(diffEmailBob));

        // diff register number and phone number and email -> return false
        Person diffPerson = new PersonBuilder(BOB).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_BOB).withRegisterNumber(VALID_REGISTER_NUMBER_AMY).withSex(VALID_SEX_BOB)
                .withStudentClass(VALID_STUDENT_CLASS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(BOB.isSamePerson(diffPerson));
    }

    @Test
    public void guiDisplayedAttributeTest() {
        Person p = ALICE;

        // name in gui
        assertEquals(p.getDisplayedName(), "Alice Pauline");

        // phone in gui
        assertEquals(p.getDisplayedPhone(), "Phone: 94351253");

        // email in gui
        assertEquals(p.getDisplayedEmail(), "Email: alice@example.com");

        // address in gui
        assertEquals(p.getDisplayedAddress(), "Address: 123, Jurong West Ave 6, #08-111");

        // register number in gui
        assertEquals(p.getDisplayedRegisterNumber(), "Register Number: 3");

        // sex in gui
        assertEquals(p.getDisplayedSex(), "Sex: F");

        // student class in gui
        assertEquals(p.getDisplayedStudentClass(), "Student Class: 1A");

        // emergency contact name in gui
        assertEquals(p.getDisplayedEcName(), "Emergency Contact Name: Joe Hardy");

        // emergency contact number in gui
        assertEquals(p.getDisplayedEcNumber(), "Emergency Contact Number: 26283728");
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different register number -> returns false
        editedAlice = new PersonBuilder(ALICE).withRegisterNumber(VALID_REGISTER_NUMBER_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different sex -> returns false
        editedAlice = new PersonBuilder(ALICE).withSex(VALID_SEX_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different class -> returns false
        editedAlice = new PersonBuilder(ALICE).withStudentClass(VALID_STUDENT_CLASS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress() + ", register number="
                + ALICE.getRegisterNumber() + ", sex=" + ALICE.getSex() + ", class=" + ALICE.getStudentClass()
                + ", emergency contact name=" + ALICE.getEcName() + ", emergency contact number="
                + ALICE.getEcNumber() + ", exams=" + ALICE.getExams() + ", submissions=" + ALICE.getSubmissions()
                + ", tags=" + ALICE.getTags() + "}";

        assertEquals(expected, ALICE.toString());
    }
}
