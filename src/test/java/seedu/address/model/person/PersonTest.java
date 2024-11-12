package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.model.person.Birthday.CUSTOM_BIRTHDAY_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.model.util.SampleDataUtil;
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

        // same name, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSamePerson(editedBob));
    }

    @Test
    public void testOverloadedConstructors() {
        Person actualBob = new Person(new Name(VALID_NAME_BOB), new Phone(VALID_PHONE_BOB), new Email(VALID_EMAIL_BOB),
                new Address(VALID_ADDRESS_BOB), SampleDataUtil.getTagSet(VALID_TAG_HUSBAND));
        Person expectedBob = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withBirthday("").withTags(VALID_TAG_HUSBAND)
                .build();
        assertEquals(expectedBob, actualBob);

        Person actualAmy = new Person(new Name(VALID_NAME_AMY), new Phone(VALID_PHONE_AMY), new Email(VALID_EMAIL_AMY),
                new Address(VALID_ADDRESS_AMY), new Remark(VALID_REMARK_AMY),
                SampleDataUtil.getTagSet(VALID_TAG_FRIEND));
        Person expectedAmy = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withRemark(VALID_REMARK_AMY)
                .withBirthday("").withTags(VALID_TAG_FRIEND).build();
        assertEquals(expectedAmy, actualAmy);
    }

    @Test
    public void personGetWeightTest() {
        assertEquals(ALICE.getWeight(0), 0);
        assertEquals(ALICE.getWeight(0, 1), 0);
        assertEquals(ALICE.getWeight(0, 1, 2), 0);
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

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress()
                + ", tags=" + ALICE.getTags() + ", remark=" + ALICE.getRemark()
                + ", birthday=" + ALICE.getBirthday()
                + ", dateOfCreation=" + ALICE.getDateOfCreation() + ", history=" + ALICE.getHistory()
                + ", properties=" + ALICE.getPropertyList() + "}";
        assertEquals(expected, ALICE.toString());
    }

    @Test
    void isBirthdayWithinNextWeek() {
        String birthdayYesterdaySomeYearsBack = LocalDate.now().minusDays(1).minusYears(20).toString();
        String birthdayTomorrowSomeYearsBack = LocalDate.now().plusDays(1).minusYears(20).toString();
        Person editedAlice = new PersonBuilder(ALICE).withBirthday(birthdayYesterdaySomeYearsBack).build();
        Person editedBob = new PersonBuilder(BOB).withBirthday(birthdayTomorrowSomeYearsBack).build();

        assertFalse(editedAlice.isBirthdayWithinNextWeek());
        assertTrue(editedBob.isBirthdayWithinNextWeek());
    }

    @Test
    void formatBirthdayMessage() {
        String today = LocalDate.now().toString();
        Person editedAlice = new PersonBuilder(ALICE).withName("Alice Pauline")
                .withBirthday(LocalDate.now().toString()).build();

        assertEquals(editedAlice.formatBirthdayMessage(), "Alice Pauline" + CUSTOM_BIRTHDAY_FORMAT + today);
    }

    @Test
    void testHashCode() {
        Person renamedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        Person aliceCopy = new PersonBuilder(ALICE).build();

        assertNotEquals(renamedAlice.hashCode(), ALICE.hashCode());
        assertEquals(ALICE.hashCode(), aliceCopy.hashCode());
    }
}
