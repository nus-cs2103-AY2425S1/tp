package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.TagContainsKeywordsPredicate;
import seedu.address.testutil.PersonBuilder;

public class TagContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        Set<Tag> firstPredicateKeywordList = new HashSet<>(Collections.singletonList(new Tag("first")));
        Set<Tag> secondPredicateKeywordList = new HashSet<>(
                Arrays.asList(new Tag("first"), new Tag("second")));

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

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagContainsKeywords_returnTrue() {
        Set<Tag> tagSet = new HashSet<>();

        // One keyword
        tagSet.add(new Tag("friends"));
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(tagSet);

        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends", "owesMoney").build()));

        // Multiple keyword
        tagSet.add(new Tag("students"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends", "students").build()));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends", "owesMoney").build()));
        assertTrue(predicate.test(new PersonBuilder().withTags("students").build()));
    }

    @Test
    public void test_tagDoesNotContainsKeywords_returnFalse() {
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag("friends"));
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(tagSet);

        // Non-matching keywords
        assertFalse(predicate.test(new PersonBuilder().withTags("owesMoney").build()));
        assertFalse(predicate.test(new PersonBuilder().build()));
        assertFalse(predicate.test(new PersonBuilder().withTags("owesMoney", "likesGambling").build()));

        tagSet.add(new Tag("students"));
        assertFalse(predicate.test(new PersonBuilder().withTags("owesMoney").build()));
        assertFalse(predicate.test(new PersonBuilder().build()));
        assertFalse(predicate.test(new PersonBuilder().withTags("owesMoney", "likesGambling").build()));
    }

    @Test
    public void toStringMethod() {
        Set<Tag> keywords = new HashSet<>(Arrays.asList(new Tag("keyword1"), new Tag("keyword2")));
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(keywords);

        String expected = TagContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
