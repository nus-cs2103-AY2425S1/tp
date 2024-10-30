package tuteez.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tuteez.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static tuteez.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static tuteez.logic.commands.CommandTestUtil.VALID_LESSON_DAY_AND_TME;
import static tuteez.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static tuteez.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static tuteez.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static tuteez.logic.commands.CommandTestUtil.VALID_TELEGRAM_BOB;
import static tuteez.testutil.Assert.assertThrows;
import static tuteez.testutil.TypicalPersons.ALICE;
import static tuteez.testutil.TypicalPersons.BOB;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import tuteez.model.person.lesson.Lesson;
import tuteez.testutil.PersonBuilder;

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
                .withAddress(VALID_ADDRESS_BOB).withTelegram(VALID_TELEGRAM_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns true
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSamePerson(editedBob));

        // different name, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // name has trailing spaces, all other attributes same -> returns true
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertTrue(BOB.isSamePerson(editedBob));
    }

    @Test
    public void nextLessonBasedOnCurrentTimeTest() {
        LocalDateTime currentTime = LocalDateTime.now();

        LocalDateTime lesson1DateTime = currentTime.plusDays(2).plusHours(10).plusMinutes(0);
        LocalDateTime lesson1EndTime = lesson1DateTime.plusHours(1);

        LocalDateTime lesson2DateTime = currentTime.plusHours(3).plusHours(15).plusMinutes(0);
        LocalDateTime lesson2EndTime = lesson2DateTime.plusHours(1);

        LocalDateTime lesson3DateTime = currentTime.plusHours(1).plusHours(3).plusMinutes(0);
        LocalDateTime lesson3EndTime = lesson3DateTime.plusHours(1);

        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("EEEE");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");

        String lesson1Str = lesson1DateTime.format(dayFormatter) + " "
                + lesson1DateTime.format(timeFormatter) + "-"
                + lesson1EndTime.format(timeFormatter);
        lesson1Str = lesson1Str.toLowerCase();

        String lesson2Str = lesson2DateTime.format(dayFormatter) + " "
                + lesson2DateTime.format(timeFormatter) + "-"
                + lesson2EndTime.format(timeFormatter);
        lesson2Str = lesson2Str.toLowerCase();

        String lesson3Str = lesson3DateTime.format(dayFormatter) + " "
                + lesson3DateTime.format(timeFormatter) + "-"
                + lesson3EndTime.format(timeFormatter);
        lesson3Str = lesson3Str.toLowerCase();

        Lesson lesson3 = new Lesson(lesson3Str);

        Person student = new PersonBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB)
                .withLessons(lesson1Str, lesson2Str, lesson3Str).build();

        assertEquals(student.nextLessonBasedOnCurrentTime(), lesson3);

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

        // different telegram username -> returns false
        editedAlice = new PersonBuilder(ALICE).withTelegram(VALID_TELEGRAM_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));

        // different lessons -> returns false
        editedAlice = new PersonBuilder(ALICE).withLessons(VALID_LESSON_DAY_AND_TME).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void testEqualObjectsHaveEqualHashCodes() {
        Person person1 = new PersonBuilder(ALICE).build();
        Person person2 = new PersonBuilder(ALICE).build();

        // If two objects are equal, their hashCode should be the same
        assertEquals(person1.hashCode(), person2.hashCode());
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()

                + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress()
                + ", telegramUsername=" + ALICE.getTelegramUsername() + ", tags=" + ALICE.getTags()
                + ", lessons=" + ALICE.getLessons() + "}";

        assertEquals(expected, ALICE.toString());
    }
}
