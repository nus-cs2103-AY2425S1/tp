package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class ContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        ContainsKeywordsPredicate firstPredicate = new ContainsKeywordsPredicate(
                firstPredicateKeywordList, firstPredicateKeywordList, firstPredicateKeywordList,
                firstPredicateKeywordList, firstPredicateKeywordList, firstPredicateKeywordList);
        ContainsKeywordsPredicate secondPredicate = new ContainsKeywordsPredicate(
                secondPredicateKeywordList, secondPredicateKeywordList, secondPredicateKeywordList,
                secondPredicateKeywordList, secondPredicateKeywordList, secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ContainsKeywordsPredicate firstPredicateCopy = new ContainsKeywordsPredicate(
                firstPredicateKeywordList, firstPredicateKeywordList, firstPredicateKeywordList,
                firstPredicateKeywordList, firstPredicateKeywordList, firstPredicateKeywordList);
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
                Collections.singletonList("Alice"), Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new ContainsKeywordsPredicate(
                Arrays.asList("Alice", "Bob"), Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new ContainsKeywordsPredicate(
                Arrays.asList("Bob", "Carol"), Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new ContainsKeywordsPredicate(
                Arrays.asList("aLIce", "bOB"), Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Substring keywords
        predicate = new ContainsKeywordsPredicate(
                Arrays.asList("Lic", "OB"), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(
                Collections.emptyList(), Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new ContainsKeywordsPredicate(
                Arrays.asList("Carol"), Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // nameKeywords match phone, email and address, but does not match name
        predicate = new ContainsKeywordsPredicate(Arrays.asList(
                "12345", "alice@email.com", "Main", "Street"), Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withTelegramHandle("12345")
                .withEmail("alice@email.com").withStudentStatus("undergraduate 1").build()));
    }

    @Test
    public void test_roleContainsKeywords_returnsTrue() {
        // One keyword
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(
                Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(),
                Collections.singletonList("President"), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withRoles("President", "Admin").build()));

        // Multiple keywords
        predicate = new ContainsKeywordsPredicate(
                Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(),
                Arrays.asList("President", "Admin"), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withRoles("President", "Admin").build()));

        // Only one matching keyword
        predicate = new ContainsKeywordsPredicate(
                Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(),
                Arrays.asList("President", "Relations"), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withRoles("External Relations", "Admin").build()));

        // Mixed-case keywords
        predicate = new ContainsKeywordsPredicate(
                Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(),
                Arrays.asList("pResIdent", "aDmiN"), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withRoles("President", "Admin").build()));

        // Substring keywords
        predicate = new ContainsKeywordsPredicate(
                Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(),
                Arrays.asList("pres", "min"), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withRoles("President", "Admin").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(keywords, Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList());

        String expected = ContainsKeywordsPredicate.class.getCanonicalName() + "{nameKeywords=" + keywords + ", "
                + "telegramHandleKeywords=[], emailKeywords=[], studentStatusKeywords=[], roleKeywords=[], "
                + "nicknameKeywords=[]}";
        assertEquals(expected, predicate.toString());
    }
}
