package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.exam.Exam;

/**
 * Jackson-friendly version of {@link Exam}.
 */
class JsonAdaptedExam {

    private final String examName;
    private final String examScore;

    /**
     * Constructs a {@code JsonAdaptedExam} with the given {@code examName} and {@code examScore}.
     */
    @JsonCreator
    public JsonAdaptedExam(@JsonProperty("exam name") String examName, @JsonProperty("exam score") String examScore) {
        this.examName = examName;
        this.examScore = examScore;
    }

    /**
     * Converts a given {@code Exam} into this class for Jackson use.
     */
    public JsonAdaptedExam(Exam source) {
        examName = source.examName;
        examScore = source.examScore;
    }

    /**
     * Converts this Jackson-friendly adapted exam object into the model's {@code Exam} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted exam.
     */
    public Exam toModelType() throws IllegalValueException {
        if (!Exam.isValidExamName(examName)) {
            throw new IllegalValueException(Exam.NAME_MESSAGE_CONSTRAINTS);
        } else if (!Exam.isValidExamScore(examScore)) {
            throw new IllegalValueException(Exam.SCORE_MESSAGE_CONSTRAINTS);
        }
        return new Exam(examName, examScore);
    }
}
