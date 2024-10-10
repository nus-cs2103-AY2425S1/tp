package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class PolicyTest {
    @Test
    public void constructor_negativeAmounts_throwsIllegalArgumentException() {
        // Constructor using premiumAMount and coverageAmount only
        assertThrows(IllegalArgumentException.class, () -> new ConcretePolicy(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> new ConcretePolicy(0, -1));

        // Constructor using premiumAmount, coverageAmount, and insuree
        assertThrows(IllegalArgumentException.class, () -> new ConcretePolicy(-1, 0, new PersonBuilder().build()));
        assertThrows(IllegalArgumentException.class, () -> new ConcretePolicy(0, -1, new PersonBuilder().build()));
    }

    @Test
    public void constructor_nullInsuree_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ConcretePolicy(0, 0, null));
    }

    @Test
    public void getters_returnCorrectValues() {
        final double premiumAmount = 100;
        final double coverageAmount = 200;
        final Person insuree = new PersonBuilder().build();
        final Policy policy = new ConcretePolicy(premiumAmount, coverageAmount, insuree);

        assertEquals(premiumAmount, policy.getPremiumAmount());
        assertEquals(coverageAmount, policy.getCoverageAmount());
        assertEquals(insuree, policy.getInsuree());
    }

    @Test
    public void setters_setCorrectValues() {
        final double premiumAmount = 100;
        final double coverageAmount = 200;
        final Person insuree = new PersonBuilder().build();
        final Policy policy = new ConcretePolicy(0, 0);

        policy.setPremiumAmount(premiumAmount);
        assertEquals(premiumAmount, policy.getPremiumAmount());
        policy.setCoverageAmount(coverageAmount);
        assertEquals(coverageAmount, policy.getCoverageAmount());
        policy.setInsuree(insuree);
        assertEquals(insuree, policy.getInsuree());
    }

    @Test
    public void setters_invalidInputs_throwsExceptions() {
        final Policy policy = new ConcretePolicy(0, 0);
        assertThrows(IllegalArgumentException.class, () -> policy.setPremiumAmount(-1)); // negative premiumAmount
        assertThrows(IllegalArgumentException.class, () -> policy.setCoverageAmount(-1)); // negative coverageAmount;
        assertThrows(NullPointerException.class, () -> policy.setInsuree(null)); // null insuree;
    }

    @Test
    public void equalsMethod() {
        final double premiumAmount = 100;
        final double coverageAmount = 200;
        final Person insuree = new PersonBuilder().build();
        final Policy policy = new ConcretePolicy(premiumAmount, coverageAmount, insuree);

        // same object -> returns true
        assertTrue(policy.equals(policy));

        // null -> returns false
        assertFalse(policy.equals(null));

        // different type -> returns false
        assertFalse(policy.equals(5));

        // policies with different premiumAmount -> returns false
        final Policy policyWithDifferentPremiumAmount = new ConcretePolicy(0, coverageAmount, insuree);
        assertFalse(policy.equals(policyWithDifferentPremiumAmount));

        // policies with different coverageAmount -> returns false
        final Policy policyWithDifferentCoverageAmount = new ConcretePolicy(premiumAmount, 0, insuree);
        assertFalse(policy.equals(policyWithDifferentCoverageAmount));

        // policies with different insuree -> returns false
        final Person differentInsuree = new PersonBuilder().withName("foo").build();
        final Policy policyWithDifferentInsuree = new ConcretePolicy(premiumAmount, coverageAmount, differentInsuree);
        assertFalse(policy.equals(policyWithDifferentInsuree));

        // policies with same values -> returns true
        final Policy policyWithSameValues = new ConcretePolicy(premiumAmount, coverageAmount, insuree);
        assertTrue(policy.equals(policyWithSameValues));
    }

    @Test
    public void toStringMethod() {
        final Policy policy = new ConcretePolicy(0, 0);
        final String expected = String.format("Premium amount: $%.2f | Coverage amount: $%.2f",
                policy.getPremiumAmount(), policy.getCoverageAmount());
        assertEquals(expected, policy.toString());
    }

    /**
     * A simple concrete Policy class for unit testing an abstract Policy class.
     */
    private class ConcretePolicy extends Policy {
        public ConcretePolicy(double premiumAmount, double coverageAmount) {
            super(premiumAmount, coverageAmount);
        }

        public ConcretePolicy(double premiumAmount, double coverageAmount, Person insuree) {
            super(premiumAmount, coverageAmount, insuree);
        }

        @Override
        public PolicyType getType() {
            return null;
        }
    }
}
