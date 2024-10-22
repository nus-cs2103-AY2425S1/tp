package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.model.policy.PolicySet;
import seedu.address.model.policy.PolicyType;

/**
 * Tests that a {@code Person}'s {@code PolicySet} contains the given policy type.
 */
public class PolicyTypeMatchesPredicate implements Predicate<Person> {
    private final PolicyType policyType;

    public PolicyTypeMatchesPredicate(PolicyType policyType) {
        this.policyType = policyType;
    }

    @Override
    public boolean test(Person person) {
        PolicySet policySet = new PolicySet();
        policySet.addAll(person.getPolicies());
        return policySet.contains(policyType);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof PolicyTypeMatchesPredicate
                && policyType.equals(((PolicyTypeMatchesPredicate) other).policyType));
    }
}
