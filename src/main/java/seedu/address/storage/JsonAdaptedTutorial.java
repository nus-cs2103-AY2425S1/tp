package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Tutorial;

/**
 * Jackson-friendly version of {@link Tutorial}.
 */
class JsonAdaptedTutorial {

    private final String tutorialNumber;

    /**
     * Constructs a {@code JsonAdaptedTutorial} with the given {@code tutorial}.
     */
    @JsonCreator
    public JsonAdaptedTutorial(String tutorial) {
        this.tutorialNumber = tutorial;
    }

    /**
     * Converts a given {@code Tutorial} into this class for Jackson use.
     */
    public JsonAdaptedTutorial(Tutorial source) {
        tutorialNumber = source.tutorial;
    }

    @JsonValue
    public String getTutorialNumber() {
        return tutorialNumber;
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
