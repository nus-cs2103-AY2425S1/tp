package tahub.contacts.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import tahub.contacts.commons.exceptions.IllegalValueException;
import tahub.contacts.model.course.Course;
import tahub.contacts.model.course.UniqueCourseList;

/**
 * An Immutable UniqueCourseList that is serializable to JSON format.
 */
@JsonRootName(value = "courselist")
class JsonSerializableCourseList {

    public static final String MESSAGE_DUPLICATE_PERSON = "Courses list contains duplicate course(s).";

    private final List<JsonAdaptedCourse> courses = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableUniqueCourseList} with the given courses.
     */
    @JsonCreator
    public JsonSerializableCourseList(@JsonProperty("courses") List<JsonAdaptedCourse> courses) {
        this.courses.addAll(courses);
    }

    /**
     * Converts a given {@code UniqueCourseList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableUniqueCourseList}.
     */
    public JsonSerializableCourseList(UniqueCourseList source) {
        courses.addAll(source.getCourseList().stream().map(JsonAdaptedCourse::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code UniqueCourseList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public UniqueCourseList toModelType() throws IllegalValueException {
        UniqueCourseList courseList = new UniqueCourseList();
        for (JsonAdaptedCourse jsonAdaptedCourse : courses) {
            Course course = jsonAdaptedCourse.toModelType();
            if (courseList.hasCourse(course)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            courseList.addCourse(course);
        }
        return courseList;
    }

}
