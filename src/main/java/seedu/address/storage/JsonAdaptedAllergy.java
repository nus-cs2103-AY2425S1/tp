package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.patient.Allergy;

/**
 * Jackson-friendly version of {@link seedu.address.model.patient.Allergy}.
 */
public class JsonAdaptedAllergy {
    private final String allergy;

    /**
     * Constructs a {@code JsonAdaptedHealthService} with the given {@code healthServiceName}.
     */
    @JsonCreator
    public JsonAdaptedAllergy(String healthServiceName) {
        this.allergy = healthServiceName;
    }

    /**
     * Converts a given {@code HealthService} into this class for Jackson use.
     */
    public JsonAdaptedAllergy(Allergy source) {
        allergy = source.value;
    }

    @JsonValue
    public String getAllergy() {
        return allergy;
    }

    /**
     * Converts this Jackson-friendly adapted allergy object into the model's {@code allergy} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted allergy.
     */
    public Allergy toModelType() throws IllegalValueException {
        if (!Allergy.isValidAllergy(allergy)) {
            throw new IllegalValueException(Allergy.MESSAGE_CONSTRAINTS);
        }
        return new Allergy(allergy);
    }
}
