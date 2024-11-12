package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.participation.Participation;
import seedu.address.model.person.Attendance;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.tutorial.Tutorial;

/**
 * Jackson-friendly version of {@link Participation}.
 * This saves Student as {@link Person#getName()}'s {@link Name#fullName} and {@link Person#getPhoneValue()}
 */
public class JsonAdaptedParticipation {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Participation's %s field is missing!";
    public static final String MISSING_ADDRESSBOOK_OBJECT_MESSAGE_FORMAT =
            "Addressbook does not contain matching %s field!";

    // Two fields to define student for now
    private final String student;
    private final String phone;
    private final String tutorial;
    private final List<JsonAdaptedAttendance> attendances = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedParticipation} with the given participation details.
     */
    @JsonCreator
    public JsonAdaptedParticipation(
            @JsonProperty("student") String student,
            @JsonProperty("phone") String phone,
            @JsonProperty("tutorial") String tutorial,
            @JsonProperty("attendances") List<JsonAdaptedAttendance> attendances) {
        this.student = student;
        this.phone = phone;

        this.tutorial = tutorial;
        if (attendances != null) {
            this.attendances.addAll(attendances);
        }
    }

    /**
     * Converts a given {@code Participation} into this class for Jackson use.
     */
    public JsonAdaptedParticipation(Participation source) {
        this.student = source.getStudent().getName().fullName;
        this.phone = source.getStudent().getPhoneValue();

        this.tutorial = source.getTutorial().getSubject();
        this.attendances.addAll(source.getAttendanceList().stream()
                .map(JsonAdaptedAttendance::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Participation} object.
     *
     * @param addressBook AddressBook addressbook with the Students and Tutorials
     *
     * @return Participation object
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted participation.
     */
    public Participation toModelType(AddressBook addressBook) throws IllegalValueException {
        final List<Attendance> attendanceList = new ArrayList<>();
        for (JsonAdaptedAttendance attendance : attendances) {
            Attendance attendanceToBeAdded = attendance.toModelType();

            //check for duplicate attendance
            if (attendanceList.stream().anyMatch(a -> a.isSameWeek(attendanceToBeAdded))) {
                continue;
            }

            attendanceList.add(attendanceToBeAdded);
        }

        if (student == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Person.class.getSimpleName()));
        }
        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Person.class.getSimpleName()));
        }
        if (tutorial == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Tutorial.class.getSimpleName()));
        }

        // Matches parsed json strings to objects in addressbook
        Person studentObject = addressBook.getPersonList()
                .filtered(p -> p.getName().fullName.equals(student) && p.getPhoneValue().equals(phone))
                .stream().findFirst()
                .orElseThrow(() -> new IllegalValueException(String.format(
                        MISSING_ADDRESSBOOK_OBJECT_MESSAGE_FORMAT, Person.class.getSimpleName())));
        Tutorial tutorialObject = addressBook.getTutorialList()
                .filtered(t -> t.getSubject().equals(tutorial))
                .stream().findFirst()
                .orElseThrow(() -> new IllegalValueException(String.format(
                        MISSING_ADDRESSBOOK_OBJECT_MESSAGE_FORMAT, Tutorial.class.getSimpleName())));

        return new Participation(studentObject, tutorialObject, attendanceList);
    }
}
