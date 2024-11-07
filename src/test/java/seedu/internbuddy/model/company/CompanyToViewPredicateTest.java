package seedu.internbuddy.model.company;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.internbuddy.testutil.TypicalCompanies;

public class CompanyToViewPredicateTest {

    @Test
    public void equals() {

        CompanyToViewPredicate firstPredicate = new CompanyToViewPredicate(TypicalCompanies.APPLE_FAVOURITED);
        CompanyToViewPredicate secondPredicate = new CompanyToViewPredicate(TypicalCompanies.GOOGLE);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CompanyToViewPredicate firstPredicateCopy = new CompanyToViewPredicate(TypicalCompanies.APPLE_FAVOURITED);
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
        CompanyToViewPredicate predicate = new CompanyToViewPredicate(TypicalCompanies.APPLE_FAVOURITED);
        assertTrue(predicate.test(TypicalCompanies.APPLE_FAVOURITED));
    }


    @Test
    public void test_doesNotMatchCompanyToView_returnsFalse() {
        CompanyToViewPredicate predicate = new CompanyToViewPredicate(TypicalCompanies.APPLE_FAVOURITED);
        assertFalse(predicate.test(TypicalCompanies.GOOGLE));
    }

    @Test
    public void toStringMethod() {
        CompanyToViewPredicate predicate = new CompanyToViewPredicate(TypicalCompanies.APPLE_FAVOURITED);

        String expected = CompanyToViewPredicate.class.getCanonicalName()
                + "{companyToView=" + TypicalCompanies.APPLE_FAVOURITED + "}";
        assertEquals(expected, predicate.toString());
    }
}
