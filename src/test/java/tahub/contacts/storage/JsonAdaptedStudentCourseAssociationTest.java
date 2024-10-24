package tahub.contacts.storage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static tahub.contacts.testutil.TypicalPersons.BENSON;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import tahub.contacts.commons.exceptions.IllegalValueException;
import tahub.contacts.model.ReadOnlyAddressBook;
import tahub.contacts.model.course.Course;
import tahub.contacts.model.course.CourseCode;
import tahub.contacts.model.course.CourseName;
import tahub.contacts.model.course.UniqueCourseList;
import tahub.contacts.model.grade.GradingSystem;
import tahub.contacts.model.person.Person;
import tahub.contacts.model.studentcourseassociation.StudentCourseAssociation;
import tahub.contacts.testutil.AttendanceExamples;

public class JsonAdaptedStudentCourseAssociationTest {

    private static final String INVALID_MATRICULATION_NUMBER = "A00";
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_MATRICULATION_NUMBER = BENSON.getMatricNumber().toString();
    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    private static final String VALID_COURSE_CODE = "CS3233";
    private static final String VALID_COURSE_NAME = "Competitive Programming";
    private static final String INVALID_COURSE_CODE = "PPPPP";
    private static final String INVALID_COURSE_NAME = "Fam|ly G^Y";

    private static final String VALID_TUTORIAL_ID = "T22";
    private static final String INVALID_TUTORIAL_ID = "XMAS";

    private static final JsonAdaptedAttendance VALID_ATTENDANCE = new JsonAdaptedAttendance(
            AttendanceExamples.ATTENDANCE_EXAMPLE_1);

    private ReadOnlyAddressBook stubAddressBook;
    private UniqueCourseList stubCourseList;
    private Person validPerson;
    private Course validCourse;

    @BeforeEach
    void setUp() {
        validPerson = BENSON;
        validCourse = new Course(new CourseCode(VALID_COURSE_CODE), new CourseName(VALID_COURSE_NAME));

        stubAddressBook = new ReadOnlyAddressBook() {
            /**
             * Returns an unmodifiable view of the persons list.
             * This list will not contain any duplicate persons.
             */
            @Override
            public ObservableList<Person> getPersonList() {
                return (ObservableList<Person>) List.of(validPerson);
            }

            @Override
            public Person getPersonByMatricNumber(String matricNumber) {
                if (VALID_MATRICULATION_NUMBER.equals(matricNumber)) {
                    return validPerson;
                }
                return null;
            }
        };

        stubCourseList = new UniqueCourseList() {
            @Override
            public Course getCourseWithCourseCode(CourseCode courseCode) {
                if (VALID_COURSE_CODE.equals(courseCode.toString())) {
                    return validCourse;
                }
                return null;
            }

            @Override
            public ObservableList<Course> asUnmodifiableObservableList() {
                return (ObservableList<Course>) List.of(validCourse);
            }
        };
    }

    @Test
    void toModelType_validStudentCourseAssociation_noExceptionThrown() {
        JsonAdaptedStudentCourseAssociation adapter = new JsonAdaptedStudentCourseAssociation(
                VALID_MATRICULATION_NUMBER, VALID_COURSE_CODE, new JsonAdaptedTutorial(VALID_TUTORIAL_ID,
                    new JsonAdaptedCourse(VALID_COURSE_CODE, VALID_COURSE_NAME)), VALID_ATTENDANCE);
        assertDoesNotThrow(() -> adapter.toModelType(stubAddressBook, stubCourseList));
    }

    @Test
    void toModelType_invalidStudent_throwsIllegalValueException() {
        JsonAdaptedStudentCourseAssociation adapter = new JsonAdaptedStudentCourseAssociation(
                INVALID_MATRICULATION_NUMBER, VALID_COURSE_CODE, new JsonAdaptedTutorial(VALID_TUTORIAL_ID,
                    new JsonAdaptedCourse(VALID_COURSE_CODE, VALID_COURSE_NAME)), VALID_ATTENDANCE);
        assertThrows(IllegalValueException.class, () -> adapter.toModelType(stubAddressBook, stubCourseList));
    }

    @Test
    void toModelType_invalidCourse_throwsIllegalValueException() {
        JsonAdaptedStudentCourseAssociation adapter = new JsonAdaptedStudentCourseAssociation(
                VALID_MATRICULATION_NUMBER, INVALID_COURSE_CODE, new JsonAdaptedTutorial(VALID_TUTORIAL_ID,
                    new JsonAdaptedCourse(INVALID_COURSE_CODE, VALID_COURSE_NAME)), VALID_ATTENDANCE);
        assertThrows(IllegalValueException.class, () -> adapter.toModelType(stubAddressBook, stubCourseList));
    }

    @Test
    void toModelType_invalidTutorial_throwsIllegalValueException() {
        JsonAdaptedStudentCourseAssociation adapter = new JsonAdaptedStudentCourseAssociation(
                VALID_MATRICULATION_NUMBER, VALID_COURSE_CODE, new JsonAdaptedTutorial(INVALID_TUTORIAL_ID,
                    new JsonAdaptedCourse(VALID_COURSE_CODE, VALID_COURSE_NAME)), VALID_ATTENDANCE);
        assertThrows(IllegalValueException.class, () -> adapter.toModelType(stubAddressBook, stubCourseList));
    }

