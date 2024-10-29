package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class TagContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        Set<Tag> firstTagSet = Set.of(new Tag("friend"));
        Set<Tag> secondTagSet = Set.of(new Tag("friend"), new Tag("colleague"));

        TagContainsKeywordsPredicate firstPredicate = new TagContainsKeywordsPredicate(firstTagSet);
        TagContainsKeywordsPredicate secondPredicate = new TagContainsKeywordsPredicate(secondTagSet);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagContainsKeywordsPredicate firstPredicateCopy = new TagContainsKeywordsPredicate(firstTagSet);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different tags -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        // One keyword
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Set.of(new Tag("friend")));
        assertTrue(predicate.test(new PersonBuilder().withTags("friend").build()));

        // Multiple keywords
        predicate = new TagContainsKeywordsPredicate(Set.of(new Tag("friend"), new Tag("colleague")));
        assertTrue(predicate.test(new PersonBuilder().withTags("friend", "colleague").build()));

        // Only one matching keyword
        predicate = new TagContainsKeywordsPredicate(Set.of(new Tag("broke"), new Tag("colleague")));
        assertTrue(predicate.test(new PersonBuilder().withTags("friend", "broke").build()));

        // Mixed-case keywords
        predicate = new TagContainsKeywordsPredicate(Set.of(new Tag("frIEnD"), new Tag("ColLeAGue")));
        assertTrue(predicate.test(new PersonBuilder().withTags("friend", "colleague").build()));
    }

    @Test
    public void test_tagDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Collections.emptySet());
        assertFalse(predicate.test(new PersonBuilder().withTags("friend").build()));

        // Non-matching keyword
        predicate = new TagContainsKeywordsPredicate(Set.of(new Tag("colleague")));
        assertFalse(predicate.test(new PersonBuilder().withTags("friend").build()));

        // Keywords match phone and address, but does not match tag
        predicate = new TagContainsKeywordsPredicate(Set.of(new Tag("12345"),
                new Tag("Main"), new Tag("Street")));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345").build()));
    }

    @Test
    public void toStringMethod() {
        Set<Tag> keyTags = Set.of(new Tag("keyword1"), new Tag("keyword2"));
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(keyTags);

        String expected = TagContainsKeywordsPredicate.class.getCanonicalName() + "{keyTags=" + keyTags + "}";
        assertEquals(expected, predicate.toString());
    }
}
