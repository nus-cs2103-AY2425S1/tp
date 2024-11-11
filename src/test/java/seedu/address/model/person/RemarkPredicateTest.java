package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class RemarkPredicateTest {

    @Test
    public void test_remarkContainsKeyword_returnsTrue() {
        RemarkPredicate predicate = new RemarkPredicate("friendly");

        // Remark contains the keyword
        assertTrue(predicate.test(new PersonBuilder().withRemark("Very friendly person").build()));

        // Different casing
        assertTrue(predicate.test(new PersonBuilder().withRemark("FRIENDLY and helpful").build()));

        // Keyword as part of a larger remark
        assertTrue(predicate.test(new PersonBuilder().withRemark("friendly and approachable").build()));
    }

    @Test
    public void test_remarkDoesNotContainKeyword_returnsFalse() {
        RemarkPredicate predicate = new RemarkPredicate("friendly");

        // Remark does not contain the keyword
        assertFalse(predicate.test(new PersonBuilder().withRemark("hardworking and diligent").build()));

        // Completely different remark
        assertFalse(predicate.test(new PersonBuilder().withRemark("not sociable").build()));

        // Empty remark but predicate has a keyword
        assertFalse(predicate.test(new PersonBuilder().withRemark("").build()));
    }

    @Test
    public void test_emptyRemarkPredicate_matchesEmptyRemark() {
        RemarkPredicate predicate = new RemarkPredicate("");

        // Person with an empty remark should match
        assertTrue(predicate.test(new PersonBuilder().withRemark("").build()));
    }

    @Test
    public void test_emptyRemarkPredicate_doesNotMatchNonEmptyRemark() {
        RemarkPredicate predicate = new RemarkPredicate("");

        // Person with a non-empty remark should not match
        assertFalse(predicate.test(new PersonBuilder().withRemark("friendly and helpful").build()));
    }

    @Test
    public void equals() {
        RemarkPredicate firstPredicate = new RemarkPredicate("friendly");
        RemarkPredicate secondPredicate = new RemarkPredicate("helpful");

        // Same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // Same value -> returns true
        RemarkPredicate firstPredicateCopy = new RemarkPredicate("friendly");
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // Different values -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

        // Different types -> returns false
        assertFalse(firstPredicate.equals("friendly"));

        // Null -> returns false
        assertFalse(firstPredicate.equals(null));
    }
}
