package seedu.address.storage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Attendance;
import seedu.address.model.person.AttendanceList;
import seedu.address.model.person.Course;
import seedu.address.model.person.Email;
import seedu.address.model.person.Grade;
import seedu.address.model.person.GradeList;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String course;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final Map<String, JsonAdaptedGrade> grades;
    private final Map<LocalDateTime, JsonAdaptedAttendance> attendances = new TreeMap<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("course") String course,
            @JsonProperty("tags") List<JsonAdaptedTag> tags,
            @JsonProperty("grades") Map<String, JsonAdaptedGrade> grades,
            @JsonProperty("attendances") Map<LocalDateTime, JsonAdaptedAttendance> attendances) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.course = course;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        if (grades != null) {
            this.grades = new HashMap<>(grades);
        } else {
            this.grades = new HashMap<>();
        }
        if (attendances != null) {
            this.attendances.putAll(attendances);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        course = source.getCourse().value;
        tags.addAll(source.getTags().stream()
                            .map(JsonAdaptedTag::new)
                            .collect(Collectors.toList()));
        grades = source.getGradeList().getMap().entrySet().stream().map(entry -> Map.entry(entry.getKey(),
                                                                                           new JsonAdaptedGrade(
                                                                                                   entry.getValue())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        source.getAttendanceList().getMap().forEach((key, value) ->
                                                            attendances.put(key, new JsonAdaptedAttendance(value)));
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

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

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

        if (course == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Course.class.getSimpleName()));
        }
        if (!Course.isValidCourse(course)) {
            throw new IllegalValueException(Course.MESSAGE_CONSTRAINTS);
        }
        final Course modelCourse = new Course(course);

        final Set<Tag> modelTags = new HashSet<>(personTags);

        final Map<String, Grade> convertedGrades = new HashMap<>();
        for (JsonAdaptedGrade grade : grades.values()) {
            final Grade convertedGrade = grade.toModelType();
            convertedGrades.put(convertedGrade.getTestName(), convertedGrade);
        }
        final GradeList modelGradeList = new GradeList(convertedGrades);

        final Map<LocalDateTime, Attendance> convertedAttendances = new TreeMap<>();
        attendances.forEach((key, value) -> convertedAttendances.put(key, value.toModelType()));
        final AttendanceList modelAttendancelist = new AttendanceList(convertedAttendances);

        return new Person(modelName, modelPhone, modelEmail, modelCourse, modelTags, modelGradeList,
                          modelAttendancelist);
    }
}
