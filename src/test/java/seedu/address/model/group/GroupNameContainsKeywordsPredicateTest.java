package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

public class GroupNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        GroupNameContainsKeywordsPredicate firstPredicate =
            new GroupNameContainsKeywordsPredicate(firstPredicateKeywordList);
        GroupNameContainsKeywordsPredicate secondPredicate =
            new GroupNameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object --> true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values --> true
        GroupNameContainsKeywordsPredicate firstPredicateCopy =
            new GroupNameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types --> false
        assertFalse(firstPredicate.equals(1));

        // null --> false
        assertFalse(firstPredicate.equals(null));

        // different --> false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_groupNameContainsKeywords_returnsTrue() {
        // one keyword
        GroupNameContainsKeywordsPredicate predicate =
            new GroupNameContainsKeywordsPredicate(Collections.singletonList("oup"));
        assertTrue(predicate.test(new Group(new GroupName("Group 213"))));

        // multiple keywords
        predicate = new GroupNameContainsKeywordsPredicate(Arrays.asList("oup", "23"));
        assertTrue(predicate.test(new Group(new GroupName("Group 213"))));

        // only one matching keyword
        predicate = new GroupNameContainsKeywordsPredicate(Arrays.asList("oup", "19312903"));
        assertTrue(predicate.test(new Group(new GroupName("Group 213"))));

        // mixed-case keywords
        predicate = new GroupNameContainsKeywordsPredicate(Arrays.asList("Oup", "19312903"));
        assertTrue(predicate.test(new Group(new GroupName("Group 213"))));
    }

    @Test
    public void test_groupNameDoesNotContainKeywords_returnsFalse() {
        // zero keywords
        GroupNameContainsKeywordsPredicate predicate = new GroupNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new Group(new GroupName("Group 213"))));

        // non-matching
        predicate = new GroupNameContainsKeywordsPredicate(Arrays.asList("Oup"));
        assertFalse(predicate.test(new Group(new GroupName("TD-12"))));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        GroupNameContainsKeywordsPredicate predicate = new GroupNameContainsKeywordsPredicate(keywords);

        String expected = GroupNameContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}

