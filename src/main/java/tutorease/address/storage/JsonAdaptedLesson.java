package tutorease.address.storage;

import static java.util.Objects.requireNonNull;
import static tutorease.address.commons.util.DateTimeUtil.dateTimeToString;
import static tutorease.address.model.lesson.StartDateTime.START_IS_AFTER_END;
import static tutorease.address.model.lesson.StudentId.INVALID_MESSAGE_CONSTRAINTS;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tutorease.address.commons.core.LogsCenter;
import tutorease.address.commons.exceptions.IllegalValueException;
import tutorease.address.model.ReadOnlyTutorEase;
import tutorease.address.model.lesson.EndDateTime;
import tutorease.address.model.lesson.Fee;
import tutorease.address.model.lesson.Lesson;
import tutorease.address.model.lesson.StartDateTime;
import tutorease.address.model.person.Person;

/**
 * JSON version of {@link Lesson}.
 */
public class JsonAdaptedLesson {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Lesson's %s field is missing!";
    private static Logger logger = LogsCenter.getLogger(JsonAdaptedLesson.class);

    private final String student;
    private final String fee;
    private final String startDateTime;
    private final String endDateTime;

    /**
     * Constructs a {@code JsonAdaptedLesson} with the given lesson details.
     *
     * @param student Student's name.
     * @param fee Fee of the lesson.
     * @param startDateTime Start date time of the lesson.
     * @param endDateTime End date time of the lesson.
     */
    @JsonCreator
    public JsonAdaptedLesson(@JsonProperty("student") String student,
                             @JsonProperty("fee") String fee,
                             @JsonProperty("startDateTime") String startDateTime,
                             @JsonProperty("endDateTime") String endDateTime) {
        logger.log(Level.INFO, "Creating JsonAdaptedLesson with student: " + student + ", fee: " + fee
                + ", startDateTime: " + startDateTime + ", endDateTime: " + endDateTime);

        this.student = student;
        this.fee = fee;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        logger.log(Level.INFO, "Created JsonAdaptedLesson with student");
    }

    /**
     * Converts a given {@code Lesson} into this class for JSON.
     *
     * @param source The lesson to be converted to JSON.
     */
    public JsonAdaptedLesson(Lesson source) {
        logger.log(Level.INFO, "Creating JsonAdaptedLesson with lesson: " + source);
        student = source.getStudentName();
        fee = source.getFeeString();
        startDateTime = dateTimeToString(source.getStartDateTime().getDateTime());
        endDateTime = dateTimeToString(source.getEndDateTime().getDateTime());
        logger.log(Level.INFO, "Created JsonAdaptedLesson with lesson");
    }

    /**
     * Converts this JSON object into the model's {@code Lesson} object.
     *
     * @param addressBook The address book to get the student from.
     * @return the lesson object.
     * @throws IllegalValueException If there were any data constraints violated.
     */
    public Lesson toModelType(ReadOnlyTutorEase addressBook) throws IllegalValueException {
        logger.log(Level.INFO, "Converting JsonAdaptedLesson to model type");
        requireNonNull(addressBook);

        // Validate not null and student exists
        validateStudent(addressBook);
        final Person studentPerson = addressBook.getPerson(student);

        // Validate not null and fee is valid
        validateFee();
        final Fee fee = new Fee(this.fee);

        // Validate not null and date time is valid
        validateStartDateTime();
        final StartDateTime startDateTime = StartDateTime.createStartDateTime(this.startDateTime);
        validateEndDateTime();
        final EndDateTime endDateTime = EndDateTime.createEndDateTime(this.endDateTime);

        validateStartBeforeEnd(startDateTime, endDateTime);
        Lesson lesson = new Lesson(studentPerson, fee, startDateTime, endDateTime);
        logger.log(Level.INFO, "Converted JsonAdaptedLesson to model type: " + lesson);
        return lesson;
    }

