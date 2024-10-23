package seedu.address.model.policy;

import java.util.Optional;

/**
 * Represents a descriptor used to edit policy details in the policy book.
 * Stores optional fields that can be modified (e.g. premium amount, coverage amount, expiry date),
 * while keeping the immutable policy type.
 */
public class EditPolicyDescriptor {

    private final PolicyType policyType;
    private PremiumAmount premiumAmount;
    private CoverageAmount coverageAmount;
    private ExpiryDate expiryDate;

    /**
     * Constructs an {@code EditPolicyDescriptor} with the specified {@code policyType}.
     * The policy type is immutable and cannot be changed once assigned.
     *
     * @param policyType the type of the policy to be edited.
     */
    public EditPolicyDescriptor(PolicyType policyType) {
        this.policyType = policyType;
    }

    /**
     * Constructs an {@code EditPolicyDescriptor} by copying another {@code EditPolicyDescriptor}.
     * The policy type is carried over unchanged, while the other fields (premium amount,
     * coverage amount, expiry date) can be set to new values.
     *
     * @param toCopy the {@code EditPolicyDescriptor} to copy from.
     */
    public EditPolicyDescriptor(EditPolicyDescriptor toCopy) {
        this.policyType = toCopy.policyType; // Policy type is immutable
        setPremiumAmount(toCopy.premiumAmount);
        setCoverageAmount(toCopy.coverageAmount);
        setExpiryDate(toCopy.expiryDate);
    }

    /**
     * Returns true if at least one of the editable fields (premium amount, coverage amount,
     * expiry date) has been edited.
     *
     * @return true if any field is edited, false otherwise.
     */
    public boolean isAnyFieldEdited() {
        return (premiumAmount != null || coverageAmount != null || expiryDate != null);
    }

    /**
     * Returns the immutable policy type of this descriptor.
     * There is no setter for this field as it cannot be changed.
     *
     * @return the {@code PolicyType} associated with this descriptor.
     */
    public PolicyType getPolicyType() {
        return policyType;
    }

    /**
     * Sets the premium amount for this descriptor.
     *
     * @param premiumAmount the {@code PremiumAmount} to set.
     */
    public void setPremiumAmount(PremiumAmount premiumAmount) {
        this.premiumAmount = premiumAmount;
    }

    /**
     * Returns the premium amount if it is present.
     *
     * @return an {@code Optional} containing the premium amount, or empty if not set.
     */
    public Optional<PremiumAmount> getPremiumAmount() {
        return Optional.ofNullable(premiumAmount);
    }

    /**
     * Sets the coverage amount for this descriptor.
     *
     * @param coverageAmount the {@code CoverageAmount} to set.
     */
    public void setCoverageAmount(CoverageAmount coverageAmount) {
        this.coverageAmount = coverageAmount;
    }

    /**
     * Returns the coverage amount if it is present.
     *
     * @return an {@code Optional} containing the coverage amount, or empty if not set.
     */
    public Optional<CoverageAmount> getCoverageAmount() {
        return Optional.ofNullable(coverageAmount);
    }

    /**
     * Sets the expiry date for this descriptor.
     *
     * @param expiryDate the {@code ExpiryDate} to set.
     */
    public void setExpiryDate(ExpiryDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    /**
     * Returns the expiry date if it is present.
     *
     * @return an {@code Optional} containing the expiry date, or empty if not set.
     */
    public Optional<ExpiryDate> getExpiryDate() {
        return Optional.ofNullable(expiryDate);
    }

    /**
     * Checks whether this {@code EditPolicyDescriptor} is equal to another object.
     * Two descriptors are considered equal if they have the same policy type, premium amount,
     * coverage amount, and expiry date.
     *
     * @param other the object to compare with.
     * @return true if both objects are equal, false otherwise.
     */
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
