package seedu.address.model.wedding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.WeddingBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NameMatchesWeddingPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first second");

        NameMatchesWeddingPredicate firstPredicate = new NameMatchesWeddingPredicate(firstPredicateKeywordList);
        NameMatchesWeddingPredicate secondPredicate = new NameMatchesWeddingPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameMatchesWeddingPredicate firstPredicateCopy = new NameMatchesWeddingPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

    }

    @Test
    public void test_nameMatchesWedding_returnsTrue() {
        NameMatchesWeddingPredicate predicate = new NameMatchesWeddingPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new WeddingBuilder().withName("Alice Bob Wedding").build()));

        predicate = new NameMatchesWeddingPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new WeddingBuilder().withName("Bob Alice Wedding").build()));

        predicate = new NameMatchesWeddingPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new WeddingBuilder().withName("Alice Bob Wedding").build()));

        predicate = new NameMatchesWeddingPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new WeddingBuilder().withName("Alice Bob Tan Wedding").build()));

        // Mixed-case keywords
        predicate = new NameMatchesWeddingPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new WeddingBuilder().withName("Alice Bob").build()));

    }

    @Test
    public void test_nameDoesNotMatchWedding_returnFalse() {
        // Zero keywords
        NameMatchesWeddingPredicate predicate = new NameMatchesWeddingPredicate(Collections.emptyList());
        assertFalse(predicate.test(new WeddingBuilder().withName("Alice Bob Wedding").build()));

        // Non-matching keyword
        predicate = new NameMatchesWeddingPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new WeddingBuilder().withName("Alice Bob Wedding").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new NameMatchesWeddingPredicate(Arrays.asList("2024-12-12", "Marina", "Sands"));
        assertFalse(predicate.test(new WeddingBuilder().withName("Alice Bob Wedding").withDate("2024-12-12")
                .withVenue("Marina Bay Sands").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        NameMatchesWeddingPredicate predicate = new NameMatchesWeddingPredicate(keywords);

        String expected = NameMatchesWeddingPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }

}
