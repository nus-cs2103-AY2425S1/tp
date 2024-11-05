package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Schedule;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_SCHEDULE_NAME = "my-appointment";
    private static final String INVALID_SCHEDULE_DATE = "2021-14";
    private static final String INVALID_SCHEDULE_TIME = "12000";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_SOCIALMEDIA_IG = "[ig-usernam$]";
    private static final String INVALID_SOCIALMEDIA_FB = "[fb-usernam$]";
    private static final String INVALID_SOCIALMEDIA_CS = "[cs-usernam$]";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_SCHEDULE_NAME = "appointment";
    private static final String VALID_SCHEDULE_DATE = "2021-10-21";
    private static final String VALID_SCHEDULE_TIME = "12:00";
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final String VALID_SOCIALMEDIA = BENSON.getSocialMedia().toString();

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_SCHEDULE_NAME, VALID_SCHEDULE_DATE,
                        VALID_SCHEDULE_TIME, VALID_TAGS, VALID_SOCIALMEDIA);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_SCHEDULE_NAME, VALID_SCHEDULE_DATE, VALID_SCHEDULE_TIME, VALID_TAGS, VALID_SOCIALMEDIA);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_SCHEDULE_NAME,
                        VALID_SCHEDULE_DATE, VALID_SCHEDULE_TIME, VALID_TAGS, VALID_SOCIALMEDIA);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS,
                VALID_SCHEDULE_NAME, VALID_SCHEDULE_DATE, VALID_SCHEDULE_TIME, VALID_TAGS, VALID_SOCIALMEDIA);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_SCHEDULE_NAME,
                        VALID_SCHEDULE_DATE, VALID_SCHEDULE_TIME, VALID_TAGS, VALID_SOCIALMEDIA);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS,
                VALID_SCHEDULE_NAME, VALID_SCHEDULE_DATE, VALID_SCHEDULE_TIME, VALID_TAGS, VALID_SOCIALMEDIA);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_SCHEDULE_NAME,
                        VALID_SCHEDULE_DATE, VALID_SCHEDULE_TIME, VALID_TAGS, VALID_SOCIALMEDIA);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                VALID_SCHEDULE_NAME, VALID_SCHEDULE_DATE, VALID_SCHEDULE_TIME, VALID_TAGS, VALID_SOCIALMEDIA);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidScheduleName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        INVALID_SCHEDULE_NAME, VALID_SCHEDULE_DATE, VALID_SCHEDULE_TIME, VALID_TAGS, VALID_SOCIALMEDIA);
        String expectedMessage = Schedule.SCHEDULE_NAME_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullScheduleName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                null, VALID_SCHEDULE_DATE, VALID_SCHEDULE_TIME, VALID_TAGS, VALID_SOCIALMEDIA);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Schedule.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidScheduleDate_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_SCHEDULE_NAME, INVALID_SCHEDULE_DATE, VALID_SCHEDULE_TIME, VALID_TAGS, VALID_SOCIALMEDIA);
        String expectedMessage = Schedule.DATE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullScheduleDate_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_SCHEDULE_NAME, null, VALID_SCHEDULE_TIME, VALID_TAGS, VALID_SOCIALMEDIA);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Schedule.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidScheduleTime_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_SCHEDULE_NAME, VALID_SCHEDULE_DATE, INVALID_SCHEDULE_TIME, VALID_TAGS, VALID_SOCIALMEDIA);
        String expectedMessage = Schedule.TIME_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullScheduleTime_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_SCHEDULE_NAME, VALID_SCHEDULE_DATE, null, VALID_TAGS, VALID_SOCIALMEDIA);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Schedule.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_SCHEDULE_NAME, VALID_SCHEDULE_DATE, VALID_SCHEDULE_TIME, invalidTags, VALID_SOCIALMEDIA);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_invalidSocialMediaIg_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_SCHEDULE_NAME, VALID_SCHEDULE_DATE, VALID_SCHEDULE_TIME, VALID_TAGS,
                        INVALID_SOCIALMEDIA_IG);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_invalidSocialMediaFb_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_SCHEDULE_NAME, VALID_SCHEDULE_DATE, VALID_SCHEDULE_TIME, VALID_TAGS,
                        INVALID_SOCIALMEDIA_FB);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_invalidSocialMediaCs_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_SCHEDULE_NAME, VALID_SCHEDULE_DATE, VALID_SCHEDULE_TIME, VALID_TAGS,
                        INVALID_SOCIALMEDIA_CS);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_nullSocialMedia_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_SCHEDULE_NAME, VALID_SCHEDULE_DATE, VALID_SCHEDULE_TIME, VALID_TAGS, null);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_validPersonDetails_returnsPersonCarl() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(CARL);
        assertEquals(CARL, person.toModelType());
    }

    @Test
    public void toModelType_validPersonDetails_returnsPersonDaniel() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(DANIEL);
        assertEquals(DANIEL, person.toModelType());
    }

}
