package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.assignment.Deadline;
import seedu.address.model.assignment.Grade;
import seedu.address.model.assignment.Status;


/**
 * Jackson-friendly version of {@link Assignment}.
 */
public class JsonAdaptedAssignment {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Assignment's %s field is missing!";
    private final String assignmentName;
    private final String deadline;
    private final String submissionStatus;
    private final String gradingStatus;
    private final String grade;

    /**
     * Constructs a {@code JsonAdaptedAssignment} with the given assignment details.
     */
    @JsonCreator
    public JsonAdaptedAssignment(@JsonProperty("assignmentName") String assignmentName,
                                 @JsonProperty("deadline") String deadline,
                                 @JsonProperty("submissionStatus") String submissionStatus,
                                 @JsonProperty("gradingStatus") String gradingStatus,
                                 @JsonProperty("grade") String grade) {
        this.assignmentName = assignmentName;
        this.deadline = deadline;
        this.submissionStatus = submissionStatus;
        this.gradingStatus = gradingStatus;
        this.grade = grade;
    }


    /**
     * Converts a given {@code Assignment} into this class for Jackson use.
     */
    public JsonAdaptedAssignment(Assignment source) {
        assignmentName = source.getAssignmentName().fullName;
        deadline = source.getDeadline().deadline.toString();
        submissionStatus = source.getSubmissionStatus().status.toString();
        gradingStatus = source.getGradingStatus().status.toString();
        grade = source.getGrade().grade
                .map(x -> x.toString())
                .orElse("NULL");
    }

    /**
     * Converts this Jackson-friendly adapted assignment object into the model's {@code Assignment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted assignment.
     */

    public Assignment toModelType() throws IllegalValueException {
        if (assignmentName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    AssignmentName.class.getSimpleName()));
        }
        if (!AssignmentName.isValidName(assignmentName)) {
            throw new IllegalValueException(AssignmentName.MESSAGE_CONSTRAINTS);
        }

        final AssignmentName modelAssignmentName = new AssignmentName(assignmentName);

        if (deadline == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Deadline.class.getSimpleName()));
        }
        if (!Deadline.isValidDeadline(deadline)) {
            throw new IllegalValueException(Deadline.MESSAGE_CONSTRAINTS);
        }
        final Deadline modelDeadline = new Deadline(deadline);

        if (submissionStatus == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName()));
        }

        if (!Status.isValidStatus(submissionStatus)) {
            throw new IllegalValueException(Status.MESSAGE_CONSTRAINTS);
        }
        final Status modelSubmissionStatus = new Status(submissionStatus);

        if (gradingStatus == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName()));
        }

        if (!Status.isValidStatus(gradingStatus)) {
            throw new IllegalValueException(Status.MESSAGE_CONSTRAINTS);
        }
        final Status modelGradingStatus = new Status(gradingStatus);

        if (grade == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Grade.class.getSimpleName()));
        }

        if (!Grade.isValidGrade(grade)) {
            throw new IllegalValueException(Grade.MESSAGE_CONSTRAINTS);
        }

        final Grade modelGrade = new Grade(grade);

        return new Assignment(modelAssignmentName, modelDeadline, modelSubmissionStatus, modelGradingStatus,
                modelGrade);

    }
}
