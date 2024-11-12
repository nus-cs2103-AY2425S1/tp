package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Interest;
import seedu.address.model.person.Major;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.University;
import seedu.address.model.person.WorkExp;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_WORKEXP = "Intern,Google,24";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_UNIVERSITY = "N@US";
    private static final String INVALID_MAJOR = "Eng#ineering*";
    private static final String INVALID_INTEREST = "Cycl!ng";
    private static final String INVALID_BIRTHDAY = "23fsdfsdf";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_WORKEXP = BENSON.getWorkExp().toString();
    private static final String VALID_UNIVERSITY = BENSON.getUniversity().toString();
    private static final String VALID_MAJOR = BENSON.getMajor().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final List<String> VALID_INTERESTS = BENSON.getInterests().stream()
            .map(Interest::toString)
            .collect(Collectors.toList());
    private static final LocalDate VALID_BIRTHDAY = BENSON.getBirthday().value;
    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_WORKEXP, VALID_TAGS,
                        VALID_UNIVERSITY, VALID_MAJOR, VALID_INTERESTS, VALID_BIRTHDAY);

        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_WORKEXP,
                VALID_TAGS, VALID_UNIVERSITY, VALID_MAJOR, VALID_INTERESTS, VALID_BIRTHDAY);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_WORKEXP, VALID_TAGS,
                        VALID_UNIVERSITY, VALID_MAJOR, VALID_INTERESTS, VALID_BIRTHDAY);

        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS,
                VALID_WORKEXP, VALID_TAGS, VALID_UNIVERSITY, VALID_MAJOR, VALID_INTERESTS, VALID_BIRTHDAY);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_WORKEXP, VALID_TAGS,
                        VALID_UNIVERSITY, VALID_MAJOR, VALID_INTERESTS, VALID_BIRTHDAY);

        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS,
                VALID_WORKEXP, VALID_TAGS, VALID_UNIVERSITY, VALID_MAJOR, VALID_INTERESTS, VALID_BIRTHDAY);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_WORKEXP, VALID_TAGS,
                        VALID_UNIVERSITY, VALID_MAJOR, VALID_INTERESTS, VALID_BIRTHDAY);

        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                VALID_WORKEXP, VALID_TAGS, VALID_UNIVERSITY, VALID_MAJOR, VALID_INTERESTS, VALID_BIRTHDAY);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidWorkExp_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        INVALID_WORKEXP, VALID_TAGS, VALID_UNIVERSITY, VALID_MAJOR, VALID_INTERESTS, VALID_BIRTHDAY);
        String expectedMessage = WorkExp.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullWorkExp_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                null, VALID_TAGS, VALID_UNIVERSITY, VALID_MAJOR, VALID_INTERESTS, VALID_BIRTHDAY);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, WorkExp.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_WORKEXP, invalidTags,
                        VALID_UNIVERSITY, VALID_MAJOR, VALID_INTERESTS, VALID_BIRTHDAY);

        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_invalidUniversity_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_WORKEXP, VALID_TAGS, INVALID_UNIVERSITY, VALID_MAJOR, VALID_INTERESTS, VALID_BIRTHDAY);
        String expectedMessage = University.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullUniversity_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_WORKEXP, VALID_TAGS, null, VALID_MAJOR, VALID_INTERESTS, VALID_BIRTHDAY);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, University.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidMajor_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_WORKEXP, VALID_TAGS, VALID_UNIVERSITY, INVALID_MAJOR, VALID_INTERESTS, VALID_BIRTHDAY);
        String expectedMessage = Major.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullMajor_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_WORKEXP, VALID_TAGS, VALID_UNIVERSITY, null, VALID_INTERESTS, VALID_BIRTHDAY);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Major.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }
    @Test
    public void toModelType_invalidInterests_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_WORKEXP, VALID_TAGS, VALID_UNIVERSITY, VALID_MAJOR,
                Collections.singletonList(INVALID_INTEREST), VALID_BIRTHDAY);

        String expectedMessage = Interest.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }


    @Test
    public void toModelType_nullInterests_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_WORKEXP, VALID_TAGS, VALID_UNIVERSITY, VALID_MAJOR,
                Collections.singletonList(null), VALID_BIRTHDAY);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Interest.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }
    @Test
    public void toModelType_multipleValidInterests_returnsPerson() throws Exception {
        List<String> validInterests = List.of("Reading", "Cycling", "Hiking");
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_WORKEXP, VALID_TAGS, VALID_UNIVERSITY, VALID_MAJOR, validInterests, VALID_BIRTHDAY);

        Person expectedPerson = new Person(new Name(VALID_NAME), new Phone(VALID_PHONE), new Email(VALID_EMAIL),
                new Address(VALID_ADDRESS), new WorkExp(VALID_WORKEXP),
                BENSON.getTags(), new University(VALID_UNIVERSITY), new Major(VALID_MAJOR),
                validInterests.stream().map(Interest::new).collect(Collectors.toSet()),
                new Birthday(VALID_BIRTHDAY.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));

        assertEquals(expectedPerson, person.toModelType());
    }
    @Test
    public void toModelType_emptyInterests_returnsPerson() throws Exception {
        List<String> emptyInterests = Collections.emptyList();
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_WORKEXP, VALID_TAGS, VALID_UNIVERSITY, VALID_MAJOR, emptyInterests, VALID_BIRTHDAY);

        Person expectedPerson = new Person(new Name(VALID_NAME), new Phone(VALID_PHONE), new Email(VALID_EMAIL),
                new Address(VALID_ADDRESS), new WorkExp(VALID_WORKEXP),
                BENSON.getTags(), new University(VALID_UNIVERSITY), new Major(VALID_MAJOR),
                Collections.emptySet(), new Birthday(VALID_BIRTHDAY.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));

        assertEquals(expectedPerson, person.toModelType());
    }
    @Test
    public void toModelType_duplicateInterests_returnsPersonWithUniqueInterests() throws Exception {
        List<String> duplicateInterests = List.of("Reading", "Reading");
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_WORKEXP, VALID_TAGS, VALID_UNIVERSITY, VALID_MAJOR, duplicateInterests, VALID_BIRTHDAY);

        Person expectedPerson = new Person(new Name(VALID_NAME), new Phone(VALID_PHONE), new Email(VALID_EMAIL),
                new Address(VALID_ADDRESS), new WorkExp(VALID_WORKEXP),
                BENSON.getTags(), new University(VALID_UNIVERSITY), new Major(VALID_MAJOR),
                Set.of(new Interest("Reading")),
                new Birthday(VALID_BIRTHDAY.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));

        assertEquals(expectedPerson, person.toModelType());
    }
    @Test
    public void toModelType_mixedValidAndInvalidInterests_throwsIllegalValueException() {
        List<String> mixedInterests = List.of("Reading", INVALID_INTEREST);
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_WORKEXP, VALID_TAGS, VALID_UNIVERSITY, VALID_MAJOR, mixedInterests, VALID_BIRTHDAY);

        String expectedMessage = Interest.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }





}
