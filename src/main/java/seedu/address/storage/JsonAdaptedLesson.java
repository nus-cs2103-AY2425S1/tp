package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Tutee;
import seedu.address.model.person.Tutor;

/**
 * Jackson-friendly version of {@link Lesson}.
 */
public class JsonAdaptedLesson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Lesson's %s field is missing!";

    private int tutorId;
    private int tuteeId;
    public JsonAdaptedLesson() {}



    /**
     * Constructs a {@code JsonAdaptedLesson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedLesson(@JsonProperty("tutorId") int tutorId, @JsonProperty("tuteeId") int tuteeId) {
        this.tutorId = tutorId;
        this.tuteeId = tuteeId;
    }

    /**
     * Converts a given {@code Lesson} into this class for Jackson use.
     */
    public JsonAdaptedLesson(Lesson source) {
        tutorId = source.getTutor().getId();
        tuteeId = source.getTutee().getId();
    }

    public int getTutorId() {
        return tutorId;
    }

    public void setTutorId(int tutorId) {
        this.tutorId = tutorId;
    }

    public int getTuteeId() {
        return tuteeId;
    }

    public void setTuteeId(int tuteeId) {
        this.tuteeId = tuteeId;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Lesson} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted lesson.
     */
    public Lesson toModelType(AddressBook addressBook) throws IllegalValueException {
        int tutorId = this.getTutorId();
        int tuteeId = this.getTuteeId();

        Tutor tutor = (Tutor) addressBook.getPersonById(tutorId);
        Tutee tutee = (Tutee) addressBook.getPersonById(tuteeId);

        return new Lesson(tutor, tutee);
    }

}
