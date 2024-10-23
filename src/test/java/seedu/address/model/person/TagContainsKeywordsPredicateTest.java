package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

public class TagContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Sponsor");
        List<String> secondPredicateKeywordList = Arrays.asList("Sponsor", "Friend");

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

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    // @Test
    // public void test_tagContainsKeywords_returnsTrue() {
    //     // One keyword
    //     TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Collections.
    //singletonList("Sponsor"));
    //     assertTrue(predicate.test(new PersonBuilder().withTags("Sponsor").build()));

    //     // Multiple keywords
    //     predicate = new TagContainsKeywordsPredicate(Arrays.asList("Sponsor", "Friend"));
    //     assertTrue(predicate.test(new PersonBuilder().withTags("Sponsor", "Friend").build()));

    //     // Only one matching keyword
    //     predicate = new TagContainsKeywordsPredicate(Arrays.asList("Friend", "Colleague"));
    //     assertTrue(predicate.test(new PersonBuilder().withTags("Sponsor", "Friend").build()));

    //     // Mixed-case keywords
    //     predicate = new TagContainsKeywordsPredicate(Arrays.asList("sPonsor", "fRieNd"));
    //     assertTrue(predicate.test(new PersonBuilder().withTags("Sponsor", "Friend").build()));
    // }

    // @Test
    // public void test_tagDoesNotContainKeywords_returnsFalse() {
    //     // Zero keywords
    //     TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Collections.emptyList());
    //     assertFalse(predicate.test(new PersonBuilder().withTags("Sponsor").build()));

    //     // Non-matching keyword
    //     predicate = new TagContainsKeywordsPredicate(Arrays.asList("Colleague"));
    //     assertFalse(predicate.test(new PersonBuilder().withTags("Sponsor", "Friend").build()));

    //     // Keywords match phone, email, and address but do not match tags
    //     predicate = new TagContainsKeywordsPredicate(Arrays.asList("12345", "example@email.com", "Main Street"));
    //     assertFalse(predicate.test(new PersonBuilder().withTags("Sponsor").withPhone("12345")
    //             .withEmail("example@email.com").withAddress("Main Street").build()));
    // }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(keywords);

        String expected = TagContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
