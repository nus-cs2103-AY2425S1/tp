package seedu.internbuddy.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.internbuddy.storage.JsonAdaptedCompany.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.internbuddy.testutil.Assert.assertThrows;
import static seedu.internbuddy.testutil.TypicalCompanies.AMAZON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.internbuddy.commons.exceptions.IllegalValueException;
import seedu.internbuddy.model.company.Address;
import seedu.internbuddy.model.company.Company;
import seedu.internbuddy.model.company.Email;
import seedu.internbuddy.model.company.Phone;
import seedu.internbuddy.model.company.Status;
import seedu.internbuddy.model.name.Name;
import seedu.internbuddy.testutil.CompanyBuilder;

public class JsonAdaptedCompanyTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_STATUS = "im interested";

    private static final String VALID_NAME = AMAZON.getName().toString();
    private static final String VALID_PHONE = AMAZON.getPhone().toString();
    private static final String VALID_EMAIL = AMAZON.getEmail().toString();
    private static final String VALID_ADDRESS = AMAZON.getAddress().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = AMAZON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final String VALID_STATUS = AMAZON.getStatus().toString();
    private static final List<JsonAdaptedApplication> VALID_APPLICATIONS = AMAZON.getApplications().stream()
            .map(JsonAdaptedApplication::new)
            .toList();
    private static final Boolean VALID_IS_FAVOURITE = false;
    private static final Boolean DEFAULT_IS_SHOWING_DETAILS = false;

    @Test
    public void toModelType_validCompanyDetails_returnsCompany() throws Exception {
        JsonAdaptedCompany company = new JsonAdaptedCompany(AMAZON);
        assertEquals(AMAZON, company.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedCompany company =
                new JsonAdaptedCompany(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, VALID_STATUS,
                        VALID_APPLICATIONS, VALID_IS_FAVOURITE, DEFAULT_IS_SHOWING_DETAILS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, company::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedCompany company = new JsonAdaptedCompany(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, VALID_STATUS, VALID_APPLICATIONS, VALID_IS_FAVOURITE, DEFAULT_IS_SHOWING_DETAILS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, company::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedCompany company =
                new JsonAdaptedCompany(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, VALID_STATUS,
                        VALID_APPLICATIONS, VALID_IS_FAVOURITE, DEFAULT_IS_SHOWING_DETAILS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, company::toModelType);
    }

    @Test
    public void toModelType_nullPhone_returnsCompanyWithNoPhone() throws Exception {
        JsonAdaptedCompany company = new JsonAdaptedCompany(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, VALID_STATUS, VALID_APPLICATIONS, VALID_IS_FAVOURITE, DEFAULT_IS_SHOWING_DETAILS);
        Company expectedCompany = new CompanyBuilder(AMAZON).withNoPhone().build();
        assertEquals(expectedCompany, company.toModelType());
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedCompany company =
                new JsonAdaptedCompany(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_TAGS, VALID_STATUS,
                        VALID_APPLICATIONS, VALID_IS_FAVOURITE, DEFAULT_IS_SHOWING_DETAILS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, company::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedCompany company = new JsonAdaptedCompany(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS,
                VALID_TAGS, VALID_STATUS, VALID_APPLICATIONS, VALID_IS_FAVOURITE, DEFAULT_IS_SHOWING_DETAILS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, company::toModelType);
    }

    @Test
    public void toModelType_nullAddress_returnsCompanyWithNoAddress() throws Exception {
        JsonAdaptedCompany company = new JsonAdaptedCompany(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                VALID_TAGS, VALID_STATUS, VALID_APPLICATIONS, VALID_IS_FAVOURITE, DEFAULT_IS_SHOWING_DETAILS);
        Company expectedCompany = new CompanyBuilder(AMAZON).withNoAddress().build();
        assertEquals(expectedCompany, company.toModelType());
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedCompany company =
                new JsonAdaptedCompany(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_TAGS, VALID_STATUS,
                        VALID_APPLICATIONS, VALID_IS_FAVOURITE, DEFAULT_IS_SHOWING_DETAILS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, company::toModelType);
    }

    @Test
    public void toModelType_nullStatus_throwsIllegalValueException() {
        JsonAdaptedCompany company = new JsonAdaptedCompany(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, null, VALID_APPLICATIONS, VALID_IS_FAVOURITE, DEFAULT_IS_SHOWING_DETAILS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, company::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedCompany company =
                new JsonAdaptedCompany(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, invalidTags, VALID_STATUS,
                        VALID_APPLICATIONS, VALID_IS_FAVOURITE, DEFAULT_IS_SHOWING_DETAILS);
        assertThrows(IllegalValueException.class, company::toModelType);
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        JsonAdaptedCompany company =
                new JsonAdaptedCompany(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_TAGS,
                        INVALID_STATUS, VALID_APPLICATIONS, VALID_IS_FAVOURITE, DEFAULT_IS_SHOWING_DETAILS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, company::toModelType);
    }
}
