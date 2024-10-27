package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.claim.Claim;
import seedu.address.model.claim.ClaimStatus;

class JsonAdaptedClaim {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Claim's %s field is missing!";

    private final String status;
    private final String description;

    /**
     * Constructs a {@code JsonAdaptedClaim} with the given claim details.
     */
    @JsonCreator
    public JsonAdaptedClaim(@JsonProperty("status") String status,
                            @JsonProperty("description") String description) {
        this.status = status;
        this.description = description;
    }

    /**
     * Converts a given {@code Claim} into this class for Jackson use.
     */
    public JsonAdaptedClaim(Claim source) {
        this.status = source.getStatus().name();
        this.description = source.getClaimDescription();
    }

    /**
     * Converts this Jackson-friendly adapted claim object into the model's {@code Claim} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Claim toModelType() throws IllegalValueException {
        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ClaimStatus.class.getSimpleName()));
        }
        if (!ClaimStatus.isValidClaimStatus(status)) {
            throw new IllegalValueException("Invalid claim status: " + status);
        }
        final ClaimStatus modelStatus = ClaimStatus.fromString(status);
        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    "Description"));
        }
        if (!Claim.isValidClaim(description)) {
            throw new IllegalValueException(Claim.MESSAGE_CONSTRAINTS);
        }
        return new Claim(modelStatus, description);
    }

    public String getStatus() {
        return this.status;
    }

    public String getDescription() {
        return this.description;
    }
}
