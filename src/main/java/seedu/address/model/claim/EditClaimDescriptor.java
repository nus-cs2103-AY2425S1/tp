package seedu.address.model.claim;

import java.util.Optional;

public class EditClaimDescriptor {
    private ClaimStatus status;
    private String description;

    public EditClaimDescriptor() {}

    public EditClaimDescriptor(EditClaimDescriptor toCopy) {
        this.status = toCopy.status;
        this.description = toCopy.description;
    }

    public boolean isStatusEdited() {
        return status != null;
    }

    public boolean isDescriptionEdited() {
        return description != null;
    }

    public boolean isAnyFieldEdited() {
        return isDescriptionEdited() || isStatusEdited();
    }

    public Optional<ClaimStatus> getStatus() {
        return Optional.ofNullable(status);
    }

    public void setStatus(ClaimStatus status) {
        this.status = status;
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

