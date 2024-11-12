package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.CLIVE;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Event;
import seedu.address.model.task.Todo;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final List<WeddingName> VALID_WEDDINGS = BENSON.getWeddings().stream()
            .map(Wedding::getWeddingName)
            .collect(Collectors.toList());

    private static final List<JsonAdaptedTask> VALID_TASKS = CARL.getTasks().stream()
            .map(task -> {
                if (task instanceof Todo) {
                    return new JsonAdaptedTodo((Todo) task);
                } else if (task instanceof Deadline) {
                    return new JsonAdaptedDeadline((Deadline) task);
                } else if (task instanceof Event) {
                    return new JsonAdaptedEvent((Event) task);
                } else {
                    throw new IllegalArgumentException("Unknown task type");
                }
            })
            .collect(Collectors.toList());

    private static final String BLANK_ADDRESS = "";
    private static final String CLIVE_NAME = CLIVE.getName().toString();
    private static final String CLIVE_PHONE = CLIVE.getPhone().toString();
    private static final String CLIVE_EMAIL = CLIVE.getEmail().toString();
    private static final List<JsonAdaptedTag> CLIVE_TAGS = CLIVE.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final List<WeddingName> CLIVE_WEDDINGS = CLIVE.getWeddings().stream()
            .map(Wedding::getWeddingName)
            .collect(Collectors.toList());

    private static final List<JsonAdaptedTask> CLIVE_TASKS = CLIVE.getTasks().stream()
            .map(task -> {
                if (task instanceof Todo) {
                    return new JsonAdaptedTodo((Todo) task);
                } else if (task instanceof Deadline) {
                    return new JsonAdaptedDeadline((Deadline) task);
                } else if (task instanceof Event) {
                    return new JsonAdaptedEvent((Event) task);
                } else {
                    throw new IllegalArgumentException("Unknown task type");
                }
            })
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                        VALID_WEDDINGS, VALID_TASKS, true);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, VALID_WEDDINGS, VALID_TASKS, true);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                        VALID_WEDDINGS, VALID_TASKS, true);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, VALID_WEDDINGS, VALID_TASKS, true);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                        VALID_WEDDINGS, VALID_TASKS, true);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS, VALID_TAGS,
                VALID_WEDDINGS, VALID_TASKS, true);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_blankAddress_returnsPerson() throws IllegalValueException {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(CLIVE_NAME, CLIVE_PHONE, CLIVE_EMAIL, BLANK_ADDRESS,
                        CLIVE_TAGS, CLIVE_WEDDINGS, CLIVE_TASKS, true);
        assertEquals(CLIVE, person.toModelType());
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_TAGS,
                VALID_WEDDINGS, CLIVE_TASKS, true);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, invalidTags,
                        VALID_WEDDINGS, CLIVE_TASKS, true);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

}
