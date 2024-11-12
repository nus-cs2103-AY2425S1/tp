package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.DaysAttended;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Student;
import seedu.address.model.person.Subject;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Student objects.
 */
public class StudentBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_GENDER = "female";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_SUBJECT = "Mathematics";
    private static final Integer DEFAULT_DAYS_ATTENDED = 0;
    private static final String DEFAULT_NEXT_OF_KIN = "Bob Bee";
    private static final String DEFAULT_EMERGENCY_CONTACT = "85355999";

    private Name name;
    private Phone phone;
    private Gender gender;
    private Email email;
    private Address address;
    private Set<Subject> subjects;
    private Set<Tag> tags;
    private Set<String> classes;
    private DaysAttended daysAttended;
    private Name nextOfKin;
    private Phone emergencyContact;


    /**
     * Creates a {@code StudentBuilder} with the default details.
     */
    public StudentBuilder() {
        name = new Name(DEFAULT_NAME);
        gender = new Gender(DEFAULT_GENDER);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        subjects = new HashSet<>(SampleDataUtil.getSubjectSet(DEFAULT_SUBJECT));
        tags = new HashSet<>();
        classes = new HashSet<>();
        daysAttended = new DaysAttended(DEFAULT_DAYS_ATTENDED);
        nextOfKin = new Name(DEFAULT_NEXT_OF_KIN);
        emergencyContact = new Phone(DEFAULT_EMERGENCY_CONTACT);
    }

    /**
     * Initializes the StudentBuilder with the data of {@code studentToCopy}.
     */
    public StudentBuilder(Student studentToCopy) {
        name = studentToCopy.getName();
        gender = studentToCopy.getGender();
        phone = studentToCopy.getPhone();
        email = studentToCopy.getEmail();
        address = studentToCopy.getAddress();
        subjects = studentToCopy.getSubjects();
        tags = new HashSet<>(studentToCopy.getTags());
        classes = new HashSet<>(studentToCopy.getClasses());
        daysAttended = studentToCopy.getDaysAttended();
        nextOfKin = studentToCopy.getNextOfKinName();
        emergencyContact = studentToCopy.getEmergencyContact();
    }

    /**
     * Sets the {@code Name} of the {@code Student} that we are building.
     */
    public StudentBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Gender} of the {@code Student} that we are building.
     */
    public StudentBuilder withGender(String gender) {
        this.gender = new Gender(gender);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Student} that we are building.
     */
    public StudentBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Student} that we are building.
     */
    public StudentBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Student} that we are building.
     */
    public StudentBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Subject} of the {@code Student} that we are building.
     */
    public StudentBuilder withSubjects(String... subjects) {
        this.subjects = SampleDataUtil.getSubjectSet(subjects);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Student} that we are building.
     */
    public StudentBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Parses the {@code classes} into a {@code Set<String>} and sets them to the {@code Student} that we are building.
     */
    public StudentBuilder withClasses(String... classes) {
        this.classes = SampleDataUtil.getClassSet(classes);
        return this;
    }

    /**
     * Sets the {@code DaysAttended} of the {@code Student} that we are building.
     */
    public StudentBuilder withDaysAttended(Integer daysAttended) {
        this.daysAttended = new DaysAttended(daysAttended);
        return this;
    }

    /**
     * Sets the {@code NextOfKin} of the {@code Student} that we are building.
     */
    public StudentBuilder withNextOfKin(String nextOfKin) {
        this.nextOfKin = new Name(nextOfKin);
        return this;
    }

    /**
     * Sets the {@code EmergencyContact} of the {@code Student} that we are building.
     */
    public StudentBuilder withEmergencyContact(String emergencyContact) {
        this.emergencyContact = new Phone(emergencyContact);
        return this;
    }

    /**
     * @return a Student object with the details of the StudentBuilder
     */
    public Student build() {
        return new Student(name, gender, phone, email, address, tags, subjects, classes, daysAttended, nextOfKin,
                emergencyContact);
    }

}
