package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.consultation.Date;
import seedu.address.model.consultation.Time;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.StudentLessonInfo;
import seedu.address.model.student.Student;
import seedu.address.testutil.StudentBuilder;

public class JsonAdaptedLessonTest {

    private static final String VALID_DATE = "2024-10-21";
    private static final String VALID_TIME = "10:00";
    private static final JsonAdaptedStudent VALID_STUDENT = new JsonAdaptedStudent(
            new StudentBuilder().withName("Alice Pauline").build());
    private static final List<JsonAdaptedStudentLessonInfo> VALID_STUDENT_INFO = List.of(
            new JsonAdaptedStudentLessonInfo(VALID_STUDENT, true, 1));
    private static final String INVALID_DATE = "invalid-date";
    private static final String INVALID_TIME = "invalid-time";

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(null, VALID_TIME, VALID_STUDENT_INFO);
        assertThrows(IllegalValueException.class, () -> lesson.toModelType(new AddressBook()),
                String.format(JsonAdaptedLesson.MISSING_FIELD_MESSAGE_FORMAT, "Date"));
    }

    @Test
    public void toModelType_invalidDateFormat_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(INVALID_DATE, VALID_TIME, VALID_STUDENT_INFO);
        assertThrows(IllegalValueException.class, () -> lesson.toModelType(new AddressBook()),
                Date.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void toModelType_nullTime_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(VALID_DATE, null, VALID_STUDENT_INFO);
        assertThrows(IllegalValueException.class, () -> lesson.toModelType(new AddressBook()),
                String.format(JsonAdaptedLesson.MISSING_FIELD_MESSAGE_FORMAT, "Time"));
    }

    @Test
    public void toModelType_invalidTimeFormat_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(VALID_DATE, INVALID_TIME, VALID_STUDENT_INFO);
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
                VALID_DATE, VALID_TIME, VALID_STUDENT_INFO);

        Lesson modelLesson = lesson.toModelType(addressBook);
        Lesson expectedLesson = new Lesson(new Date(VALID_DATE), new Time(VALID_TIME),
                List.of(new StudentLessonInfo(student, true, 1)));

        assertEquals(expectedLesson, modelLesson);
    }

    @Test
    public void jsonAdaptedLesson_fromLesson_success() {
        // Create a Lesson instance
        Student student = new StudentBuilder().withName("Alice Pauline").build();
        Lesson lesson = new Lesson(new Date(VALID_DATE), new Time(VALID_TIME),
                List.of(new StudentLessonInfo(student, false, 0)));

        // Convert the Lesson to JsonAdaptedLesson
        JsonAdaptedLesson jsonAdaptedLesson = new JsonAdaptedLesson(lesson);
        JsonAdaptedLesson expected = new JsonAdaptedLesson(VALID_DATE, VALID_TIME,
                List.of(new JsonAdaptedStudentLessonInfo(new JsonAdaptedStudent(student),
                        false, 0)));

        // Verify the converted values
        assertEquals(jsonAdaptedLesson, expected);
    }
}
