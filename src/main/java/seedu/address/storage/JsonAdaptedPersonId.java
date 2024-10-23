package seedu.address.storage;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonId;

/**
 * Jackson-friendly version of {@link PersonId}.
 */
public class JsonAdaptedPersonId {
    private final String idString;

    /**
     * Constructs a {@code JsonAdaptedPersonId} with the given {@code idString}.
     */
    @JsonCreator
    public JsonAdaptedPersonId(String idString) {
        this.idString = idString;
    }

    /**
     * Converts a given {@code PersonId} into this class for Jackson use.
     */
    public JsonAdaptedPersonId(PersonId source) {
        idString = source.toString();
    }

    @JsonValue
    public String getIdString() {
        return idString;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code PersonId} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public PersonId toModelType() throws IllegalValueException {
        if (!PersonId.isValidPersonId(idString)) {
            throw new IllegalValueException(PersonId.MESSAGE_CONSTRAINTS);
        }
        return new PersonId(idString);
    }

}
