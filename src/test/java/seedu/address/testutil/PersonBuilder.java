package seedu.address.testutil;

import java.util.ArrayList;

import seedu.address.model.person.Address;
import seedu.address.model.person.Course;
import seedu.address.model.person.Email;
import seedu.address.model.person.Grade;
import seedu.address.model.person.Module;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.StudentId;
import seedu.address.model.person.Tag;


/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_STUDENT_ID = "12345678";
    public static final String DEFAULT_COURSE = "Computer Science";
    public static final String DEFAULT_TAG = "Student";

    private StudentId studentId;
    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Course course;
    private Tag tag;
    private ArrayList<Module> modules;


    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        studentId = new StudentId(DEFAULT_STUDENT_ID);
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        course = new Course(DEFAULT_COURSE);
        tag = new Tag(DEFAULT_TAG);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        studentId = personToCopy.getStudentId();
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        course = personToCopy.getCourse();
        tag = personToCopy.getTag();
        modules = personToCopy.getModules();
    }

    /**
     * Sets the {@code StudentId} of the {@code Person} that we are building.
     */
    public PersonBuilder withStudentId(String studentId) {
        this.studentId = new StudentId(studentId);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTag(String tag) {
        this.tag = new Tag(tag);
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
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Course} of the {@code Person} that we are building.
     */
    public PersonBuilder withCourse(String course) {
        this.course = new Course(course);
        return this;
    }

    /**
     * Adds an ungraded {@code Module} of the {@code Person} that we are building.
     */
    public PersonBuilder addUngradedModule(String module) {
        Module personModule = new Module(module);
        if (this.modules == null) {
            this.modules = new ArrayList<>();
        }
        this.modules.add(personModule);
        return this;
    }

    /**
     * Adds a graded {@code Module} of the {@code Person} that we are building.
     */
    public PersonBuilder addGradedModule(String module, String grade) {
        Module personModule = new Module(module);
        Grade moduleGrade = new Grade(grade);
        personModule.setGrade(moduleGrade);
        if (this.modules == null) {
            this.modules = new ArrayList<>();
        }
        this.modules.add(personModule);
        return this;
    }

    /**
     * Empties the {@code ArrayList<Module>} of the {@code Person} that we are building
     * for testing utility purposes.
     */
    public PersonBuilder emptyModuleList() {
        this.modules = new ArrayList<>();
        return this;
    }

    /**
     * Builds the {@code Person}.
     */
    public Person build() {
        Person p = new Person(studentId, name, phone, email, address, course, tag);
        if (modules != null && !modules.isEmpty()) {
            p.setModules(modules);
        }
        return p;
    }
}
