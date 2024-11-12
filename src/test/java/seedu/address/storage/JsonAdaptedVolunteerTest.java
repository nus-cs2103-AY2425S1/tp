package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedVolunteer.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalVolunteers.ALICE;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.volunteer.Email;
import seedu.address.model.volunteer.Name;
import seedu.address.model.volunteer.Phone;
import seedu.address.model.volunteer.VolunteerDates;

public class JsonAdaptedVolunteerTest {

    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "1a2b";
    private static final String INVALID_EMAIL = "2020-13-32";
    private static final String INVALID_DATE = "2022/02/02";

    private static final String VALID_NAME = ALICE.getName().toString();
    private static final String VALID_PHONE = ALICE.getPhone().toString();
    private static final String VALID_EMAIL = ALICE.getEmail().toString();
    private static final String VALID_DATE = ALICE.getAvailableDates().toParsableString();
    private static final List<String> VALID_INVOLVED_IN = ALICE.getEvents();

    @Test
    public void toModelType_validVolunteerDetails_returnsVolunteer() throws Exception {
        JsonAdaptedVolunteer volunteer = new JsonAdaptedVolunteer(ALICE);
        assertEquals(ALICE, volunteer.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedVolunteer volunteer =
                new JsonAdaptedVolunteer(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_DATE, VALID_INVOLVED_IN);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, volunteer::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedVolunteer volunteer =
                new JsonAdaptedVolunteer(null, VALID_PHONE, VALID_EMAIL, VALID_DATE, VALID_INVOLVED_IN);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, volunteer::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedVolunteer volunteer =
                new JsonAdaptedVolunteer(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_DATE, VALID_INVOLVED_IN);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, volunteer::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedVolunteer volunteer =
                new JsonAdaptedVolunteer(VALID_NAME, null, VALID_EMAIL, VALID_DATE, VALID_INVOLVED_IN);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, volunteer::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedVolunteer volunteer =
                new JsonAdaptedVolunteer(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_DATE, VALID_INVOLVED_IN);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, volunteer::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedVolunteer volunteer =
                new JsonAdaptedVolunteer(VALID_NAME, VALID_PHONE, null, VALID_DATE, VALID_INVOLVED_IN);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, volunteer::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedVolunteer volunteer =
                new JsonAdaptedVolunteer(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_DATE, VALID_INVOLVED_IN);
        String expectedMessage = VolunteerDates.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, volunteer::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedVolunteer volunteer =
                new JsonAdaptedVolunteer(VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_INVOLVED_IN);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, VolunteerDates.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, volunteer::toModelType);
    }

    /*
    Note that we do not test for null involvedIn as if it is null, it will be replaced with an empty list.

    Also, we do not test for invalid involvedIn as the checks are handled in JsonSerializableAddressBook::toModelType,
    where AddressBook::validateAllEvents will be called to handle the validation.
     */


}

