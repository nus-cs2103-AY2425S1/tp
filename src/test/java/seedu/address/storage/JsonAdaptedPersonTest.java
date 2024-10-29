package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.GEORGE_WEDDING;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.role.Role;
import seedu.address.model.wedding.Wedding;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_ROLE = "#client";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_ROLE = BENSON.getRole().map(r -> r.roleName).orElse("");
    private static final int VALID_OWN_WEDDING = BENSON.getOwnWedding() != null
            ? BENSON.getOwnWedding().hashCode() : 0;
    private static final List<Integer> VALID_WEDDING_JOBS = new ArrayList<>(
            Arrays.asList(BENSON.getWeddingJobs().hashCode()));
    private static final List<Wedding> VALID_WEDDING_LIST = new ArrayList<>();

    private static final JsonAdaptedPerson BENSON_JSON = new JsonAdaptedPerson(BENSON);
    private static final JsonAdaptedPerson BENSON_JSON_COPY = new JsonAdaptedPerson(
            VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_ROLE, VALID_OWN_WEDDING, VALID_WEDDING_JOBS);

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        VALID_WEDDING_LIST.add(GEORGE_WEDDING);
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType(VALID_WEDDING_LIST));
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_ROLE, VALID_OWN_WEDDING, VALID_WEDDING_JOBS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, () ->
                person.toModelType(VALID_WEDDING_LIST));
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_ROLE, VALID_OWN_WEDDING, VALID_WEDDING_JOBS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () ->
                person.toModelType(VALID_WEDDING_LIST));
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_ROLE, VALID_OWN_WEDDING, VALID_WEDDING_JOBS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, () ->
                person.toModelType(VALID_WEDDING_LIST));
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null, VALID_EMAIL,
                VALID_ADDRESS, VALID_ROLE, VALID_OWN_WEDDING, VALID_WEDDING_JOBS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () ->
                person.toModelType(VALID_WEDDING_LIST));
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL,
                VALID_ADDRESS, VALID_ROLE, VALID_OWN_WEDDING, VALID_WEDDING_JOBS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, () ->
                person.toModelType(VALID_WEDDING_LIST));
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, null,
                VALID_ADDRESS, VALID_ROLE, VALID_OWN_WEDDING, VALID_WEDDING_JOBS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () ->
                person.toModelType(VALID_WEDDING_LIST));
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                INVALID_ADDRESS, VALID_ROLE, VALID_OWN_WEDDING, VALID_WEDDING_JOBS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, () ->
                person.toModelType(VALID_WEDDING_LIST));
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                null, VALID_ROLE, VALID_OWN_WEDDING, VALID_WEDDING_JOBS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () ->
                person.toModelType(VALID_WEDDING_LIST));
    }

    @Test
    public void toModelType_invalidRole_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, INVALID_ROLE, VALID_OWN_WEDDING, VALID_WEDDING_JOBS);
        assertThrows(IllegalValueException.class, Role.MESSAGE_CONSTRAINTS, () ->
                person.toModelType(VALID_WEDDING_LIST));
    }

    @Test
    public void toModelType_nullRole_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, null, VALID_OWN_WEDDING, VALID_WEDDING_JOBS);
        assertNotNull(person.toModelType(VALID_WEDDING_LIST));
    }

    @Test
    public void toModelType_withWeddings_returnsPerson() throws Exception {
        // Create a test wedding and add it to the list
        Wedding testWedding = new Wedding(new Name("Test Wedding"), null, null, null);
        List<Wedding> weddingList = new ArrayList<>();
        weddingList.add(testWedding);

        // Create a person with the test wedding as ownWedding
        List<Integer> weddingJobs = new ArrayList<>();
        weddingJobs.add(testWedding.hashCode());

        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_ROLE, testWedding.hashCode(), weddingJobs);

        Person modelPerson = person.toModelType(weddingList);
        assertEquals(testWedding, modelPerson.getOwnWedding());
        assertEquals(1, modelPerson.getWeddingJobs().size());
        assertEquals(testWedding, modelPerson.getWeddingJobs().iterator().next());
    }
    @Test
    public void equals_sameObject_returnsTrue() {
        assertTrue(BENSON_JSON.equals(BENSON_JSON));
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        assertFalse(BENSON_JSON.equals(null));
    }

    @Test
    public void equals_differentClass_returnsFalse() {
        assertFalse(BENSON_JSON.equals("not a person"));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        assertTrue(BENSON_JSON.equals(BENSON_JSON_COPY));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                "Different Name",
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_ROLE,
                VALID_OWN_WEDDING,
                VALID_WEDDING_JOBS
        );
        assertFalse(BENSON_JSON.equals(person));
    }

    @Test
    public void equals_nullTags_handledCorrectly() {
        JsonAdaptedPerson person1WithNullTags = new JsonAdaptedPerson(
                "John Doe",
                "john@example.com",
                "91234567",
                "123 Main St",
                null,
                0,
                new ArrayList<>()
        );
        JsonAdaptedPerson person2WithNullTags = new JsonAdaptedPerson(
                "John Doe",
                "john@example.com",
                "91234567",
                "123 Main St",
                null,
                0,
                new ArrayList<>()
        );
        assertTrue(person1WithNullTags.equals(person2WithNullTags));
    }

    @Test
    public void equals_emptyTags_handledCorrectly() {
        JsonAdaptedPerson person1WithEmptyTags = new JsonAdaptedPerson(
                "John Doe",
                "john@example.com",
                "91234567",
                "123 Main St",
                "",
                0,
                new ArrayList<>()
        );
        JsonAdaptedPerson person2WithEmptyTags = new JsonAdaptedPerson(
                "John Doe",
                "john@example.com",
                "91234567",
                "123 Main St",
                "",
                0,
                new ArrayList<>()
        );
        assertTrue(person1WithEmptyTags.equals(person2WithEmptyTags));
    }

    @Test
    public void hashCode_sameValues_sameHashCode() {
        assertEquals(BENSON_JSON.hashCode(), BENSON_JSON_COPY.hashCode());
    }

    @Test
    public void hashCode_differentValues_differentHashCode() {
        JsonAdaptedPerson person2 = new JsonAdaptedPerson(
                "Different Name",
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_ROLE,
                VALID_OWN_WEDDING,
                VALID_WEDDING_JOBS
        );
        assertNotEquals(BENSON_JSON.hashCode(), person2.hashCode());
    }

    @Test
    public void hashCode_consistentResults() {
        // Hash code should be consistent across multiple calls
        int initialHashCode = BENSON_JSON.hashCode();
        assertEquals(initialHashCode, BENSON_JSON.hashCode());
        assertEquals(initialHashCode, BENSON_JSON.hashCode());
    }

    @Test
    public void hashCode_nullTags_consistentHashCode() {
        // Null tags should not affect hash code
        JsonAdaptedPerson personWithNullTags = new JsonAdaptedPerson(
                "John Doe",
                "john@example.com",
                "91234567",
                "123 Main St",
                null,
                0,
                new ArrayList<>()
        );
        int hashCode1 = personWithNullTags.hashCode();
        int hashCode2 = personWithNullTags.hashCode();
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    public void hashCode_emptyTags_consistentHashCode() {
        JsonAdaptedPerson personWithEmptyTags = new JsonAdaptedPerson(
                "John Doe",
                "john@example.com",
                "91234567",
                "123 Main St",
                "",
                0,
                new ArrayList<>()
        );
        int hashCode1 = personWithEmptyTags.hashCode();
        int hashCode2 = personWithEmptyTags.hashCode();
        assertEquals(hashCode1, hashCode2);
    }
}
