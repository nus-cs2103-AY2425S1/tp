package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedOwner.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalOwners.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.owner.Address;
import seedu.address.model.owner.Email;
import seedu.address.model.owner.IdentificationCardNumber;
import seedu.address.model.owner.Name;
import seedu.address.model.owner.Phone;

public class JsonAdaptedOwnerTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_IC_NUMBER = "S1234567A";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_IC_NUMBER = BENSON.getIdentificationNumber().toString();

    @Test
    public void toModelType_validOwnerDetails_returnsPerson() throws Exception {
        JsonAdaptedOwner owner = new JsonAdaptedOwner(BENSON);
        assertEquals(BENSON, owner.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedOwner owner =
                new JsonAdaptedOwner(VALID_IC_NUMBER, INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, owner::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedOwner owner = new JsonAdaptedOwner(VALID_IC_NUMBER, null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, owner::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedOwner owner =
                new JsonAdaptedOwner(VALID_IC_NUMBER, VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, owner::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedOwner owner =
                new JsonAdaptedOwner(VALID_IC_NUMBER, VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, owner::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedOwner owner = new JsonAdaptedOwner(VALID_IC_NUMBER, VALID_NAME, VALID_PHONE, null, VALID_ADDRESS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, owner::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedOwner owner =
                new JsonAdaptedOwner(VALID_IC_NUMBER, VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, owner::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedOwner owner = new JsonAdaptedOwner(VALID_IC_NUMBER, VALID_NAME, VALID_PHONE, VALID_EMAIL, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, owner::toModelType);
    }

    @Test
    public void toModelType_invalidIcNumber_throwsIllegalValueException() {
        JsonAdaptedOwner owner =
                new JsonAdaptedOwner(INVALID_IC_NUMBER, VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS);
        String expectedMessage = IdentificationCardNumber.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, owner::toModelType);
    }

    @Test
    public void toModelType_nullIcNumber_throwsIllegalValueException() {
        JsonAdaptedOwner owner = new JsonAdaptedOwner(null, VALID_NAME, VALID_PHONE, VALID_EMAIL,
            VALID_ADDRESS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
            IdentificationCardNumber.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, owner::toModelType);
    }

}
