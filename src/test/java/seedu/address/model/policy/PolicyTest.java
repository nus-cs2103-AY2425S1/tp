package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertInstanceOf;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.claim.Claim;
import seedu.address.model.claim.ClaimList;
import seedu.address.model.claim.ClaimStatus;
import seedu.address.model.claim.exceptions.DuplicateClaimException;

public class PolicyTest {
    private final PremiumAmount premiumAmount = new PremiumAmount(200.0);
    private final CoverageAmount coverageAmount = new CoverageAmount(20000.0);
    private final ExpiryDate expiryDate = new ExpiryDate("12/23/2024");
    private final ClaimList claimList = makeClaimListWithOneClaim();

    @Test
    public void constructor_nullInputs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ConcretePolicy(null, coverageAmount,
                expiryDate, claimList));
        assertThrows(NullPointerException.class, () -> new ConcretePolicy(premiumAmount, null,
                expiryDate, claimList));
        assertThrows(NullPointerException.class, () -> new ConcretePolicy(premiumAmount, coverageAmount,
                null, claimList));
        assertThrows(NullPointerException.class, () -> new ConcretePolicy(premiumAmount, coverageAmount,
                expiryDate, null));
    }

    @Test
    public void makePolicy_returnCorrectPolicy() {
        // return LifePolicy
        Policy expected = new LifePolicy(premiumAmount, coverageAmount, expiryDate, claimList);
        Policy actual = Policy.makePolicy(PolicyType.LIFE, premiumAmount, coverageAmount, expiryDate,
                claimList);
        assertEquals(expected, actual);

        // return HealthPolicy
        expected = new HealthPolicy(premiumAmount, coverageAmount, expiryDate, claimList);
        actual = Policy.makePolicy(PolicyType.HEALTH, premiumAmount, coverageAmount, expiryDate,
                claimList);
        assertEquals(expected, actual);

        // return EducationPolicy
        expected = new EducationPolicy(premiumAmount, coverageAmount, expiryDate, claimList);
        actual = Policy.makePolicy(PolicyType.EDUCATION, premiumAmount, coverageAmount, expiryDate,
                claimList);
        assertEquals(expected, actual);
    }

    @Test
    public void makePolicy_nullPolicyType_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Policy.makePolicy(
                null, premiumAmount, coverageAmount, expiryDate, claimList));
    }

    @Test
    public void getters_returnCorrectValues() {
        final Policy policy = new ConcretePolicy(premiumAmount, coverageAmount, expiryDate, claimList);

        assertEquals(premiumAmount, policy.getPremiumAmount());
        assertEquals(coverageAmount, policy.getCoverageAmount());
        assertEquals(expiryDate, policy.getExpiryDate());
        assertEquals(List.copyOf(claimList), policy.getClaimList());
    }

    @Test
    public void addClaim_returnNewUpdatedPolicy() {
        // LifePolicy is used here instead of ConcretePolicy since Policy#addClaim requires a specific policy type
        final LifePolicy policy = new LifePolicy(premiumAmount, coverageAmount, expiryDate, new ClaimList());
        final Claim claim = new Claim(ClaimStatus.PENDING, "foo");

        final ClaimList expectedClaimList = new ClaimList();
        expectedClaimList.add(claim);

        final Policy actualPolicy = policy.addClaim(claim);

        assertInstanceOf(Policy.class, actualPolicy);
        assertEquals(List.copyOf(expectedClaimList), actualPolicy.getClaimList());
    }

    @Test
    public void addClaim_nullInput_throwsNullPointerException() {
        // LifePolicy is used here instead of ConcretePolicy since Policy#addClaim requires a specific policy type
        final LifePolicy policy = new LifePolicy(premiumAmount, coverageAmount, expiryDate, claimList);
        assertThrows(NullPointerException.class, () -> policy.addClaim(null));
    }

    @Test
    public void addClaim_duplicateClaim_throwsDuplicateClaimException() {
        // LifePolicy is used here instead of ConcretePolicy since Policy#addClaim requires a specific policy type
        final LifePolicy policy = new LifePolicy(premiumAmount, coverageAmount, expiryDate, claimList);
        final Claim duplicateClaim = claimList.get(0); // Get the first claim in claimList
        assertThrows(DuplicateClaimException.class, () -> policy.addClaim(duplicateClaim));
    }

    @Test
    public void deleteClaim_returnNewUpdatedPolicy() {
        // LifePolicy is used here instead of ConcretePolicy since Policy#removeClaim requires a specific policy type
        final LifePolicy policy = new LifePolicy(premiumAmount, coverageAmount, expiryDate, claimList);
        final ClaimList expectedClaimList = new ClaimList(claimList);
        expectedClaimList.remove(0);

        final Policy actualPolicy = policy.removeClaim(0);

        assertInstanceOf(Policy.class, actualPolicy);
        assertEquals(List.copyOf(expectedClaimList), actualPolicy.getClaimList());
    }

    @Test
    public void equalsMethod() {
        final Policy policy = new ConcretePolicy(premiumAmount, coverageAmount, expiryDate, claimList);

        // same object -> returns true
        assertTrue(policy.equals(policy));

        // null -> returns false
        assertFalse(policy.equals(null));

        // different type -> returns false
        assertFalse(policy.equals(5));

        // policies with different premiumAmount -> returns false
        final PremiumAmount differentPremiumAmount = new PremiumAmount(0);
        final Policy policyWithDifferentPremiumAmount = new ConcretePolicy(
                differentPremiumAmount, coverageAmount, expiryDate, claimList);
        assertFalse(policy.equals(policyWithDifferentPremiumAmount));

        // policies with different coverageAmount -> returns false
        final CoverageAmount differentCoverageAmount = new CoverageAmount(0);
        final Policy policyWithDifferentCoverageAmount = new ConcretePolicy(
                premiumAmount, differentCoverageAmount, expiryDate, claimList);
        assertFalse(policy.equals(policyWithDifferentCoverageAmount));

        // policies with different expiryDate -> returns false
        final ExpiryDate differentExpiryDate = new ExpiryDate("12/12/1000");
        final Policy policyWithDifferentExpiry = new ConcretePolicy(
                premiumAmount, coverageAmount, differentExpiryDate, claimList);
        assertFalse(policy.equals(policyWithDifferentExpiry));

        // policies with different claimList -> returns false
        final ClaimList differentClaimList = new ClaimList();
        differentClaimList.add(new Claim(ClaimStatus.PENDING, "bar"));
        final Policy policyWithDifferentClaimList = new ConcretePolicy(
                premiumAmount, coverageAmount, expiryDate, differentClaimList);
        assertFalse(policy.equals(policyWithDifferentClaimList));

        // policies with same values -> returns true
        final Policy policyWithSameValues = new ConcretePolicy(premiumAmount, coverageAmount, expiryDate, claimList);
        assertTrue(policy.equals(policyWithSameValues));
    }

    @Test
    public void toString_policyWithClaims() {
        final Policy policy = new ConcretePolicy(premiumAmount, coverageAmount, expiryDate, claimList);
        final String expected = String.format("Premium amount: $%s | Coverage amount: $%s "
                + "| Expiry date: %s | Claims:\n%s",
                policy.getPremiumAmount().toString(), policy.getCoverageAmount().toString(),
                policy.getExpiryDate().toString(), policy.getClaimList().toString());
        assertEquals(expected, policy.toString());
    }

    @Test
    public void toString_policyWithoutClaims() {
        final ClaimList emptyClaimList = new ClaimList();
        final Policy policy = new ConcretePolicy(premiumAmount, coverageAmount, expiryDate, emptyClaimList);
        final String expected = String.format("Premium amount: $%s | Coverage amount: $%s "
                + "| Expiry date: %s | No claims",
                policy.getPremiumAmount().toString(), policy.getCoverageAmount().toString(),
                policy.getExpiryDate().toString(), policy.getClaimList().toString());
        assertEquals(expected, policy.toString());
    }

    private ClaimList makeClaimListWithOneClaim() {
        ClaimList claimList = new ClaimList();
        claimList.add(new Claim(ClaimStatus.PENDING, "foo"));
        return claimList;
    }

    /**
     * A simple concrete Policy class for unit testing an abstract Policy class.
     */
    private class ConcretePolicy extends Policy {
        public ConcretePolicy(PremiumAmount premiumAmount, CoverageAmount coverageAmount, ExpiryDate expiryDate,
                ClaimList claimList) {
            super(premiumAmount, coverageAmount, expiryDate, claimList);
        }

        @Override
        public PolicyType getType() {
            return null;
        }
    }
}
