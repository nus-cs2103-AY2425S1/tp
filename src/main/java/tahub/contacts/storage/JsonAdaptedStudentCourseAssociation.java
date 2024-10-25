package tahub.contacts.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tahub.contacts.commons.exceptions.IllegalValueException;
import tahub.contacts.model.ReadOnlyAddressBook;
import tahub.contacts.model.course.Attendance;
import tahub.contacts.model.course.Course;
import tahub.contacts.model.course.CourseCode;
import tahub.contacts.model.course.UniqueCourseList;
import tahub.contacts.model.grade.GradingSystem;
import tahub.contacts.model.person.Person;
import tahub.contacts.model.studentcourseassociation.StudentCourseAssociation;
import tahub.contacts.model.tutorial.Tutorial;


/**
 * Jackson-friendly version of {@link StudentCourseAssociation}.
 */
class JsonAdaptedStudentCourseAssociation {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "StudentCourseAssociation's %s field is missing!";
    private final String matricNumber;
    private final String courseCode;
    private final JsonAdaptedTutorial tutorial;
    private final JsonAdaptedAttendance attendance;

    /**
     * Constructs a {@code JsonAdaptedStudentCourseAssociation} with the given
     * Student Course Association details.
     */
    @JsonCreator
    public JsonAdaptedStudentCourseAssociation(
            @JsonProperty("matricNumber") String matricNumber,
            @JsonProperty("courseCode") String courseCode,
            @JsonProperty("tutorial") JsonAdaptedTutorial tutorial,
            @JsonProperty("attendance") JsonAdaptedAttendance attendance) {
        this.matricNumber = matricNumber;
        this.courseCode = courseCode;
        this.tutorial = tutorial;
        this.attendance = attendance;
    }

    /**
     * Converts a given {@code StudentCourseAssociation} into this class for Jackson use.
     *
     */
    public JsonAdaptedStudentCourseAssociation(StudentCourseAssociation source) {
        this.matricNumber = String.valueOf(source.getStudent().getMatricNumber());
        this.courseCode = String.valueOf(source.getCourse().courseCode);
        this.tutorial = new JsonAdaptedTutorial(source.getTutorial());
        this.attendance = new JsonAdaptedAttendance(source.getAttendance());
    }

    /**
     * Converts the JsonAdaptedStudentCourseAssociation object into a StudentCourseAssociation object.
     * Validates all fields for null values before creating a new StudentCourseAssociation.
     * If the student/course/tutorials are invalid, the error is handled in their respective adapted classes.
     *
     * @return a new StudentCourseAssociation object representing the JSON data
     * @throws IllegalValueException if the student, course, or tutorial fields are invalid
     */
    public StudentCourseAssociation toModelType(ReadOnlyAddressBook addressBook,
                                                UniqueCourseList courseList) throws IllegalValueException {
        final Person student = addressBook.getPersonByMatricNumber(matricNumber);

        // Checks if the student is valid
        if (student == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    JsonAdaptedPerson.class.getSimpleName()));
        }

        if (!CourseCode.isValidCourseCode(courseCode)) {
            throw new IllegalValueException(CourseCode.MESSAGE_CONSTRAINTS);
        }

        final Course course = courseList.getCourseWithCourseCode(new CourseCode(courseCode));

        // Checks if the course is valid
        if (course == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    JsonAdaptedCourse.class.getSimpleName()));
        }

        // Checks if the tutorial is valid
        if (this.tutorial == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    JsonAdaptedTutorial.class.getSimpleName()));
        }
        final Tutorial tutorialModel = this.tutorial.toModelType();

        // Checks if the attendance is valid
        if (this.attendance == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    JsonAdaptedAttendance.class.getSimpleName()));
        }
        final Attendance attendanceModel = this.attendance.toModelType();

        return new StudentCourseAssociation(student, course, tutorialModel,
                new GradingSystem(), attendanceModel);
    }
}
