package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedProperty.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalProperty.ADMIRALTY;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.property.PostalCode;
import seedu.address.model.property.Type;
import seedu.address.model.property.Unit;

public class JsonAdaptedPropertyTest {
    private static final String INVALID_POSTALCODE = "12345";
    private static final String INVALID_UNIT = "00 00";
    private static final String INVALID_TYPE = "PUBLIC";

    private static final String VALID_POSTALCODE = ADMIRALTY.getPostalCode().toString();
    private static final String VALID_UNIT = ADMIRALTY.getUnit().toString();
    private static final String VALID_TYPE = "CONDO";

    @Test
    public void toModelType_validPropertyDetails_returnsProperty() throws Exception {
        JsonAdaptedProperty property = new JsonAdaptedProperty(ADMIRALTY);
        assertEquals(ADMIRALTY, property.toModelType());
    }

    @Test
    public void toModelType_invalidPostalCode_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(INVALID_POSTALCODE, VALID_UNIT, VALID_TYPE);
        String expectedMessage = PostalCode.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    public void toModelType_invalidUnit_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(VALID_POSTALCODE, INVALID_UNIT, VALID_TYPE);
        String expectedMessage = Unit.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    public void toModelType_invalidType_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(VALID_POSTALCODE, VALID_UNIT, INVALID_TYPE);
        String expectedMessage = Type.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(null, VALID_UNIT, VALID_TYPE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                PostalCode.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    public void toModelType_nullUnit_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(VALID_POSTALCODE, null, VALID_TYPE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Unit.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    public void toModelType_nullType_throwsIllegalValueException() {
        JsonAdaptedProperty property = new JsonAdaptedProperty(VALID_POSTALCODE, VALID_UNIT, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Type.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }
}
