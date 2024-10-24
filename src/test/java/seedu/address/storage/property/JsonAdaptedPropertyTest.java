package seedu.address.storage.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.property.JsonAdaptedProperty.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.property.TypicalProperties.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.property.AskingPrice;
import seedu.address.model.property.LandlordName;
import seedu.address.model.property.Location;
import seedu.address.model.property.Phone;
import seedu.address.model.property.PropertyType;

public class JsonAdaptedPropertyTest {
    private static final String INVALID_NAME = "Z!x!n";
    private static final String INVALID_PHONE = "aaa";
    private static final String INVALID_LOCATION = " ";
    private static final String INVALID_ASKING_PRICE = "12";
    private static final String INVALID_PROPERTY_TYPE = " apple";

    private static final String VALID_NAME = ALICE.getName().toString();
    private static final String VALID_PHONE = ALICE.getPhone().toString();
    private static final String VALID_LOCATION = ALICE.getLocation().toString();
    private static final String VALID_ASKING_PRICE = ALICE.getAskingPrice().toString();
    private static final String VALID_PROPERTY_TYPE = ALICE.getPropertyType().toString();


    @Test
    public void toModelType_validPropertyDetails_returnsProperty() throws Exception {
        JsonAdaptedProperty property = new JsonAdaptedProperty(ALICE);
        assertEquals(ALICE, property.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(INVALID_NAME, VALID_PHONE, VALID_LOCATION, VALID_ASKING_PRICE,
                VALID_PROPERTY_TYPE);
        String expectedMessage = LandlordName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(null, VALID_PHONE, VALID_LOCATION, VALID_ASKING_PRICE,
            VALID_PROPERTY_TYPE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, LandlordName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(VALID_NAME, INVALID_PHONE, VALID_LOCATION, VALID_ASKING_PRICE,
                VALID_PROPERTY_TYPE);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(VALID_NAME, null, VALID_LOCATION, VALID_ASKING_PRICE,
                VALID_PROPERTY_TYPE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    public void toModelType_invalidLocation_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(VALID_NAME, VALID_PHONE, INVALID_LOCATION, VALID_ASKING_PRICE,
                VALID_PROPERTY_TYPE);
        String expectedMessage = Location.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    public void toModelType_nullLocation_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(VALID_NAME, VALID_PHONE, null, VALID_ASKING_PRICE,
                VALID_PROPERTY_TYPE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Location.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    public void toModelType_invalidAskingPrice_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(VALID_NAME, VALID_PHONE, VALID_LOCATION, INVALID_ASKING_PRICE,
                VALID_PROPERTY_TYPE);
        String expectedMessage = AskingPrice.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    public void toModelType_nullAskingPrice_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(VALID_NAME, VALID_PHONE, VALID_LOCATION, null,
                VALID_PROPERTY_TYPE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, AskingPrice.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    public void toModelType_invalidPropertyType_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(VALID_NAME, VALID_PHONE, VALID_LOCATION, VALID_ASKING_PRICE,
                INVALID_PROPERTY_TYPE);
        String expectedMessage = PropertyType.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

}
