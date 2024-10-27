package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.model.claim.ClaimList;

public class PolicyExpiryDatePredicateTest {

    private final LocalDate currentDate = LocalDate.now();

    @Test
    public void test_policyExpiresWithinDays_returnsTrue() {
        // Policy that expires within the given time frame (e.g., 10 days from now)
        Policy policy = new HealthPolicy(null, null,
                new ExpiryDate(currentDate.plusDays(5)), new ClaimList());
        PolicyExpiryDatePredicate predicate = new PolicyExpiryDatePredicate(currentDate, 10);
        assertTrue(predicate.test(policy));
    }

    @Test
    public void test_policyExpiresToday_returnsTrue() {
        // Policy that expires today
        Policy policy = new HealthPolicy(null, null, new ExpiryDate(currentDate),
                new ClaimList());
        PolicyExpiryDatePredicate predicate = new PolicyExpiryDatePredicate(currentDate, 10);
        assertTrue(predicate.test(policy));
    }

    @Test
    public void test_policyExpiresExactlyAtEndOfDaysFromExpiry_returnsTrue() {
        // Policy that expires exactly at the end of the given time frame (e.g., 10 days from now)
        Policy policy = new HealthPolicy(null, null,
                new ExpiryDate(currentDate.plusDays(10)), new ClaimList());
        PolicyExpiryDatePredicate predicate = new PolicyExpiryDatePredicate(currentDate, 10);
        assertTrue(predicate.test(policy));
    }

    @Test
    public void test_policyExpiresAfterDaysFromExpiry_returnsFalse() {
        // Policy that expires after the given time frame (e.g., 15 days from now)
        Policy policy = new HealthPolicy(null, null,
                new ExpiryDate(currentDate.plusDays(15)), new ClaimList());
        PolicyExpiryDatePredicate predicate = new PolicyExpiryDatePredicate(currentDate, 10);
        assertFalse(predicate.test(policy));
    }

    @Test
    public void test_policyAlreadyExpired_returnsFalse() {
        // Policy that expired in the past (e.g., 5 days ago)
        Policy policy = new HealthPolicy(null, null,
                new ExpiryDate(currentDate.minusDays(5)), new ClaimList());
        PolicyExpiryDatePredicate predicate = new PolicyExpiryDatePredicate(currentDate, 10);
        assertFalse(predicate.test(policy));
    }

    @Test
    public void equals() {
        // same object -> returns true
        PolicyExpiryDatePredicate predicate = new PolicyExpiryDatePredicate(currentDate, 10);
        assertTrue(predicate.equals(predicate));

        // same values -> returns true
        PolicyExpiryDatePredicate predicateCopy = new PolicyExpiryDatePredicate(currentDate, 10);
        assertTrue(predicate.equals(predicateCopy));

        // different types -> returns false
        assertFalse(predicate.equals(5));

        // null -> returns false
        assertFalse(predicate.equals(null));

        // different expiry days -> returns false
        PolicyExpiryDatePredicate differentPredicate = new PolicyExpiryDatePredicate(currentDate, 15);
        assertFalse(predicate.equals(differentPredicate));

        // different current date -> returns false
        PolicyExpiryDatePredicate differentDatePredicate = new PolicyExpiryDatePredicate(currentDate.minusDays(1), 10);
        assertFalse(predicate.equals(differentDatePredicate));
    }
}
