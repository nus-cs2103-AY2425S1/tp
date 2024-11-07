package tahub.contacts.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import tahub.contacts.commons.exceptions.IllegalValueException;
import tahub.contacts.model.grade.Grade;
import tahub.contacts.model.grade.GradingSystem;

/**
 * An Immutable GradingSystem that is serializable to JSON format.
 */
@JsonRootName(value = "gradingsystem")
class JsonSerializableGradingSystem {

    private final List<JsonAdaptedGrade> grades = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableGradingSystem} with the given grades.
     */
    @JsonCreator
    public JsonSerializableGradingSystem(@JsonProperty("grades") List<JsonAdaptedGrade> grades) {
        this.grades.addAll(grades);
    }

    /**
     * Converts a given {@code GradingSystem} into this class for Jackson use.
     */
    public JsonSerializableGradingSystem(GradingSystem source) {
        if (source == null) {
            return;
        }
        grades.addAll(source.getAllGrades().entrySet().stream()
                              .map(entry -> new JsonAdaptedGrade(
                                      new Grade(entry.getKey(), entry.getValue(),
                                                source.getAllWeights().getOrDefault(entry.getKey(),
                                                                                    1.0))))
                              .collect(Collectors.toList()));
    }

    /**
     * Converts this grading system into the model's {@code GradingSystem} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public GradingSystem toModelType() throws IllegalValueException {
        GradingSystem gradingSystem = new GradingSystem();
        for (JsonAdaptedGrade jsonAdaptedGrade : grades) {
            Grade grade = jsonAdaptedGrade.toModelType();
            gradingSystem.addGrade(grade.getAssessmentName(), grade.getScorePercentage());
            gradingSystem.setAssessmentWeight(grade.getAssessmentName(), grade.getWeight());
        }
        return gradingSystem;
    }
}
