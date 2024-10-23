package seedu.address.model.person.keywordspredicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagName;

public class TagContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        // Test same object -> returns true
        TagContainsKeywordsPredicate firstPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("photo"));
        assertEquals(firstPredicate, firstPredicate);

        // Test null -> returns false
        assertFalse(firstPredicate.equals(null));

        // Test different types -> returns false
        assertNotEquals(5, firstPredicate);

        // Test different predicates -> returns false
        TagContainsKeywordsPredicate secondPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("venue"));
        assertNotEquals(firstPredicate, secondPredicate);

        // Test same predicate with same keyword -> returns true
        TagContainsKeywordsPredicate firstPredicateCopy =
                new TagContainsKeywordsPredicate(Collections.singletonList("photo"));
        assertEquals(firstPredicate, firstPredicateCopy);
    }

    @Test
    public void test_tagContainsKeywords() {
        // One keyword that fully matches the tag
        TagContainsKeywordsPredicate predicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("photographer"));
        assertTrue(predicate.test(new Tag(new TagName("Photographer"))));

        // One keyword that partially matches the tag
        predicate = new TagContainsKeywordsPredicate(Collections.singletonList("photo"));
        assertTrue(predicate.test(new Tag(new TagName("Photographer"))));

        // Multiple keywords, one matches partially
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Photographer", "Venue Planner"));
        assertTrue(predicate.test(new Tag(new TagName("Photographer"))));

        // Multiple keywords, none match
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("florist", "venue"));
        assertFalse(predicate.test(new Tag(new TagName("Photographer"))));

        // Test case-insensitive match
        predicate = new TagContainsKeywordsPredicate(Collections.singletonList("photo"));
        assertTrue(predicate.test(new Tag(new TagName("Photographer"))));

        // Test no keywords -> should return false
        predicate = new TagContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new Tag(new TagName("Photographer"))));
    }
}
