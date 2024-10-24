package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.submission.Submission;

/**
 * Jackson-friendly version of {@link Submission}.
 */
class JsonAdaptedSubmission {

    private final String submissionName;
    private final String submissionStatus;

    /**
     * Constructs a {@code JsonAdaptedSubmission} with the given {@code submissionName} and {@code submissionStatus}.
     */
    @JsonCreator
    public JsonAdaptedSubmission(@JsonProperty("submission name") String submissionName,
                                 @JsonProperty("submission status") String submissionStatus) {
        this.submissionName = submissionName;
        this.submissionStatus = submissionStatus;
    }

    /**
     * Converts a given {@code Submission} into this class for Jackson use.
     */
    public JsonAdaptedSubmission(Submission source) {
        submissionName = source.submissionName;
        submissionStatus = source.submissionStatus;
    }

    /**
     * Converts this Jackson-friendly adapted submission object into the model's {@code Submission} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted submission.
     */
    public Submission toModelType() throws IllegalValueException {
        if (!Submission.isValidSubmissionName(submissionName)) {
            throw new IllegalValueException(Submission.NAME_MESSAGE_CONSTRAINTS);
        } else if (!Submission.isValidSubmissionStatus(submissionStatus)) {
            throw new IllegalValueException(Submission.STATUS_MESSAGE_CONSTRAINTS);
        }
        return new Submission(submissionName, submissionStatus);
    }

}
