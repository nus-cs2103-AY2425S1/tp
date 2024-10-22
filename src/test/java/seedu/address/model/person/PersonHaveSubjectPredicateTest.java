package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_MATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_SCIENCE;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonHaveSubjectPredicateTest {

    @Test
    public void equals() {
        String firstSubject = "math";
        String secondSubject = "science";

        PersonHaveSubjectPredicate firstPredicate = new PersonHaveSubjectPredicate(firstSubject);
        PersonHaveSubjectPredicate secondPredicate = new PersonHaveSubjectPredicate(secondSubject);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonHaveSubjectPredicate firstPredicateCopy = new PersonHaveSubjectPredicate(firstSubject);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different subjects -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_personHasSubject_returnsTrue() {
        // Use ALICE with a specific subject
        Person person = new PersonBuilder(ALICE).withSubject(VALID_SUBJECT_MATH).build();

        // Test case: subject matches exactly
        PersonHaveSubjectPredicate predicate = new PersonHaveSubjectPredicate("math");
        assertTrue(predicate.test(person));

        // Test case: case-insensitive match
        predicate = new PersonHaveSubjectPredicate("MATH");
        assertTrue(predicate.test(person));

        // Test case: match with leading/trailing spaces
        predicate = new PersonHaveSubjectPredicate("  math  ");
        assertTrue(predicate.test(person));
    }

    @Test
    public void test_personDoesNotHaveSubject_returnsFalse() {
        // Build a person with a single subject
        Person person = new PersonBuilder().withSubject(VALID_SUBJECT_MATH).build();

        // Test case: different subject
        PersonHaveSubjectPredicate predicate = new PersonHaveSubjectPredicate("science");
        assertFalse(predicate.test(person));

        // Test case: case-insensitive non-match
        predicate = new PersonHaveSubjectPredicate("SCIENCE");
        assertFalse(predicate.test(person));

        // Test case: partially matching (should not match)
        predicate = new PersonHaveSubjectPredicate("mat");
        assertFalse(predicate.test(person));
    }

    @Test
    public void test_personHasMultipleSubjects_returnsTrue() {
        // Build a person with multiple subjects
        Person person = new PersonBuilder().withSubject("math", "english").build();

        // Test case: match with one of the subjects
        PersonHaveSubjectPredicate predicate = new PersonHaveSubjectPredicate("english");
        assertTrue(predicate.test(person));

        // Test case: case-insensitive match with one of the subjects
        predicate = new PersonHaveSubjectPredicate("ENGLISH");
        assertTrue(predicate.test(person));
    }

    @Test
    public void test_personWithNoSubjects_returnsFalse() {
        // Build a person with no subjects
        Person person = new PersonBuilder().withSubject().build();

        // Test case: check if it matches any subject (should not match)
        PersonHaveSubjectPredicate predicate = new PersonHaveSubjectPredicate("math");
        assertFalse(predicate.test(person));
    }


    @Test
    public void test_emptySubjectToFind_returnsFalse() {
        // Use a person with a valid subject
        Person person = new PersonBuilder().withSubject("math").build();

        // Test case: empty string as subject to find should return false
        PersonHaveSubjectPredicate predicate = new PersonHaveSubjectPredicate("");
        assertFalse(predicate.test(person));
    }

    @Test
    public void test_edgeCases() {
        Person person = new PersonBuilder().withSubject(VALID_SUBJECT_SCIENCE).build();

        // Test case: Extra spaces around the subject to find (should still match)
        PersonHaveSubjectPredicate predicate = new PersonHaveSubjectPredicate(" science ");
        assertTrue(predicate.test(person));

        // Test case: Empty subject to find (should not match)
        predicate = new PersonHaveSubjectPredicate("");
        assertFalse(predicate.test(person));

        // Test case: Subject that includes special characters
        predicate = new PersonHaveSubjectPredicate("science!");
        assertFalse(predicate.test(person));
    }
}
