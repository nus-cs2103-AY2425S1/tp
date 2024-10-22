package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.EmergencyContact;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Relationship;

public class JsonAdaptedEmergencyContactTest {
    private static final String INVALID_ECNAME = "R@chel";
    private static final String INVALID_ECPHONE = "+651234";
    private static final String INVALID_ECRS = "Pet";
    private static final String INVALID_ECRS_CHARACTERS = "@s5!ho";
    private static final String VALID_ECNAME = BENSON.getFirstEmergencyContact().getName().toString();
    private static final String VALID_ECPHONE = BENSON.getFirstEmergencyContact().getPhone().toString();
    private static final String VALID_ECRS = BENSON.getFirstEmergencyContact().getRelationship().toString();

    @Test
    public void toModelType_validEmergencyContactDetails_returnsEmergencyContact() throws Exception {
        EmergencyContact emergencyContact = BENSON.getFirstEmergencyContact();
        JsonAdaptedEmergencyContact jsonAdaptedEmergencyContact = new JsonAdaptedEmergencyContact(emergencyContact);
        assertEquals(jsonAdaptedEmergencyContact.toModelType(),
                emergencyContact);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedEmergencyContact emergencyContact =
                        new JsonAdaptedEmergencyContact(INVALID_ECNAME, VALID_ECPHONE, VALID_ECRS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, emergencyContact::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedEmergencyContact person = new JsonAdaptedEmergencyContact(null, VALID_ECPHONE, VALID_ECRS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedEmergencyContact emergencyContact =
                        new JsonAdaptedEmergencyContact(VALID_ECNAME, INVALID_ECPHONE, VALID_ECRS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, emergencyContact::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedEmergencyContact emergencyContact =
                new JsonAdaptedEmergencyContact(VALID_ECNAME, null, VALID_ECRS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, emergencyContact::toModelType);
    }

    @Test
    public void toModelType_invalidRelationship_throwsIllegalValueException() {
        JsonAdaptedEmergencyContact emergencyContact =
            new JsonAdaptedEmergencyContact(VALID_ECNAME, VALID_ECPHONE, INVALID_ECRS);
        String expectedMessage = Relationship.RELATIONSHIP_TYPE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, emergencyContact::toModelType);
    }

    @Test
    public void toModelType_invalidAlphanumericRelationship_throwsIllegalValueException() {
        JsonAdaptedEmergencyContact emergencyContact =
                new JsonAdaptedEmergencyContact(VALID_ECNAME, VALID_ECPHONE, INVALID_ECRS_CHARACTERS);
        String expectedMessage = Relationship.ALPHANUMERIC_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, emergencyContact::toModelType);
    }

    @Test
    public void toModelType_nullRelationship_throwsIllegalValueException() {
        JsonAdaptedEmergencyContact emergencyContact =
            new JsonAdaptedEmergencyContact(VALID_ECNAME, VALID_ECPHONE, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Relationship.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, emergencyContact::toModelType);
    }
}
