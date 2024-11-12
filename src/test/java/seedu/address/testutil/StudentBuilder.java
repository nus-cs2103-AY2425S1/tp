package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

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
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Student objects.
 */
public class StudentBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMERGENCY_CONTACT = "93838383";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_NOTE = "";
    public static final String DEFAULT_LEVEL = "S1 EXPRESS";

    private Name name;
    private Phone phone;
    private EmergencyContact emergencyContact;
    private Address address;
    private Note note;
    private Set<Subject> subjects;
    private Level level;
    private TaskListBuilder taskList;
    private Set<LessonTime> lessonTimes;

    /**
     * Creates a {@code StudentBuilder} with the default details.
     */
    public StudentBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        emergencyContact = new EmergencyContact(DEFAULT_EMERGENCY_CONTACT);
        address = new Address(DEFAULT_ADDRESS);
        note = new Note(DEFAULT_NOTE);
        subjects = new HashSet<>();
        level = new Level(DEFAULT_LEVEL);
        taskList = new TaskListBuilder();
        lessonTimes = new HashSet<>();
    }

    /**
     * Initializes the StudentBuilder with the data of {@code studentToCopy}.
     */
    public StudentBuilder(Student studentToCopy) {
        name = studentToCopy.getName();
        phone = studentToCopy.getPhone();
        emergencyContact = studentToCopy.getEmergencyContact();
        address = studentToCopy.getAddress();
        note = studentToCopy.getNote();
        subjects = new HashSet<>(studentToCopy.getSubjects());
        level = studentToCopy.getLevel();
        taskList = new TaskListBuilder(studentToCopy.getTaskList());
        lessonTimes = new HashSet<>(studentToCopy.getLessonTimes());
    }

    /**
     * Sets the {@code Name} of the {@code Student} that we are building.
     * @param name the name of the student
     * @return the updated StudentBuilder instance
     */
    public StudentBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code subjects} into a {@code Set<Subject>} and sets it to the {@code Student} that we are building.
     * @param subjects the subjects to be added
     * @return the updated StudentBuilder instance
     */
    public StudentBuilder withSubjects(String... subjects) {
        this.subjects = SampleDataUtil.getSubjectSet(subjects);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Student} that we are building.
     * @param address the address of the student
     * @return the updated StudentBuilder instance
     */
    public StudentBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Note} of the {@code Student} that we are building.
     * @param note the note associated with the student
     * @return the updated StudentBuilder instance
     */
    public StudentBuilder withNote(String note) {
        this.note = new Note(note);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Student} that we are building.
     * @param phone the phone number of the student
     * @return the updated StudentBuilder instance
     */
    public StudentBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code EmergencyContact} of the {@code Student} that we are building.
     * @param phone the emergency contact phone number of the student
     * @return the updated StudentBuilder instance
     */
    public StudentBuilder withEmergencyContact(String phone) {
        this.emergencyContact = new EmergencyContact(phone);
        return this;
    }

    /**
     * Sets the {@code Level} of the {@code Student} that we are building.
     * @param level the academic level of the student
     * @return the updated StudentBuilder instance
     */
    public StudentBuilder withLevel(String level) {
        this.level = level.isEmpty() ? new Level("NONE NONE") : new Level(level);
        return this;
    }

    /**
     * Sets the {@code TaskList} of the {@code Student} that we are building.
     * @param tasks the tasks to be added to the student's task list
     * @return the updated StudentBuilder instance
     */
    public StudentBuilder withTaskList(Task... tasks) {
        this.taskList.withTasks(tasks);
        return this;
    }

    /**
     * Parses the {@code lessonTimes} into a {@code Set<LessonTime>} and sets it to the {@code Student} that we are
     * building.
     * @param lessonTimes the lesson times associated with the student
     * @return the updated StudentBuilder instance
     */
    public StudentBuilder withLessonTimes(String... lessonTimes) {
        this.lessonTimes = SampleDataUtil.getLessonTimeSet(lessonTimes);
        return this;
    }

    /**
     * Builds and returns the {@code Student} object with the current configurations.
     * @return the built Student object
     */
    public Student build() {
        return new Student(name, phone, emergencyContact, address, note, subjects,
                level, taskList.build(), lessonTimes);
    }
}
