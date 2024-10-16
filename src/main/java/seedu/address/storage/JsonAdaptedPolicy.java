package seedu.address.storage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.policy.EducationPolicy;
import seedu.address.model.policy.HealthPolicy;
import seedu.address.model.policy.LifePolicy;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyType;

class JsonAdaptedPolicy {
    private static final String DATE_FORMAT = "MM/dd/yyyy";

    private final double premiumAmount;
    private final double coverageAmount;
    private final String policyType;
    private final String expiryDate;

    /**
     * Constructs a {@code JsonAdaptedPolicy} with the given policy details.
     */
    @JsonCreator
    public JsonAdaptedPolicy(@JsonProperty("premiumAmount") double premiumAmount,
                             @JsonProperty("coverageAmount") double coverageAmount,
                             @JsonProperty("policyType") String policyType,
                             @JsonProperty("expiryDate") String expiryDate) {
        this.premiumAmount = premiumAmount;
        this.coverageAmount = coverageAmount;
        this.policyType = policyType;
        this.expiryDate = expiryDate;
    }

    /**
     * Converts a given {@code Policy} into this class for Jackson use.
     */
    public JsonAdaptedPolicy(Policy source) {
        this.premiumAmount = source.getPremiumAmount();
        this.coverageAmount = source.getCoverageAmount();
        this.policyType = source.getType().name(); // Enum to String conversion
        this.expiryDate = source.getExpiryDate().format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    /**
     * Converts this Jackson-friendly adapted policy object into the model's {@code Policy} object.
     *
     * @throws IllegalArgumentException if there were any data constraints violated.
     */
    public Policy toModelType() throws IllegalValueException {
        final LocalDate modelExpiryDate;
        try {
            modelExpiryDate = LocalDate.parse(expiryDate, DateTimeFormatter.ofPattern(DATE_FORMAT));
        } catch (DateTimeParseException e) {
            throw new IllegalValueException("Invalid date format for expiry date: " + expiryDate);
        }

        try {
            final PolicyType modelPolicyType = PolicyType.valueOf(policyType.toUpperCase());
            if (modelPolicyType == PolicyType.LIFE) {
                return new LifePolicy(premiumAmount, coverageAmount, modelExpiryDate);
            } else if (modelPolicyType == PolicyType.HEALTH) {
                return new HealthPolicy(premiumAmount, coverageAmount, modelExpiryDate);
            } else {
                return new EducationPolicy(premiumAmount, coverageAmount, modelExpiryDate);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException("Invalid policy type: " + policyType);
        }
    }

}
