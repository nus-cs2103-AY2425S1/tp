package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.claim.Claim;
import seedu.address.model.claim.ClaimList;
import seedu.address.model.claim.ClaimStatus;
import seedu.address.model.policy.exceptions.DuplicateClaimException;

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
        Policy expected = new LifePolicy(premiumAmount, coverageAmount, expiryDate, null);
        Policy actual = Policy.makePolicy(PolicyType.LIFE, premiumAmount, coverageAmount, expiryDate, null);
        assertEquals(expected, actual);

        // return HealthPolicy
        expected = new HealthPolicy(premiumAmount, coverageAmount, expiryDate, null);
        actual = Policy.makePolicy(PolicyType.HEALTH, premiumAmount, coverageAmount, expiryDate, null);
        assertEquals(expected, actual);

        // return EducationPolicy
        expected = new EducationPolicy(premiumAmount, coverageAmount, expiryDate, null);
        actual = Policy.makePolicy(PolicyType.EDUCATION, premiumAmount, coverageAmount, expiryDate, null);
        assertEquals(expected, actual);
    }

    @Test
    public void makePolicy_nullPolicyType_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Policy.makePolicy(
                null, premiumAmount, coverageAmount, expiryDate, null));
    }

    @Test
    public void getters_returnCorrectValues() {
        final Policy policy = new ConcretePolicy(premiumAmount, coverageAmount, expiryDate);

        assertEquals(premiumAmount, policy.getPremiumAmount());
        assertEquals(coverageAmount, policy.getCoverageAmount());
        assertEquals(expiryDate, policy.getExpiryDate());
    }

    @Test
    public void addClaim_returnNewUpdatedPolicy() {
        final ConcretePolicy policy = new ConcretePolicy(premiumAmount, coverageAmount, expiryDate);
        final Claim claim = new Claim(ClaimStatus.PENDING, "foo");

        final ClaimList expectedClaimList = new ClaimList();
        expectedClaimList.add(claim);
        final Policy expectedPolicy = new LifePolicy(premiumAmount, coverageAmount, expiryDate, expectedClaimList);

        final Policy actualPolicy = policy.addClaim(claim);

        assertEquals(expectedPolicy, actualPolicy);
    }

    @Test
    public void addClaim_nullInput_throwsNullPointerException() {
        final ConcretePolicy policy = new ConcretePolicy(premiumAmount, coverageAmount, expiryDate);
        assertThrows(NullPointerException.class, () -> policy.addClaim(null));
    }

    @Test
    public void addClaim_duplicateClaim_throwsDuplicateClaimException() {
        final ConcretePolicy policy = new ConcretePolicy(premiumAmount, coverageAmount, expiryDate);
        final Claim duplicateClaim = new Claim(ClaimStatus.PENDING, "foo");
        Policy updatedPolicy = policy.addClaim(duplicateClaim);
        assertThrows(DuplicateClaimException.class, () -> updatedPolicy.addClaim(duplicateClaim));
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
    public void toString_policyWithClaims() {
        final Policy policy = new ConcretePolicy(premiumAmount, coverageAmount, expiryDate);
        final Claim claim = new Claim(ClaimStatus.PENDING, "foo");
        final Policy policyWithClaim = policy.addClaim(claim);
        final String expected = String.format("Policy type: Life | Premium amount: $%s | Coverage amount: $%s "
                + "| Expiry date: %s | Claims:\n%s",
                policy.getPremiumAmount().toString(), policy.getCoverageAmount().toString(),
                policy.getExpiryDate().toString(), policyWithClaim.getClaimList().toString());
        assertEquals(expected, policyWithClaim.toString());
    }

    @Test
    public void toString_policyWithoutClaims() {
        final Policy policy = new ConcretePolicy(premiumAmount, coverageAmount, expiryDate);
        final String expected = String.format("Premium amount: $%s | Coverage amount: $%s "
                + "| Expiry date: %s | No claims",
                policy.getPremiumAmount().toString(), policy.getCoverageAmount().toString(),
                policy.getExpiryDate().toString(), policy.getClaimList().toString());
        assertEquals(expected, policy.toString());
    }

    /**
     * A simple concrete Policy class for unit testing an abstract Policy class.
     */
    private class ConcretePolicy extends Policy {
        public ConcretePolicy(PremiumAmount premiumAmount, CoverageAmount coverageAmount, ExpiryDate expiryDate) {
            super(premiumAmount, coverageAmount, expiryDate, new ClaimList());
        }

        @Override
        public PolicyType getType() {
            return PolicyType.LIFE;
        }
    }
}