    @Test
    void testConstructor() throws IllegalValueException {
        JsonAdaptedPerson validPerson = new JsonAdaptedPerson(VALID_MATRICULATION_NUMBER, VALID_NAME, VALID_PHONE,
                VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        JsonAdaptedCourse validCourse = new JsonAdaptedCourse(VALID_COURSE_CODE, VALID_COURSE_NAME);
        JsonAdaptedTutorial validTutorial = new JsonAdaptedTutorial(VALID_TUTORIAL_ID, validCourse);

        StudentCourseAssociation sca = new StudentCourseAssociation(validPerson.toModelType(),
                validCourse.toModelType(), validTutorial.toModelType(), new GradingSystem(),
                VALID_ATTENDANCE.toModelType());
        JsonAdaptedStudentCourseAssociation adaptedSca = new JsonAdaptedStudentCourseAssociation(sca);
        assertEquals(sca, adaptedSca.toModelType(stubAddressBook, stubCourseList));
    }

    @Test
    void toModelType_nullInputs_throwsIllegalValueException() {
        assertThrows(IllegalValueException.class, () -> {
            new JsonAdaptedStudentCourseAssociation(null, null, null, null)
                    .toModelType(stubAddressBook, stubCourseList);
        });
    }

    @Test
    void toModelType_validInputs_returnStudentCourseAssociation() throws IllegalValueException {
        JsonAdaptedPerson validPerson = new JsonAdaptedPerson(VALID_MATRICULATION_NUMBER, VALID_NAME, VALID_PHONE,
                VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        JsonAdaptedCourse validCourse = new JsonAdaptedCourse(VALID_COURSE_CODE, VALID_COURSE_NAME);
        JsonAdaptedTutorial validTutorial = new JsonAdaptedTutorial(VALID_TUTORIAL_ID, validCourse);
        // valid inputs so should not throw exception
        JsonAdaptedStudentCourseAssociation adapter = new JsonAdaptedStudentCourseAssociation(
                String.valueOf(validPerson.toModelType().getMatricNumber()),
                String.valueOf(validCourse.toModelType().courseCode), validTutorial, VALID_ATTENDANCE);
        assertDoesNotThrow(() -> {
            StudentCourseAssociation sca = adapter.toModelType(stubAddressBook, stubCourseList);
            assertEquals(VALID_MATRICULATION_NUMBER, sca.getStudent().getMatricNumber().toString());
            assertEquals(VALID_COURSE_CODE, sca.getCourse().courseCode.toString());
            assertEquals(VALID_TUTORIAL_ID, sca.getTutorial().getTutorialId());
        });
    }

    @Test
    void toModelType_nullTutorial_throwsIllegalValueException() throws IllegalValueException {
        JsonAdaptedPerson validPerson = new JsonAdaptedPerson(VALID_MATRICULATION_NUMBER, VALID_NAME, VALID_PHONE,
                VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        JsonAdaptedCourse validCourse = new JsonAdaptedCourse(VALID_COURSE_CODE, VALID_COURSE_NAME);
        JsonAdaptedStudentCourseAssociation adapter = new JsonAdaptedStudentCourseAssociation(
                String.valueOf(validPerson.toModelType().getMatricNumber()),
                String.valueOf(validCourse.toModelType().courseCode), null, VALID_ATTENDANCE);
        assertThrows(IllegalValueException.class, () -> adapter.toModelType(stubAddressBook, stubCourseList));
    }

    @Test
    void toModelType_nullCourseAndTutorial_throwsIllegalValueException() throws IllegalValueException {
        JsonAdaptedPerson validPerson = new JsonAdaptedPerson(VALID_MATRICULATION_NUMBER, VALID_NAME, VALID_PHONE,
                VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);

        JsonAdaptedStudentCourseAssociation adapter = new JsonAdaptedStudentCourseAssociation(
                String.valueOf(validPerson.toModelType().getMatricNumber()),
                INVALID_COURSE_CODE, null, VALID_ATTENDANCE);
        assertThrows(IllegalValueException.class, () -> adapter.toModelType(stubAddressBook, stubCourseList));
    }

    @Test
    void toModelType_invalidStudentAllFields_throwsIllegalValueException() throws IllegalValueException {
        JsonAdaptedCourse validCourse = new JsonAdaptedCourse(VALID_COURSE_CODE, VALID_COURSE_NAME);
        JsonAdaptedTutorial validTutorial = new JsonAdaptedTutorial(VALID_TUTORIAL_ID, validCourse);

        JsonAdaptedStudentCourseAssociation adapter = new JsonAdaptedStudentCourseAssociation(
                INVALID_MATRICULATION_NUMBER,
                String.valueOf(validCourse.toModelType().courseCode), validTutorial, VALID_ATTENDANCE);
        assertThrows(IllegalValueException.class, () -> adapter.toModelType(stubAddressBook, stubCourseList));
    }

    @Test
    void toModelType_nullAttendance_throwsIllegalValueException() throws IllegalValueException {
        JsonAdaptedPerson validPerson = new JsonAdaptedPerson(VALID_MATRICULATION_NUMBER, VALID_NAME, VALID_PHONE,
                VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        JsonAdaptedCourse validCourse = new JsonAdaptedCourse(VALID_COURSE_CODE, VALID_COURSE_NAME);
        JsonAdaptedTutorial validTutorial = new JsonAdaptedTutorial(VALID_TUTORIAL_ID, validCourse);

        JsonAdaptedStudentCourseAssociation adapter = new JsonAdaptedStudentCourseAssociation(
                String.valueOf(validPerson.toModelType().getMatricNumber()),
                String.valueOf(validCourse.toModelType().courseCode), validTutorial, null);
        assertThrows(IllegalValueException.class, () -> adapter.toModelType(stubAddressBook, stubCourseList));
    }
}
