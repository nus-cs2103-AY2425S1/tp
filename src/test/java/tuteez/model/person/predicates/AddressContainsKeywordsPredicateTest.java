package tuteez.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import tuteez.model.person.Person;
import tuteez.testutil.PersonBuilder;

public class AddressContainsKeywordsPredicateTest {

    private static final Person personWithAddress =
            new PersonBuilder().withName("bob").withAddress("123 Main Street").build();

    @Test
    public void test_addressMatchesKeyword_returnsTrue() {
        List<String> keywords = List.of("Main");
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(keywords);
        assertTrue(predicate.test(personWithAddress));
    }

    @Test
    public void test_addressMatchesKeywordCaseInsensitive_returnsTrue() {
        List<String> keywords = List.of("main");
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(keywords);
        assertTrue(predicate.test(personWithAddress));
    }

    @Test
    public void test_addressMatchesPartialKeyword_returnsTrue() {
        List<String> keywords = List.of("123");
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(keywords);
        assertTrue(predicate.test(personWithAddress));
    }

    @Test
    public void test_addressDoesNotMatchAnyKeyword_returnsFalse() {
        List<String> keywords = List.of("456", "Elm");
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(keywords);
        assertFalse(predicate.test(personWithAddress));
    }

    @Test
    public void test_addressMatchesMultipleKeywords_returnsTrue() {
        List<String> keywords = List.of("Main", "456", "Elm");
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(keywords);
        assertTrue(predicate.test(personWithAddress));
    }

    @Test
    public void equals_sameKeywords_returnsTrue() {
        List<String> keywords1 = List.of("Main", "Street");
        List<String> keywords2 = List.of("Main", "Street");

        AddressContainsKeywordsPredicate predicate1 = new AddressContainsKeywordsPredicate(keywords1);
        AddressContainsKeywordsPredicate predicate2 = new AddressContainsKeywordsPredicate(keywords2);

        assertTrue(predicate1.equals(predicate2));
    }

    @Test
    public void equals_differentKeywords_returnsFalse() {
        List<String> keywords1 = List.of("Main", "Street");
        List<String> keywords2 = List.of("Broadway", "Avenue");

        AddressContainsKeywordsPredicate predicate1 = new AddressContainsKeywordsPredicate(keywords1);
        AddressContainsKeywordsPredicate predicate2 = new AddressContainsKeywordsPredicate(keywords2);

        assertFalse(predicate1.equals(predicate2));
    }
}

