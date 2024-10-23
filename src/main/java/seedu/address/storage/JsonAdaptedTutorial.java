package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.AttendanceStatus;
import seedu.address.model.person.Tutorial;

/**
 * Jackson-friendly version of {@link Tutorial}.
 */
class JsonAdaptedTutorial {

    private final String tutorialNumber;
    private final AttendanceStatus attendanceStatus;

    /**
     * Constructs a {@code JsonAdaptedTutorial} with the given {@code tutorial}.
     */
    @JsonCreator
    public JsonAdaptedTutorial(@JsonProperty("tutorialNumber") String tutorialNumber,
                               @JsonProperty("attendanceStatus") AttendanceStatus attendanceStatus) {
        this.tutorialNumber = tutorialNumber;
        this.attendanceStatus = attendanceStatus;
    }

    /**
     * Converts a given {@code Tutorial} into this class for Jackson use.
     */
    public JsonAdaptedTutorial(Tutorial source, AttendanceStatus attendanceStatus) {
        this.tutorialNumber = source.tutorial;
        this.attendanceStatus = attendanceStatus;
    }

    @JsonProperty("tutorialNumber")
    public String getTutorialNumber() {
        return tutorialNumber;
    }

    @JsonProperty("attendanceStatus")
    public AttendanceStatus getCompleted() {
        return attendanceStatus;
    }

    /**
     * Converts this Jackson-friendly adapted tutorial object into the model's {@code Tutorial} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tutorial.
     */
    public Tutorial toModelType() throws IllegalValueException {
        if (!Tutorial.isValidTutorial(tutorialNumber)) {
            throw new IllegalValueException(Tutorial.MESSAGE_CONSTRAINTS);
        }
        return new Tutorial(tutorialNumber);
    }

}
