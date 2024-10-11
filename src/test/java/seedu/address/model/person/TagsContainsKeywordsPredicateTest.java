package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class TagsContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Vegan");
        List<String> secondPredicateKeywordList = Arrays.asList("Vegan", "Gluten-free");

        TagsContainsKeywordsPredicate firstPredicate = new TagsContainsKeywordsPredicate(firstPredicateKeywordList);
        TagsContainsKeywordsPredicate secondPredicate = new TagsContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagsContainsKeywordsPredicate firstPredicateCopy = new TagsContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        // One keyword
        TagsContainsKeywordsPredicate predicate = new TagsContainsKeywordsPredicate(Collections.singletonList("Vegan"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Vegan").build()));

        // Multiple keywords mixed with shortcuts
        predicate = new TagsContainsKeywordsPredicate(Arrays.asList("v", "Vegetarian"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Vegan", "Vegetarian").build()));

        // Only one matching keyword
        predicate = new TagsContainsKeywordsPredicate(Arrays.asList("Vegan", "Gluten free"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Vegan").build()));

        // Mixed-case keywords
        predicate = new TagsContainsKeywordsPredicate(Arrays.asList("v", "glUtEn free"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Vegan", "Gluten Free").build()));
    }

    @Test
    public void test_tagDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TagsContainsKeywordsPredicate predicate = new TagsContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("Vegan").build()));

        // Non-matching keyword
        predicate = new TagsContainsKeywordsPredicate(Arrays.asList("Vegetarian"));
        assertFalse(predicate.test(new PersonBuilder().withTags("Vegan").build()));

        // Non-matching shortcut keyword
        predicate = new TagsContainsKeywordsPredicate(Arrays.asList("vg"));
        assertFalse(predicate.test(new PersonBuilder().withTags("Vegan").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("Vegan", "Gluten free");
        TagsContainsKeywordsPredicate predicate = new TagsContainsKeywordsPredicate(keywords);

        String expected = TagsContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
