package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class TelegramHandleContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("@first");
        List<String> secondPredicateKeywordList = Arrays.asList("@first", "@second");

        TelegramHandleContainsKeywordsPredicate firstPredicate =
                new TelegramHandleContainsKeywordsPredicate(firstPredicateKeywordList);
        TelegramHandleContainsKeywordsPredicate secondPredicate =
                new TelegramHandleContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TelegramHandleContainsKeywordsPredicate firstPredicateCopy =
                new TelegramHandleContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_telegramHandleContainsKeywords_returnsTrue() {
        // One keyword
        TelegramHandleContainsKeywordsPredicate predicate =
                new TelegramHandleContainsKeywordsPredicate(Collections.singletonList("@Alice"));
        assertTrue(predicate.test(new PersonBuilder().withTelegramHandle("@Alice").build()));

        // Mixed-case keywords
        predicate = new TelegramHandleContainsKeywordsPredicate(Arrays.asList("@aLIce", "@bOB"));
        assertTrue(predicate.test(new PersonBuilder().withTelegramHandle("@Alice").build()));
    }

    @Test
    public void test_telegramHandleDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TelegramHandleContainsKeywordsPredicate predicate =
                new TelegramHandleContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTelegramHandle("@Alice").build()));

        // Non-matching keyword
        predicate = new TelegramHandleContainsKeywordsPredicate(Arrays.asList("@Carol"));
        assertFalse(predicate.test(new PersonBuilder().withTelegramHandle("@Alice").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new TelegramHandleContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("@keyword1", "@keyword2");
        TelegramHandleContainsKeywordsPredicate predicate =
                new TelegramHandleContainsKeywordsPredicate(keywords);

        String expected = TelegramHandleContainsKeywordsPredicate.class.getCanonicalName()
                + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }

}
