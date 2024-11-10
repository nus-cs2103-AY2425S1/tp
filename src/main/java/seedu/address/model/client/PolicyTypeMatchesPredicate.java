package seedu.address.model.client;

import java.util.function.Predicate;

import seedu.address.model.policy.PolicySet;
import seedu.address.model.policy.PolicyType;

/**
 * Tests that a {@code Client}'s {@code PolicySet} contains the given policy type.
 */
public class PolicyTypeMatchesPredicate implements Predicate<Client> {
    private final PolicyType policyType;

    public PolicyTypeMatchesPredicate(PolicyType policyType) {
        this.policyType = policyType;
    }

    @Override
    public boolean test(Client client) {
        PolicySet policySet = new PolicySet(client.getPolicies());
        return policySet.contains(policyType);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof PolicyTypeMatchesPredicate
                && policyType.equals(((PolicyTypeMatchesPredicate) other).policyType));
    }
}
