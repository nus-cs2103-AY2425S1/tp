package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.CLIVE;
import static seedu.address.testutil.TypicalVendors.BORIS;

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

public class JsonAdaptedVendorTest {

    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BORIS.getName().toString();
    private static final String VALID_PHONE = BORIS.getPhone().toString();
    private static final String VALID_EMAIL = BORIS.getEmail().toString();
    private static final String VALID_ADDRESS = BORIS.getAddress().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BORIS.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedWedding> VALID_WEDDINGS = BORIS.getWeddings().stream()
            .map(JsonAdaptedWedding::new)
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
    private static final List<JsonAdaptedWedding> CLIVE_WEDDINGS = CLIVE.getWeddings().stream()
            .map(JsonAdaptedWedding::new)
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
    public void toModelType_validVendorDetails_returnsVendor() throws Exception {
        JsonAdaptedVendor vendor = new JsonAdaptedVendor(BORIS);
        assertEquals(BORIS, vendor.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedVendor vendor =
                new JsonAdaptedVendor(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                        VALID_WEDDINGS, VALID_TASKS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, vendor::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedVendor vendor = new JsonAdaptedVendor(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, VALID_WEDDINGS, VALID_TASKS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, vendor::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedVendor vendor =
                new JsonAdaptedVendor(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                        VALID_WEDDINGS, VALID_TASKS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, vendor::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedVendor vendor = new JsonAdaptedVendor(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, VALID_WEDDINGS, VALID_TASKS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, vendor::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedVendor vendor =
                new JsonAdaptedVendor(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_TAGS,
                        VALID_WEDDINGS, VALID_TASKS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, vendor::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedVendor vendor = new JsonAdaptedVendor(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS, VALID_TAGS,
                VALID_WEDDINGS, VALID_TASKS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, vendor::toModelType);
    }

    @Test
    public void toModelType_blankAddress_returnsPerson() throws IllegalValueException {
        JsonAdaptedVendor vendor =
                new JsonAdaptedVendor(CLIVE_NAME, CLIVE_PHONE, CLIVE_EMAIL, BLANK_ADDRESS,
                        CLIVE_TAGS, CLIVE_WEDDINGS, CLIVE_TASKS);
        assertEquals(CLIVE, vendor.toModelType());
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedVendor vendor = new JsonAdaptedVendor(VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_TAGS,
                VALID_WEDDINGS, CLIVE_TASKS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, vendor::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedVendor person =
                new JsonAdaptedVendor(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, invalidTags,
                        VALID_WEDDINGS, CLIVE_TASKS);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    // to add, test cases for invalid Tasks once vendors are integrated with tasks

}
