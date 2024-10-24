package seedu.address.model.skill;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class SkillsContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        SkillsContainsKeywordsPredicate firstPredicate = new SkillsContainsKeywordsPredicate(
                firstPredicateKeywordList);
        SkillsContainsKeywordsPredicate secondPredicate = new SkillsContainsKeywordsPredicate(
                secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        SkillsContainsKeywordsPredicate firstPredicateCopy = new SkillsContainsKeywordsPredicate(
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
    public void test_skillsContainsKeywords_returnsTrue() {
        // One keyword
        SkillsContainsKeywordsPredicate predicate = new SkillsContainsKeywordsPredicate(
                Collections.singletonList("database"));
        assertTrue(predicate.test(new PersonBuilder().withSkills("database").build()));

        // Multiple keywords
        predicate = new SkillsContainsKeywordsPredicate(Arrays.asList("frontend", "backend"));
        assertTrue(predicate.test(new PersonBuilder().withSkills("frontend", "backend").build()));

        // Only one matching keyword
        predicate = new SkillsContainsKeywordsPredicate(Arrays.asList("frontend", "backend"));
        assertTrue(predicate.test(new PersonBuilder().withSkills("frontend", "database").build()));

        // Mixed-case keywords
        predicate = new SkillsContainsKeywordsPredicate(Arrays.asList("fRonTenD", "BacKEnD"));
        assertTrue(predicate.test(new PersonBuilder().withSkills("frontend", "backend").build()));
    }

    @Test
    public void test_skillsDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        SkillsContainsKeywordsPredicate predicate = new SkillsContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withSkills("frontend").build()));

        // Non-matching keyword
        predicate = new SkillsContainsKeywordsPredicate(Arrays.asList("database"));
        assertFalse(predicate.test(new PersonBuilder().withSkills("frontend", "backend").build()));

        // Person does not have any skills
        predicate = new SkillsContainsKeywordsPredicate(Arrays.asList("frontend", "backend"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void test_personIsNull_throwsAssertionError() {
        SkillsContainsKeywordsPredicate predicate = new SkillsContainsKeywordsPredicate(Arrays.asList("database"));
        assertThrows(AssertionError.class, () -> predicate.test(null));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        SkillsContainsKeywordsPredicate predicate = new SkillsContainsKeywordsPredicate(keywords);

        String expected = SkillsContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
