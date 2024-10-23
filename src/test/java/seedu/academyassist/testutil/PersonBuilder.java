package seedu.academyassist.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.academyassist.model.person.Address;
import seedu.academyassist.model.person.Email;
import seedu.academyassist.model.person.Ic;
import seedu.academyassist.model.person.Name;
import seedu.academyassist.model.person.Person;
import seedu.academyassist.model.person.Phone;
import seedu.academyassist.model.person.StudentId;
import seedu.academyassist.model.person.Subject;
import seedu.academyassist.model.person.YearGroup;
import seedu.academyassist.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_IC = "S1234567B";
    public static final String DEFAULT_YEARGROUP = "2";
    public static final String DEFAULT_STUDENT_ID = "S00008";
    public static final String DEFAULT_SUBJECT = "MATH";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Ic ic;
    private YearGroup yearGroup;
    private StudentId studentId;
    private Set<Subject> subjects;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        this(DEFAULT_STUDENT_ID);
    }

    /**
     * Creates a {@code PersonBuilder} with the default details but student id specified.
     */
    public PersonBuilder(String studentId) {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        ic = new Ic(DEFAULT_IC);
        yearGroup = new YearGroup(DEFAULT_YEARGROUP);
        this.studentId = new StudentId(studentId);
        subjects = new HashSet<>();
        subjects.add(new Subject(DEFAULT_SUBJECT));
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        ic = personToCopy.getIc();
        yearGroup = personToCopy.getYearGroup();
        studentId = personToCopy.getStudentId();
        subjects = new HashSet<>(personToCopy.getSubjects());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
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
     * Sets the {@code Ic} of the {@code Person} that we are building.
     */
    public PersonBuilder withIc(String ic) {
        this.ic = new Ic(ic);
        return this;
    }

    /**
     * Sets the {@code YearGroup} of the {@code Person} that we are building.
     */
    public PersonBuilder withYearGroup(String yearGroup) {
        this.yearGroup = new YearGroup(yearGroup);
        return this;
    }

    /**
     * Sets the {@code StudentId} of the {@code Person} that we are building.
     */
    public PersonBuilder withStudentId(String studentId) {
        this.studentId = new StudentId(studentId);
        return this;
    }

    /**
     * Parses the {@code subjects} into a {@code Set<Subject>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withSubjects(String ... subjects) {
        this.subjects = SampleDataUtil.getSubjectSet(subjects);
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, address, ic, yearGroup, studentId, subjects);
    }

}
