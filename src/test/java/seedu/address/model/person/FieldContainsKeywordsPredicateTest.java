package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class FieldContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("second", "third");

        FieldContainsKeywordsPredicate firstPredicate = new FieldContainsKeywordsPredicate(firstPredicateKeywordList,
                "name");
        FieldContainsKeywordsPredicate secondPredicate = new FieldContainsKeywordsPredicate(secondPredicateKeywordList,
                "name");
        FieldContainsKeywordsPredicate thirdPredicate = new FieldContainsKeywordsPredicate(firstPredicateKeywordList,
                "id");

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FieldContainsKeywordsPredicate firstPredicateCopy =
                new FieldContainsKeywordsPredicate(firstPredicateKeywordList, "name");
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords but same field -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

        // same keywords but different fields -> returns false
        assertFalse(firstPredicate.equals(thirdPredicate));
    }

    @Test
    public void test_fieldContainsKeywords_returnsTrue() {
        // One keyword
        FieldContainsKeywordsPredicate predicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("Amy"), "name");
        assertTrue(predicate.test(new PersonBuilder().withName("Amy").build()));

        // Multiple keywords
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("Amy", "Ben"), "name");
        assertTrue(predicate.test(new PersonBuilder().withName("Ben").build()));

        // Mixed-case keywords
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("aMy", "bEn"), "name");
        assertTrue(predicate.test(new PersonBuilder().withName("Amy").build()));
    }

    @Test
    public void test_fieldDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        FieldContainsKeywordsPredicate predicate =
                new FieldContainsKeywordsPredicate(Collections.emptyList(), "name");
        assertFalse(predicate.test(new PersonBuilder().withName("Amy").build()));

        // Non-matching keyword
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("Amy", "Ben"), "name");
        assertFalse(predicate.test(new PersonBuilder().withName("Clarence").build()));

        // Keywords match other field(s)
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("D7"), "Name");
        assertFalse(predicate.test(new PersonBuilder().withId("P12345").withWard("D7").build()));

        // Unknown field
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("Amy", "Ben"), "random");
        assertFalse(predicate.test(new PersonBuilder().withName("Amy").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        FieldContainsKeywordsPredicate namePredicate = new FieldContainsKeywordsPredicate(keywords, "name");

        String expected = FieldContainsKeywordsPredicate.class.getCanonicalName() + "{field=" + "name" + ", keywords="
                + keywords + "}";
        assertEquals(expected, namePredicate.toString());
    }
}
