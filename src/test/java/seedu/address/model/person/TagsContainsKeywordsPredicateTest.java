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

        assertTrue(firstPredicate.equals(firstPredicate));

        TagsContainsKeywordsPredicate firstPredicateCopy = new TagsContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        assertFalse(firstPredicate.equals(1));
        assertFalse(firstPredicate.equals(null));
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        TagsContainsKeywordsPredicate predicate = new TagsContainsKeywordsPredicate(Collections.singletonList("Vegan"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Vegan").build()));

        predicate = new TagsContainsKeywordsPredicate(Arrays.asList("v", "Vegetarian"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Vegan", "Vegetarian").build()));

        predicate = new TagsContainsKeywordsPredicate(Arrays.asList("v", "veg"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Vegan", "Vegetarian").build()));

        predicate = new TagsContainsKeywordsPredicate(Arrays.asList("v", "glUtEn free"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Vegan", "Gluten Free").build()));

        predicate = new TagsContainsKeywordsPredicate(Arrays.asList("VeGaN", "vegetarian"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Vegan", "Vegetarian").build()));
    }

    @Test
    public void test_tagDoesNotContainKeywords_returnsFalse() {
        TagsContainsKeywordsPredicate predicate = new TagsContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("Vegan").build()));

        predicate = new TagsContainsKeywordsPredicate(Arrays.asList("Vegetarian"));
        assertFalse(predicate.test(new PersonBuilder().withTags("Vegan").build()));

        predicate = new TagsContainsKeywordsPredicate(Arrays.asList("vg"));
        assertFalse(predicate.test(new PersonBuilder().withTags("Vegan").build()));

        predicate = new TagsContainsKeywordsPredicate(Arrays.asList("nonexistent"));
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