    private void validateEndDateTime() throws IllegalValueException {
        validateEndDateTimeNotNull();
        isValidEndDateTime();
    }

    private void validateStartDateTime() throws IllegalValueException {
        validateStartDateTimeNotNull();
        isValidStartDateTime();
    }

    private void validateFee() throws IllegalValueException {
        validateFeeNotNull();
        isValidFee();
    }

    private void validateStudent(ReadOnlyTutorEase addressBook) throws IllegalValueException {
        validateStudentNotNull();
        validateStudentExists(addressBook);
    }

    private static void validateStartBeforeEnd(StartDateTime startDateTime, EndDateTime endDateTime)
            throws IllegalValueException {
        logger.log(Level.INFO, "Validating start date time is before end date time");

        if (startDateTime.isAfter(endDateTime)) {
            logger.log(Level.WARNING, START_IS_AFTER_END);
            throw new IllegalValueException(START_IS_AFTER_END);
        }
    }

    private void isValidEndDateTime() throws IllegalValueException {
        logger.log(Level.INFO, "Validating end date time");

        if (!EndDateTime.isValidDateTime(endDateTime)) {
            logger.log(Level.WARNING, EndDateTime.END_DATE_MESSAGE_CONSTRAINTS);
            throw new IllegalValueException(EndDateTime.END_DATE_MESSAGE_CONSTRAINTS);
        }
    }

    private void validateEndDateTimeNotNull() throws IllegalValueException {
        logger.log(Level.INFO, "Validating end date time not null");

        if (endDateTime == null) {
            String message = String.format(MISSING_FIELD_MESSAGE_FORMAT, "EndDateTime");
            logger.log(Level.WARNING, message);
            throw new IllegalValueException(message);
        }
    }

    private void isValidStartDateTime() throws IllegalValueException {
        logger.log(Level.INFO, "Validating start date time");

        if (!StartDateTime.isValidDateTime(startDateTime)) {
            logger.log(Level.WARNING, StartDateTime.START_DATE_MESSAGE_CONSTRAINTS);
            throw new IllegalValueException(StartDateTime.START_DATE_MESSAGE_CONSTRAINTS);
        }
    }

    private void validateStartDateTimeNotNull() throws IllegalValueException {
        logger.log(Level.INFO, "Validating start date time not null");

        if (startDateTime == null) {
            String message = String.format(MISSING_FIELD_MESSAGE_FORMAT, StartDateTime.class.getSimpleName());
            logger.log(Level.WARNING, message);
            throw new IllegalValueException(message);
        }
    }

    private void isValidFee() throws IllegalValueException {
        logger.log(Level.INFO, "Validating fee");

        if (!Fee.isValidFee(fee)) {
            logger.log(Level.WARNING, Fee.MESSAGE_CONSTRAINTS);
            throw new IllegalValueException(Fee.MESSAGE_CONSTRAINTS);
        }
    }

    private void validateFeeNotNull() throws IllegalValueException {
        logger.log(Level.INFO, "Validating fee not null");

        if (fee == null) {
            String message = String.format(MISSING_FIELD_MESSAGE_FORMAT, Fee.class.getSimpleName());
            logger.log(Level.WARNING, message);
            throw new IllegalValueException(message);
        }
    }

    private void validateStudentExists(ReadOnlyTutorEase addressBook) throws IllegalValueException {
        logger.log(Level.INFO, "Validating student exists");

        if (addressBook.getPerson(student) == null) {
            logger.log(Level.WARNING, INVALID_MESSAGE_CONSTRAINTS);
            throw new IllegalValueException(INVALID_MESSAGE_CONSTRAINTS);
        }
    }

    private void validateStudentNotNull() throws IllegalValueException {
        logger.log(Level.INFO, "Validating student not null");

        if (student == null) {
            String message = String.format(MISSING_FIELD_MESSAGE_FORMAT, Person.class.getSimpleName());
            logger.log(Level.WARNING, message);
            throw new IllegalValueException(message);
        }
    }
}
