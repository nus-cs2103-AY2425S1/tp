package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.claim.Claim;
import seedu.address.model.claim.ClaimList;
import seedu.address.model.policy.CoverageAmount;
import seedu.address.model.policy.ExpiryDate;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyType;
import seedu.address.model.policy.PremiumAmount;



class JsonAdaptedPolicy {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Policy's %s field is missing!";

    private final String policyType;
    private final double premiumAmount;
    private final double coverageAmount;
    private final String expiryDate;
    private final List<JsonAdaptedClaim> claims = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPolicy} with the given policy details.
     */
    @JsonCreator
    public JsonAdaptedPolicy(@JsonProperty("policyType") String policyType,
                             @JsonProperty("premiumAmount") double premiumAmount,
                             @JsonProperty("coverageAmount") double coverageAmount,
                             @JsonProperty("expiryDate") String expiryDate,
                             @JsonProperty("claims")List<JsonAdaptedClaim> claims) {
        this.policyType = policyType;
        this.premiumAmount = premiumAmount;
        this.coverageAmount = coverageAmount;
        this.expiryDate = expiryDate;
        if (claims != null) {
            this.claims.addAll(claims);
        }
    }

    /**
     * Converts a given {@code Policy} into this class for Jackson use.
     */
    public JsonAdaptedPolicy(Policy source) {
        this.policyType = source.getType().name().toLowerCase();
        this.premiumAmount = source.getPremiumAmount().value;
        this.coverageAmount = source.getCoverageAmount().value;
        this.expiryDate = source.getExpiryDate().toString();
        this.claims.addAll(source.getList().stream()
                .map(JsonAdaptedClaim::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted policy object into the model's {@code Policy} object.
     *
     * @throws IllegalArgumentException if there were any data constraints violated.
     */
    public Policy toModelType() throws IllegalValueException {
        if (policyType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    PolicyType.class.getSimpleName()));
        }
        if (!PolicyType.isValidPolicyType(policyType)) {
            throw new IllegalValueException(PolicyType.MESSAGE_CONSTRAINTS);
        }
        final PolicyType modelPolicyType = PolicyType.fromString(policyType);

        if (!PremiumAmount.isValidPremiumAmount(premiumAmount)) {
            throw new IllegalValueException(PremiumAmount.MESSAGE_CONSTRAINTS);
        }
        final PremiumAmount modelPremiumAmount = new PremiumAmount(premiumAmount);

        if (!CoverageAmount.isValidCoverageAmount(coverageAmount)) {
            throw new IllegalValueException(CoverageAmount.MESSAGE_CONSTRAINTS);
        }
        final CoverageAmount modelCoverageAmount = new CoverageAmount(coverageAmount);

        if (expiryDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ExpiryDate.class.getSimpleName()));
        }
        if (!ExpiryDate.isValidExpiryDate(expiryDate)) {
            throw new IllegalValueException(ExpiryDate.MESSAGE_CONSTRAINTS);
        }
        final ExpiryDate modelExpiryDate = new ExpiryDate(expiryDate);
        final List<Claim> policyClaims = new ArrayList<>();
        for (JsonAdaptedClaim claim : claims) {
            policyClaims.add(claim.toModelType());
        }
        final ClaimList modelClaims = new ClaimList();
        modelClaims.addAll(policyClaims);
        return Policy.makePolicy(modelPolicyType, modelPremiumAmount, modelCoverageAmount, modelExpiryDate,
                modelClaims);
    }

}
