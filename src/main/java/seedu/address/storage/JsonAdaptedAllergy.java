package seedu.address.storage;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_CONSTRAINTS_ALPHANUMERIC;
import static seedu.address.logic.Messages.MESSAGE_CONSTRAINTS_LENGTH;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Allergy;

/**
 * Jackson-friendly version of {@link Allergy}.
 */
class JsonAdaptedAllergy {

    private final String allergyName;

    /**
     * Constructs a {@code JsonAdaptedAllergy} with the given {@code allergyName}.
     */
    @JsonCreator
    public JsonAdaptedAllergy(String allergyName) {
        this.allergyName = allergyName;
    }

    /**
     * Converts a given {@code Allergy} into this class for Jackson use.
     */
    public JsonAdaptedAllergy(Allergy source) {
        allergyName = source.allergyName;
    }

    @JsonValue
    public String getAllergyName() {
        return allergyName;
    }

    /**
     * Converts this Jackson-friendly adapted allergy object into the model's {@code Allergy} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted allergy.
     */
    public Allergy toModelType() throws IllegalValueException {
        requireNonNull(allergyName);

        if (!Allergy.isAlphanumeric(allergyName)) {
            throw new IllegalValueException(MESSAGE_CONSTRAINTS_ALPHANUMERIC);
        }

        if (!Allergy.isValidLength(allergyName)) {
            throw new IllegalValueException(MESSAGE_CONSTRAINTS_LENGTH);
        }

        return new Allergy(allergyName);
    }


}
