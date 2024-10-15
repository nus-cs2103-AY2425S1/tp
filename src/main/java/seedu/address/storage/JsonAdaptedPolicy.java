package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.policy.*;

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
    public Policy toModelType() throws IllegalArgumentException {
        final PolicyType modelPolicyType = PolicyType.valueOf(policyType);
        if (modelPolicyType == PolicyType.LIFE) {
            return new LifePolicy(premiumAmount, coverageAmount);
        }
        if (modelPolicyType == PolicyType.HEALTH) {
            return new HealthPolicy(premiumAmount, coverageAmount);
        }
        if (modelPolicyType == PolicyType.EDUCATION) {
            return new EducationPolicy(premiumAmount, coverageAmount);
        }
    }
}
