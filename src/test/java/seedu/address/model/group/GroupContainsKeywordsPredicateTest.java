package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.GroupBuilder;



public class GroupContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        GroupContainsKeywordsPredicate firstPredicate = new GroupContainsKeywordsPredicate(firstPredicateKeywordList);
        GroupContainsKeywordsPredicate secondPredicate = new GroupContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        GroupContainsKeywordsPredicate firstPredicateCopy =
                new GroupContainsKeywordsPredicate(firstPredicateKeywordList);
        assertEquals(firstPredicate, firstPredicateCopy);

        // different types -> returns false
        assertNotEquals(1, firstPredicate);

        // null -> returns false
        assertNotEquals(null, firstPredicate);

        // different group -> returns false
        assertNotEquals(firstPredicate, secondPredicate);

        assertFalse(firstPredicate.equals(null));
    }

    @Test
    public void test_groupNameContainsKeywords_returnsTrue() {
        // One keyword
        GroupContainsKeywordsPredicate predicate =
                new GroupContainsKeywordsPredicate(Collections.singletonList("Study"));
        assertTrue(predicate.test(new GroupBuilder().withGroupName("Study Group").build()));

        // Multiple keywords
        predicate = new GroupContainsKeywordsPredicate(Arrays.asList("Study", "Group"));
        assertTrue(predicate.test(new GroupBuilder().withGroupName("Study Group").build()));

        // Only one matching keyword
        predicate = new GroupContainsKeywordsPredicate(Arrays.asList("Group", "Sports"));
        assertTrue(predicate.test(new GroupBuilder().withGroupName("Sports Team").build()));

        // Mixed-case keywords
        predicate = new GroupContainsKeywordsPredicate(Arrays.asList("stuDy", "grOup"));
        assertTrue(predicate.test(new GroupBuilder().withGroupName("Study Group").build()));

        assertEquals(predicate.getKeywords(), Arrays.asList("stuDy", "grOup"));
    }
}

