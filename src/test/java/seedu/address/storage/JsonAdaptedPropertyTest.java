package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedProperty.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Condo;
import seedu.address.model.person.PostalCode;
import seedu.address.model.person.Price;
import seedu.address.model.person.Property;
import seedu.address.model.person.UnitNumber;
import seedu.address.model.tag.Tag;

public class JsonAdaptedPropertyTest {

    private static final String VALID_HOUSING_TYPE = "Condo";
    private static final String VALID_POSTAL_CODE = "123456";
    private static final String VALID_UNIT_NUMBER = "10-09";
    private static final String VALID_PRICE = "23232993";
    private static final String VALID_ACTUAL_PRICE = "1000000";
    private static final List<JsonAdaptedTag> VALID_TAGS =
            List.of(new JsonAdaptedTag("spacious"), new JsonAdaptedTag("very nice"));

    private static final String INVALID_POSTAL_CODE = "123";
    private static final String INVALID_UNIT_NUMBER = "10-";
    private static final String INVALID_PRICE = "abc";
    private static final String INVALID_TAG = "#invalidTag";

    @Test
    void toModelType_validPropertyDetails_returnsProperty() throws IllegalValueException {
        JsonAdaptedProperty property = new JsonAdaptedProperty(VALID_HOUSING_TYPE, VALID_POSTAL_CODE,
                VALID_UNIT_NUMBER, VALID_PRICE, VALID_ACTUAL_PRICE, VALID_TAGS);
        Property expectedProperty =
                new Condo(new PostalCode(VALID_POSTAL_CODE), new UnitNumber(VALID_UNIT_NUMBER), new Price(VALID_PRICE),
                        new HashSet<>(List.of(new Tag("spacious"), new Tag("very nice"))));
        assertEquals(expectedProperty, property.toModelType());
    }

    @Test
    void toModelType_nullHousingType_throwsIllegalValueException() {
        JsonAdaptedProperty property =
                new JsonAdaptedProperty(null, VALID_POSTAL_CODE, VALID_UNIT_NUMBER,
                        VALID_PRICE, VALID_ACTUAL_PRICE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "HousingType");
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    void toModelType_invalidPostalCode_throwsIllegalValueException() {
        JsonAdaptedProperty property =
                new JsonAdaptedProperty(VALID_HOUSING_TYPE, INVALID_POSTAL_CODE, VALID_UNIT_NUMBER,
                        VALID_PRICE, VALID_ACTUAL_PRICE, VALID_TAGS);
        String expectedMessage = PostalCode.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    void toModelType_nullPostalCode_throwsIllegalValueException() {
        JsonAdaptedProperty property =
                new JsonAdaptedProperty(VALID_HOUSING_TYPE, null, VALID_UNIT_NUMBER,
                        VALID_PRICE, VALID_ACTUAL_PRICE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, PostalCode.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    void toModelType_invalidUnitNumber_throwsIllegalValueException() {
        JsonAdaptedProperty property =
                new JsonAdaptedProperty(VALID_HOUSING_TYPE, VALID_POSTAL_CODE, INVALID_UNIT_NUMBER,
                        VALID_PRICE, VALID_ACTUAL_PRICE, VALID_TAGS);
        String expectedMessage = UnitNumber.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    void toModelType_nullUnitNumber_throwsIllegalValueException() {
        JsonAdaptedProperty property =
                new JsonAdaptedProperty(VALID_HOUSING_TYPE, VALID_POSTAL_CODE, null,
                        VALID_PRICE, VALID_ACTUAL_PRICE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, UnitNumber.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    void toModelType_invalidPrice_throwsIllegalValueException() {
        JsonAdaptedProperty property =
                new JsonAdaptedProperty(VALID_HOUSING_TYPE, VALID_POSTAL_CODE, VALID_UNIT_NUMBER,
                        INVALID_PRICE, VALID_ACTUAL_PRICE, VALID_TAGS);
        String expectedMessage = Price.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    void toModelType_nullPrice_throwsIllegalValueException() {
        JsonAdaptedProperty property =
                new JsonAdaptedProperty(VALID_HOUSING_TYPE, VALID_POSTAL_CODE, VALID_UNIT_NUMBER,
                        null, VALID_ACTUAL_PRICE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, property::toModelType);
    }

    @Test
    void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = List.of(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedProperty property =
                new JsonAdaptedProperty(VALID_HOUSING_TYPE, VALID_POSTAL_CODE, VALID_UNIT_NUMBER,
                        VALID_PRICE, VALID_ACTUAL_PRICE, invalidTags);
        assertThrows(IllegalValueException.class, property::toModelType);
    }
}
