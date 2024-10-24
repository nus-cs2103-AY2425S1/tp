package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tut.Tutorial;
import seedu.address.model.tut.TutorialList;

/**
 * An Immutable TutorialList that is serializable to JSON format.
 */
@JsonRootName(value = "tutorials")
class JsonSerializableTutorialList {

    private final List<JsonAdaptedTutorial> tutorials = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableTutorialList} with the given tutorials.
     */
    @JsonCreator
    public JsonSerializableTutorialList(@JsonProperty("tutorials") List<JsonAdaptedTutorial> tutorials) {
        this.tutorials.addAll(tutorials);
    }

    /**
     * Converts a given {@code TutorialList} into this class for Jackson use.
     *
     * @param source Future changes to this will not affect the created {@code JsonSerializableTutorialList}.
     */
    public JsonSerializableTutorialList(TutorialList source) {
        tutorials.addAll(source.getTutorials().stream()
                .map(JsonAdaptedTutorial::new)
                .toList());
    }

    /**
     * Converts this tutorial list into the model's {@code TutorialList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public TutorialList toModelType() throws IllegalValueException {
        TutorialList tutorialList = new TutorialList();
        for (JsonAdaptedTutorial jsonAdaptedTut : tutorials) {
            if (!jsonAdaptedTut.toModelType().equals(Tutorial.none())) {
                Tutorial tutorial = jsonAdaptedTut.toModelType();
                tutorialList.addTutorial(tutorial);
            }
        }
        return tutorialList;
    }

    public List<JsonAdaptedTutorial> getTutorials() {
        return tutorials;
    }
}
