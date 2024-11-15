package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class PolicyExpiryDatePredicateTest {

    private final LocalDate currentDate = LocalDate.now();
    private final PolicyExpiryDatePredicate predicateWith10Days = new PolicyExpiryDatePredicate(currentDate, 10);

    private Policy createPolicyWithExpiryInDays(int daysFromNow) {
        return new HealthPolicy(null, null, new ExpiryDate(currentDate.plusDays(daysFromNow)), null);
    }

    @Test
    public void test_policyExpiresWithinDays_returnsTrue() {
        Policy policyExpiringIn5Days = createPolicyWithExpiryInDays(5);
        assertTrue(predicateWith10Days.test(policyExpiringIn5Days));
    }

    @Test
    public void test_policyExpiresToday_returnsTrue() {
        Policy policyExpiringToday = createPolicyWithExpiryInDays(0);
        assertTrue(predicateWith10Days.test(policyExpiringToday));
    }

    @Test
    public void test_policyExpiresExactlyAtEndOfDaysFromExpiry_returnsTrue() {
        Policy policyExpiringIn10Days = createPolicyWithExpiryInDays(10);
        assertTrue(predicateWith10Days.test(policyExpiringIn10Days));
    }

    @Test
    public void test_policyExpiresAfterDaysFromExpiry_returnsFalse() {
        Policy policyExpiringIn15Days = createPolicyWithExpiryInDays(15);
        assertFalse(predicateWith10Days.test(policyExpiringIn15Days));
    }

    @Test
    public void test_policyAlreadyExpired_returnsFalse() {
        Policy policyExpired5DaysAgo = createPolicyWithExpiryInDays(-5);
        assertFalse(predicateWith10Days.test(policyExpired5DaysAgo));
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(predicateWith10Days.equals(predicateWith10Days));

        // same values -> returns true
        PolicyExpiryDatePredicate predicateCopy = new PolicyExpiryDatePredicate(currentDate, 10);
        assertTrue(predicateWith10Days.equals(predicateCopy));

        // different types -> returns false
        assertFalse(predicateWith10Days.equals(5));

        // null -> returns false
        assertFalse(predicateWith10Days.equals(null));

        // different expiry days -> returns false
        PolicyExpiryDatePredicate differentDaysPredicate = new PolicyExpiryDatePredicate(currentDate, 15);
        assertFalse(predicateWith10Days.equals(differentDaysPredicate));

        // different current date -> returns false
        PolicyExpiryDatePredicate differentDatePredicate = new PolicyExpiryDatePredicate(currentDate.minusDays(1), 10);
        assertFalse(predicateWith10Days.equals(differentDatePredicate));
    }
}
