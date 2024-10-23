package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MEDCON;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Priority;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_NRIC = "S123456";
    private static final String INVALID_DATE_OF_BIRTH_FORMAT = "2000-01-0";
    private static final String INVALID_DATE_OF_BIRTH_FUTURE_DATE = LocalDate.now().plusDays(2)
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    private static final String INVALID_GENDER = "X";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_ALLERGY = "#friend";
    private static final String INVALID_PRIORITY = "invalidPriority";
    private static final String INVALID_APPOINTMENTS = "yolo:today:0420";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_NRIC = BENSON.getNric().toString();
    private static final String VALID_DATE_OF_BIRTH = BENSON.getDateOfBirth().toString();
    private static final String VALID_GENDER = BENSON.getGender().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_PRIORITY = BENSON.getPriority().toString();
    private static final List<JsonAdaptedAllergy> VALID_ALLERGIES = BENSON.getAllergies().stream()
                                                                 .map(JsonAdaptedAllergy::new)
                                                                 .collect(Collectors.toList());
    private static final List<JsonAdaptedAppointment> VALID_APPOINTMENTS = BENSON.getAppointments().stream()
                                                                                 .map(JsonAdaptedAppointment::new)
                                                                                 .collect(Collectors.toList());
    private static final List<JsonAdaptedMedCon> VALID_MEDCON = BENSON.getMedCons().stream()
                                                                      .map(JsonAdaptedMedCon::new)
                                                                      .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_DATE_OF_BIRTH,
                        VALID_GENDER, VALID_NRIC, VALID_ALLERGIES, VALID_PRIORITY, VALID_APPOINTMENTS, VALID_MEDCON);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_DATE_OF_BIRTH, VALID_GENDER, VALID_NRIC, VALID_ALLERGIES, VALID_PRIORITY, VALID_APPOINTMENTS,
                VALID_MEDCON);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_DATE_OF_BIRTH,
                        VALID_GENDER, VALID_NRIC, VALID_ALLERGIES, VALID_PRIORITY, VALID_APPOINTMENTS, VALID_MEDCON);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS,
                VALID_DATE_OF_BIRTH, VALID_GENDER, VALID_NRIC, VALID_ALLERGIES, VALID_PRIORITY, VALID_APPOINTMENTS,
                VALID_MEDCON);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_DATE_OF_BIRTH,
                        VALID_GENDER, VALID_NRIC, VALID_ALLERGIES, VALID_PRIORITY, VALID_APPOINTMENTS, VALID_MEDCON);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS,
                VALID_DATE_OF_BIRTH, VALID_GENDER, VALID_NRIC, VALID_ALLERGIES, VALID_PRIORITY, VALID_APPOINTMENTS,
                VALID_MEDCON);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_ADDRESS,
                        VALID_DATE_OF_BIRTH, VALID_GENDER, VALID_ALLERGIES, VALID_PRIORITY, VALID_APPOINTMENTS,
                        VALID_MEDCON);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                VALID_ADDRESS, VALID_DATE_OF_BIRTH, VALID_GENDER, VALID_ALLERGIES, VALID_PRIORITY, VALID_APPOINTMENTS,
                        VALID_MEDCON);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidDateOfBirthDateFormat_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        INVALID_DATE_OF_BIRTH_FORMAT, VALID_GENDER, VALID_NRIC, VALID_ALLERGIES, VALID_PRIORITY,
                        VALID_APPOINTMENTS, VALID_MEDCON);
        String expectedMessage = DateOfBirth.MESSAGE_CONSTRAINTS_WRONG_FORMAT;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidDateOfBirthFutureDate_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        INVALID_DATE_OF_BIRTH_FUTURE_DATE, VALID_GENDER, VALID_NRIC, VALID_ALLERGIES, VALID_PRIORITY,
                        VALID_APPOINTMENTS, VALID_MEDCON);
        String expectedMessage = DateOfBirth.MESSAGE_CONSTRAINTS_FUTURE_DATE;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullDateOfBirth_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                null, VALID_GENDER, VALID_NRIC, VALID_ALLERGIES, VALID_PRIORITY,
                VALID_APPOINTMENTS, VALID_MEDCON);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DateOfBirth.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidGender_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_DATE_OF_BIRTH,
                        INVALID_GENDER, VALID_NRIC, VALID_ALLERGIES, VALID_PRIORITY, VALID_APPOINTMENTS, VALID_MEDCON);
        String expectedMessage = Gender.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullGender_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_DATE_OF_BIRTH, null, VALID_NRIC, VALID_ALLERGIES, VALID_PRIORITY, VALID_APPOINTMENTS,
                VALID_MEDCON);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Gender.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidNric_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_DATE_OF_BIRTH,
                        VALID_GENDER, INVALID_NRIC, VALID_ALLERGIES, VALID_PRIORITY, VALID_APPOINTMENTS, VALID_MEDCON);
        String expectedMessage = Nric.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullNric_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_DATE_OF_BIRTH, VALID_GENDER, null, VALID_ALLERGIES, VALID_PRIORITY,
                VALID_APPOINTMENTS, VALID_MEDCON);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAllergies_throwsIllegalValueException() {
        List<JsonAdaptedAllergy> invalidAllergies = new ArrayList<>(VALID_ALLERGIES);
        invalidAllergies.add(new JsonAdaptedAllergy(INVALID_ALLERGY));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_ADDRESS,
                        VALID_DATE_OF_BIRTH, VALID_GENDER, invalidAllergies, VALID_PRIORITY, VALID_APPOINTMENTS,
                        VALID_MEDCON);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_invalidAppointments_throwsIllegalValueException() {
        List<JsonAdaptedAppointment> invalidAppointments = new ArrayList<>(VALID_APPOINTMENTS);
        invalidAppointments.add(new JsonAdaptedAppointment(INVALID_APPOINTMENTS));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_ADDRESS,
                        VALID_DATE_OF_BIRTH, VALID_GENDER, VALID_ALLERGIES,
                        VALID_PRIORITY, invalidAppointments, VALID_MEDCON);
    }
    @Test
    public void toModelType_invalidPriority_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_DATE_OF_BIRTH, VALID_GENDER, VALID_NRIC, VALID_ALLERGIES, INVALID_PRIORITY, VALID_APPOINTMENTS,
                VALID_MEDCON);
        String expectedMessage = Priority.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalArgumentException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPriority_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_DATE_OF_BIRTH, VALID_GENDER, VALID_NRIC, VALID_ALLERGIES, null, VALID_APPOINTMENTS,
                VALID_MEDCON);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Priority.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidMedCons_throwsIllegalValueException() {
        List<JsonAdaptedMedCon> invalidMedCons = new ArrayList<>(VALID_MEDCON);
        invalidMedCons.add(new JsonAdaptedMedCon(INVALID_MEDCON));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_ADDRESS,
                        VALID_DATE_OF_BIRTH, VALID_GENDER, VALID_ALLERGIES, VALID_PRIORITY, VALID_APPOINTMENTS,
                        invalidMedCons);
        assertThrows(IllegalValueException.class, person::toModelType);
    }



}
