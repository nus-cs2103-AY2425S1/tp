package hallpointer.address.model.member;

import static hallpointer.address.testutil.TypicalSessions.MEETING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import hallpointer.address.testutil.MemberBuilder;
import hallpointer.address.testutil.SessionBuilder;

public class NameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsKeywordsPredicate firstPredicate = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        NameContainsKeywordsPredicate secondPredicate = new NameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordsPredicate firstPredicateCopy = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different member -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new MemberBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new MemberBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new MemberBuilder().withName("Alice Carol").build()));

        // Two matching keywords, out of order
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol", "Alice"));
        assertTrue(predicate.test(new MemberBuilder().withName("Alice Carol").build()));

        // Keyword same up to case
        predicate = new NameContainsKeywordsPredicate(Collections.singletonList("aliCE"));
        assertTrue(predicate.test(new MemberBuilder().withName("Alice Bob").build()));

        // Keyword with slash
        predicate = new NameContainsKeywordsPredicate(Collections.singletonList("s/o"));
        assertTrue(predicate.test(new MemberBuilder().withName("Alice s/o Carl").build()));

        // Alphanumeric keyword
        predicate = new NameContainsKeywordsPredicate(Collections.singletonList("Lai1"));
        assertTrue(predicate.test(new MemberBuilder().withName("David Lai1").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new MemberBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new MemberBuilder().withName("Alice Bob").build()));

        // Keywords match telegram and room and session, but does not match name
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("aliceinwonderland", "1-2-3", "Meeting"));
        assertFalse(predicate.test(new MemberBuilder().withName("Alice").withTelegram("aliceinwonderland")
                .withRoom("1-2-3").withSessions(new SessionBuilder(MEETING).build()).build()));

        // No partial match
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Carol1"));
        assertFalse(predicate.test(new MemberBuilder().withName("Carol").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(keywords);

        String expected = NameContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
