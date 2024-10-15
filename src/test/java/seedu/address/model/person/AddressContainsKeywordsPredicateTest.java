package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class AddressContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        AddressContainsKeywordsPredicate firstPredicate =
                new AddressContainsKeywordsPredicate(firstPredicateKeywordList);
        AddressContainsKeywordsPredicate secondPredicate =
                new AddressContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AddressContainsKeywordsPredicate firstPredicateCopy =
                new AddressContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_addressContainsKeywords_returnsTrue() {
        AddressContainsKeywordsPredicate predicate;
        //One keyword
        predicate = new AddressContainsKeywordsPredicate(Collections.singletonList("Geylang"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Blk 30 Geylang Street 29, #06-40").build()));

        // Multiple keywords
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Geylang", "Street"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Blk 30 Geylang Street 29, #06-40").build()));

        // Only one matching keyword
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Geylang", "Serangoon"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Blk 30 Geylang Street 29, #06-40").build()));

        // Mixed-case keywords
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("gEYlaNG", "STreET"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Blk 30 Geylang Street 29, #06-40").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        AddressContainsKeywordsPredicate predicate;
        // Zero keywords
        predicate = new AddressContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withAddress("Blk 30 Geylang Street 29, #06-40").build()));

        // Non-matching keyword
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Alice"));
        assertFalse(predicate.test(new PersonBuilder().withAddress("Blk 30 Geylang Street 29, #06-40").build()));

        // Keywords match name, phone, and email, but does not match address
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Alice", "12345", "alice@email.com"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(keywords);

        String expected = AddressContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
