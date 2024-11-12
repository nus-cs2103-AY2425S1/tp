package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class OrgContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        OrgContainsKeywordsPredicate firstPredicate = new OrgContainsKeywordsPredicate(firstPredicateKeywordList);
        OrgContainsKeywordsPredicate secondPredicate = new OrgContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        OrgContainsKeywordsPredicate firstPredicateCopy = new OrgContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_orgContainsKeywords_returnsTrue() {
        // One keyword
        OrgContainsKeywordsPredicate predicate = new OrgContainsKeywordsPredicate(Collections.singletonList("NUS"));
        assertTrue(predicate.test(new PersonBuilder().build()));

        // Multiple keywords
        predicate = new OrgContainsKeywordsPredicate(Arrays.asList("NUS", "NTU"));
        assertTrue(predicate.test(new PersonBuilder().withOrganisation("NUS NUS").build()));

        // Only one matching keyword
        predicate = new OrgContainsKeywordsPredicate(Arrays.asList("NUS", "NTU"));
        assertTrue(predicate.test(new PersonBuilder().withOrganisation("NTU SMU").build()));

        // Mixed-case keywords
        predicate = new OrgContainsKeywordsPredicate(Arrays.asList("Nus", "Ntu"));
        assertTrue(predicate.test(new PersonBuilder().withOrganisation("NUS").build()));

        // Keyword is blank
        predicate = new OrgContainsKeywordsPredicate(Arrays.asList(""));
        assertTrue(predicate.test(new PersonBuilder().withOrganisation("NUS").build()));
    }

    @Test
    public void test_orgDoesNotContainKeywords_returnsFalse() {

        // Non-matching keyword
        OrgContainsKeywordsPredicate predicate = new OrgContainsKeywordsPredicate(Arrays.asList("NUS"));
        assertFalse(predicate.test(new PersonBuilder().withOrganisation("NTU").build()));

        // Zero keywords
        predicate = new OrgContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Keywords match phone, email and address, but does not match organisation
        predicate = new OrgContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        OrgContainsKeywordsPredicate predicate = new OrgContainsKeywordsPredicate(keywords);

        String expected = OrgContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
