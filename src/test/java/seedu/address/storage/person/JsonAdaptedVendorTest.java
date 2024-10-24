package seedu.address.storage.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.person.JsonAdaptedVendor.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalVendors.BRIAN;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Budget;
import seedu.address.model.person.Company;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.storage.JsonAdaptedTag;

public class JsonAdaptedVendorTest extends JsonAdaptedPersonTest {
    private static final String TYPE = "Vendor";
    private static final String VALID_NAME = BRIAN.getName().toString();
    private static final String VALID_PHONE = BRIAN.getPhone().toString();
    private static final String VALID_EMAIL = BRIAN.getEmail().toString();
    private static final String VALID_ADDRESS = BRIAN.getAddress().toString();
    private static final String VALID_COMPANY = BRIAN.getCompany().toString();
    private static final String VALID_BUDGET = BRIAN.getBudget().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BRIAN.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedVendor vendor = new JsonAdaptedVendor(BRIAN);
        assertEquals(BRIAN, vendor.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedVendor vendor =
                new JsonAdaptedVendor(TYPE, INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_COMPANY, VALID_BUDGET, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, vendor::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedVendor vendor = new JsonAdaptedVendor(TYPE, null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_COMPANY, VALID_BUDGET, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, vendor::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedVendor vendor =
                new JsonAdaptedVendor(TYPE, VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_COMPANY, VALID_BUDGET, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, vendor::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedVendor vendor = new JsonAdaptedVendor(TYPE, VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS,
                VALID_COMPANY, VALID_BUDGET, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, vendor::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedVendor vendor =
                new JsonAdaptedVendor(TYPE, VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS,
                        VALID_COMPANY, VALID_BUDGET, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, vendor::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedVendor vendor = new JsonAdaptedVendor(TYPE, VALID_NAME, VALID_PHONE, null, VALID_ADDRESS,
                VALID_COMPANY, VALID_BUDGET, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, vendor::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedVendor vendor =
                new JsonAdaptedVendor(TYPE, VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS,
                        VALID_COMPANY, VALID_BUDGET, VALID_TAGS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, vendor::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedVendor vendor = new JsonAdaptedVendor(TYPE, VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                VALID_COMPANY, VALID_BUDGET, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, vendor::toModelType);
    }

    @Test
    public void toModelType_nullCompany_throwsIllegalValueException() {
        JsonAdaptedVendor vendor = new JsonAdaptedVendor(TYPE, VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                null, VALID_BUDGET, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Company.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, vendor::toModelType);
    }

    @Test
    public void toModelType_nullBudget_throwsIllegalValueException() {
        JsonAdaptedVendor vendor = new JsonAdaptedVendor(TYPE, VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_COMPANY, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Budget.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, vendor::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedVendor vendor =
                new JsonAdaptedVendor(TYPE, VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_COMPANY, VALID_BUDGET, invalidTags);
        assertThrows(IllegalValueException.class, vendor::toModelType);
    }
}
