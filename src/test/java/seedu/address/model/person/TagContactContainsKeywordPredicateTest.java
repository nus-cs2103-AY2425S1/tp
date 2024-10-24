package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class TagContactContainsKeywordPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TagContactContainsKeywordPredicate firstPredicate =
                new TagContactContainsKeywordPredicate(firstPredicateKeywordList);
        TagContactContainsKeywordPredicate secondPredicate =
                new TagContactContainsKeywordPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagContactContainsKeywordPredicate firstPredicateCopy =
                new TagContactContainsKeywordPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagContactContainsKeywords_returnsTrue() {
        // One keyword
        TagContactContainsKeywordPredicate predicate =
                new TagContactContainsKeywordPredicate(Collections.singletonList("friend"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friend colleague").build()));

        // Multiple keywords
        predicate = new TagContactContainsKeywordPredicate(Arrays.asList("friend", "colleague"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friend", "colleague").build()));

        // Only one matching keyword
        predicate = new TagContactContainsKeywordPredicate(Arrays.asList("friend", "classmate"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friend", "colleague").build()));

        // Mixed-case keywords
        predicate = new TagContactContainsKeywordPredicate(Arrays.asList("fRiEnD"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Friend").build()));
    }

    @Test
    public void test_tagContactDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TagContactContainsKeywordPredicate predicate =
                new TagContactContainsKeywordPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("Alice").build()));

        // Non-matching keyword
        predicate = new TagContactContainsKeywordPredicate(Arrays.asList("friend"));
        assertFalse(predicate.test(new PersonBuilder().withTags("cook", "driver").build()));

        // Keywords match phone, name email and address, but does not match tag
        predicate =
                new TagContactContainsKeywordPredicate(Arrays.asList(
                        "Alice", "12345", "alice@email.com", "Main", "Street", "friend"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withTags("classmate").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        TagContactContainsKeywordPredicate predicate = new TagContactContainsKeywordPredicate(keywords);

        String expected = TagContactContainsKeywordPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
