package seedu.address.storage;

import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.student.Name;
import seedu.address.model.student.PresentDates;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentId;
import seedu.address.model.student.TutorialId;

/**
 * Jackson-friendly version of {@link Student}.
 */
class JsonAdaptedStudent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Student's %s field is missing!";

    private final String name;
    private final String studentId;
    private final String tutorialId;
    private final JsonAdaptedPresentDates presentDates;
    /**
     * Constructs a {@code JsonAdaptedStudent} with the given student details.
     */
    @JsonCreator
    public JsonAdaptedStudent(@JsonProperty("name") String name,
                              @JsonProperty("attendance") JsonAdaptedPresentDates presentDates,
                              @JsonProperty("studentId") String studentId,
                              @JsonProperty("tutorialId") String tutorialId) {
        this.name = name;
        this.studentId = studentId;
        this.tutorialId = tutorialId;
        this.presentDates = presentDates;
    }

    /**
     * Converts a given {@code Student} into this class for Jackson use.
     */
    public JsonAdaptedStudent(Student source) {
        name = source.getName().fullName;
        studentId = source.getStudentId().value;
        tutorialId = source.getTutorialId().toString();
        presentDates = new JsonAdaptedPresentDates(source.getPresentDates());
    }

    /**
     * Converts this Jackson-friendly adapted student object into the model's {@code Student} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted student.
     */
    public Student toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (studentId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StudentId.class.getSimpleName()));
        }
        if (!StudentId.isValidStudentId(studentId)) {
            throw new IllegalValueException(StudentId.MESSAGE_CONSTRAINTS);
        }
        final StudentId modelStudentId = new StudentId(studentId);

        if (tutorialId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TutorialId.class.getSimpleName()));
        }
        final TutorialId modelTutorialId = TutorialId.of(tutorialId);

        final PresentDates modelPresentDates;

        if (presentDates != null) {
            modelPresentDates = presentDates.toModelType();
        } else {
            modelPresentDates = new PresentDates(new HashSet<>());
        }

        return new Student(modelName, modelStudentId, modelTutorialId, modelPresentDates);
    }

}
