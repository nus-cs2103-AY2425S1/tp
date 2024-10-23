package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.EmergencyContact;
import seedu.address.model.person.LessonTime;
import seedu.address.model.person.Level;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Subject;
import seedu.address.model.person.task.Task;
import seedu.address.model.person.task.TaskList;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String emergencyContact;
    private final String address;
    private final String note;
    private final String level;
    private final List<JsonAdaptedSubject> subjects = new ArrayList<>();
    private final List<JsonAdaptedTask> taskList = new ArrayList<>();
    private final List<JsonAdaptedLessonTime> lessonTimes = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("emergencyContact") String emergencyContact,
            @JsonProperty("address") String address, @JsonProperty("note") String note,
            @JsonProperty("subjects") List<JsonAdaptedSubject> subjects,
            @JsonProperty("level") String level, @JsonProperty("taskList") List<JsonAdaptedTask> taskList,
            @JsonProperty("lessonTimes") List<JsonAdaptedLessonTime> lessonTimes) {
        this.name = name;
        this.phone = phone;
        this.emergencyContact = emergencyContact;
        this.address = address;
        this.note = note;
        this.level = level;
        if (subjects != null) {
            this.subjects.addAll(subjects);
        }
        if (taskList != null) {
            this.taskList.addAll(taskList);
        }
        if (lessonTimes != null) {
            this.lessonTimes.addAll(lessonTimes);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        emergencyContact = source.getEmergencyContact().value;
        address = source.getAddress().value;
        note = source.getNote().value;
        level = source.getLevel().levelName;
        subjects.addAll(source.getSubjects().stream()
                .map(JsonAdaptedSubject::new)
                .collect(Collectors.toList()));
        taskList.addAll(source.getTaskList().getjsonAdaptedTaskList());
        lessonTimes.addAll(source.getLessonTimes().stream()
                .map(JsonAdaptedLessonTime::new)
                .collect(Collectors.toSet()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Subject> personSubjects = new ArrayList<>();
        for (JsonAdaptedSubject subject : subjects) {
            personSubjects.add(subject.toModelType());
        }

        final List<LessonTime> personLessonTimes = new ArrayList<>();
        for (JsonAdaptedLessonTime lt: lessonTimes) {
            personLessonTimes.add(lt.toModelType());
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

        if (emergencyContact == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EmergencyContact.class.getSimpleName()));
        }
        if (!EmergencyContact.isValidPhone(emergencyContact)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final EmergencyContact modelEmergencyContact = new EmergencyContact(emergencyContact);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (note == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Note.class.getSimpleName()));
        }
        final Note modelNote = new Note(note);

        final Set<Subject> modelSubjects = new HashSet<>(personSubjects);

        if (level == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Level.class.getSimpleName()));
        }
        if (!Level.isValidLevelName(level)) {
            throw new IllegalValueException(Level.MESSAGE_CONSTRAINTS);
        }

        final Level modelLevel = new Level(level);

        List<Task> list = new ArrayList<>();
        for (JsonAdaptedTask jsonAdaptedTask : taskList) {
            Task modelType = jsonAdaptedTask.toModelType();
            list.add(modelType);
        }
        TaskList modelTaskList = new TaskList();
        modelTaskList.setTasks(list);

        final Set<LessonTime> modelLessonTimes = new HashSet<>(personLessonTimes);

        return new Person(modelName, modelPhone, modelEmergencyContact,
                modelAddress, modelNote, modelSubjects, modelLevel, modelTaskList, modelLessonTimes);


    }

}
