package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.list.GroupList;
import seedu.address.testutil.PersonBuilder;

public class GroupContainsKeywordsPredicateTest {
    private static GroupContainsKeywordsPredicate predicate;
    private static Group group;
    private static GroupList groups;

    @BeforeEach
    void setUp() {
        predicate = new GroupContainsKeywordsPredicate(Collections.emptyList());
        group = new Group("group 1");
        groups = new GroupList();
        groups.addGroup(group);
    }

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("group 1");
        List<String> secondPredicateKeywordList = Arrays.asList("group 1", "group 2");

        GroupContainsKeywordsPredicate firstPredicate = new GroupContainsKeywordsPredicate(firstPredicateKeywordList);
        GroupContainsKeywordsPredicate secondPredicate = new GroupContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        GroupContainsKeywordsPredicate firstPredicateCopy =
                new GroupContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(AssertionError.class, () -> new GroupContainsKeywordsPredicate(null));
    }

    @Test
    public void test_groupNameContainsKeywords_returnsTrue() {
        // One keyword
        GroupContainsKeywordsPredicate predicate =
                new GroupContainsKeywordsPredicate(Collections.singletonList("1"));
        assertTrue(predicate.test(new PersonBuilder().withGroups("group 1").build()));

        // Only one matching keyword
        predicate = new GroupContainsKeywordsPredicate(Arrays.asList("2", "3"));
        assertTrue(predicate.test(new PersonBuilder().withGroups("group 2").build()));

        // Matches the first few letters
        predicate = new GroupContainsKeywordsPredicate(Arrays.asList("gro"));
        assertTrue(predicate.test(new PersonBuilder().withGroups("group 2").build()));
    }

    @Test
    public void test_groupNameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        GroupContainsKeywordsPredicate predicate = new GroupContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withGroups("group 1").build()));

        // Non-matching keyword
        predicate = new GroupContainsKeywordsPredicate(Arrays.asList("4"));
        assertFalse(predicate.test(new PersonBuilder().withGroups("group 6").build()));

        // Keyword that contains the substring but does not begin with the substring
        predicate = new GroupContainsKeywordsPredicate(Arrays.asList("5"));
        assertFalse(predicate.test(new PersonBuilder().withGroups("group 51").build()));

        // Keywords match studentid, email and major, but does not match group
        predicate = new GroupContainsKeywordsPredicate(Arrays.asList("A9900990L", "e1234567@u.nus.edu", "Computer",
                "Science"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withStudentId("A9900990L")
                .withEmail("e1234567@u.nus.edu").withMajor("Computer Science").withGroups("group 1").build()));
    }

    @Test
    void testHasPrefixMatch_matchFound() {
        List<String> keywords = new ArrayList<>(List.of("gro"));
        assertTrue(predicate.hasPrefixMatch(keywords, groups));
    }

    @Test
    void testHasPrefixMatch_matchNotFound() {
        List<String> keywords = new ArrayList<>(List.of("1"));
        assertFalse(predicate.hasPrefixMatch(keywords, groups));
    }

    @Test
    void testHasFullWordMatching_matchFound() {
        List<String> keywords = new ArrayList<>(List.of("1"));
        assertTrue(predicate.hasFullWordMatching(keywords, groups));
    }

    @Test
    void testHasFullWordMatching_matchNotFound() {
        List<String> keywords = new ArrayList<>(List.of("gro"));
        assertFalse(predicate.hasFullWordMatching(keywords, groups));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("1", "2");
        GroupContainsKeywordsPredicate predicate = new GroupContainsKeywordsPredicate(keywords);

        String expected = GroupContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
