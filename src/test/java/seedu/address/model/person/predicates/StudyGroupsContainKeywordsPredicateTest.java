package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class StudyGroupsContainKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        StudyGroupsContainKeywordsPredicate firstPredicate = new StudyGroupsContainKeywordsPredicate(
                firstPredicateKeywordList);
        StudyGroupsContainKeywordsPredicate secondPredicate = new StudyGroupsContainKeywordsPredicate(
                secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        StudyGroupsContainKeywordsPredicate firstPredicateCopy = new StudyGroupsContainKeywordsPredicate(
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
    public void test_studyGroupsContainKeywords_returnsTrue() {
        // One keyword
        StudyGroupsContainKeywordsPredicate predicate = new StudyGroupsContainKeywordsPredicate(
                Collections.singletonList("Group1"));
        assertTrue(predicate.test(new PersonBuilder().withStudyGroupTags("Group1", "Group2").build()));

        // Multiple keywords
        predicate = new StudyGroupsContainKeywordsPredicate(Arrays.asList("Group1", "Group2"));
        assertTrue(predicate.test(new PersonBuilder().withStudyGroupTags("Group1", "Group2").build()));

        // Only one matching keyword
        predicate = new StudyGroupsContainKeywordsPredicate(Arrays.asList("Group1", "NotGroup"));
        assertTrue(predicate.test(new PersonBuilder().withStudyGroupTags("Group1", "Group2").build()));

        // Mixed-case keywords
        predicate = new StudyGroupsContainKeywordsPredicate(Arrays.asList("gRoUP1", "GRouP2"));
        assertTrue(predicate.test(new PersonBuilder().withStudyGroupTags("Group1", "Group2").build()));
    }

    @Test
    public void test_studyGroupsDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        StudyGroupsContainKeywordsPredicate predicate = new StudyGroupsContainKeywordsPredicate(
                Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withStudyGroupTags("Group1", "Group2").build()));

        // Non-matching keyword
        predicate = new StudyGroupsContainKeywordsPredicate(Arrays.asList("Group3", "Group4"));
        assertFalse(predicate.test(new PersonBuilder().withStudyGroupTags("Group1", "Group2").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        StudyGroupsContainKeywordsPredicate predicate = new StudyGroupsContainKeywordsPredicate(keywords);

        String expected = StudyGroupsContainKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
