package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.student.Student;
import seedu.address.model.student.TutorialClass;
import seedu.address.model.tut.TutDate;
import seedu.address.model.tut.Tutorial;

/**
 * Jackson-friendly version of {@link Tutorial}.
 */
public class JsonAdaptedTut {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Tutorial's %s field is missing!";

    private final String tutName;
    private final TutorialClass tutorialClassName;
    private final List<JsonAdaptedStudent> students = new ArrayList<>();
    private final List<JsonAdaptedTutDate> tutDates = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedTut} with the given tutorial details.
     */
    @JsonCreator
    public JsonAdaptedTut(
            @JsonProperty("tutName") String tutName,
            @JsonProperty("tutorialClassName") TutorialClass tutorialClassName,
            @JsonProperty("students") List<JsonAdaptedStudent> students,
            @JsonProperty("tutDates") List<JsonAdaptedTutDate> tutDates) {
        this.tutName = tutName;
        this.tutorialClassName = tutorialClassName;
        if (students != null) {
            this.students.addAll(students);
        }
        if (tutDates != null) {
            this.tutDates.addAll(tutDates);
        }
    }

    /**
     * Converts a given {@code Tut} into this class for Jackson use.
     */
    public JsonAdaptedTut(Tutorial source) {
        this.tutName = source.getTutName();
        this.tutorialClassName = source.getTutorialClass();
        this.students.addAll(source.getStudents().stream()
                .map(JsonAdaptedStudent::new)
                .collect(Collectors.toList()));
        this.tutDates.addAll(source.getTutDates().stream()
                .map(JsonAdaptedTutDate::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted tutorial object into the model's {@code Tut} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tutorial.
     */
    public Tutorial toModelType() throws IllegalValueException {
        if (tutName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "tutName"));
        }
        if (!Tutorial.isValidName(tutName)) {
            throw new IllegalValueException(Tutorial.MESSAGE_NAME_CONSTRAINTS);
        }

        if (tutorialClassName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "tutorialClassName"));
        }

        final TutorialClass modelTutorialClass = tutorialClassName;

        // Convert students
        final List<Student> modelStudents = new ArrayList<>();
        for (JsonAdaptedStudent student : students) {
            modelStudents.add(student.toModelType());
        }

        // Convert tutDates
        final List<TutDate> modelTutDates = new ArrayList<>();
        for (JsonAdaptedTutDate tutDate : tutDates) {
            modelTutDates.add(tutDate.toModelType());
        }

        Tutorial tutorial = new Tutorial(tutName, modelTutorialClass);
        for (Student student : modelStudents) {
            tutorial.add(student);
        }
        for (TutDate tutDate : modelTutDates) {
            tutorial.addTutorialDate(tutDate);
        }

        return tutorial;
    }
}
