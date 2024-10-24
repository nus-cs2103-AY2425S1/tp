package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.GuestBuilder;
import seedu.address.testutil.VendorBuilder;

public class TagContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TagContainsKeywordsPredicate firstPredicate = new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        TagContainsKeywordsPredicate secondPredicate = new TagContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagContainsKeywordsPredicate firstPredicateCopy = new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different tag -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        // One keyword
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Collections.singletonList("friend"));
        assertTrue(predicate.test(new GuestBuilder().withTags("friend", "classmate").build()));
        assertTrue(predicate.test(new VendorBuilder().withTags("friend", "classmate").build()));

        // Multiple keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("friend", "classmate"));
        assertTrue(predicate.test(new GuestBuilder().withTags("friend", "classmate").build()));
        assertTrue(predicate.test(new VendorBuilder().withTags("friend", "classmate").build()));

        // Only one matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("friend", "colleague"));
        assertTrue(predicate.test(new GuestBuilder().withTags("friend", "classmate").build()));
        assertTrue(predicate.test(new VendorBuilder().withTags("friend", "classmate").build()));

        // Mixed-case keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("fRiEnD", "cLaSsMaTe"));
        assertTrue(predicate.test(new GuestBuilder().withTags("friend", "classmate").build()));
        assertTrue(predicate.test(new VendorBuilder().withTags("friend", "classmate").build()));
    }

    @Test
    public void test_tagDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new GuestBuilder().withTags("friend", "classmate").build()));
        assertFalse(predicate.test(new VendorBuilder().withTags("friend", "classmate").build()));

        // Non-matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("colleague"));
        assertFalse(predicate.test(new GuestBuilder().withTags("friend", "classmate").build()));
        assertFalse(predicate.test(new VendorBuilder().withTags("friend", "classmate").build()));

        // Keywords match name, phone, email and address, but does not match tag
        predicate = new TagContainsKeywordsPredicate(
                Arrays.asList("alice", "12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new GuestBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withTags("friend", "classmate").build()));
        assertFalse(predicate.test(new VendorBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withTags("friend", "classmate").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(keywords);

        String expected = TagContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
