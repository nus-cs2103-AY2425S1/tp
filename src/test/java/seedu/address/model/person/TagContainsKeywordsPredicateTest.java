package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class TagContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TagContainsKeywordsPredicate firstPredicate =
                new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        TagContainsKeywordsPredicate secondPredicate =
                new TagContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagContainsKeywordsPredicate firstPredicateCopy =
                new TagContainsKeywordsPredicate(firstPredicateKeywordList);
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
        TagContainsKeywordsPredicate predicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("family"));
        assertTrue(predicate.test(new PersonBuilder().withTags("family", "friend").build()));

        // Multiple keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("family", "friend"));
        assertTrue(predicate.test(new PersonBuilder().withTags("family", "friend").build()));

        // Only one matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("family", "colleague"));
        assertTrue(predicate.test(new PersonBuilder().withTags("family", "friend").build()));
    }

    @Test
    public void test_tagDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("family").build()));

        // Non-matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("colleague"));
        assertFalse(predicate.test(new PersonBuilder().withTags("family").build()));

        // Keywords match name, address and phone, but does not match tags
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Alice", "92345678", "Main Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("92345678")
                .withAddress("Main Street").withTags("family").build()));
    }

    @Test
    public void test_personHasNoTag_returnsFalse() {
        TagContainsKeywordsPredicate predicateWithoutKeywords =
                new TagContainsKeywordsPredicate(Collections.emptyList());

        assertFalse(predicateWithoutKeywords.test(new PersonBuilder(ALICE).withTags().build()));

        TagContainsKeywordsPredicate predicateWithKeyword =
                new TagContainsKeywordsPredicate(Arrays.asList("family"));
        assertFalse(predicateWithKeyword.test(new PersonBuilder(ALICE).withTags().build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(keywords);

        String expected = TagContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
