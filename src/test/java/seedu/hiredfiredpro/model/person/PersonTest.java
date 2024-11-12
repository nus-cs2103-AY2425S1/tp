package seedu.hiredfiredpro.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.hiredfiredpro.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.hiredfiredpro.logic.commands.CommandTestUtil.VALID_INTERVIEW_SCORE_BOB;
import static seedu.hiredfiredpro.logic.commands.CommandTestUtil.VALID_JOB_AMY;
import static seedu.hiredfiredpro.logic.commands.CommandTestUtil.VALID_JOB_BOB;
import static seedu.hiredfiredpro.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.hiredfiredpro.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.hiredfiredpro.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.hiredfiredpro.logic.commands.CommandTestUtil.VALID_SKILL_JAVA;
import static seedu.hiredfiredpro.logic.commands.CommandTestUtil.VALID_SKILL_PYTHON;
import static seedu.hiredfiredpro.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.hiredfiredpro.testutil.Assert.assertThrows;
import static seedu.hiredfiredpro.testutil.TypicalPersons.ALICE;
import static seedu.hiredfiredpro.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.hiredfiredpro.testutil.PersonBuilder;

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

        // same name and same job, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withSkills(VALID_SKILL_JAVA)
                .withInterviewScore(VALID_INTERVIEW_SCORE_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // same name, all other attributes different -> returns false
        editedAlice = new PersonBuilder(ALICE).withJob(VALID_JOB_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withSkills(VALID_SKILL_JAVA)
                .withInterviewScore(VALID_INTERVIEW_SCORE_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns true
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns true
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertTrue(BOB.isSamePerson(editedBob));
    }

    @Test
    public void hasJobAndStatus() {
        Job amyJob = new Job(VALID_JOB_AMY);
        Name amyName = new Name(VALID_NAME_AMY);
        Name bobName = new Name(VALID_NAME_BOB);
        Job bobJob = new Job(VALID_JOB_BOB);
        // same object - > same job and status -> true
        assertTrue(BOB.hasJobAndStatus(bobName, bobJob));
        // different name, same job -> returns false
        assertFalse(BOB.hasJobAndStatus(amyName, amyJob));
        // different job same name -> returns true
        assertFalse(BOB.hasJobAndStatus(bobName, amyJob));
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

        // different job -> returns false
        editedAlice = new PersonBuilder(ALICE).withJob(VALID_JOB_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different skills -> returns false
        editedAlice = new PersonBuilder(ALICE).withSkills(VALID_SKILL_PYTHON).build();
        assertFalse(ALICE.equals(editedAlice));

        // different interviewScore -> returns false
        editedAlice = new PersonBuilder(ALICE).withInterviewScore(VALID_INTERVIEW_SCORE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", job=" + ALICE.getJob()
                + ", phone=" + ALICE.getPhone() + ", email=" + ALICE.getEmail()
                + ", skills=" + ALICE.getSkills() + ", interview score=" + ALICE.getInterviewScore()
                + ", tags=" + ALICE.getTags() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
