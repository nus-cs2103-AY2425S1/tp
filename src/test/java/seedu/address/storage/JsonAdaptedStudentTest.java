package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedStudent.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudents.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.student.Address;
import seedu.address.model.student.EmergencyContact;
import seedu.address.model.student.Level;
import seedu.address.model.student.Name;
import seedu.address.model.student.Note;
import seedu.address.model.student.Phone;

public class JsonAdaptedStudentTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMERGENCY_CONTACT = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_SUBJECT = "#friend";
    private static final String INVALID_LEVEL = "JC4";
    private static final String INVALID_TASK_DESCRIPTION = " ";
    private static final String INVALID_TASK_DEADLINE = "tomorrow";
    private static final String INVALID_LESSON_TIME = "every thurs";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMERGENCY_CONTACT = BENSON.getEmergencyContact().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_NOTE = BENSON.getNote().toString();
    private static final String VALID_LEVEL = BENSON.getLevel().toString();
    private static final List<JsonAdaptedSubject> VALID_SUBJECTS = BENSON.getSubjects().stream()
            .map(JsonAdaptedSubject::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedTask> VALID_TASKLIST = BENSON.getTaskList().getjsonAdaptedTaskList();
    private static final List<JsonAdaptedLessonTime> VALID_LESSON_TIMES = BENSON.getLessonTimes().stream()
            .map(JsonAdaptedLessonTime::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validStudentDetails_returnsStudent() throws Exception {
        JsonAdaptedStudent student = new JsonAdaptedStudent(BENSON);
        assertEquals(BENSON, student.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(INVALID_NAME, VALID_PHONE, VALID_EMERGENCY_CONTACT,
                        VALID_ADDRESS, VALID_NOTE, VALID_SUBJECTS, VALID_LEVEL, VALID_TASKLIST, VALID_LESSON_TIMES);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(null, VALID_PHONE, VALID_EMERGENCY_CONTACT,
                        VALID_ADDRESS, VALID_NOTE, VALID_SUBJECTS, VALID_LEVEL, VALID_TASKLIST, VALID_LESSON_TIMES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, INVALID_PHONE, VALID_EMERGENCY_CONTACT,
                        VALID_ADDRESS, VALID_NOTE, VALID_SUBJECTS, VALID_LEVEL, VALID_TASKLIST, VALID_LESSON_TIMES);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidEmergencyContact_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, INVALID_EMERGENCY_CONTACT,
                        VALID_ADDRESS, VALID_NOTE, VALID_SUBJECTS, VALID_LEVEL, VALID_TASKLIST, VALID_LESSON_TIMES);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, null, VALID_EMERGENCY_CONTACT,
                        VALID_ADDRESS, VALID_NOTE, VALID_SUBJECTS, VALID_LEVEL, VALID_TASKLIST, VALID_LESSON_TIMES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullEmergencyContact_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, null,
                        VALID_ADDRESS, VALID_NOTE, VALID_SUBJECTS, VALID_LEVEL, VALID_TASKLIST, VALID_LESSON_TIMES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EmergencyContact.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMERGENCY_CONTACT,
                        INVALID_ADDRESS, VALID_NOTE, VALID_SUBJECTS, VALID_LEVEL, VALID_TASKLIST, VALID_LESSON_TIMES);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMERGENCY_CONTACT,
                        null, VALID_NOTE, VALID_SUBJECTS, VALID_LEVEL, VALID_TASKLIST, VALID_LESSON_TIMES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidSubjects_throwsIllegalValueException() {
        List<JsonAdaptedSubject> invalidSubjects = new ArrayList<>(VALID_SUBJECTS);
        invalidSubjects.add(new JsonAdaptedSubject(INVALID_SUBJECT));
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMERGENCY_CONTACT,
                        VALID_ADDRESS, VALID_NOTE, invalidSubjects, VALID_LEVEL, VALID_TASKLIST, VALID_LESSON_TIMES);
        assertThrows(IllegalValueException.class, student::toModelType);
    }

    @Test
    public void toModelType_invalidLevel_throwsIllegalArgumentException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMERGENCY_CONTACT,
                        VALID_ADDRESS, VALID_NOTE, VALID_SUBJECTS, INVALID_LEVEL, VALID_TASKLIST, VALID_LESSON_TIMES);
        String expectedMessage = Level.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }
    @Test
    public void toModelType_nullNote_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMERGENCY_CONTACT,
                        VALID_ADDRESS, null, VALID_SUBJECTS, VALID_LEVEL, VALID_TASKLIST, VALID_LESSON_TIMES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Note.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidTaskList_throwsIllegalValueException() {
        List<JsonAdaptedTask> invalidTaskList = new ArrayList<>(VALID_TASKLIST);
        invalidTaskList.add(new JsonAdaptedTask(INVALID_TASK_DESCRIPTION, INVALID_TASK_DEADLINE));
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMERGENCY_CONTACT,
                        VALID_ADDRESS, VALID_NOTE, VALID_SUBJECTS, VALID_LEVEL, invalidTaskList, VALID_LESSON_TIMES);
        assertThrows(IllegalValueException.class, student::toModelType);
    }

    @Test
    public void toModelType_invalidLessonTimes_throwsIllegalValueException() {
        List<JsonAdaptedLessonTime> invalidLessonTimes = new ArrayList<>(VALID_LESSON_TIMES);
        invalidLessonTimes.add(new JsonAdaptedLessonTime(INVALID_LESSON_TIME));
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMERGENCY_CONTACT,
                        VALID_ADDRESS, VALID_NOTE, VALID_SUBJECTS, VALID_LEVEL, VALID_TASKLIST, invalidLessonTimes);
        assertThrows(IllegalValueException.class, student::toModelType);
    }
}
