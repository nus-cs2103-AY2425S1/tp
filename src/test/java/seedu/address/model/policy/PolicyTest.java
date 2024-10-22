package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PolicyTest {
    private final PremiumAmount premiumAmount = new PremiumAmount(200.0);
    private final CoverageAmount coverageAmount = new CoverageAmount(20000.0);
    private final ExpiryDate expiryDate = new ExpiryDate("12/23/2024");

    @Test
    public void constructor_nullInputs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ConcretePolicy(null, coverageAmount, expiryDate));
        assertThrows(NullPointerException.class, () -> new ConcretePolicy(premiumAmount, null, expiryDate));
        assertThrows(NullPointerException.class, () -> new ConcretePolicy(premiumAmount, coverageAmount, null));
    }

    @Test
    public void makePolicy_returnCorrectPolicy() {
        // return LifePolicy
        Policy expected = new LifePolicy(premiumAmount, coverageAmount, expiryDate);
        Policy actual = Policy.makePolicy(PolicyType.LIFE, premiumAmount, coverageAmount, expiryDate);
        assertEquals(expected, actual);

        // return HealthPolicy
        expected = new HealthPolicy(premiumAmount, coverageAmount, expiryDate);
        actual = Policy.makePolicy(PolicyType.HEALTH, premiumAmount, coverageAmount, expiryDate);
        assertEquals(expected, actual);

        // return EducationPolicy
        expected = new EducationPolicy(premiumAmount, coverageAmount, expiryDate);
        actual = Policy.makePolicy(PolicyType.EDUCATION, premiumAmount, coverageAmount, expiryDate);
        assertEquals(expected, actual);
    }

    @Test
    public void makePolicy_nullPolicyType_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Policy.makePolicy(
                null, premiumAmount, coverageAmount, expiryDate));
    }

    @Test
    public void getters_returnCorrectValues() {
        final Policy policy = new ConcretePolicy(premiumAmount, coverageAmount, expiryDate);

        assertEquals(premiumAmount, policy.getPremiumAmount());
        assertEquals(coverageAmount, policy.getCoverageAmount());
        assertEquals(expiryDate, policy.getExpiryDate());
    }

    @Test
    public void setters_setCorrectValues() {
        final Policy policy = new ConcretePolicy(new PremiumAmount(0), new CoverageAmount(0), expiryDate);

        policy.setPremiumAmount(premiumAmount);
        assertEquals(premiumAmount, policy.getPremiumAmount());
        policy.setCoverageAmount(coverageAmount);
        assertEquals(coverageAmount, policy.getCoverageAmount());
        policy.setExpiryDate(expiryDate);
        assertEquals(expiryDate, policy.getExpiryDate());
    }

    @Test
    public void setters_nullInputs_throwsNullPointerException() {
        final Policy policy = new ConcretePolicy(premiumAmount, coverageAmount, expiryDate);
        assertThrows(NullPointerException.class, () -> policy.setPremiumAmount(null));
        assertThrows(NullPointerException.class, () -> policy.setCoverageAmount(null));
        assertThrows(NullPointerException.class, () -> policy.setExpiryDate(null));
    }

    @Test
    public void equalsMethod() {
        final Policy policy = new ConcretePolicy(premiumAmount, coverageAmount, expiryDate);

        // same object -> returns true
        assertTrue(policy.equals(policy));

        // null -> returns false
        assertFalse(policy.equals(null));

        // different type -> returns false
        assertFalse(policy.equals(5));

        // policies with different premiumAmount -> returns false
        final PremiumAmount differentPremiumAmount = new PremiumAmount(0);
        final Policy policyWithDifferentPremiumAmount = new ConcretePolicy(
                differentPremiumAmount, coverageAmount, expiryDate);
        assertFalse(policy.equals(policyWithDifferentPremiumAmount));

        // policies with different coverageAmount -> returns false
        final CoverageAmount differentCoverageAmount = new CoverageAmount(0);
        final Policy policyWithDifferentCoverageAmount = new ConcretePolicy(
                premiumAmount, differentCoverageAmount, expiryDate);
        assertFalse(policy.equals(policyWithDifferentCoverageAmount));

        // policies with different expiryDate -> returns false
        final ExpiryDate differentExpiryDate = new ExpiryDate("12/12/1000");
        final Policy policyWithDifferentExpiry = new ConcretePolicy(
                premiumAmount, coverageAmount, differentExpiryDate);
        assertFalse(policy.equals(policyWithDifferentExpiry));

        // policies with same values -> returns true
        final Policy policyWithSameValues = new ConcretePolicy(premiumAmount, coverageAmount, expiryDate);
        assertTrue(policy.equals(policyWithSameValues));
    }

    @Test
    public void toStringMethod() {
        final Policy policy = new ConcretePolicy(premiumAmount, coverageAmount, expiryDate);
        final String expected = String.format("Premium amount: $%s | Coverage amount: $%s "
                + "| Expiry date: %s | Claims: %s",
                policy.getPremiumAmount().toString(), policy.getCoverageAmount().toString(),
                policy.getExpiryDate().toString(), policy.getClaimSet().toString());
        assertEquals(expected, policy.toString());
    }

    /**
     * A simple concrete Policy class for unit testing an abstract Policy class.
     */
    private class ConcretePolicy extends Policy {
        public ConcretePolicy(PremiumAmount premiumAmount, CoverageAmount coverageAmount, ExpiryDate expiryDate) {
            super(premiumAmount, coverageAmount, expiryDate);
        }

        @Override
        public PolicyType getType() {
            return null;
        }
    }
}
