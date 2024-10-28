package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.student.Address;
import seedu.address.model.student.EmergencyContact;
import seedu.address.model.student.LessonTime;
import seedu.address.model.student.Level;
import seedu.address.model.student.Name;
import seedu.address.model.student.Note;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Student;
import seedu.address.model.student.Subject;
import seedu.address.model.student.task.Task;
import seedu.address.model.student.task.TaskList;

/**
 * Jackson-friendly version of {@link Student}.
 */
class JsonAdaptedStudent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Student's %s field is missing!";

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
     * Constructs a {@code JsonAdaptedStudent} with the given student details.
     */
    @JsonCreator
    public JsonAdaptedStudent(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                              @JsonProperty("emergencyContact") String emergencyContact,
                              @JsonProperty("address") String address, @JsonProperty("note") String note,
                              @JsonProperty("subjects") List<JsonAdaptedSubject> subjects,
                              @JsonProperty("level") String level,
                              @JsonProperty("taskList") List<JsonAdaptedTask> taskList,
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
     * Converts a given {@code Student} into this class for Jackson use.
     */
    public JsonAdaptedStudent(Student source) {
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
     * Converts this Jackson-friendly adapted student object into the model's {@code Student} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted student.
     */
    public Student toModelType() throws IllegalValueException {
        final List<Subject> studentSubjects = new ArrayList<>();
        for (JsonAdaptedSubject subject : subjects) {
            studentSubjects.add(subject.toModelType());
        }

        final List<LessonTime> studentLessonTimes = new ArrayList<>();
        for (JsonAdaptedLessonTime lt: lessonTimes) {
            studentLessonTimes.add(lt.toModelType());
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

        final Set<Subject> modelSubjects = new HashSet<>(studentSubjects);

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

        final Set<LessonTime> modelLessonTimes = new HashSet<>(studentLessonTimes);

        return new Student(modelName, modelPhone, modelEmergencyContact,
                modelAddress, modelNote, modelSubjects, modelLevel, modelTaskList, modelLessonTimes);


    }

}
