package seedu.address.testutil;

import seedu.address.model.claim.ClaimStatus;
import seedu.address.model.claim.EditClaimDescriptor;

/**
 * A utility class to help with building EditClaimDescriptor objects.
 */
public class EditClaimDescriptorBuilder {

    private EditClaimDescriptor descriptor;

    public EditClaimDescriptorBuilder() {
        descriptor = new EditClaimDescriptor();
    }

    public EditClaimDescriptorBuilder(EditClaimDescriptor descriptor) {
        this.descriptor = new EditClaimDescriptor(descriptor);
    }

    /**
     * Sets the {@code ClaimStatus} of the {@code EditClaimDescriptor} that we are building.
     */
    public EditClaimDescriptorBuilder withStatus(ClaimStatus status) {
        descriptor.setStatus(status);
        return this;
    }

    /**
     * Sets the {@code description} of the {@code EditClaimDescriptor} that we are building.
     */
    public EditClaimDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(description);
        return this;
    }

    /**
     * Builds and returns the EditClaimDescriptor object.
     */
    public EditClaimDescriptor build() {
        return descriptor;
    }
}
