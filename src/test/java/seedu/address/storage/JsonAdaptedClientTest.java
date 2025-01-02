package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedClient.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalClients.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.client.Address;
import seedu.address.model.client.Email;
import seedu.address.model.client.Job;
import seedu.address.model.client.Name;
import seedu.address.model.client.Phone;

public class JsonAdaptedClientTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_JOB = "";
    private static final String INVALID_INCOME_STRING = "-99";
    private static final String INVALID_TIER = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_JOB = BENSON.getJob().toString();
    private static final String VALID_INCOME_STRING = BENSON.getIncome().toString();
    private static final JsonAdaptedTier VALID_TIER = new JsonAdaptedTier(BENSON.getTier());
    private static final String VALID_REMARK = BENSON.getRemark().toString();
    private static final JsonAdaptedStatus VALID_STATUS = new JsonAdaptedStatus(BENSON.getStatus());

    @Test
    public void toModelType_validClientDetails_returnsClient() throws Exception {
        JsonAdaptedClient client = new JsonAdaptedClient(BENSON);
        assertEquals(BENSON, client.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedClient client =
                new JsonAdaptedClient(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_JOB, VALID_INCOME_STRING, VALID_TIER, VALID_REMARK, VALID_STATUS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedClient client =
                new JsonAdaptedClient(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_JOB, VALID_INCOME_STRING, VALID_TIER, VALID_REMARK, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedClient client =
                new JsonAdaptedClient(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_JOB, VALID_INCOME_STRING, VALID_TIER, VALID_REMARK, VALID_STATUS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedClient client =
                new JsonAdaptedClient(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS,
                        VALID_JOB, VALID_INCOME_STRING, VALID_TIER, VALID_REMARK, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedClient client =
                new JsonAdaptedClient(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS,
                        VALID_JOB, VALID_INCOME_STRING, VALID_TIER, VALID_REMARK, VALID_STATUS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedClient client =
                new JsonAdaptedClient(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS,
                        VALID_JOB, VALID_INCOME_STRING, VALID_TIER, VALID_REMARK, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedClient client =
                new JsonAdaptedClient(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS,
                        VALID_JOB, VALID_INCOME_STRING, VALID_TIER, VALID_REMARK, VALID_STATUS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedClient client =
                new JsonAdaptedClient(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                        VALID_JOB, VALID_INCOME_STRING, VALID_TIER, VALID_REMARK, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);
    }

    @Test
    public void toModelType_invalidJob_throwsIllegalValueException() {
        JsonAdaptedClient client =
                new JsonAdaptedClient(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        INVALID_JOB, VALID_INCOME_STRING, VALID_TIER, VALID_REMARK, VALID_STATUS);
        String expectedMessage = Job.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);
    }


    @Test
    public void toModelType_nullJob_throwsIllegalValueException() {
        JsonAdaptedClient client =
                new JsonAdaptedClient(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        null, VALID_INCOME_STRING, VALID_TIER, VALID_REMARK, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Job.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, client::toModelType);
    }

    @Test
    public void toModelType_invalidTier_throwsIllegalValueException() {
        JsonAdaptedTier invalidTier = new JsonAdaptedTier(INVALID_TIER);;
        JsonAdaptedClient client =
                new JsonAdaptedClient(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_JOB, VALID_INCOME_STRING, invalidTier, VALID_REMARK, VALID_STATUS);
        assertThrows(IllegalValueException.class, client::toModelType);
    }

    @Test
    public void toModelType_invalidRemark_throwsIllegalValueException() {
        JsonAdaptedClient client =
                new JsonAdaptedClient(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_JOB, VALID_INCOME_STRING, VALID_TIER, null, VALID_STATUS);
        assertThrows(IllegalValueException.class, client::toModelType);
    }
}
