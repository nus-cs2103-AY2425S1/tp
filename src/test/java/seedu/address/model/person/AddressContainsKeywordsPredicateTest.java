package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;

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
        // One keyword
        AddressContainsKeywordsPredicate predicate =
                new AddressContainsKeywordsPredicate(Collections.singletonList("wall street"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("wall street").build()));

        // Multiple keywords
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("wall", "street"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("wall street").build()));

        // Only one matching keyword
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("wall", "michigan"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("wall street").build()));
    }

    @Test
    public void test_addressDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withAddress("12345678").build()));

        // Non-matching keyword
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("7899"));
        assertFalse(predicate.test(new PersonBuilder().withAddress("12345678").build()));

        // Keywords match name, email and phone, but does not match address
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Alice", "alice@email.com", "1234"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("92345678")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void test_personHasNoAddress_returnsFalse() {
        AddressContainsKeywordsPredicate predicateWithoutKeywords =
                new AddressContainsKeywordsPredicate(Collections.emptyList());

        assertFalse(predicateWithoutKeywords.test(new PersonBuilder(ALICE).withAddress().build()));

        AddressContainsKeywordsPredicate predicateWithKeyword =
                new AddressContainsKeywordsPredicate(Arrays.asList("Blk"));
        assertFalse(predicateWithKeyword.test(new PersonBuilder(ALICE).withAddress().build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(keywords);

        String expected = AddressContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
