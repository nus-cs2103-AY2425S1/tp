package seedu.address.testutil;

import seedu.address.model.policy.CoverageAmount;
import seedu.address.model.policy.EditPolicyDescriptor;
import seedu.address.model.policy.ExpiryDate;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyType;
import seedu.address.model.policy.PremiumAmount;

/**
 * A utility class to help with building EditPolicyDescriptor objects.
 */
public class EditPolicyDescriptorBuilder {

    private EditPolicyDescriptor descriptor;

    public EditPolicyDescriptorBuilder(PolicyType policyType) {
        descriptor = new EditPolicyDescriptor(policyType);
    }

    public EditPolicyDescriptorBuilder(EditPolicyDescriptor descriptor) {
        this.descriptor = new EditPolicyDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPolicyDescriptor} with fields containing {@code policy}'s details
     */
    public EditPolicyDescriptorBuilder(Policy policy) {
        descriptor = new EditPolicyDescriptor(policy.getType());
        descriptor.setPremiumAmount(policy.getPremiumAmount());
        descriptor.setCoverageAmount(policy.getCoverageAmount());
        descriptor.setExpiryDate(policy.getExpiryDate());
    }

    /**
     * Sets the {@code PremiumAmount} of the {@code EditPolicyDescriptor} that we are building.
     */
    public EditPolicyDescriptorBuilder withPremiumAmount(String premiumAmount) {
        descriptor.setPremiumAmount(new PremiumAmount(premiumAmount));
        return this;
    }

    /**
     * Sets the {@code CoverageAmount} of the {@code EditPolicyDescriptor} that we are building.
     */
    public EditPolicyDescriptorBuilder withCoverageAmount(String coverageAmount) {
        descriptor.setCoverageAmount(new CoverageAmount(coverageAmount));
        return this;
    }

    /**
     * Sets the {@code ExpiryDate} of the {@code EditPolicyDescriptor} that we are building.
     */
    public EditPolicyDescriptorBuilder withExpiryDate(String expiryDate) {
        descriptor.setExpiryDate(new ExpiryDate(expiryDate));
        return this;
    }

    public EditPolicyDescriptor build() {
        return descriptor;
    }
}

