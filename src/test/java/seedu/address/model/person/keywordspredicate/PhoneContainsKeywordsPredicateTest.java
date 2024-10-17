package seedu.address.model.person.keywordspredicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PhoneContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("938493");
        List<String> secondPredicateKeywordList = Arrays.asList("83748", "10293");

        PhoneContainsKeywordsPredicate firstPredicate = new PhoneContainsKeywordsPredicate(firstPredicateKeywordList);
        PhoneContainsKeywordsPredicate secondPredicate =
                new PhoneContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PhoneContainsKeywordsPredicate firstPredicateCopy =
                new PhoneContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_phoneContainsKeywords_returnsTrue() {
        // One full-matching keyword
        PhoneContainsKeywordsPredicate predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("10293"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("10293").build()));

        // One full-matching keyword
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("3847384", "1237685"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("1237685").build()));

        // One partial-matching keyword
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("234"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("38490234").build()));
    }

    @Test
    public void test_phoneDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PhoneContainsKeywordsPredicate predicate = new PhoneContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPhone("98765432").build()));

        // Non-matching keyword
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("123"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("87654").build()));

        // Non-matching keywords
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("5960, 856"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("87654").build()));

        // Keywords match name, email and address, but does not match phone
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("Alice", "12345",
                "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("5768")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("238958", "203498549");
        PhoneContainsKeywordsPredicate predicate = new PhoneContainsKeywordsPredicate(keywords);

        String expected = PhoneContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }

}
