package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CAR_MAKE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CAR_MODEL;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CAR_VIN_0;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CAR_VRN_0;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CAR_MAKE_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CAR_MODEL_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CAR_VIN_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CAR_VRN_A;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_CAR_FIELD_MESSAGE_FORMAT;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BENSON_WITH_CAR;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.car.CarMake;
import seedu.address.model.car.CarModel;
import seedu.address.model.car.Vin;
import seedu.address.model.car.Vrn;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;


public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_IS_SERVICING = "lol";
    private static final String INVALID_ISSUE = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_IS_SERVICING = Boolean.toString(false);
    private static final List<JsonAdaptedIssue> VALID_ISSUES = BENSON.getIssues().stream()
            .map(JsonAdaptedIssue::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_validPersonDetailsWithCar_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON_WITH_CAR);
        assertEquals(BENSON_WITH_CAR, person.toModelType());
    }

    /*
     * Test for missing Car Fields
     */
    @Test
    public void toModelType_missingVin_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_IS_SERVICING, VALID_ISSUES, null, VALID_CAR_VRN_A,
                VALID_CAR_MAKE_A, VALID_CAR_MODEL_A);
        String expectedMessage = String.format(MISSING_CAR_FIELD_MESSAGE_FORMAT, "Vin");
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_missingVrn_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_IS_SERVICING, VALID_ISSUES, VALID_CAR_VIN_A, null,
                VALID_CAR_MAKE_A, VALID_CAR_MODEL_A);
        String expectedMessage = String.format(MISSING_CAR_FIELD_MESSAGE_FORMAT, "Vrn");
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_missingMake_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_IS_SERVICING, VALID_ISSUES, VALID_CAR_VIN_A, VALID_CAR_VRN_A,
                null, VALID_CAR_MODEL_A);
        String expectedMessage = String.format(MISSING_CAR_FIELD_MESSAGE_FORMAT, "CarMake");
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_missingModel_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_IS_SERVICING, VALID_ISSUES, VALID_CAR_VIN_A, VALID_CAR_VRN_A,
                VALID_CAR_MAKE_A, null);
        String expectedMessage = String.format(MISSING_CAR_FIELD_MESSAGE_FORMAT, "CarModel");
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidIsServicing_setBooleanToFalse() {
        // Corrects the invalid isServicing field to false by default.
        JsonAdaptedPerson personJson = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                INVALID_IS_SERVICING, VALID_ISSUES, null, null, null, null);
        try {
            Person person = personJson.toModelType();
            assertFalse(((Person) person).isServicing());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void toModelType_nullIsServicing_setBooleanToFalse() {
        // Corrects the missing isServicing field to false by default.
        JsonAdaptedPerson personJson = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                null, VALID_ISSUES, null, null, null, null);
        try {
            Person person = personJson.toModelType();
            assertFalse(((Person) person).isServicing());
        } catch (Exception e) {
            fail();
        }
    }

    /*
     * Test for invalid Car Fields
     */
    @Test
    public void toModelType_invalidVin_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_IS_SERVICING, VALID_ISSUES, INVALID_CAR_VIN_0, VALID_CAR_VRN_A,
                VALID_CAR_MAKE_A, VALID_CAR_MODEL_A);
        String expectedMessage = Vin.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidVrn_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_IS_SERVICING, VALID_ISSUES, VALID_CAR_VIN_A, INVALID_CAR_VRN_0,
                VALID_CAR_MAKE_A, VALID_CAR_MODEL_A);
        String expectedMessage = Vrn.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidMake_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_IS_SERVICING, VALID_ISSUES, VALID_CAR_VIN_A, VALID_CAR_VRN_A,
                INVALID_CAR_MAKE, VALID_CAR_MODEL_A);
        String expectedMessage = CarMake.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidModel_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_IS_SERVICING, VALID_ISSUES, VALID_CAR_VIN_A, VALID_CAR_VRN_A,
                VALID_CAR_MAKE_A, INVALID_CAR_MODEL);
        String expectedMessage = CarModel.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    /*
     * Test for invalid Person Fields
     */
    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_IS_SERVICING, VALID_ISSUES, null, null, null, null);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_IS_SERVICING, VALID_ISSUES, null, null, null, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_IS_SERVICING, VALID_ISSUES, null, null, null, null);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS,
                VALID_IS_SERVICING, VALID_ISSUES, null, null, null, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_IS_SERVICING,
                        VALID_ISSUES, null, null, null, null);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS,
                VALID_IS_SERVICING, VALID_ISSUES, null, null, null, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS,
                        VALID_IS_SERVICING, VALID_ISSUES, null, null, null, null);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                VALID_IS_SERVICING, VALID_ISSUES, null, null, null, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidIssues_throwsIllegalValueException() {
        List<JsonAdaptedIssue> invalidIssues = new ArrayList<>(VALID_ISSUES);
        invalidIssues.add(new JsonAdaptedIssue(INVALID_ISSUE));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_IS_SERVICING, invalidIssues, null, null, null, null);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

}
