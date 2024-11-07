package tahub.contacts.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tahub.contacts.commons.exceptions.IllegalValueException;
import tahub.contacts.model.course.Course;
import tahub.contacts.model.course.CourseCode;
import tahub.contacts.model.course.CourseName;

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
        courseCode = source.courseCode.courseCode;
        courseName = source.courseName.courseName;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Course} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Course toModelType() throws IllegalValueException {
        if (!CourseCode.isValidCourseCode(courseCode)) {
            throw new IllegalValueException(CourseCode.MESSAGE_CONSTRAINTS);
        }
        if (!CourseName.isValidCourseName(courseName)) {
            throw new IllegalValueException(CourseName.MESSAGE_CONSTRAINTS);
        }
        return new Course(new CourseCode(courseCode), new CourseName(courseName));
    }
}
