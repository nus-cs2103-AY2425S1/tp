package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class NameAndClassIdContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("1", "2");
        List<String> thirdPredicateKeywordList = Arrays.asList("second");
        List<String> fourthPredicateKeywordList = Arrays.asList("3", "4");

        NameAndClassIdContainsKeywordsPredicate firstPredicate =
                new NameAndClassIdContainsKeywordsPredicate(firstPredicateKeywordList, secondPredicateKeywordList);
        NameAndClassIdContainsKeywordsPredicate secondPredicate =
                new NameAndClassIdContainsKeywordsPredicate(thirdPredicateKeywordList, fourthPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));
        assertTrue(secondPredicate.equals(secondPredicate));

        // same values -> returns true
        NameAndClassIdContainsKeywordsPredicate firstPredicateCopy =
                new NameAndClassIdContainsKeywordsPredicate(firstPredicateKeywordList, secondPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void testNameAndClassIdContainsKeywordsreturnsTrue() {
        // One keyword
        NameAndClassIdContainsKeywordsPredicate predicate =
                new NameAndClassIdContainsKeywordsPredicate(Collections.singletonList("Alice"),
                        Collections.singletonList("1"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withClassId("1").build()));

        // Multiple keywords
        predicate =
                new NameAndClassIdContainsKeywordsPredicate(Arrays.asList("Tom", "Alice"), Arrays.asList("1", "2"));
        assertTrue(predicate.test(new PersonBuilder().withName("Tom").withClassId("2").build()));

        // Only one matching keyword
        predicate =
                new NameAndClassIdContainsKeywordsPredicate(Arrays.asList("Tom", "Harry"), Arrays.asList("1", "2"));
        assertTrue(predicate.test(new PersonBuilder().withName("Harry").withClassId("1").build()));



    }

    @Test
    public void testNameAndClassIdContainsKeywordsreturnsFalse() {
        // Zero keywords
        NameAndClassIdContainsKeywordsPredicate predicate =
                new NameAndClassIdContainsKeywordsPredicate(Collections.emptyList(), Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withClassId("1").build()));

        // Non-matching name
        predicate =
                new NameAndClassIdContainsKeywordsPredicate(Arrays.asList("Carol"), Arrays.asList("1"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").withClassId("1").build()));

        // Non-matching classId
        predicate =
                new NameAndClassIdContainsKeywordsPredicate(Arrays.asList("Alice"), Arrays.asList("2"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withClassId("1").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        List<String> keywords2 = List.of("keyword3", "keyword4");
        NameAndClassIdContainsKeywordsPredicate predicate =
                new NameAndClassIdContainsKeywordsPredicate(keywords, keywords2);

        String expected = NameAndClassIdContainsKeywordsPredicate.class.getCanonicalName()
                + "{Name keywords=" + keywords + ", ClassId keywords=" + keywords2 + "}";
        assertEquals(expected, predicate.toString());
    }
}
