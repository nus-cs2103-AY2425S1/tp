package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DATE_FORMAT;
import static seedu.address.model.person.Birthday.MESSAGE_INVALID_BIRTHDAY_AFTER_PRESENT;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.DateOfCreation;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_BIRTHDAY = "not a date";
    private static final String TOO_LATE_BIRTHDAY = LocalDate.now().plusDays(5).toString();
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_DATE_OF_CREATION = LocalDate.now().plusDays(100).toString();
    private static final String VALID_LOG_MESSAGE = "message";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_REMARK = BENSON.getRemark().toString();
    private static final String VALID_DATE_OF_CREATION = BENSON.getDateOfCreation().getDateOfCreation().toString();
    private static final List<JsonAdaptedHistoryEntry> VALID_HISTORY = BENSON.getHistory()
            .getHistoryEntries().entrySet().stream()
            .map(entry -> new JsonAdaptedHistoryEntry(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());
    private static final String VALID_BIRTHDAY = BENSON.getBirthday().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedProperty> VALID_PROPERTIES = BENSON.getPropertyList().getProperties().stream()
            .map(JsonAdaptedProperty::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_REMARK, VALID_BIRTHDAY, VALID_TAGS,
                VALID_DATE_OF_CREATION, VALID_HISTORY, VALID_PROPERTIES);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_REMARK, VALID_BIRTHDAY, VALID_TAGS,
                VALID_DATE_OF_CREATION, VALID_HISTORY, VALID_PROPERTIES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_REMARK, VALID_BIRTHDAY, VALID_TAGS,
                VALID_DATE_OF_CREATION, VALID_HISTORY, VALID_PROPERTIES);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS,
                VALID_REMARK, VALID_BIRTHDAY, VALID_TAGS,
                VALID_DATE_OF_CREATION, VALID_HISTORY, VALID_PROPERTIES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS,
                VALID_REMARK, VALID_BIRTHDAY, VALID_TAGS,
                VALID_DATE_OF_CREATION, VALID_HISTORY, VALID_PROPERTIES);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, null, VALID_ADDRESS,
                VALID_REMARK, VALID_BIRTHDAY, VALID_TAGS,
                VALID_DATE_OF_CREATION, VALID_HISTORY, VALID_PROPERTIES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS,
                VALID_REMARK, VALID_BIRTHDAY, VALID_TAGS,
                VALID_DATE_OF_CREATION, VALID_HISTORY, VALID_PROPERTIES);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                VALID_REMARK, VALID_BIRTHDAY, VALID_TAGS,
                VALID_DATE_OF_CREATION, VALID_HISTORY, VALID_PROPERTIES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullDateOfCreation_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_REMARK, VALID_BIRTHDAY, VALID_TAGS,
                null, VALID_HISTORY, VALID_PROPERTIES);

        String expectedMessage = String.format(JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT,
                DateOfCreation.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }


    @Test
    public void toModelType_historyWithFutureDate_throwsIllegalValueException() {
        // Set up a future date entry in history
        List<JsonAdaptedHistoryEntry> historyWithFutureDate = List.of(
                new JsonAdaptedHistoryEntry(LocalDate.now().plusDays(1), List.of(VALID_LOG_MESSAGE))
        );

        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_REMARK,
                VALID_BIRTHDAY, VALID_TAGS, VALID_DATE_OF_CREATION, historyWithFutureDate, VALID_PROPERTIES);

        String expectedMessage = JsonAdaptedPerson.INVALID_HISTORY_DATE_IN_FUTURE;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_historyWithDateBeforeCreation_throwsIllegalValueException() {
        // Set up a history entry with a date before the date of creation
        List<JsonAdaptedHistoryEntry> historyWithEarlyDate = List.of(
                new JsonAdaptedHistoryEntry(LocalDate.parse(VALID_DATE_OF_CREATION)
                        .minusDays(1), List.of(VALID_LOG_MESSAGE))
        );

        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_REMARK,
                VALID_BIRTHDAY, VALID_TAGS, VALID_DATE_OF_CREATION, historyWithEarlyDate, VALID_PROPERTIES);

        String expectedMessage = JsonAdaptedPerson.INVALID_HISTORY_DATE_BEFORE_CREATION;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullBirthday_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_REMARK, null, VALID_TAGS,
                VALID_DATE_OF_CREATION, VALID_HISTORY, VALID_PROPERTIES);

        String expectedMessage = String.format(JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT,
                Birthday.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidBirthdayFormat_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_REMARK, INVALID_BIRTHDAY, VALID_TAGS,
                VALID_DATE_OF_CREATION, VALID_HISTORY, VALID_PROPERTIES);

        assertThrows(IllegalValueException.class, MESSAGE_INVALID_DATE_FORMAT, person::toModelType);
    }

    @Test
    public void toModelType_invalidBirthday_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_REMARK, TOO_LATE_BIRTHDAY, VALID_TAGS,
                VALID_DATE_OF_CREATION, VALID_HISTORY, VALID_PROPERTIES);

        assertThrows(IllegalValueException.class, MESSAGE_INVALID_BIRTHDAY_AFTER_PRESENT, person::toModelType);
    }
}
