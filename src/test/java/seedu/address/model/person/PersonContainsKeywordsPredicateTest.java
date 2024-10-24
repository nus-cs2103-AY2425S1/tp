package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        Map<String, String> firstPredicateSearchCriteria = Map.of("name", "first");
        Map<String, String> secondPredicateSearchCriteria = Map.of("name", "first", "address", "second");

        PersonContainsKeywordsPredicate firstPredicate =
                new PersonContainsKeywordsPredicate(firstPredicateSearchCriteria);
        PersonContainsKeywordsPredicate secondPredicate =
                new PersonContainsKeywordsPredicate(secondPredicateSearchCriteria);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonContainsKeywordsPredicate firstPredicateCopy =
                new PersonContainsKeywordsPredicate(firstPredicateSearchCriteria);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different search criteria -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_personMatchesSearchCriteria_returnsTrue() {
        // Search by name
        Map<String, String> searchCriteria = Map.of("name", "Alice");
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(searchCriteria);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Search by name and address
        searchCriteria = Map.of("name", "Alice", "address", "Street");
        predicate = new PersonContainsKeywordsPredicate(searchCriteria);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withAddress("123 Street").build()));

        // Search by phone
        searchCriteria = Map.of("phone", "91234567");
        predicate = new PersonContainsKeywordsPredicate(searchCriteria);
        assertTrue(predicate.test(new PersonBuilder().withPhone("91234567").build()));

        // Search by email
        searchCriteria = Map.of("email", "alice@example.com");
        predicate = new PersonContainsKeywordsPredicate(searchCriteria);
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));

        // Search by birthday
        searchCriteria = Map.of("birthday", "01 01 2002");
        predicate = new PersonContainsKeywordsPredicate(searchCriteria);
        assertTrue(predicate.test(new PersonBuilder().withBirthday("01 01 2002").build()));

        // Search by tag
        searchCriteria = Map.of("tag", "friend");
        predicate = new PersonContainsKeywordsPredicate(searchCriteria);
        assertTrue(predicate.test(new PersonBuilder().withTags("friend").build()));
    }

    @Test
    public void test_partialMatches_returnsTrue() {
        // Partial match at the beginning of the name
        Map<String, String> searchCriteria = Map.of("name", "Ali");
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(searchCriteria);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Partial match with a tag
        searchCriteria = Map.of("tag", "coff");
        predicate = new PersonContainsKeywordsPredicate(searchCriteria);
        assertTrue(predicate.test(new PersonBuilder().withTags("coffeeLover").build()));

        // Partial match with address
        searchCriteria = Map.of("address", "Stre");
        predicate = new PersonContainsKeywordsPredicate(searchCriteria);
        assertTrue(predicate.test(new PersonBuilder().withAddress("Main Street").build()));

        // Partial match with email
        searchCriteria = Map.of("email", "ali");
        predicate = new PersonContainsKeywordsPredicate(searchCriteria);
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));

        // Partial match with phone
        searchCriteria = Map.of("phone", "123");
        predicate = new PersonContainsKeywordsPredicate(searchCriteria);
        assertTrue(predicate.test(new PersonBuilder().withPhone("91234567").build()));
    }

    @Test
    public void test_personDoesNotMatchSearchCriteria_returnsFalse() {
        // Search by name (no match)
        Map<String, String> searchCriteria = Map.of("name", "Carol");
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(searchCriteria);
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Search by name and address (no match)
        searchCriteria = Map.of("name", "Alice", "address", "Main Street");
        predicate = new PersonContainsKeywordsPredicate(searchCriteria);
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").withAddress("123 Jurong Street").build()));

        // Search by phone (no match)
        searchCriteria = Map.of("phone", "98765432");
        predicate = new PersonContainsKeywordsPredicate(searchCriteria);
        assertFalse(predicate.test(new PersonBuilder().withPhone("91234567").build()));

        // Search by email (no match)
        searchCriteria = Map.of("email", "bob@example.com");
        predicate = new PersonContainsKeywordsPredicate(searchCriteria);
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));

        // Search by tag (no match)
        searchCriteria = Map.of("tag", "colleague");
        predicate = new PersonContainsKeywordsPredicate(searchCriteria);
        assertFalse(predicate.test(new PersonBuilder().withTags("friend").build()));
    }

    @Test
    public void test_partialMatches_returnsFalse() {
        // Partial match not present
        Map<String, String> searchCriteria = Map.of("name", "Alz");
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(searchCriteria);
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Incorrect partial match in email domain
        searchCriteria = Map.of("email", "exampl");
        predicate = new PersonContainsKeywordsPredicate(searchCriteria);
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@exemplar.com").build()));
    }

    @Test
    public void toStringMethod() {
        Map<String, String> searchCriteria = Map.of("name", "keyword1", "address", "keyword2");
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(searchCriteria);

        String expected =
                PersonContainsKeywordsPredicate.class.getCanonicalName() + "{searchCriteria=" + searchCriteria + "}";
        assertEquals(expected, predicate.toString());
    }
}
