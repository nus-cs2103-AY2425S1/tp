package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class GroupContainsKeywordsPredicateTest {

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
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        GroupContainsKeywordsPredicate predicate =
                new GroupContainsKeywordsPredicate(Collections.singletonList("1"));
        assertTrue(predicate.test(new PersonBuilder().withTags("group 1").build()));

        // Only one matching keyword
        predicate = new GroupContainsKeywordsPredicate(Arrays.asList("2", "3"));
        assertTrue(predicate.test(new PersonBuilder().withTags("group 2").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        GroupContainsKeywordsPredicate predicate = new GroupContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("group 1").build()));

        // Non-matching keyword
        predicate = new GroupContainsKeywordsPredicate(Arrays.asList("4"));
        assertFalse(predicate.test(new PersonBuilder().withTags("group 6").build()));

        // Keyword that contains the number but is not equal
        predicate = new GroupContainsKeywordsPredicate(Arrays.asList("5"));
        assertFalse(predicate.test(new PersonBuilder().withTags("group 51").build()));

        // Keywords match studentid, email and major, but does not match group
        predicate = new GroupContainsKeywordsPredicate(Arrays.asList("A9900990L", "e1234567@u.nus.edu", "Computer",
                "Science"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withStudentId("A9900990L")
                .withEmail("e1234567@u.nus.edu").withMajor("Computer Science").withTags("group 1").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("1", "2");
        GroupContainsKeywordsPredicate predicate = new GroupContainsKeywordsPredicate(keywords);

        String expected = GroupContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
