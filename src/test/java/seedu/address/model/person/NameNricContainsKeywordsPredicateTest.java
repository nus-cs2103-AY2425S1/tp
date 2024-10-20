package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class NameNricContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameNricContainsKeywordsPredicate firstPredicate =
                new NameNricContainsKeywordsPredicate(firstPredicateKeywordList);
        NameNricContainsKeywordsPredicate secondPredicate =
                new NameNricContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameNricContainsKeywordsPredicate firstPredicateCopy =
                new NameNricContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        NameNricContainsKeywordsPredicate predicate =
                new NameNricContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new NameNricContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new NameNricContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new NameNricContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nricContainsKeywords_returnsTrue() {
        // One keyword
        NameNricContainsKeywordsPredicate predicate =
                new NameNricContainsKeywordsPredicate(Collections.singletonList("S1751126B"));
        assertTrue(predicate.test(new PersonBuilder().withNric("S1751126B").build()));

        // Multiple keywords
        predicate = new NameNricContainsKeywordsPredicate(Arrays.asList("S1751126B", "S8596794J"));
        assertTrue(predicate.test(new PersonBuilder().withNric("S1751126B").build()));
        assertTrue(predicate.test(new PersonBuilder().withNric("S8596794J").build()));

        // Mixed-case keywords
        predicate = new NameNricContainsKeywordsPredicate(Arrays.asList("S5594223d", "s1751126B"));
        assertTrue(predicate.test(new PersonBuilder().withNric("S5594223D").build()));
        assertTrue(predicate.test(new PersonBuilder().withNric("S1751126B").build()));
    }

    @Test
    public void test_nameAndNricContainsKeywords_returnsTrue() {
        // Multiple keywords
        NameNricContainsKeywordsPredicate predicate = new NameNricContainsKeywordsPredicate(Arrays.asList(
                "S1751126B", "Ida", "S0233700B", "Benson"));
        assertTrue(predicate.test(new PersonBuilder().withNric("S1751126B").build()));
        assertTrue(predicate.test(new PersonBuilder().withName("Ida Mueller").build()));
        assertTrue(predicate.test(new PersonBuilder().withName("Benson Meier").withNric("S0233700B").build()));

        // Mixed-case keywords
        predicate = new NameNricContainsKeywordsPredicate(Arrays.asList("S5594223d", "s1751126B"));
        assertTrue(predicate.test(new PersonBuilder().withNric("S5594223D").build()));
        assertTrue(predicate.test(new PersonBuilder().withNric("S1751126B").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameNricContainsKeywordsPredicate predicate = new NameNricContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));
        assertFalse(predicate.test(new PersonBuilder().withNric("S5594223D").build()));

        // Non-matching keyword
        predicate = new NameNricContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        predicate = new NameNricContainsKeywordsPredicate(Arrays.asList("S1751126B"));
        assertFalse(predicate.test(new PersonBuilder().withName("S5594223D").build()));

        // Keywords match phone, email and address, but does not match name or nric
        predicate = new NameNricContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com",
                "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withNric("S5594223D").withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        NameNricContainsKeywordsPredicate predicate = new NameNricContainsKeywordsPredicate(keywords);

        String expected = NameNricContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
