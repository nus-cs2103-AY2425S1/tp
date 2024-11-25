package seedu.hireme.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.hireme.storage.JsonAdaptedInternship.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.hireme.storage.JsonAdaptedInternship.MISSING_STATUS_FIELD_MESSAGE;
import static seedu.hireme.testutil.Assert.assertThrows;
import static seedu.hireme.testutil.TypicalInternshipApplications.GOOGLE;

import org.junit.jupiter.api.Test;

import seedu.hireme.commons.exceptions.IllegalValueException;
import seedu.hireme.model.internshipapplication.Date;
import seedu.hireme.model.internshipapplication.Email;
import seedu.hireme.model.internshipapplication.Name;
import seedu.hireme.model.internshipapplication.Role;
import seedu.hireme.model.internshipapplication.Status;

public class JsonAdaptedInternshipTest {
    private static final String INVALID_COMPANY_NAME = "G@@GLE";
    private static final String INVALID_COMPANY_EMAIL = "google.com";
    private static final String INVALID_ROLE = " ";
    private static final String INVALID_DATESTRING = "4 Sep 1998";
    private static final String INVALID_STATUS = "SELF-EMPLOYED";
    private static final String VALID_COMPANY_NAME = GOOGLE.getCompany().getName().toString();
    private static final String VALID_COMPANY_EMAIL = GOOGLE.getCompany().getEmail().toString();
    private static final String VALID_ROLE = GOOGLE.getRole().toString();
    private static final String VALID_DATE = GOOGLE.getDateOfApplication().toString();
    private static final String VALID_STATUS = GOOGLE.getStatus().toString();

    @Test
    public void toModelType_validInternshipApplicationDetails_returnsInternshipApplication() throws Exception {
        JsonAdaptedInternship application = new JsonAdaptedInternship(GOOGLE);
        assertEquals(GOOGLE, application.toModelType());
    }

    @Test
    public void toModelType_invalidCompanyName_throwsIllegalValueException() {
        JsonAdaptedInternship application =
                new JsonAdaptedInternship(INVALID_COMPANY_NAME, VALID_COMPANY_EMAIL,
                                          VALID_ROLE, VALID_DATE, VALID_STATUS);

        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_nullCompanyName_throwsIllegalValueException() {
        JsonAdaptedInternship application =
                new JsonAdaptedInternship(null, VALID_COMPANY_EMAIL, VALID_ROLE, VALID_DATE, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidRole_throwsIllegalValueException() {
        JsonAdaptedInternship application =
                new JsonAdaptedInternship(VALID_COMPANY_NAME, VALID_COMPANY_EMAIL,
                                          INVALID_ROLE, VALID_DATE, VALID_STATUS);

        String expectedMessage = Role.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_nullRole_throwsIllegalValueException() {
        JsonAdaptedInternship application =
                new JsonAdaptedInternship(VALID_COMPANY_NAME, VALID_COMPANY_EMAIL, null, VALID_DATE, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Role.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidCompanyEmail_throwsIllegalValueException() {
        JsonAdaptedInternship application =
                new JsonAdaptedInternship(VALID_COMPANY_NAME, INVALID_COMPANY_EMAIL,
                                          VALID_ROLE, VALID_DATE, VALID_STATUS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_nullCompanyEmail_throwsIllegalValueException() {
        JsonAdaptedInternship application =
                new JsonAdaptedInternship(VALID_COMPANY_NAME, null, VALID_ROLE, VALID_DATE, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedInternship application =
                new JsonAdaptedInternship(VALID_COMPANY_NAME, VALID_COMPANY_EMAIL,
                                          VALID_ROLE, INVALID_DATESTRING, VALID_STATUS);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedInternship application =
                new JsonAdaptedInternship(VALID_COMPANY_NAME, VALID_COMPANY_EMAIL, VALID_ROLE, null, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_nullStatus_throwsIllegalValueException() {
        JsonAdaptedInternship application =
                new JsonAdaptedInternship(VALID_COMPANY_NAME, VALID_COMPANY_EMAIL,
                VALID_ROLE, VALID_DATE, null);
        String expectedMessage = MISSING_STATUS_FIELD_MESSAGE;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        JsonAdaptedInternship application =
                new JsonAdaptedInternship(VALID_COMPANY_NAME, VALID_COMPANY_EMAIL,
                VALID_ROLE, VALID_DATE, INVALID_STATUS);
        String expectedMessage = Status.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

}
