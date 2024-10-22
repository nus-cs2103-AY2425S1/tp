package tutorease.address.storage;

import static java.util.Objects.requireNonNull;
import static tutorease.address.commons.util.DateTimeUtil.dateTimeToString;
import static tutorease.address.model.lesson.StartDateTime.START_IS_AFTER_END;
import static tutorease.address.model.lesson.StudentId.INVALID_MESSAGE_CONSTRAINTS;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tutorease.address.commons.exceptions.IllegalValueException;
import tutorease.address.model.ReadOnlyTutorEase;
import tutorease.address.model.lesson.EndDateTime;
import tutorease.address.model.lesson.Lesson;
import tutorease.address.model.lesson.StartDateTime;
import tutorease.address.model.person.Person;

/**
 * Jackson-friendly version of {@link Lesson}.
 */
public class JsonAdaptedLesson {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Lesson's %s field is missing!";
    private final String student;
    private final String startDateTime;
    private final String endDateTime;

    /**
     * Constructs a {@code JsonAdaptedLesson} with the given lesson details.
     */
    @JsonCreator
    public JsonAdaptedLesson(@JsonProperty("student") String student,
                             @JsonProperty("startDateTime") String startDateTime,
                             @JsonProperty("endDateTime") String endDateTime) {
        this.student = student;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    /**
     * Converts a given {@code Lesson} into this class for Jackson use.
     */
    public JsonAdaptedLesson(Lesson source) {
        student = source.getStudent().getName().fullName;
        startDateTime = dateTimeToString(source.getStartDateTime().getDateTime());
        endDateTime = dateTimeToString(source.getEndDateTime().getDateTime());
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Lesson} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Lesson toModelType(ReadOnlyTutorEase addressBook) throws IllegalValueException {
        requireNonNull(addressBook);
        if (student == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Person.class.getSimpleName()));
        }
        if (addressBook.getPerson(student) == null) {
            throw new IllegalValueException(INVALID_MESSAGE_CONSTRAINTS);
        }
        final Person studentPerson = addressBook.getPerson(student);

        if (startDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StartDateTime.class.getSimpleName()));
        }
        if (!StartDateTime.isValidDateTime(startDateTime)) {
            throw new IllegalValueException(StartDateTime.START_DATE_MESSAGE_CONSTRAINTS);
        }
        final StartDateTime startDateTime = StartDateTime.createStartDateTime(this.startDateTime);

        if (endDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "EndDateTime"));
        }
        if (!EndDateTime.isValidDateTime(endDateTime)) {
            throw new IllegalValueException(EndDateTime.END_DATE_MESSAGE_CONSTRAINTS);
        }
        final EndDateTime endDateTime = EndDateTime.createEndDateTime(this.endDateTime);

        if (startDateTime.isAfter(endDateTime)) {
            throw new IllegalValueException(START_IS_AFTER_END);
        }
        return new Lesson(studentPerson, startDateTime, endDateTime);
    }
}
