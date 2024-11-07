package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedEmployee.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEmployees.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.employee.Address;
import seedu.address.model.employee.Email;
import seedu.address.model.employee.EmployeeId;
import seedu.address.model.employee.Name;
import seedu.address.model.employee.Phone;

public class JsonAdaptedEmployeeTest {
    private static final String INVALID_EMPLOYEE_ID = "123b";
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_EMPLOYEE_ID = BENSON.getEmployeeId().toString();
    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedSkill> VALID_SKILLS = BENSON.getSkills().stream()
            .map(JsonAdaptedSkill::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validEmployeeDetails_returnsEmployee() throws Exception {
        JsonAdaptedEmployee employee = new JsonAdaptedEmployee(BENSON);
        assertEquals(BENSON, employee.toModelType());
    }

    @Test
    public void toModelType_invalidEmployeeId_throwsIllegalValueException() {
        JsonAdaptedEmployee employee = new JsonAdaptedEmployee(INVALID_EMPLOYEE_ID, VALID_NAME,
                VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, VALID_SKILLS);
        String expectedMessage = EmployeeId.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, employee::toModelType);
    }

    @Test
    public void toModelType_nullEmployeeId_throwsIllegalValueException() {
        JsonAdaptedEmployee employee = new JsonAdaptedEmployee(null, VALID_NAME,
                VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, VALID_SKILLS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EmployeeId.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, employee::toModelType);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedEmployee employee = new JsonAdaptedEmployee(VALID_EMPLOYEE_ID, INVALID_NAME,
                VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, VALID_SKILLS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, employee::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedEmployee employee = new JsonAdaptedEmployee(VALID_EMPLOYEE_ID, null,
                VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, VALID_SKILLS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, employee::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedEmployee employee = new JsonAdaptedEmployee(VALID_EMPLOYEE_ID, VALID_NAME,
                INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, VALID_SKILLS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, employee::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedEmployee employee = new JsonAdaptedEmployee(VALID_EMPLOYEE_ID, VALID_NAME,
                null, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, VALID_SKILLS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, employee::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedEmployee employee = new JsonAdaptedEmployee(VALID_EMPLOYEE_ID, VALID_NAME,
                VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_TAGS, VALID_SKILLS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, employee::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedEmployee employee = new JsonAdaptedEmployee(VALID_EMPLOYEE_ID, VALID_NAME,
                VALID_PHONE, null, VALID_ADDRESS, VALID_TAGS, VALID_SKILLS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, employee::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedEmployee employee = new JsonAdaptedEmployee(VALID_EMPLOYEE_ID, VALID_NAME,
                VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_TAGS, VALID_SKILLS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, employee::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedEmployee employee = new JsonAdaptedEmployee(VALID_EMPLOYEE_ID, VALID_NAME,
                VALID_PHONE, VALID_EMAIL, null, VALID_TAGS, VALID_SKILLS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, employee::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedEmployee employee = new JsonAdaptedEmployee(VALID_EMPLOYEE_ID, VALID_NAME,
                VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, invalidTags, VALID_SKILLS);
        assertThrows(IllegalValueException.class, employee::toModelType);
    }

}
