package tahub.contacts.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tahub.contacts.commons.exceptions.IllegalValueException;
import tahub.contacts.model.course.Course;

/**
 * Jackson-friendly version of {@link Course}.
 */
public class JsonAdaptedCourse {
    private final String courseCode;
    private final String courseName;

    /**
     * Constructs a {@code JsonAdaptedCourse} with the given course details.
     */
    @JsonCreator
    public JsonAdaptedCourse(@JsonProperty("courseCode") String courseCode,
                             @JsonProperty("courseName") String courseName) {
        this.courseCode = courseCode;
        this.courseName = courseName;
    }

    /**
     * Converts a given {@code Course} into this class for Jackson use.
     */
    public JsonAdaptedCourse(Course source) {
        courseCode = source.courseCode;
        courseName = source.courseName;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Course toModelType() throws IllegalValueException {
        if (!Course.isValidCourseCode(courseCode)) {
            throw new IllegalValueException(Course.COURSE_CODE_MESSAGE_CONSTRAINTS);
        }
        if (!Course.isValidCourseName(courseName)) {
            throw new IllegalValueException(Course.COURSE_NAME_MESSAGE_CONSTRAINTS);
        }
        return new Course(courseCode, courseName);
    }
}
