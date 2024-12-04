package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Person;
import seedu.address.model.person.Subject;
import seedu.address.model.person.Tutee;
import seedu.address.model.person.Tutor;

/**
 * Jackson-friendly version of {@link Lesson}.
 */
public class JsonAdaptedLesson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Lesson's %s field is missing!";
    public static final String INVALID_TUTOR_ID = "Tutor ID does not correspond to a tutor.";
    public static final String INVALID_TUTEE_ID = "Tutee ID does not correspond to a tutee.";

    private int tutorId;
    private int tuteeId;
    private JsonAdaptedSubject subject;
    public JsonAdaptedLesson() {}

    /**
     * Constructs a {@code JsonAdaptedLesson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedLesson(@JsonProperty("tutorId") int tutorId, @JsonProperty("tuteeId") int tuteeId,
                             @JsonProperty("subject") JsonAdaptedSubject subject) {
        this.tutorId = tutorId;
        this.tuteeId = tuteeId;
        this.subject = subject;
    }

    /**
     * Converts a given {@code Lesson} into this class for Jackson use.
     */
    public JsonAdaptedLesson(Lesson source) {
        tutorId = source.getTutor().getId();
        tuteeId = source.getTutee().getId();
        subject = new JsonAdaptedSubject(source.getSubject());
    }

    public int getTutorId() {
        return tutorId;
    }

    public int getTuteeId() {
        return tuteeId;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Lesson} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted lesson.
     */
    public Lesson toModelType(AddressBook addressBook) throws IllegalValueException {
        int tutorId = this.getTutorId();
        int tuteeId = this.getTuteeId();
        if (subject == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Subject.class.getSimpleName()));
        }
        Subject subject = this.subject.toModelType();

        Person tutorToCheck = addressBook.getPersonById(tutorId);
        if (!(tutorToCheck instanceof Tutor)) {
            throw new IllegalValueException(INVALID_TUTOR_ID);
        }
        Tutor tutor = (Tutor) tutorToCheck;

        Person tuteeToCheck = addressBook.getPersonById(tuteeId);
        if (!(tuteeToCheck instanceof Tutee)) {
            throw new IllegalValueException(INVALID_TUTEE_ID);
        }
        Tutee tutee = (Tutee) tuteeToCheck;

        return new Lesson(tutor, tutee, subject);
    }

}
