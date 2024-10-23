package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BETTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_ANDY;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ANDY;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BETTY;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.testutil.PersonBuilder;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_NAME2 = BETTY.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().map(Object :: toString).orElse(null);;
    private static final String VALID_EMAIL = BENSON.getEmail().map(Object :: toString).orElse(null);
    private static final String VALID_ADDRESS = BENSON.getAddress().map(Object :: toString).orElse(null);
    private static final JsonAdaptedModuleRoleMap VALID_EMPTY_MODULE_ROLE_MAP =
            new JsonAdaptedModuleRoleMap(ANDY.getModuleRoleMap());
    private static final JsonAdaptedModuleRoleMap VALID_MODULE_ROLE_MAP =
            new JsonAdaptedModuleRoleMap(BETTY.getModuleRoleMap());
    private static final List<JsonAdaptedTag> VALID_TAGS_ANDY = ANDY.getTags().stream()
            .map(JsonAdaptedTag :: new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag :: new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_nullPhone_returnPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME_BETTY, null, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, VALID_MODULE_ROLE_MAP);
        Person betty = new PersonBuilder(BETTY).withAddress(VALID_ADDRESS).withEmptyPhone().build();
        assertEquals(betty, person.toModelType());
    }

    @Test
    public void toModelType_nullEmail_returnPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME_BETTY, VALID_PHONE, null, VALID_ADDRESS,
                VALID_TAGS, VALID_MODULE_ROLE_MAP);
        Person betty = new PersonBuilder(BETTY).withAddress(VALID_ADDRESS).withEmptyEmail().build();
        assertEquals(betty, person.toModelType());
    }

    @Test
    public void toModelType_nullAddress_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME2, VALID_PHONE, VALID_EMAIL, null,
                VALID_TAGS, VALID_MODULE_ROLE_MAP);
        assertEquals(BETTY, person.toModelType());
    }

    @Test
    public void toModelType_emptyModuleRoleMap_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME_ANDY, VALID_PHONE_ANDY, VALID_EMAIL_ANDY,
                VALID_ADDRESS_ANDY, VALID_TAGS_ANDY, VALID_EMPTY_MODULE_ROLE_MAP);
        assertEquals(ANDY, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                        VALID_EMPTY_MODULE_ROLE_MAP);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person :: toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, VALID_EMPTY_MODULE_ROLE_MAP);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person :: toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                        VALID_EMPTY_MODULE_ROLE_MAP);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person :: toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS,
                        VALID_TAGS, VALID_EMPTY_MODULE_ROLE_MAP);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person :: toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS,
                        VALID_TAGS, VALID_EMPTY_MODULE_ROLE_MAP);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person :: toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, invalidTags,
                        VALID_EMPTY_MODULE_ROLE_MAP);
        assertThrows(IllegalValueException.class, person :: toModelType);
    }

    @Test
    public void toModelType_invalidRoleTypeInModuleRoleMap_throwsIllegalValueException() {
        HashMap<String, String> invalidMap = new HashMap<>();
        invalidMap.put("CS1101S", "invalid string");
        JsonAdaptedModuleRoleMap invalidJsonMap = new JsonAdaptedModuleRoleMap(invalidMap);
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                        invalidJsonMap);
        assertThrows(IllegalValueException.class, person :: toModelType);
    }
}
