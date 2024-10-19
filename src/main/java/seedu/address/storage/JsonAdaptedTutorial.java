package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Tutorial;

/**
 * Jackson-friendly version of {@link Tutorial}.
 */
class JsonAdaptedTutorial {

    private final String tutorialNumber;
    private final Boolean completed;

    /**
     * Constructs a {@code JsonAdaptedTutorial} with the given {@code tutorial}.
     */
    @JsonCreator
    public JsonAdaptedTutorial(@JsonProperty("tutorialNumber") String tutorialNumber,
                               @JsonProperty("completed") Boolean completed) {
        this.tutorialNumber = tutorialNumber;
        this.completed = completed;
    }

    /**
     * Converts a given {@code Tutorial} into this class for Jackson use.
     */
    public JsonAdaptedTutorial(Tutorial source, Boolean completed) {
        this.tutorialNumber = source.tutorial;
        this.completed = completed;
    }

    @JsonProperty("tutorialNumber")
    public String getTutorialNumber() {
        return tutorialNumber;
    }

    @JsonProperty("completed")
    public Boolean getCompleted() {
        return completed;
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
