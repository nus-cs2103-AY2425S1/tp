package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.policy.EducationPolicy;
import seedu.address.model.policy.HealthPolicy;
import seedu.address.model.policy.LifePolicy;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyType;

class JsonAdaptedPolicy {
    private final double premiumAmount;
    private final double coverageAmount;
    private final String policyType;
    /**
     * Constructs a {@code JsonAdaptedPolicy} with the given policy details.
     */
    @JsonCreator
    public JsonAdaptedPolicy(@JsonProperty("premiumAmount") double premiumAmount,
                             @JsonProperty("coverageAmount") double coverageAmount,
                             @JsonProperty("policyType") String policyType) {
        this.premiumAmount = premiumAmount;
        this.coverageAmount = coverageAmount;
        this.policyType = policyType;
    }
    /**
     * Converts a given {@code Policy} into this class for Jackson use.
     */
    public JsonAdaptedPolicy(Policy source) {
        this.premiumAmount = source.getPremiumAmount();
        this.coverageAmount = source.getCoverageAmount();
        this.policyType = source.getType().name(); // Enum to String conversion
    }
    /**
     * Converts this Jackson-friendly adapted policy object into the model's {@code Policy} object.
     *
     * @throws IllegalArgumentException if there were any data constraints violated.
     */
    public Policy toModelType() throws IllegalValueException {
        try {
            final PolicyType modelPolicyType = PolicyType.valueOf(policyType.toUpperCase());
            if (modelPolicyType == PolicyType.LIFE) {
                return new LifePolicy(premiumAmount, coverageAmount);
            } else if (modelPolicyType == PolicyType.HEALTH) {
                return new HealthPolicy(premiumAmount, coverageAmount);
            } else {
                return new EducationPolicy(premiumAmount, coverageAmount);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException("Invalid policy type: " + policyType);
        }
    }

}
