package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

//@@author gho7sie

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
            new GroupNameContainsKeywordsPredicate(Collections.singletonList("f12"));
        assertTrue(predicate.test(new Group(new GroupName("CS2103-F12-4"))));

        // only one matching keyword
        predicate = new GroupNameContainsKeywordsPredicate(Arrays.asList("CS2103", "19312903"));
        assertTrue(predicate.test(new Group(new GroupName("CS2103-F12-4"))));

        // mixed-case keywords
        predicate = new GroupNameContainsKeywordsPredicate(Arrays.asList("f12", "Cs2103"));
        assertTrue(predicate.test(new Group(new GroupName("CS2103-F12-4"))));
    }

    @Test
    public void test_groupNameDoesNotContainKeywords_returnsFalse() {
        // zero keywords
        GroupNameContainsKeywordsPredicate predicate = new GroupNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new Group(new GroupName("CS2103-F12-4"))));

        // non-matching
        predicate = new GroupNameContainsKeywordsPredicate(List.of("SF12"));
        assertFalse(predicate.test(new Group(new GroupName("CS2103-F12-4"))));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        GroupNameContainsKeywordsPredicate predicate = new GroupNameContainsKeywordsPredicate(keywords);

        String expected = GroupNameContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}

