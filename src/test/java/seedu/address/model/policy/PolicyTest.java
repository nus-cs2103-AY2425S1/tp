package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.dateformatter.DateFormatter.MM_DD_YYYY_FORMATTER;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class PolicyTest {
    @Test
    public void constructor_negativeAmounts_throwsIllegalArgumentException() {
        final LocalDate expiryDate = LocalDate.now();

        // Constructor using premiumAmount, coverageAmount and expiryDate only
        assertThrows(IllegalArgumentException.class, () -> new ConcretePolicy(-1, 0, expiryDate));
        assertThrows(IllegalArgumentException.class, () -> new ConcretePolicy(0, -1, expiryDate));
    }

    @Test
    public void constructor_nullInputs_throwsNullPointerException() {

        // Constructor using premiumAmount, coverageAmount and expiryDate only
        assertThrows(NullPointerException.class, () -> new ConcretePolicy(0, 0, null));

    }

    @Test
    public void getters_returnCorrectValues() {
        final double premiumAmount = 100;
        final double coverageAmount = 200;
        final LocalDate expiryDate = LocalDate.now();
        final Policy policy = new ConcretePolicy(premiumAmount, coverageAmount, expiryDate);

        assertEquals(premiumAmount, policy.getPremiumAmount());
        assertEquals(coverageAmount, policy.getCoverageAmount());
        assertEquals(expiryDate, policy.getExpiryDate());
    }

    @Test
    public void setters_setCorrectValues() {
        final double premiumAmount = 100;
        final double coverageAmount = 200;
        final LocalDate expiryDate = LocalDate.now();
        final Policy policy = new ConcretePolicy(0, 0, expiryDate);

        policy.setPremiumAmount(premiumAmount);
        assertEquals(premiumAmount, policy.getPremiumAmount());
        policy.setCoverageAmount(coverageAmount);
        assertEquals(coverageAmount, policy.getCoverageAmount());
        policy.setExpiryDate(expiryDate);
        assertEquals(expiryDate, policy.getExpiryDate());
    }

    @Test
    public void setters_invalidInputs_throwsExceptions() {
        final Policy policy = new ConcretePolicy(0, 0, LocalDate.now());
        assertThrows(IllegalArgumentException.class, () -> policy.setPremiumAmount(-1)); // negative premiumAmount
        assertThrows(IllegalArgumentException.class, () -> policy.setCoverageAmount(-1)); // negative coverageAmount;
        assertThrows(NullPointerException.class, () -> policy.setExpiryDate(null)); // null expiryDate;
    }

    @Test
    public void equalsMethod() {
        final double premiumAmount = 100;
        final double coverageAmount = 200;
        final LocalDate expiryDate = LocalDate.now();
        final Policy policy = new ConcretePolicy(premiumAmount, coverageAmount, expiryDate);

        // same object -> returns true
        assertTrue(policy.equals(policy));

        // null -> returns false
        assertFalse(policy.equals(null));

        // different type -> returns false
        assertFalse(policy.equals(5));

        // policies with different premiumAmount -> returns false
        final Policy policyWithDifferentPremiumAmount = new ConcretePolicy(0, coverageAmount, expiryDate);
        assertFalse(policy.equals(policyWithDifferentPremiumAmount));

        // policies with different coverageAmount -> returns false
        final Policy policyWithDifferentCoverageAmount = new ConcretePolicy(premiumAmount, 0, expiryDate);
        assertFalse(policy.equals(policyWithDifferentCoverageAmount));

        // policies with different expiryDate -> returns false
        final LocalDate differentExpiryDate = LocalDate.of(2000, 10, 10);
        final Policy policyWithDifferentExpiry = new ConcretePolicy(premiumAmount, coverageAmount,
                differentExpiryDate);
        assertFalse(policy.equals(policyWithDifferentExpiry));

        // policies with same values -> returns true
        final Policy policyWithSameValues = new ConcretePolicy(premiumAmount, coverageAmount, expiryDate);
        assertTrue(policy.equals(policyWithSameValues));
    }

    @Test
    public void isExpiredMethod() {
        final LocalDate today = LocalDate.now();
        final LocalDate yesterday = today.minusDays(1);
        final LocalDate tomorrow = today.plusDays(1);

        final Policy policy = new ConcretePolicy(0, 0, today);
        assertTrue(policy.isExpired());

        policy.setExpiryDate(yesterday);
        assertTrue(policy.isExpired());

        policy.setExpiryDate(tomorrow);
        assertFalse(policy.isExpired());
    }

    @Test
    public void toStringMethod() {
        final Policy policy = new ConcretePolicy(0, 0, LocalDate.now());
        final String expected = String.format("Premium amount: $%.2f | Coverage amount: $%.2f "
                + "| Expiry date: %s",
                policy.getPremiumAmount(), policy.getCoverageAmount(),
                policy.getExpiryDate().format(MM_DD_YYYY_FORMATTER));
        assertEquals(expected, policy.toString());
    }

    /**
     * A simple concrete Policy class for unit testing an abstract Policy class.
     */
    private class ConcretePolicy extends Policy {
        public ConcretePolicy(double premiumAmount, double coverageAmount, LocalDate expiryDate) {
            super(premiumAmount, coverageAmount, expiryDate);
        }

        @Override
        public PolicyType getType() {
            return null;
        }
    }
}
