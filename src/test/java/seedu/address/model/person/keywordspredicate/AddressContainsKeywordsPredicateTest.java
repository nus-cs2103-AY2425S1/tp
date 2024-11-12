package seedu.address.model.person.keywordspredicate;

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
        List<String> firstPredicateKeywordList = Collections.singletonList("5, Tampines Street 13, #02-03");
        List<String> secondPredicateKeywordList = Arrays.asList("Main street", "Dhoby Ghaut Street 11");

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
        // One full-matching keyword
        AddressContainsKeywordsPredicate predicate =
                new AddressContainsKeywordsPredicate(Arrays.asList("74 University Town, #04-02"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("74 University Town, #04-02").build()));

        // One partial-matching keyword
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("University Town"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("74 University Town, #04-02").build()));

        // One partial-matching keyword, case-insensitive
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("uNiversiTy Tow"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("74 University Town, #04-02").build()));
    }

    @Test
    public void test_addressDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Sentosa"));
        assertFalse(predicate.test(new PersonBuilder().withAddress("8 Little India #02-04").build()));

        // Keywords match name, email and phone, but does not match address
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Alice", "12345",
                "alice@email.com", "West", "Avenue"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("58 Eunos Link, #02-04", "364 Ubi Avenue 3");
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(keywords);

        String expected = AddressContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
