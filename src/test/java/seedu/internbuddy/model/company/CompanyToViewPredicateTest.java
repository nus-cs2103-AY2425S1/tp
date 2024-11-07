package seedu.internbuddy.model.company;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.internbuddy.testutil.TypicalCompanies;

public class CompanyToViewPredicateTest {

    @Test
    public void equals() {

        CompanyToViewPredicate firstPredicate = new CompanyToViewPredicate(TypicalCompanies.APPLE);
        CompanyToViewPredicate secondPredicate = new CompanyToViewPredicate(TypicalCompanies.GOOGLE);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CompanyToViewPredicate firstPredicateCopy = new CompanyToViewPredicate(TypicalCompanies.APPLE);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different company -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_matchesCompanyToView_returnsTrue() {
        CompanyToViewPredicate predicate = new CompanyToViewPredicate(TypicalCompanies.APPLE);
        assertTrue(predicate.test(TypicalCompanies.APPLE));
    }


    @Test
    public void test_doesNotMatchCompanyToView_returnsFalse() {
        CompanyToViewPredicate predicate = new CompanyToViewPredicate(TypicalCompanies.APPLE);
        assertFalse(predicate.test(TypicalCompanies.GOOGLE));
    }

    @Test
    public void toStringMethod() {
        CompanyToViewPredicate predicate = new CompanyToViewPredicate(TypicalCompanies.APPLE);

        String expected = CompanyToViewPredicate.class.getCanonicalName()
                + "{companyToView=" + TypicalCompanies.APPLE + "}";
        assertEquals(expected, predicate.toString());
    }
}
