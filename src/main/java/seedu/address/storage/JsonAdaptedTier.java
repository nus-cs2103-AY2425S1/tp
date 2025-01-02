package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tier.Tier;

/**
 * Jackson-friendly version of {@link Tier}.
 */
class JsonAdaptedTier {

    private final String tierName;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tierName}.
     */
    @JsonCreator
    public JsonAdaptedTier(String tierName) {
        this.tierName = tierName;
    }

    /**
     * Converts a given {@code Tier} into this class for Jackson use.
     */
    public JsonAdaptedTier(Tier source) {
        tierName = source.getValue();
    }

    @JsonValue
    public String getTierName() {
        return tierName;
    }

    @Override
    public String toString() {
        return tierName;
    }

    /**
     * Converts this Jackson-friendly adapted tier object into the model's {@code Tier} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tier.
     */
    public Tier toModelType() throws IllegalValueException {
        if (!Tier.isValidTierName(tierName)) {
            throw new IllegalValueException(Tier.MESSAGE_CONSTRAINTS);
        }
        return new Tier(tierName);
    }
}
