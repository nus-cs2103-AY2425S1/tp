package seedu.internbuddy.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.internbuddy.storage.JsonAdaptedApplication.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.internbuddy.testutil.Assert.assertThrows;
import static seedu.internbuddy.testutil.TypicalApplications.SWE_APPLICATION;

import org.junit.jupiter.api.Test;

import seedu.internbuddy.commons.exceptions.IllegalValueException;
import seedu.internbuddy.model.application.AppStatus;
import seedu.internbuddy.model.application.Description;
import seedu.internbuddy.model.name.Name;

public class JsonAdaptedApplicationTest {
    private static final String INVALID_NAME = "";
    private static final String INVALID_DESCRIPTION = " ";
    private static final String INVALID_STATUS = "applying";
    private static final String VALID_NAME = SWE_APPLICATION.getName().fullName;
    private static final String VALID_DESCRIPTION = SWE_APPLICATION.getDescription().fullDescription;
    private static final String VALID_STATUS = SWE_APPLICATION.getAppStatus().value;

    @Test
    public void toModelType_validApplicationDetails_returnsApplications() throws Exception {
        JsonAdaptedApplication application = new JsonAdaptedApplication(VALID_NAME, VALID_DESCRIPTION, VALID_STATUS);
        assertEquals(SWE_APPLICATION, application.toModelType());
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(null, VALID_DESCRIPTION, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(INVALID_NAME, VALID_DESCRIPTION, VALID_STATUS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(VALID_NAME, null, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(VALID_NAME, INVALID_DESCRIPTION, VALID_STATUS);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_nullStatus_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(VALID_NAME, VALID_DESCRIPTION, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, AppStatus.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(VALID_NAME, VALID_DESCRIPTION, INVALID_STATUS);
        String expectedMessage = AppStatus.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }
}
