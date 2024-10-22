package seedu.address.model.policy;

import java.util.Optional;
import seedu.address.model.policy.PremiumAmount;
import seedu.address.model.policy.CoverageAmount;
import seedu.address.model.policy.ExpiryDate;
import seedu.address.model.policy.PolicyType;

public class EditPolicyDescriptor {
    private final PolicyType policyType;
    private PremiumAmount premiumAmount;
    private CoverageAmount coverageAmount;
    private ExpiryDate expiryDate;

    public EditPolicyDescriptor(PolicyType policyType) {
        this.policyType = policyType;
    }

    public EditPolicyDescriptor(EditPolicyDescriptor toCopy) {
        this.policyType = toCopy.policyType; // No setter for policyType, so it remains unchanged
        setPremiumAmount(toCopy.premiumAmount);
        setCoverageAmount(toCopy.coverageAmount);
        setExpiryDate(toCopy.expiryDate);
    }

    public boolean isAnyFieldEdited() {
        return (premiumAmount != null || coverageAmount != null || expiryDate != null);
    }

    // Getter for policyType, but no setter
    public PolicyType getPolicyType() {
        return policyType;
    }

    public void setPremiumAmount(PremiumAmount premiumAmount) {
        this.premiumAmount = premiumAmount;
    }

    public Optional<PremiumAmount> getPremiumAmount() {
        return Optional.ofNullable(premiumAmount);
    }

    public void setCoverageAmount(CoverageAmount coverageAmount) {
        this.coverageAmount = coverageAmount;
    }

    public Optional<CoverageAmount> getCoverageAmount() {
        return Optional.ofNullable(coverageAmount);
    }

    public void setExpiryDate(ExpiryDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Optional<ExpiryDate> getExpiryDate() {
        return Optional.ofNullable(expiryDate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof EditPolicyDescriptor)) {
            return false;
        }
        EditPolicyDescriptor otherDescriptor = (EditPolicyDescriptor) other;
        return getPolicyType().equals(otherDescriptor.getPolicyType())
                && getPremiumAmount().equals(otherDescriptor.getPremiumAmount())
                && getCoverageAmount().equals(otherDescriptor.getCoverageAmount())
                && getExpiryDate().equals(otherDescriptor.getExpiryDate());
    }
}
