package seedu.address.storage;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.DateTimeUtil.dateTimeToString;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.lesson.EndDateTime;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LocationIndex;
import seedu.address.model.lesson.StartDateTime;
import seedu.address.model.person.Person;

public class JsonAdaptedLesson {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Lesson's %s field is missing!";
    private final String student;
    private final String locationIndex;
    private final String startDateTime;
    private final String endDateTime;

    /**
     * Constructs a {@code JsonAdaptedLesson} with the given lesson details.
     */
    @JsonCreator
    public JsonAdaptedLesson(@JsonProperty("student") String student, @JsonProperty("locationIndex") String locationIndex,
            @JsonProperty("startDateTime") String startDateTime, @JsonProperty("endDateTime") String endDateTime) {
        this.student = student;
        this.locationIndex = locationIndex;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    /**
     * Converts a given {@code Lesson} into this class for Jackson use.
     */
    public JsonAdaptedLesson(Lesson source) {
        student = source.getStudent().getName().fullName;
        locationIndex = source.getLocationIndex().toString();
        startDateTime = dateTimeToString(source.getStartDateTime().getDateTime());
        endDateTime = dateTimeToString(source.getEndDateTime().getDateTime());
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Lesson} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Lesson toModelType(ReadOnlyAddressBook addressBook) throws IllegalValueException {
        requireNonNull(addressBook);
        if (student == null || addressBook.getPerson(student) == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Person.class.getSimpleName()));
        }
        final Person studentPerson = addressBook.getPerson(student);

        if (locationIndex == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, LocationIndex.class.getSimpleName()));
        }
        final LocationIndex locationIndex = new LocationIndex(this.locationIndex);

        if (startDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, StartDateTime.class.getSimpleName()));
        }
        final StartDateTime startDateTime = StartDateTime.createStartDateTime(this.startDateTime);

        if (endDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "EndDateTime"));
        }
        final EndDateTime endDateTime = EndDateTime.createEndDateTime(this.endDateTime);

        if(startDateTime.isAfter(endDateTime)) {
            throw new IllegalValueException("StartDateTime cannot be after EndDateTime");
        }
        return new Lesson(studentPerson, locationIndex, startDateTime, endDateTime);
    }
}
