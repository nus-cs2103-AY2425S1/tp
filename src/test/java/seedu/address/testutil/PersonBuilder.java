package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.EmergencyContact;
import seedu.address.model.person.Level;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Subject;
import seedu.address.model.person.task.Task;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMERGENCY_CONTACT = "93838383";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_NOTE = "";
    public static final String DEFAULT_LEVEL = "S1 Express";

    private Name name;
    private Phone phone;
    private EmergencyContact emergencyContact;
    private Address address;
    private Note note;
    private Set<Subject> subjects;
    private Level level;
    private TaskListBuilder taskList;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        emergencyContact = new EmergencyContact(DEFAULT_EMERGENCY_CONTACT);
        address = new Address(DEFAULT_ADDRESS);
        note = new Note(DEFAULT_NOTE);
        subjects = new HashSet<>();
        level = new Level(DEFAULT_LEVEL);
        taskList = new TaskListBuilder();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        emergencyContact = personToCopy.getEmergencyContact();
        address = personToCopy.getAddress();
        note = personToCopy.getNote();
        subjects = new HashSet<>(personToCopy.getSubjects());
        level = personToCopy.getLevel();
        taskList = new TaskListBuilder(personToCopy.getTaskList());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code subjects} into a {@code Set<Subject>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withSubjects(String ... subjects) {
        this.subjects = SampleDataUtil.getSubjectSet(subjects);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Note} of the {@code Person} that we are building.
     */
    public PersonBuilder withNote(String note) {
        this.note = new Note(note);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code EmergencyContact} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmergencyContact(String phone) {
        this.emergencyContact = new EmergencyContact(phone);
        return this;
    }

    /**
     * Sets the {@code Level} of the {@code Person} that we are building.
     */
    public PersonBuilder withLevel(String level) {
        this.level = level.isEmpty() ? new Level("NONE NONE") : new Level(level);
        return this;
    }

    /**
     * Sets the {@code TaskList} of the {@code Person} that we are building.
     */
    public PersonBuilder withTaskList(Task ... tasks) {
        this.taskList.withTasks(tasks);
        return this;
    }

    public Person build() {
        return new Person(name, phone, emergencyContact, address, note, subjects, level, taskList.build());
    }

}
