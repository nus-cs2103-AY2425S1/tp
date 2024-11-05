package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.testutil.StudentBuilder;

public class JsonAdaptedStudentLessonInfoTest {
    private static final JsonAdaptedStudent VALID_STUDENT = new JsonAdaptedStudent(
            new StudentBuilder().withName("Alice Pauline").build());
    private static final JsonAdaptedStudentLessonInfo VALID_INFO = new JsonAdaptedStudentLessonInfo(
            VALID_STUDENT, true, 1);

    @Test
    public void toModelType_studentNotInAddressBook_throwsIllegalValueException() {
        AddressBook emptyAddressBook = new AddressBook();
        assertThrows(IllegalValueException.class, () -> VALID_INFO.toModelType(emptyAddressBook));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        assertEquals(VALID_INFO, VALID_INFO);
    }

    @Test
    public void equals_differentType_returnsFalse() {
        assertNotEquals("123", VALID_INFO);
    }
}
