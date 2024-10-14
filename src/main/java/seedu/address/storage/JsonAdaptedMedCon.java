package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.MedCon;

/**
 * Jackson-friendly version of {@link MedCon}.
 */
class JsonAdaptedMedCon {

    private final String medConName;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName}.
     */
    @JsonCreator
    public JsonAdaptedMedCon(String medConName) {
        this.medConName = medConName;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedMedCon(MedCon source) {
        medConName = source.getMedCon();
    }

    @JsonValue
    public String getmedConName() {
        return medConName;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code MedCon} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted MedCon.
     */
    public MedCon toModelType() throws IllegalValueException {
        return new MedCon(medConName);
    }

}
