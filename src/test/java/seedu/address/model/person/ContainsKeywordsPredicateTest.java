package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.NameSearchCriteria;
import seedu.address.logic.parser.NricSearchCriteria;
import seedu.address.logic.parser.RoleSearchCriteria;
import seedu.address.logic.parser.TagSearchCriteria;
import seedu.address.testutil.PersonBuilder;

public class ContainsKeywordsPredicateTest {

    @Test
    public void equals() {

        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        ContainsKeywordsPredicate firstPredicate = new ContainsKeywordsPredicate(
                List.of(new NameSearchCriteria(firstPredicateKeywordList)));
        ContainsKeywordsPredicate secondPredicate = new ContainsKeywordsPredicate(
                List.of(new NameSearchCriteria(secondPredicateKeywordList)));

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ContainsKeywordsPredicate firstPredicateCopy = new ContainsKeywordsPredicate(
                List.of(new NameSearchCriteria(firstPredicateKeywordList)));
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
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(
                List.of(new NameSearchCriteria(List.of("Alice"))));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        List<String> multipleKeywords = Arrays.asList("Alice", "Bob");
        predicate = new ContainsKeywordsPredicate(List.of(new NameSearchCriteria(multipleKeywords)));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        List<String> oneMatchingKeyword = Arrays.asList("Bob", "Carol");
        predicate = new ContainsKeywordsPredicate(List.of(new NameSearchCriteria(oneMatchingKeyword)));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        List<String> mixedCaseKeywords = Arrays.asList("aLIce", "bOB");
        predicate = new ContainsKeywordsPredicate(List.of(new NameSearchCriteria(mixedCaseKeywords)));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new ContainsKeywordsPredicate(List.of(new NameSearchCriteria(List.of("Carol"))));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        //ArgumentMultimap mapForNotMatchingNameKeywords = new ArgumentMultimap();
        //mapForNotMatchingNameKeywords.put(PREFIX_PHONE, "12345");
        // mapForNotMatchingNameKeywords.put(PREFIX_EMAIL, "alice@email.com");
        // mapForNotMatchingNameKeywords.put(PREFIX_ADDRESS, "Main");
        // mapForNotMatchingNameKeywords.put(PREFIX_ADDRESS, "Streer");
        // predicate = new ContainsKeywordsPredicate(List.of(new NameSearchCriteria(List.of)));
        // assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
        //        .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod_withName() {
        List<String> keywords = List.of("keyword1", "keyword2");
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(
                List.of(new NameSearchCriteria(keywords)));

        String expected = ContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=["
                + new NameSearchCriteria(keywords) + "]}";
        assertEquals(expected, predicate.toString());
    }

    @Test
    public void toStringMethod_withTags() {
        List<String> names = List.of("Alice", "Bob");
        List<String> tags = List.of("friend", "family");

        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(
                List.of(new NameSearchCriteria(names), new TagSearchCriteria(tags)));

        String expected = ContainsKeywordsPredicate.class.getCanonicalName()
                + "{keywords=[" + new NameSearchCriteria(names) + ", "
                + new TagSearchCriteria(tags) + "]}";

        assertEquals(expected, predicate.toString());
    }

    @Test
    public void toStringMethod_withRole() {
        List<String> roles = List.of("patient", "caregiver");

        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(
                List.of(new RoleSearchCriteria(roles)));

        String expected = ContainsKeywordsPredicate.class.getCanonicalName()
                + "{keywords=[" + new RoleSearchCriteria(roles) + "]}";

        assertEquals(expected, predicate.toString());
    }

    @Test
    public void test_personWithNoMatchingTags_returnsFalse() {
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(
                List.of(new TagSearchCriteria(List.of("friend"))));
        assertFalse(predicate.test(new PersonBuilder().withTags("enemy", "foe").build()));
    }
    @Test
    public void test_personWithNoMatchingNric_returnsFalse() {
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(
                List.of(new NricSearchCriteria(List.of("s1234567d"))));
        assertFalse(predicate.test(new PersonBuilder().withNric("S6283947C").build()));
    }
    @Test
    public void test_personWithNoMatchingRole_returnsFalse() {
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(
                List.of(new RoleSearchCriteria(List.of("patient"))));
        assertFalse(predicate.test(new PersonBuilder().withRole("caregiver").build()));
    }

    @Test
    public void test_personWithNoMatchingName_returnsFalse() {
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(
                List.of(new NameSearchCriteria(List.of("test"))));
        assertFalse(predicate.test(new PersonBuilder().withName("John").build()));
    }
}
