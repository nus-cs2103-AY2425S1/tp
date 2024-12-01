package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.EmergencyContact;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Relationship;

/**
 * Jackson-friendly version of {@link EmergencyContact}.
 */
public class JsonAdaptedEmergencyContact {
    private final String ecName;
    private final String ecPhone;
    private final String ecRelationship;
    /**
     * Constructs a {@code JsonAdaptedEmergencyContact} with the given {@code ecName},
     * {@code ecPhone} and {@code ecRelationship}.
     */
    @JsonCreator
    public JsonAdaptedEmergencyContact(@JsonProperty("ecName") String ecName,
                                       @JsonProperty("ecPhone") String ecPhone,
                                       @JsonProperty("ecRelationship") String ecRelationship) {
        this.ecName = ecName;
        this.ecPhone = ecPhone;
        this.ecRelationship = ecRelationship;
    }

    /**
     * Converts a given {@code Emergency Contact} into this class for Jackson use.
     */
    public JsonAdaptedEmergencyContact(EmergencyContact source) {
        ecName = source.getName().fullName;
        ecPhone = source.getPhone().value;
        ecRelationship = source.getRelationship().relationship;
    }

    /**
     * Converts this Jackson-friendly adapted Emergency Contact object into the model's {@code EmergencyContact} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public EmergencyContact toModelType() throws IllegalValueException {
        if (ecName == null) {
            throw new IllegalValueException(String.format(JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT,
                    Name.class.getSimpleName()));
        }
        if (!Name.isValidName(ecName)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelEcName = new Name(ecName);

        if (ecPhone == null) {
            throw new IllegalValueException(String.format(JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT,
                    Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(ecPhone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelEcPhone = new Phone(ecPhone);

        if (ecRelationship == null) {
            throw new IllegalValueException(String.format(JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT,
                    Relationship.class.getSimpleName()));
        }
        if (!Relationship.isAlphanumericRelationship(ecRelationship)) {
            throw new IllegalValueException(Relationship.ALPHANUMERIC_CONSTRAINTS);
        }
        if (!Relationship.isValidRelationship(ecRelationship)) {
            throw new IllegalValueException(Relationship.RELATIONSHIP_TYPE_CONSTRAINTS);
        }
        final Relationship modelEcRelationship = new Relationship(ecRelationship);

        return new EmergencyContact(modelEcName, modelEcPhone, modelEcRelationship);
    }
}
