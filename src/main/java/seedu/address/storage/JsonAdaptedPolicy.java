package seedu.address.storage;

import static seedu.address.commons.core.dateformatter.DateFormatter.MM_DD_YYYY_FORMATTER;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.core.dateformatter.DateFormatter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.policy.EducationPolicy;
import seedu.address.model.policy.HealthPolicy;
import seedu.address.model.policy.LifePolicy;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyType;

class JsonAdaptedPolicy {
    private final String policyType;
    private final double premiumAmount;
    private final double coverageAmount;
    private final String expiryDate;

    /**
     * Constructs a {@code JsonAdaptedPolicy} with the given policy details.
     */
    @JsonCreator
    public JsonAdaptedPolicy(@JsonProperty("policyType") String policyType,
                             @JsonProperty("premiumAmount") double premiumAmount,
                             @JsonProperty("coverageAmount") double coverageAmount,
                             @JsonProperty("expiryDate") String expiryDate) {
        this.policyType = policyType;
        this.premiumAmount = premiumAmount;
        this.coverageAmount = coverageAmount;
        this.expiryDate = expiryDate;
    }

    /**
     * Converts a given {@code Policy} into this class for Jackson use.
     */
    public JsonAdaptedPolicy(Policy source) {
        this.policyType = source.getType().name().toLowerCase();
        this.premiumAmount = source.getPremiumAmount();
        this.coverageAmount = source.getCoverageAmount();
        this.expiryDate = source.getExpiryDate().format(MM_DD_YYYY_FORMATTER);
    }

    /**
     * Converts this Jackson-friendly adapted policy object into the model's {@code Policy} object.
     *
     * @throws IllegalArgumentException if there were any data constraints violated.
     */
    public Policy toModelType() throws IllegalValueException {
        final LocalDate expiryDate;
        try {
            expiryDate = LocalDate.parse(this.expiryDate, MM_DD_YYYY_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(DateFormatter.MM_DD_YYYY_MESSAGE_CONSTRAINTS);
        }

        final PolicyType modelPolicyType;
        try {
            modelPolicyType = PolicyType.valueOf(policyType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(Policy.POLICY_TYPE_MESSAGE_CONSTRAINTS);
        }

        try {
            switch (modelPolicyType) {
            case LIFE:
                return new LifePolicy(premiumAmount, coverageAmount, expiryDate);
            case HEALTH:
                return new HealthPolicy(premiumAmount, coverageAmount, expiryDate);
            case EDUCATION:
                return new EducationPolicy(premiumAmount, coverageAmount, expiryDate);
            default:
                throw new RuntimeException("Policy type " + modelPolicyType + " is not accounted for.");
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(Policy.AMOUNT_MESSAGE_CONSTRAINTS);
        }
    }

}
