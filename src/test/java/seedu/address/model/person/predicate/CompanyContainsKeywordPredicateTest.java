package seedu.address.model.person.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.predicates.CompanyContainsKeywordPredicate;
import seedu.address.testutil.PersonBuilder;

public class CompanyContainsKeywordPredicateTest {
    @Test
    public void equals() {

        CompanyContainsKeywordPredicate firstPredicate = new CompanyContainsKeywordPredicate("NUS");
        CompanyContainsKeywordPredicate secondPredicate = new CompanyContainsKeywordPredicate("NTU");

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CompanyContainsKeywordPredicate firstPredicateCopy = new CompanyContainsKeywordPredicate("NUS");
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_companyContainsKeywords_returnsTrue() {
        // One keyword
        CompanyContainsKeywordPredicate predicate = new CompanyContainsKeywordPredicate("NUS Computing");
        assertTrue(predicate.test(new PersonBuilder().withCompany("NUS Computing").build()));

        // Partial matching keyword
        predicate = new CompanyContainsKeywordPredicate("NU");
        assertTrue(predicate.test(new PersonBuilder().withCompany("NUS Computing").build()));

        // Mixed-case keywords
        predicate = new CompanyContainsKeywordPredicate("nTu");
        assertTrue(predicate.test(new PersonBuilder().withCompany("NTU Art").build()));
    }

    @Test
    public void test_companyDoesNotContainKeywords_returnsFalse() {
        // Bad keyword
        CompanyContainsKeywordPredicate predicate = new CompanyContainsKeywordPredicate("$$$$$$$$$$$$");
        assertFalse(predicate.test(new PersonBuilder().withCompany("NUS Computing").build()));

        // Non-matching keyword
        predicate = new CompanyContainsKeywordPredicate("SMU");
        assertFalse(predicate.test(new PersonBuilder().withCompany("NUS Computing").build()));

    }

    @Test
    public void toStringMethod() {
        CompanyContainsKeywordPredicate predicate = new CompanyContainsKeywordPredicate("testing 1 2 3");

        String expected = CompanyContainsKeywordPredicate.class.getCanonicalName() + "{keyword=testing 1 2 3}";
        assertEquals(expected, predicate.toString());
    }
}
