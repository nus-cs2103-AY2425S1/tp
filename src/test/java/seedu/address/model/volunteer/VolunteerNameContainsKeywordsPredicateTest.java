package seedu.address.model.volunteer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.VolunteerBuilder;

public class VolunteerNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstKeywordList = Collections.singletonList("John");
        List<String> secondKeywordList = Arrays.asList("John", "Doe");

        VolunteerNameContainsKeywordsPredicate firstPredicate =
                new VolunteerNameContainsKeywordsPredicate(firstKeywordList);
        VolunteerNameContainsKeywordsPredicate secondPredicate =
                new VolunteerNameContainsKeywordsPredicate(secondKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        VolunteerNameContainsKeywordsPredicate firstPredicateCopy =
                new VolunteerNameContainsKeywordsPredicate(firstKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_volunteerNameContainsKeywords_returnsTrue() {
        // One keyword
        VolunteerNameContainsKeywordsPredicate predicate =
                new VolunteerNameContainsKeywordsPredicate(Collections.singletonList("John"));
        assertTrue(predicate.test(new VolunteerBuilder().withName("John Doe").build()));

        // Multiple keywords
        predicate = new VolunteerNameContainsKeywordsPredicate(Arrays.asList("John", "Doe"));
        assertTrue(predicate.test(new VolunteerBuilder().withName("John Doe").build()));

        // Only one matching keyword
        predicate = new VolunteerNameContainsKeywordsPredicate(Arrays.asList("Jane", "Doe"));
        assertTrue(predicate.test(new VolunteerBuilder().withName("Jane Smith").build()));

        // Mixed-case keywords
        predicate = new VolunteerNameContainsKeywordsPredicate(Arrays.asList("jOhN", "dOe"));
        assertTrue(predicate.test(new VolunteerBuilder().withName("John Doe").build()));
    }

    @Test
    public void test_volunteerNameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        VolunteerNameContainsKeywordsPredicate predicate =
                new VolunteerNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new VolunteerBuilder().withName("John Doe").build()));

        // Non-matching keyword
        predicate = new VolunteerNameContainsKeywordsPredicate(Arrays.asList("Michael"));
        assertFalse(predicate.test(new VolunteerBuilder().withName("John Doe").build()));

        // Keywords match other fields but not name
        predicate = new VolunteerNameContainsKeywordsPredicate(Arrays.asList("Charity", "2023-01-23"));
        assertFalse(predicate.test(new VolunteerBuilder().withName("Jane Doe")
                .withEvents("Charity Walk").withAvailableDate("2023-01-23").build()));
    }

    @Test
    public void test_toStringMethod() {
        List<String> keywords = List.of("John", "Doe");
        VolunteerNameContainsKeywordsPredicate predicate = new VolunteerNameContainsKeywordsPredicate(keywords);

        String expected = VolunteerNameContainsKeywordsPredicate.class.getCanonicalName()
                + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
