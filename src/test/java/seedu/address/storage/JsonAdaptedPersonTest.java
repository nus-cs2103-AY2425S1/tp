package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BILL;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BILL.getName().toString();
    private static final String VALID_PHONE = BILL.getPhone().toString();
    private static final String VALID_EMAIL = BILL.getEmail().toString();
    private static final String VALID_ADDRESS = BILL.getAddress().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BILL.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedProperty> VALID_SELL_PROPERTIES = BILL.getListOfSellingProperties().stream()
            .map(JsonAdaptedProperty::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedProperty> VALID_BUY_PROPERTIES = BILL.getListOfBuyingProperties().stream()
            .map(JsonAdaptedProperty::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedProperty> VALID_PROPERTIES_SOLD = BILL.getListOfPropertiesSold().stream()
            .map(JsonAdaptedProperty::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedProperty> VALID_PROPERTIES_BOUGHT = BILL.getListOfPropertiesBought().stream()
            .map(JsonAdaptedProperty::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BILL);
        assertEquals(BILL, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                        VALID_SELL_PROPERTIES, VALID_BUY_PROPERTIES, VALID_PROPERTIES_SOLD, VALID_PROPERTIES_BOUGHT);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                        VALID_SELL_PROPERTIES, VALID_BUY_PROPERTIES, VALID_PROPERTIES_SOLD, VALID_PROPERTIES_BOUGHT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                        VALID_SELL_PROPERTIES, VALID_BUY_PROPERTIES, VALID_PROPERTIES_SOLD, VALID_PROPERTIES_BOUGHT);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                        VALID_SELL_PROPERTIES, VALID_BUY_PROPERTIES, VALID_PROPERTIES_SOLD, VALID_PROPERTIES_BOUGHT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                        VALID_SELL_PROPERTIES, VALID_BUY_PROPERTIES, VALID_PROPERTIES_SOLD, VALID_PROPERTIES_BOUGHT);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS, VALID_TAGS,
                        VALID_SELL_PROPERTIES, VALID_BUY_PROPERTIES, VALID_PROPERTIES_SOLD, VALID_PROPERTIES_BOUGHT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_TAGS,
                        VALID_SELL_PROPERTIES, VALID_BUY_PROPERTIES, VALID_PROPERTIES_SOLD, VALID_PROPERTIES_BOUGHT);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_TAGS,
                        VALID_SELL_PROPERTIES, VALID_BUY_PROPERTIES, VALID_PROPERTIES_SOLD, VALID_PROPERTIES_BOUGHT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, invalidTags,
                        VALID_SELL_PROPERTIES, VALID_BUY_PROPERTIES, VALID_PROPERTIES_SOLD, VALID_PROPERTIES_BOUGHT);
        assertThrows(IllegalValueException.class, person::toModelType);
    }
}
