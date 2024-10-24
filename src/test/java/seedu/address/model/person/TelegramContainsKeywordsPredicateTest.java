package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class TelegramContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TelegramContainsKeywordsPredicate firstPredicate = new TelegramContainsKeywordsPredicate(
                firstPredicateKeywordList);
        TelegramContainsKeywordsPredicate secondPredicate = new TelegramContainsKeywordsPredicate(
                secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TelegramContainsKeywordsPredicate firstPredicateCopy = new TelegramContainsKeywordsPredicate(
                firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_telegramContainsKeywords_returnsTrue() {
        // One keyword
        TelegramContainsKeywordsPredicate predicate = new TelegramContainsKeywordsPredicate(Collections
                .singletonList("javierTan"));
        assertTrue(predicate.test(new PersonBuilder().withTelegram("javierTan").build()));

        // Multiple keywords
        predicate = new TelegramContainsKeywordsPredicate(Arrays.asList("javiertan", "melinda"));
        assertTrue(predicate.test(new PersonBuilder().withTelegram("melinda").build()));

        // Mixed-case keywords
        predicate = new TelegramContainsKeywordsPredicate(Arrays.asList("AbCd", "QqQQ"));
        assertTrue(predicate.test(new PersonBuilder().withTelegram("abcd").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TelegramContainsKeywordsPredicate predicate = new TelegramContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTelegram("alisa").build()));

        // Non-matching keyword
        predicate = new TelegramContainsKeywordsPredicate(Arrays.asList("ben"));
        assertFalse(predicate.test(new PersonBuilder().withTelegram("boy").build()));

        // Keywords match name, phone and role, but does not match telegram
        predicate = new TelegramContainsKeywordsPredicate(Arrays.asList("Alice", "12345678", "alice", "member"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345678")
                .withTelegram("alicePauline").withRoles("member").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        TelegramContainsKeywordsPredicate predicate = new TelegramContainsKeywordsPredicate(keywords);

        String expected = TelegramContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
