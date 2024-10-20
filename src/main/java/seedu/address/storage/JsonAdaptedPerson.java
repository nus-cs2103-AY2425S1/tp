package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.AttendanceStatus;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.StudentId;
import seedu.address.model.person.Tutorial;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String studentId;
    private final String phone;
    private final String email;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final List<JsonAdaptedTutorial> tutorials = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("studentId") String studentId,
                             @JsonProperty("phone") String phone, @JsonProperty("email") String email,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags,
                             @JsonProperty("tutorials") List<JsonAdaptedTutorial> tutorials) {
        this.name = name;
        this.studentId = studentId;
        this.phone = phone;
        this.email = email;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        if (tutorials != null) {
            this.tutorials.addAll(tutorials);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        studentId = source.getStudentId().value;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        tutorials.addAll(source.getTutorials().entrySet().stream()
                .map(entry -> new JsonAdaptedTutorial(entry.getKey().tutorial, entry.getValue()))
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        final Map<Tutorial, AttendanceStatus> personTutorials = new LinkedHashMap<>();
        if (tutorials.isEmpty()) {
            for (int i = 1; i <= Person.MAXIMUM_TUTORIALS; i++) {
                Tutorial tutorial = new Tutorial(String.valueOf(i));
                personTutorials.put(tutorial, AttendanceStatus.NOT_TAKEN_PLACE);
            }
        } else {
            for (JsonAdaptedTutorial tut : tutorials) {
                AttendanceStatus completed = tut.getCompleted();
                personTutorials.put(tut.toModelType(), completed);
            }
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (studentId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StudentId.class.getSimpleName()));
        }
        if (!StudentId.isValidStudentId(studentId)) {
            throw new IllegalValueException(StudentId.MESSAGE_CONSTRAINTS);
        }
        final StudentId modelStudentId = new StudentId(studentId);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        final Set<Tag> modelTags = new HashSet<>(personTags);

        final Map<Tutorial, AttendanceStatus> modelTutorials = new LinkedHashMap<>(personTutorials);

        return new Person(modelName, modelStudentId, modelPhone, modelEmail, modelTags, modelTutorials);
    }

}
