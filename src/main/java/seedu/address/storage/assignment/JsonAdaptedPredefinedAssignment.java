package seedu.address.storage.assignment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.assignment.PredefinedAssignment;

/**
 * Jackson-friendly version of {@link PredefinedAssignment}.
 */
class JsonAdaptedPredefinedAssignment {
    private final String name;
    private final Float maxScore;

    @JsonCreator
    public JsonAdaptedPredefinedAssignment(@JsonProperty("name") String name,
                                           @JsonProperty("maxScore") Float maxScore) {
        this.name = name;
        this.maxScore = maxScore;
    }

    public PredefinedAssignment toModelType() {
        return new PredefinedAssignment(name, maxScore);
    }

    public String getName() {
        return name;
    }

    public Float getMaxScore() {
        return maxScore;
    }
}
