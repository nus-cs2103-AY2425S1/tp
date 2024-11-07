package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedClient.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalClients.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.client.Buyer;
import seedu.address.model.client.Email;
import seedu.address.model.client.NameWithoutNumber;
import seedu.address.model.client.Phone;

public class JsonAdaptedClientTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TYPE = "invalidType";

    private static final String VALID_NAME = ALICE.getName().toString();
    private static final String VALID_PHONE = ALICE.getPhone().toString();
    private static final String VALID_EMAIL = ALICE.getEmail().toString();
    private static final String VALID_TYPE = ALICE instanceof Buyer ? "BUYER" : "SELLER";

    @Test
    public void toModelType_validClientDetails_returnsClient() throws Exception {
        JsonAdaptedClient client = new JsonAdaptedClient(ALICE);
        assertEquals(ALICE, client.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedClient client = new JsonAdaptedClient(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_TYPE);
        String expectedMessage = NameWithoutNumber.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedClient client = new JsonAdaptedClient(null, VALID_PHONE, VALID_EMAIL, VALID_TYPE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                NameWithoutNumber.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedClient client = new JsonAdaptedClient(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_TYPE);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedClient client = new JsonAdaptedClient(VALID_NAME, null, VALID_EMAIL, VALID_TYPE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedClient client = new JsonAdaptedClient(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_TYPE);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedClient client = new JsonAdaptedClient(VALID_NAME, VALID_PHONE, null, VALID_TYPE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);
    }

    @Test
    public void toModelType_invalidType_throwsIllegalValueException() {
        JsonAdaptedClient client = new JsonAdaptedClient(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_TYPE);
        assertThrows(IllegalValueException.class, client::toModelType);
    }
}
