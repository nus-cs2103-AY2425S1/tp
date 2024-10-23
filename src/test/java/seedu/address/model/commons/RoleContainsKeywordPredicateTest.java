package seedu.address.model.commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class RoleContainsKeywordPredicateTest {

    @Test
    public void equals() {
        RoleContainsKeywordPredicate firstPredicate = new RoleContainsKeywordPredicate("organiser");
        RoleContainsKeywordPredicate firstPredicateUpper = new RoleContainsKeywordPredicate("ORGANISER");
        RoleContainsKeywordPredicate thirdPredicate = new RoleContainsKeywordPredicate("artist");

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same keyword -> returns true
        RoleContainsKeywordPredicate firstPredicateCopy = new RoleContainsKeywordPredicate("organiser");
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // Different case -> returns true
        assertTrue(firstPredicate.equals(firstPredicateUpper));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different role -> returns false
        assertFalse(firstPredicate.equals(thirdPredicate));
    }

    @Test
    public void test_roleContainsKeyword_returnsTrue() {
        // one word role
        RoleContainsKeywordPredicate predicate = new RoleContainsKeywordPredicate("organiser");
        assertTrue(predicate.test(new PersonBuilder().withRole("organiser").build()));

        // two word role
        predicate = new RoleContainsKeywordPredicate("stage manager");
        assertTrue(predicate.test(new PersonBuilder().withRole("stage manager").build()));

        // Mixed-case keyword
        predicate = new RoleContainsKeywordPredicate("stAgE mAnAgER");
        assertTrue(predicate.test(new PersonBuilder().withRole("stage manager").build()));
    }

    @Test
    public void test_roleDoesNotContainKeyword_returnsFalse() {
        // Non-matching keyword
        RoleContainsKeywordPredicate predicate = new RoleContainsKeywordPredicate("artist");
        assertFalse(predicate.test(new PersonBuilder().withRole("promoter").build()));
    }

    @Test
    public void toStringMethod() {
        String keyword = "organiser";
        RoleContainsKeywordPredicate predicate = new RoleContainsKeywordPredicate(keyword);

        String expected = RoleContainsKeywordPredicate.class.getCanonicalName() + "{keyword=" + keyword + "}";
        assertEquals(expected, predicate.toString());
    }
}
