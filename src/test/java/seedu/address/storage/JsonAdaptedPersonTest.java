package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.Messages;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.FamilySize;
import seedu.address.model.person.Income;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Priority;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_PRIORITY = "CRITICAL";
    private static final String INVALID_FORMAT_DATE_OF_BIRTH = "31 December 2022";
    private static final String INVALID_FUTURE_DATE_OF_BIRTH = "9999-01-01";
    private static final double INVALID_INCOME = -1929;
    private static final int INVALID_FAMILY_SIZE = -10;
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_UPDATED_AT = "jrfwhefwef";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_PRIORITY = BENSON.getPriority().toString();
    private static final String VALID_DATE_OF_BIRTH = BENSON.getDateOfBirth().getValue().toString();
    private static final double VALID_INCOME = BENSON.getIncome().getValue();
    private static final int VALID_FAMILY_SIZE = BENSON.getFamilySize().getValue();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .toList();
    private static final String VALID_UPDATED_AT = LocalDateTime.now().toString();

    private static final ArrayList<JsonAdaptedScheme> VALID_SCHEMES = new ArrayList<>();

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_PRIORITY, VALID_DATE_OF_BIRTH,
                        VALID_INCOME, VALID_FAMILY_SIZE, VALID_TAGS, VALID_SCHEMES, VALID_UPDATED_AT);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_PRIORITY, VALID_DATE_OF_BIRTH,
                        VALID_INCOME, VALID_FAMILY_SIZE, VALID_TAGS, VALID_SCHEMES, VALID_UPDATED_AT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_PRIORITY, VALID_DATE_OF_BIRTH,
                        VALID_INCOME, VALID_FAMILY_SIZE, VALID_TAGS, VALID_SCHEMES, VALID_UPDATED_AT);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS, VALID_PRIORITY, VALID_DATE_OF_BIRTH,
                        VALID_INCOME, VALID_FAMILY_SIZE, VALID_TAGS, VALID_SCHEMES, VALID_UPDATED_AT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_PRIORITY, VALID_DATE_OF_BIRTH,
                        VALID_INCOME, VALID_FAMILY_SIZE, VALID_TAGS, VALID_SCHEMES, VALID_UPDATED_AT);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME, VALID_PHONE, null, VALID_ADDRESS, VALID_PRIORITY, VALID_DATE_OF_BIRTH,
                        VALID_INCOME, VALID_FAMILY_SIZE, VALID_TAGS, VALID_SCHEMES, VALID_UPDATED_AT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_PRIORITY, VALID_DATE_OF_BIRTH,
                        VALID_INCOME, VALID_FAMILY_SIZE, VALID_TAGS, VALID_SCHEMES, VALID_UPDATED_AT);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_PRIORITY, VALID_DATE_OF_BIRTH,
                        VALID_INCOME, VALID_FAMILY_SIZE, VALID_TAGS, VALID_SCHEMES, VALID_UPDATED_AT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPriority_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, INVALID_PRIORITY, VALID_DATE_OF_BIRTH,
                        VALID_INCOME, VALID_FAMILY_SIZE, VALID_TAGS, VALID_SCHEMES, VALID_UPDATED_AT);
        String expectedMessage = Priority.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPriority_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, null, VALID_DATE_OF_BIRTH,
                        VALID_INCOME, VALID_FAMILY_SIZE, VALID_TAGS, VALID_SCHEMES, VALID_UPDATED_AT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Priority.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidFormatDateOfBirth_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_PRIORITY,
                        INVALID_FORMAT_DATE_OF_BIRTH, VALID_INCOME, VALID_FAMILY_SIZE, VALID_TAGS, VALID_SCHEMES,
                        VALID_UPDATED_AT);
        String expectedMessage = Messages.MESSAGE_INVALID_DATE_FORMAT;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidDateOfBirth_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_PRIORITY,
                        INVALID_FUTURE_DATE_OF_BIRTH, VALID_INCOME, VALID_FAMILY_SIZE, VALID_TAGS, VALID_SCHEMES,
                        VALID_UPDATED_AT);
        String expectedMessage = DateOfBirth.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullDateOfBirth_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_PRIORITY, null,
                        VALID_INCOME, VALID_FAMILY_SIZE, VALID_TAGS, VALID_SCHEMES, VALID_UPDATED_AT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DateOfBirth.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidIncome_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_PRIORITY, VALID_DATE_OF_BIRTH,
                        INVALID_INCOME, VALID_FAMILY_SIZE, VALID_TAGS, VALID_SCHEMES, VALID_UPDATED_AT);
        String expectedMessage = Income.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidFamilySize_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_PRIORITY, VALID_DATE_OF_BIRTH,
                        VALID_INCOME, INVALID_FAMILY_SIZE, VALID_TAGS, VALID_SCHEMES, VALID_UPDATED_AT);
        String expectedMessage = FamilySize.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_PRIORITY, VALID_DATE_OF_BIRTH,
                        VALID_INCOME, VALID_FAMILY_SIZE, invalidTags, VALID_SCHEMES, VALID_UPDATED_AT);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_invalidUpdatedAt_shouldNotThrow() {
        assertDoesNotThrow(() -> new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_PRIORITY, VALID_DATE_OF_BIRTH,
                VALID_INCOME, VALID_FAMILY_SIZE, VALID_TAGS, VALID_SCHEMES, INVALID_UPDATED_AT));
    }
}
