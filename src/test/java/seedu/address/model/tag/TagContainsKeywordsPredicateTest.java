package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

/**
 * Tests for {@link TagContainsKeywordsPredicate}.
 */
class TagContainsKeywordsPredicateTest {

    /**
     * Test equality of the predicate.
     */
    @Test
    public void equals() {
        List<String> listOfTags1 = Collections.singletonList("first");
        List<String> listOfTags2 = Arrays.asList("first", "second");

        TagContainsKeywordsPredicate predicate1 = new TagContainsKeywordsPredicate(listOfTags1);
        TagContainsKeywordsPredicate predicate2 = new TagContainsKeywordsPredicate(listOfTags2);
        TagContainsKeywordsPredicate predicate1Copy = new TagContainsKeywordsPredicate(listOfTags1);

        // Check that the same object is equal to itself
        assertTrue(predicate1.equals(predicate1Copy));

        // Check that different types are not equal
        assertFalse(predicate1.equals(1));

        // Check that objects with different tags are not equal
        assertFalse(predicate1.equals(predicate2));
    }

    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        TagContainsKeywordsPredicate predicate1 = new TagContainsKeywordsPredicate(Collections.singletonList("first"));
        assertTrue(predicate1.test(new PersonBuilder().withTags("first").build()));

        //Checks the empty params should list all the people out
        TagContainsKeywordsPredicate predicateEmpty = new TagContainsKeywordsPredicate(Collections.emptyList());
        assertTrue(predicateEmpty.test(new PersonBuilder().withTags("first").build()));

        //Checks as long as tags are contained by the predicate's tags list the object should be found
        TagContainsKeywordsPredicate predicate2 = new TagContainsKeywordsPredicate(Arrays.asList("first", "second"));
        assertTrue(predicate2.test(new PersonBuilder().withTags("first", "second").build()));
        assertTrue(predicate2.test(new PersonBuilder().withTags("first", "second", "third").build()));
        //Checks that the order of the tags in the person object shouldn't matter
        assertTrue(predicate2.test(new PersonBuilder().withTags("second", "first").build()));
        assertTrue(predicate2.test(new PersonBuilder().withTags("third", "second", "first").build()));
    }

    @Test
    public void test_tagContainsKeywords_returnsFalse() {
        TagContainsKeywordsPredicate predicate2 = new TagContainsKeywordsPredicate(Arrays.asList("first", "second"));
        assertFalse(predicate2.test(new PersonBuilder().withTags("first").build()));
    }
}
