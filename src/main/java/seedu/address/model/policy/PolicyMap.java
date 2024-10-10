package seedu.address.model.policy;

import java.util.HashMap;

/**
 * A class to store a mapping of {@code PolicyType} to {@code Policy}.
 * This ensures that a single PolicyMap cannot have multiple policies with the same {@code PolicyType}.
 * For example, there cannot be two {@code LifePolicy} objects in a PolicyMap.
 */
public class PolicyMap {
    private final HashMap<PolicyType, Policy> policies = new HashMap<>();

    /**
     * Add the specified Policy to the PolicyMap.
     * Does nothing if the type of Policy to add already exists in the PolicyMap.
     * Return whether the Policy has been successfully added to the PolicyMap.
     *
     * @param policy the policy to be added.
     * @return false if the type of Policy to add already exists in the PolicyMap,
     *         and return true otherwise.
     */
    public boolean add(Policy policy) {
        if (policies.containsKey(policy.getType())) {
            return false;
        }
        policies.put(policy.getType(), policy);
        return true;
    }

    /**
     * Deletes the specified Policy Type from this PolicyMap, if it exists.
     * Does nothing if the type of Policy to delete does not exists in the PolicyMap.
     * Return whether the Policy has been deleted successfully.
     *
     * @param type of Policy to be deleted.
     * @return false if no Policy of specified type exists in the PolicyMap,
     *         and return true otherwise.
     */
    public boolean delete(PolicyType type) {
        if (!policies.containsKey(type)) {
            return false;
        }
        policies.remove(type);
        return true;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        int count = 1;
        for (Policy policy : policies.values()) {
            result.append(count + ". " + policy + "\n");
            count++;
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PolicyMap)) {
            return false;
        }

        PolicyMap otherPolicyMap = (PolicyMap) other;
        return policies.equals(otherPolicyMap.policies);
    }
}
