package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class TagsContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TagsContainsKeywordsPredicate firstPredicate = new TagsContainsKeywordsPredicate(
                firstPredicateKeywordList);
        TagsContainsKeywordsPredicate secondPredicate = new TagsContainsKeywordsPredicate(
                secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagsContainsKeywordsPredicate firstPredicateCopy = new TagsContainsKeywordsPredicate(
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
    public void test_tagsContainsKeywords_returnsTrue() {
        // One keyword
        TagsContainsKeywordsPredicate predicate = new TagsContainsKeywordsPredicate(
                Collections.singletonList("database"));
        assertTrue(predicate.test(new PersonBuilder().withTags("database").build()));

        // Multiple keywords
        predicate = new TagsContainsKeywordsPredicate(Arrays.asList("frontend", "backend"));
        assertTrue(predicate.test(new PersonBuilder().withTags("frontend", "backend").build()));

        // Only one matching keyword
        predicate = new TagsContainsKeywordsPredicate(Arrays.asList("frontend", "backend"));
        assertTrue(predicate.test(new PersonBuilder().withTags("frontend", "database").build()));

        // Mixed-case keywords
        predicate = new TagsContainsKeywordsPredicate(Arrays.asList("fRonTenD", "BacKEnD"));
        assertTrue(predicate.test(new PersonBuilder().withTags("frontend", "backend").build()));
    }

    @Test
    public void test_tagsDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TagsContainsKeywordsPredicate predicate = new TagsContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("frontend").build()));

        // Non-matching keyword
        predicate = new TagsContainsKeywordsPredicate(Arrays.asList("database"));
        assertFalse(predicate.test(new PersonBuilder().withTags("frontend", "backend").build()));

        // Person does not have any tags
        predicate = new TagsContainsKeywordsPredicate(Arrays.asList("frontend", "backend"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void test_personIsNull_throwsAssertionError() {
        TagsContainsKeywordsPredicate predicate = new TagsContainsKeywordsPredicate(Arrays.asList("database"));
        assertThrows(AssertionError.class, () -> predicate.test(null));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        TagsContainsKeywordsPredicate predicate = new TagsContainsKeywordsPredicate(keywords);

        String expected = TagsContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
