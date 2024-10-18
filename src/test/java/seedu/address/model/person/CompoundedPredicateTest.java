package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class CompoundedPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsKeywordsPredicate firstNamePredicate = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        NameContainsKeywordsPredicate secondNamePredicate =
                new NameContainsKeywordsPredicate(secondPredicateKeywordList);
        OrgContainsKeywordsPredicate firstOrgPredicate = new OrgContainsKeywordsPredicate(firstPredicateKeywordList);
        OrgContainsKeywordsPredicate secondOrgPredicate = new OrgContainsKeywordsPredicate(secondPredicateKeywordList);

        CompoundedPredicate firstPredicate = new CompoundedPredicate(firstNamePredicate, firstOrgPredicate);
        CompoundedPredicate secondPredicate = new CompoundedPredicate(secondNamePredicate, secondOrgPredicate);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CompoundedPredicate firstPredicateCopy = new CompoundedPredicate(firstNamePredicate, firstOrgPredicate);
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

        //Name and Organisation match
        CompoundedPredicate predicate =
                new CompoundedPredicate(new NameContainsKeywordsPredicate(Collections.singletonList("Alice")),
                        new OrgContainsKeywordsPredicate(Collections.singletonList("NUS")));

        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withOrganisation("NUS").build()));

        //Name keyword is blank
        predicate =
                new CompoundedPredicate(new NameContainsKeywordsPredicate(Collections.singletonList("")),
                        new OrgContainsKeywordsPredicate(Collections.singletonList("NTU")));

        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withOrganisation("NTU").build()));

        //Organisation keyword is blank
        predicate =
                new CompoundedPredicate(new NameContainsKeywordsPredicate(Collections.singletonList("Alice")),
                        new OrgContainsKeywordsPredicate(Collections.singletonList("")));

        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withOrganisation("NUS").build()));

    }

    @Test
    public void test_orgDoesNotContainKeywords_returnsFalse() {

        //Only Name matches
        CompoundedPredicate predicate =
                new CompoundedPredicate(new NameContainsKeywordsPredicate(Collections.singletonList("Alice")),
                        new OrgContainsKeywordsPredicate(Collections.singletonList("NTU")));

        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").withOrganisation("NUS").build()));

        //Only Organisation matches
        predicate =
                new CompoundedPredicate(new NameContainsKeywordsPredicate(Collections.singletonList("Alice")),
                        new OrgContainsKeywordsPredicate(Collections.singletonList("NTU")));

        assertFalse(predicate.test(new PersonBuilder().withName("Bob Clarice").withOrganisation("NTU").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        CompoundedPredicate predicate = new CompoundedPredicate(new NameContainsKeywordsPredicate(keywords),
                new OrgContainsKeywordsPredicate(keywords));

        String expected =
                CompoundedPredicate.class.getCanonicalName() + "{name predicate="
                        + NameContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}, "
                        + "organisation predicate=" + OrgContainsKeywordsPredicate.class.getCanonicalName()
                        + "{keywords=" + keywords + "}" + "}";
        assertEquals(expected, predicate.toString());
    }
}
