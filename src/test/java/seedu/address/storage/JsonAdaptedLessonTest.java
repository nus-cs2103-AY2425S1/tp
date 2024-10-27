package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.consultation.Date;
import seedu.address.model.consultation.Time;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.student.Student;
import seedu.address.testutil.StudentBuilder;

public class JsonAdaptedLessonTest {

    private static final String VALID_DATE = "2024-10-21";
    private static final String VALID_TIME = "10:00";
    private static final List<JsonAdaptedStudent> VALID_STUDENTS = Collections.singletonList(
            new JsonAdaptedStudent(new StudentBuilder().withName("Alice Pauline").build()));
    private static final Map<JsonAdaptedStudent, Boolean> VALID_MAP = Collections.singletonMap(
            new JsonAdaptedStudent(new StudentBuilder().withName("Alice Pauline").build()), false
    );
    private static final String INVALID_DATE = "invalid-date";
    private static final String INVALID_TIME = "invalid-time";

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(null, VALID_TIME, VALID_STUDENTS, VALID_MAP);
        assertThrows(IllegalValueException.class, () -> lesson.toModelType(new AddressBook()),
                String.format(JsonAdaptedLesson.MISSING_FIELD_MESSAGE_FORMAT, "Date"));
    }

    @Test
    public void toModelType_invalidDateFormat_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(INVALID_DATE, VALID_TIME, VALID_STUDENTS, VALID_MAP);
        assertThrows(IllegalValueException.class, () -> lesson.toModelType(new AddressBook()),
                Date.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void toModelType_nullTime_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(VALID_DATE, null, VALID_STUDENTS, VALID_MAP);
        assertThrows(IllegalValueException.class, () -> lesson.toModelType(new AddressBook()),
                String.format(JsonAdaptedLesson.MISSING_FIELD_MESSAGE_FORMAT, "Time"));
    }

    @Test
    public void toModelType_invalidTimeFormat_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(VALID_DATE, INVALID_TIME, VALID_STUDENTS, VALID_MAP);
        assertThrows(IllegalValueException.class, () -> lesson.toModelType(new AddressBook()),
                Time.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void toModelType_validLesson_returnsLesson() throws Exception {
        // Create a student that is in the AddressBook
        Student student = new StudentBuilder().withName("Alice Pauline").build();
        AddressBook addressBook = new AddressBook();
        addressBook.addStudent(student);

        JsonAdaptedStudent jsonStudent = new JsonAdaptedStudent(student);
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(
                VALID_DATE, VALID_TIME, Arrays.asList(jsonStudent), Collections.singletonMap(jsonStudent, false));

        Lesson modelLesson = lesson.toModelType(addressBook);
        Lesson expectedLesson = new Lesson(new Date(VALID_DATE), new Time(VALID_TIME),
                Arrays.asList(student), Collections.singletonMap(student, false));

        assertEquals(expectedLesson, modelLesson);
    }

    @Test
    public void jsonAdaptedLesson_fromLesson_success() {
        // Create a Lesson instance
        Student student = new StudentBuilder().withName("Alice Pauline").build();
        Lesson lesson = new Lesson(new Date(VALID_DATE), new Time(VALID_TIME),
                Arrays.asList(student), Collections.singletonMap(student, false));

        // Convert the Lesson to JsonAdaptedLesson
        JsonAdaptedLesson jsonAdaptedLesson = new JsonAdaptedLesson(lesson);

        // Verify the converted values
        assertEquals(VALID_DATE, jsonAdaptedLesson.getDate());
        assertEquals(VALID_TIME, jsonAdaptedLesson.getTime());
        assertEquals(1, jsonAdaptedLesson.getStudents().size());
        assertEquals(student.getName().fullName, jsonAdaptedLesson.getStudents().get(0).getName());
    }
}
