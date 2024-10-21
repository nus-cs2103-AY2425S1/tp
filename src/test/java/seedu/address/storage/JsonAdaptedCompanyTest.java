package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedCompany.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCompanies.NUS;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.common.Address;
import seedu.address.model.common.Name;
import seedu.address.model.company.BillingDate;
import seedu.address.model.person.Phone;

public class JsonAdaptedCompanyTest {
    private static final String INVALID_NAME = " ";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_BILLING_DATE = "0";
    private static final String INVALID_PHONE = "+651234";
    private static final String VALID_NAME = NUS.getName().fullName;
    private static final String VALID_ADDRESS = NUS.getAddress().value;
    private static final String VALID_BILLING_DATE = NUS.getBillingDate().date;
    private static final String VALID_PHONE = NUS.getPhone().value;

    @Test
    public void toModelType_validCompanyDetails_returnsCompany() throws Exception {
        JsonAdaptedCompany company = new JsonAdaptedCompany(NUS);
        assertEquals(NUS, company.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedCompany company =
                new JsonAdaptedCompany(INVALID_NAME, VALID_ADDRESS, VALID_BILLING_DATE, VALID_PHONE);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, company::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedCompany company =
                new JsonAdaptedCompany(null, VALID_ADDRESS, VALID_BILLING_DATE, VALID_PHONE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, company::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedCompany company =
                new JsonAdaptedCompany(VALID_NAME, INVALID_ADDRESS, VALID_BILLING_DATE, VALID_PHONE);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, company::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedCompany company =
                new JsonAdaptedCompany(VALID_NAME, null, VALID_BILLING_DATE, VALID_PHONE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, company::toModelType);
    }

    @Test
    public void toModelType_invalidBillingDate_throwsIllegalValueException() {
        JsonAdaptedCompany company =
                new JsonAdaptedCompany(VALID_NAME, VALID_ADDRESS, INVALID_BILLING_DATE, VALID_PHONE);
        String expectedMessage = BillingDate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, company::toModelType);
    }

    @Test
    public void toModelType_nullBillingDate_throwsIllegalValueException() {
        JsonAdaptedCompany company =
                new JsonAdaptedCompany(VALID_NAME, VALID_ADDRESS, null, VALID_PHONE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, BillingDate.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, company::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedCompany company =
                new JsonAdaptedCompany(VALID_NAME, VALID_ADDRESS, VALID_BILLING_DATE, INVALID_PHONE);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, company::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedCompany company =
                new JsonAdaptedCompany(VALID_NAME, VALID_ADDRESS, VALID_BILLING_DATE, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, company::toModelType);
    }
}
