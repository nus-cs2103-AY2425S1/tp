package keycontacts.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import keycontacts.commons.exceptions.IllegalValueException;
import keycontacts.model.ReadOnlyStudentDirectory;
import keycontacts.model.StudentDirectory;
import keycontacts.model.student.Student;

/**
 * An Immutable StudentDirectory that is serializable to JSON format.
 */
@JsonRootName(value = "studentdirectory")
class JsonSerializableStudentDirectory {

    public static final String MESSAGE_DUPLICATE_STUDENT = "Students list contains duplicate student(s).";
    public static final String MESSAGE_CLASHING_LESSONS = "Students list contains clashing lesson(s).";

    private final List<JsonAdaptedStudent> students = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableStudentDirectory} with the given students.
     */
    @JsonCreator
    public JsonSerializableStudentDirectory(@JsonProperty("students") List<JsonAdaptedStudent> students) {
        this.students.addAll(students);
    }

    /**
     * Converts a given {@code ReadOnlyStudentDirectory} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableStudentDirectory}.
     */
    public JsonSerializableStudentDirectory(ReadOnlyStudentDirectory source) {
        students.addAll(source.getStudentList().stream().map(JsonAdaptedStudent::new).collect(Collectors.toList()));
    }

    /**
     * Converts this student directory into the model's {@code StudentDirectory} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public StudentDirectory toModelType() throws IllegalValueException {
        StudentDirectory studentDirectory = new StudentDirectory();
        for (JsonAdaptedStudent jsonAdaptedStudent : students) {
            Student student = jsonAdaptedStudent.toModelType();
            if (studentDirectory.hasStudent(student)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_STUDENT);
            }

            studentDirectory.addStudent(student);
        }

        if (studentDirectory.hasClashingLessons()) {
            throw new IllegalValueException(MESSAGE_CLASHING_LESSONS);
        }

        return studentDirectory;
    }

}
