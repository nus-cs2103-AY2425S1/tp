package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.student.Student;
import seedu.address.model.student.TutorialId;
import seedu.address.model.tut.TutDate;
import seedu.address.model.tut.TutName;
import seedu.address.model.tut.Tutorial;

/**
 * Jackson-friendly version of {@link Tutorial}.
 */
public class JsonAdaptedTutorial {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Tutorial's %s field is missing!";

    private final String tutName;
    private final String tutorialId;
    private final List<JsonAdaptedStudent> students = new ArrayList<>();
    private final List<JsonAdaptedTutDate> tutDates = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedTut} with the given tutorial details.
     */
    @JsonCreator
    public JsonAdaptedTutorial(
            @JsonProperty("tutName") String tutName,
            @JsonProperty("tutorialId") String tutorialId,
            @JsonProperty("students") List<JsonAdaptedStudent> students,
            @JsonProperty("tutDates") List<JsonAdaptedTutDate> tutDates) {
        this.tutName = tutName;
        this.tutorialId = tutorialId;
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

    public JsonAdaptedTutorial(Tutorial source) {
        this.tutName = source.getTutName().tutName;
        this.tutorialId = source.getTutorialId().getValue();
        this.students.addAll(source.getStudents().stream()
                .map(JsonAdaptedStudent::new)
                .toList());
        this.tutDates.addAll(source.getTutDates().stream()
                .map(JsonAdaptedTutDate::new)
                .toList());
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

        if (!TutName.isValidTutName(tutName)) {
            throw new IllegalValueException(Tutorial.MESSAGE_NAME_CONSTRAINTS);
        }

        if (tutorialId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "tutorialId"));
        }

        final TutorialId modelTutorialId = TutorialId.of(tutorialId);
        final TutName modelTutName = new TutName(tutName);
        final List<Student> modelStudents = new ArrayList<>();
        for (JsonAdaptedStudent student : students) {
            modelStudents.add(student.toModelType());
        }
        final List<TutDate> modelTutDates = new ArrayList<>();
        for (JsonAdaptedTutDate tutDate : tutDates) {
            modelTutDates.add(tutDate.toModelType());
        }

        Tutorial tutorial = Tutorial.of(modelTutName, modelTutorialId);

        for (Student student : modelStudents) {
            tutorial.add(student);
        }
        for (TutDate tutDate : modelTutDates) {
            tutorial.addTutorialDate(tutDate);
        }

        return tutorial;
    }
}
