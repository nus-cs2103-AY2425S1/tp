package tahub.contacts.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tahub.contacts.commons.exceptions.IllegalValueException;
import tahub.contacts.model.grade.Grade;

/**
 * Jackson-friendly version of {@link Grade}.
 */
class JsonAdaptedGrade {

    private final String assessmentName;
    private final double scorePercentage;
    private final double weight;

    /**
     * Constructs a {@code JsonAdaptedGrade} with the given grade details.
     */
    @JsonCreator
    public JsonAdaptedGrade(@JsonProperty("assessmentName") String assessmentName,
                            @JsonProperty("scorePercentage") double scorePercentage,
                            @JsonProperty("weight") double weight) {
        this.assessmentName = assessmentName;
        this.scorePercentage = scorePercentage;
        this.weight = weight;
    }

    /**
     * Converts a given {@code Grade} into this class for Jackson use.
     */
    public JsonAdaptedGrade(Grade source) {
        this.assessmentName = source.getAssessmentName();
        this.scorePercentage = source.getScorePercentage();
        this.weight = source.getWeight();
    }

    /**
     * Converts this Jackson-friendly adapted grade object into the model's {@code Grade} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted grade.
     */
    public Grade toModelType() throws IllegalValueException {
        if (assessmentName == null || assessmentName.trim().isEmpty()) {
            throw new IllegalValueException("Assessment name cannot be null or empty.");
        }
        if (scorePercentage < 0 || scorePercentage > 100) {
            throw new IllegalValueException("Score percentage must be between 0 and 100.");
        }
        if (weight < 0 || weight > 1) {
            throw new IllegalValueException("Weight must be between 0 and 1.");
        }
        return new Grade(assessmentName, scorePercentage, weight);
    }
}
