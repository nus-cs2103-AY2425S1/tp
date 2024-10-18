package tahub.contacts.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tahub.contacts.commons.exceptions.IllegalValueException;
import tahub.contacts.model.course.Course;
import tahub.contacts.model.courseclass.recitation.Recitation;
import tahub.contacts.model.courseclass.tutorial.Tutorial;

/**
 * Represents a JSON-adapted Recitation object.
 */
class JsonAdaptedRecitation {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Recitation's %s field is missing!";
    private final String recitationId;
    private final Course course;

    /**
     * Constructs a JSON-adapted Recitation object.
     *
     * @param recitationId The ID of the recitation.
     * @param course The Course object associated with the recitation.
     */
    @JsonCreator
    public JsonAdaptedRecitation(@JsonProperty("recitationId") String recitationId,
                               @JsonProperty("course") Course course) {
        this.recitationId = recitationId;
        this.course = course;
    }

    /**
     * Constructs a JSON-adapted Recitation object based on the provided Recitation source.
     *
     * @param source the original Recitation object to be adapted
     */
    public JsonAdaptedRecitation(Recitation source) {
        recitationId = source.getRecitationId();
        course = source.getCourse();
    }

    /**
     * Converts the JSON-adapted Recitation object to a model Recitation object.
     *
     * @return A model Recitation object constructed from the JSON-adapted Recitation object.
     * @throws IllegalValueException If the conversion encounters any invalid fields or constraints during the process.
     */
    public Recitation toModelType() throws IllegalValueException {

        if (recitationId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Tutorial.class.getSimpleName()));
        }

        final String modelRecitationId = recitationId;

        if (course == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Course.class.getSimpleName()));
        }
        if (!Course.isValidCourseCode(course.courseCode)) {
            throw new IllegalValueException(Course.COURSE_CODE_MESSAGE_CONSTRAINTS);
        } else if (!Course.isValidCourseName(course.courseName)) {
            throw new IllegalValueException(Course.COURSE_NAME_MESSAGE_CONSTRAINTS);
        }
        final Course modelCourse = new Course(course.courseCode, course.courseName);

        return new Recitation(modelRecitationId, modelCourse);
    }
}

