package seedu.address.storage;

import org.junit.jupiter.api.Test;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.internshipapplication.Date;
import seedu.address.model.internshipapplication.Email;
import seedu.address.model.internshipapplication.Name;
import seedu.address.model.internshipapplication.Role;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedInternship.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalInternshipApplications.GOOGLE;

public class JsonAdaptedInternshipTest {
    private static final String INVALID_COMPANY_NAME = "G@@GLE";
    private static final String INVALID_COMPANY_EMAIL = "google.com";
    private static final String INVALID_ROLE = " ";
    private static final String INVALID_DATESTRING = "4 Sep 1998";

    private static final String VALID_COMPANY_NAME = GOOGLE.getCompany().getName().toString();
    private static final String VALID_COMPANY_EMAIL = GOOGLE.getCompany().getEmail().toString();
    private static final String VALID_ROLE = GOOGLE.getRole().toString();
    private static final String VALID_DATE = GOOGLE.getDateOfApplication().toString();

    @Test
    public void toModelType_validInternshipApplicationDetails_returnsInternshipApplication() throws Exception {
        JsonAdaptedInternship application = new JsonAdaptedInternship(GOOGLE);
        assertEquals(GOOGLE, application.toModelType());
    }

    @Test
    public void toModelType_invalidCompanyName_throwsIllegalValueException() {
        JsonAdaptedInternship application =
                new JsonAdaptedInternship(INVALID_COMPANY_NAME, VALID_COMPANY_EMAIL, VALID_ROLE, VALID_DATE);

        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_nullCompanyName_throwsIllegalValueException() {
        JsonAdaptedInternship application =
                new JsonAdaptedInternship(null, VALID_COMPANY_EMAIL, VALID_ROLE, VALID_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidRole_throwsIllegalValueException() {
        JsonAdaptedInternship application =
                new JsonAdaptedInternship(VALID_COMPANY_NAME, VALID_COMPANY_EMAIL, INVALID_ROLE, VALID_DATE);

        String expectedMessage = Role.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_nullRole_throwsIllegalValueException() {
        JsonAdaptedInternship application =
                new JsonAdaptedInternship(VALID_COMPANY_NAME, VALID_COMPANY_EMAIL, null, VALID_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Role.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidCompanyEmail_throwsIllegalValueException() {
        JsonAdaptedInternship application =
                new JsonAdaptedInternship(VALID_COMPANY_NAME, INVALID_COMPANY_EMAIL, VALID_ROLE, VALID_DATE);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_nullCompanyEmail_throwsIllegalValueException() {
        JsonAdaptedInternship application =
                new JsonAdaptedInternship(VALID_COMPANY_NAME, null, VALID_ROLE, VALID_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedInternship application =
                new JsonAdaptedInternship(VALID_COMPANY_NAME, VALID_COMPANY_EMAIL, VALID_ROLE, INVALID_DATESTRING);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedInternship application =
                new JsonAdaptedInternship(VALID_COMPANY_NAME, VALID_COMPANY_EMAIL, VALID_ROLE, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

}
