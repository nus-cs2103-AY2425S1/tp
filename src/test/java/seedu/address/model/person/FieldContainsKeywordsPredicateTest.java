package seedu.address.model.person;

import seedu.address.testutil.PersonBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FieldContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("P12345");
        List<String> secondPredicateKeywordList = Arrays.asList("P12345", "P67890");

        FieldContainsKeywordsPredicate firstPredicate = new FieldContainsKeywordsPredicate(firstPredicateKeywordList,
                "id");
        FieldContainsKeywordsPredicate secondPredicate = new FieldContainsKeywordsPredicate(secondPredicateKeywordList,
                "id");

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FieldContainsKeywordsPredicate firstPredicateCopy =
                new FieldContainsKeywordsPredicate(firstPredicateKeywordList, "id");
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_fieldContainsKeywords_returnsTrue() {
        // One keyword
        FieldContainsKeywordsPredicate predicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("C6"), "ward");
        assertTrue(predicate.test(new PersonBuilder().withWard("C6").build()));

        // Multiple keywords
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("C6", "D7"), "ward");
        assertTrue(predicate.test(new PersonBuilder().withWard("D7").build()));

        // Mixed-case keywords
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("c6", "D7"), "ward");
        assertTrue(predicate.test(new PersonBuilder().withName("C6").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        final String INVALID_FIELD = "random";
        // Zero keywords
        FieldContainsKeywordsPredicate predicate =
                new FieldContainsKeywordsPredicate(Collections.emptyList(), "id");
        assertFalse(predicate.test(new PersonBuilder().withId("P1").build()));

        // Non-matching keyword
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("P2", "P3"), "id");
        assertFalse(predicate.test(new PersonBuilder().withId("P1").build()));

        // Keywords match other field(s)
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("D7"), "id");
        assertFalse(predicate.test(new PersonBuilder().withId("P1").withWard("D7").build()));

        // Unknown field
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("P1", "C6"), INVALID_FIELD);
        assertFalse(predicate.test(new PersonBuilder().withId("P1").withWard("C6").build()));
    }

    @Test
    public void toStringMethod() {
        final String VALID_FIELD = "id";
        List<String> keywords = List.of("keyword1", "keyword2");
        FieldContainsKeywordsPredicate predicate = new FieldContainsKeywordsPredicate(keywords, VALID_FIELD);

        String expected = FieldContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords +
                ", field=" + VALID_FIELD + "}";
        assertEquals(expected, predicate.toString());
    }
}
