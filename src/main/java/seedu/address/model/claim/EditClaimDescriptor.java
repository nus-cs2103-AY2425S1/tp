package seedu.address.model.claim;

import java.util.Optional;

/**
 * A descriptor class that specifies the details of a claim to be edited.
 * This class provides optional fields, allowing the flexibility to update
 * only selected attributes (such as claim status or description) for an existing claim.
 */
public class EditClaimDescriptor {
    private ClaimStatus status;
    private String description;

    /**
     * Constructs an empty {@code EditClaimDescriptor}.
     */
    public EditClaimDescriptor() {}

    /**
     * Constructs a copy of the given {@code EditClaimDescriptor}.
     *
     * @param toCopy The descriptor to copy from.
     */
    public EditClaimDescriptor(EditClaimDescriptor toCopy) {
        this.status = toCopy.status;
        this.description = toCopy.description;
    }

    /**
     * Checks if the claim status has been edited.
     *
     * @return true if the status has been edited, false otherwise.
     */
    public boolean isStatusEdited() {
        return status != null;
    }

    /**
     * Checks if the claim description has been edited.
     *
     * @return true if the description has been edited, false otherwise.
     */
    public boolean isDescriptionEdited() {
        return description != null;
    }

    /**
     * Checks if any field (status or description) has been edited.
     *
     * @return true if at least one field is edited, false otherwise.
     */
    public boolean isAnyFieldEdited() {
        return isDescriptionEdited() || isStatusEdited();
    }

    /**
     * Gets the edited claim status if present.
     *
     * @return An Optional containing the claim status if edited, or an empty Optional otherwise.
     */
    public Optional<ClaimStatus> getStatus() {
        return Optional.ofNullable(status);
    }

    /**
     * Sets the claim status to be edited.
     *
     * @param status The new claim status.
     */
    public void setStatus(ClaimStatus status) {
        this.status = status;
    }

    /**
     * Gets the edited claim description if present.
     *
     * @return An Optional containing the claim description if edited, or an empty Optional otherwise.
     */
    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    /**
     * Sets the claim description to be edited.
     *
     * @param description The new claim description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Checks if this {@code EditClaimDescriptor} is equal to another object.
     *
     * @param other The other object to compare with.
     * @return true if both descriptors have equal status and description values, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof EditClaimDescriptor)) {
            return false;
        }
        EditClaimDescriptor e = (EditClaimDescriptor) other;
        return getStatus().equals(e.getStatus())
                && getDescription().equals(e.getDescription());
    }
}
