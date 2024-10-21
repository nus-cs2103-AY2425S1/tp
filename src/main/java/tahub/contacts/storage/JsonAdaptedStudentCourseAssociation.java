package tahub.contacts.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tahub.contacts.commons.exceptions.IllegalValueException;
import tahub.contacts.model.course.Course;
import tahub.contacts.model.person.Person;
import tahub.contacts.model.studentcourseassociation.StudentCourseAssociation;
import tahub.contacts.model.tutorial.Tutorial;



/**
 * Jackson-friendly version of {@link StudentCourseAssociation}.
 */
class JsonAdaptedStudentCourseAssociation {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "StudentCourseAssociation's %s field is missing!";
    private final JsonAdaptedPerson student;
    private final JsonAdaptedCourse course;
    private final JsonAdaptedTutorial tutorial;
    // private final GradingSystem grades;

    /**
     * Constructs a {@code JsonAdaptedStudentCourseAssociation} with the given {@code StudentClassAssociation}.
     */
    @JsonCreator
    public JsonAdaptedStudentCourseAssociation(
            @JsonProperty("student") JsonAdaptedPerson student,
            @JsonProperty("course") JsonAdaptedCourse course,
            @JsonProperty("tutorial") JsonAdaptedTutorial tutorial) {
        this.student = student;
        this.course = course;
        this.tutorial = tutorial;
        // this.grades = new GradingSystem();
    }


    /**
     * Converts a given {@code StudentCourseAssociation} into this class for Jackson use.
     */
    public JsonAdaptedStudentCourseAssociation(StudentCourseAssociation source) {
        this.student = new JsonAdaptedPerson(source.getStudent());
        this.course = new JsonAdaptedCourse(source.getCourse());
        this.tutorial = new JsonAdaptedTutorial(source.getTutorial());
        // this.grades = new GradingSystem();
    }

    /**
     * Converts the JsonAdaptedStudentCourseAssociation object into a StudentCourseAssociation object.
     * Validates the student, course, and tutorial fields before creating a new StudentCourseAssociation.
     *
     * @return a new StudentCourseAssociation object representing the JSON data
     * @throws IllegalValueException if the student, course, or tutorial fields are invalid
     */
    public StudentCourseAssociation toModelType() throws IllegalValueException {

        // Checks if the student is valid
        if (this.student == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    JsonAdaptedPerson.class.getSimpleName()));
        }
        final Person studentModel = this.student.toModelType();
        if (!Person.isValidPerson(studentModel)) {
            throw new IllegalValueException("The Person JSON object is invalid!");
        }

        // Checks if the course is valid
        if (this.course == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    JsonAdaptedCourse.class.getSimpleName()));
        }
        final Course courseModel = this.course.toModelType();
        if (!Course.isValidCourseCode(courseModel.courseCode)) {
            throw new IllegalValueException(Course.COURSE_CODE_MESSAGE_CONSTRAINTS);
        } else if (!Course.isValidCourseName(courseModel.courseName)) {
            throw new IllegalValueException(Course.COURSE_NAME_MESSAGE_CONSTRAINTS);
        }

        // Checks if the tutorial is valid
        if (this.tutorial == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    JsonAdaptedTutorial.class.getSimpleName()));
        }
        final Tutorial tutorialModel = this.tutorial.toModelType();
        if (!Tutorial.isValidTutorialId(tutorialModel.getTutorialId())) {
            throw new IllegalValueException(Tutorial.TUTORIAL_ID_MESSAGE_CONSTRAINTS);
        } else if (!Course.isValidCourseCode(tutorialModel.getCourse().courseCode)) {
            throw new IllegalValueException(Course.COURSE_CODE_MESSAGE_CONSTRAINTS);
        } else if (!Course.isValidCourseName(tutorialModel.getCourse().courseName)) {
            throw new IllegalValueException(Course.COURSE_NAME_MESSAGE_CONSTRAINTS);
        }

        return new StudentCourseAssociation(studentModel, courseModel, tutorialModel);
    }
}
