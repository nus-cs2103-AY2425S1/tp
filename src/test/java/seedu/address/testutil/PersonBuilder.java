package seedu.address.testutil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.exam.Exam;
import seedu.address.model.person.AbsentDate;
import seedu.address.model.person.AbsentReason;
import seedu.address.model.person.Address;
import seedu.address.model.person.EcName;
import seedu.address.model.person.EcNumber;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.RegisterNumber;
import seedu.address.model.person.Sex;
import seedu.address.model.person.StudentClass;
import seedu.address.model.submission.Submission;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_REGISTER_NUMBER = "1";
    public static final String DEFAULT_SEX = "F";
    public static final String DEFAULT_STUDENT_CLASS = "1A";
    public static final String DEFAULT_ECNAME = "Joe Hardy";
    public static final String DEFAULT_ECNUMBER = "";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private RegisterNumber registerNumber;
    private Sex sex;
    private StudentClass studentClass;
    private EcName ecName;
    private EcNumber ecNumber;
    private Set<Exam> exams;
    private Set<Tag> tags;
    private HashMap<AbsentDate, AbsentReason> attendances;
    private Set<Submission> submissions;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        registerNumber = new RegisterNumber(DEFAULT_REGISTER_NUMBER);
        sex = new Sex(DEFAULT_SEX);
        studentClass = new StudentClass(DEFAULT_STUDENT_CLASS);
        ecName = new EcName(DEFAULT_ECNAME);
        ecNumber = new EcNumber(DEFAULT_ECNUMBER);
        exams = new HashSet<>();
        tags = new HashSet<>();
        attendances = new HashMap<>();
        submissions = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        registerNumber = personToCopy.getRegisterNumber();
        sex = personToCopy.getSex();
        studentClass = personToCopy.getStudentClass();
        ecName = personToCopy.getEcName();
        ecNumber = personToCopy.getEcNumber();
        exams = new HashSet<>(personToCopy.getExams());
        tags = new HashSet<>(personToCopy.getTags());
        attendances = new HashMap<>(personToCopy.getAttendances());
        submissions = new HashSet<>(personToCopy.getSubmissions());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code exams} into a {@code Set<Exam>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withExams(String ... exams) {
        this.exams = SampleDataUtil.getExamSet(exams);
        return this;
    }

    /**
     * Parses the {@code submissions} into a {@code Set<Submission>} and set it to the {@code Person} that we are
     * building.
     */
    public PersonBuilder withSubmissions(String ... submissions) {
        this.submissions = SampleDataUtil.getSubmissionSet(submissions);
        return this;
    }

    /**
     * Sets the {@code String submissionStatus} for a specified {@code Submission} of the {@code Person} that we are
     * building.
     */
    public PersonBuilder withSubmissionStatus(String submissionName, String submissionStatus) {
        Set<Submission> updatedSubmissions = new HashSet<>();
        for (Submission submission : this.submissions) {
            Submission updatedSubmission = submission;
            if (submission.submissionName.equals(submissionName)) {
                updatedSubmission = new Submission(submissionName, submissionStatus);
            }
            updatedSubmissions.add(updatedSubmission);
        }
        this.submissions = updatedSubmissions;
        return this;
    }

    /**
     * Sets the {@code String examScore} for a specified {@code Exam} of the {@code Person} that we are building.
     */
    public PersonBuilder withExamScore(String examName, String examScore) {
        Set<Exam> updatedExams = new HashSet<>();
        for (Exam exam : this.exams) {
            Exam updatedExam = exam;
            if (exam.examName.equals(examName)) {
                updatedExam = new Exam(examName, examScore);
            }
            updatedExams.add(updatedExam);
        }
        this.exams = updatedExams;
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
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
     * Sets the {@code RegisterNumber} of the {@code Person} that we are building.
     */
    public PersonBuilder withRegisterNumber(String registerNumber) {
        this.registerNumber = new RegisterNumber(registerNumber);
        return this;
    }

    /**
     * Sets the {@code Sex} of the {@code Person} that we are building.
     */
    public PersonBuilder withSex(String sex) {
        this.sex = new Sex(sex);
        return this;
    }

    /**
     * Sets the {@code StudentClass} of the {@code Person} that we are building.
     */
    public PersonBuilder withStudentClass(String studentClass) {
        this.studentClass = new StudentClass(studentClass);
        return this;
    }

    /**
     * Sets the {@code ecName} of the {@code Person} that we are building.
     */
    public PersonBuilder withEcName(String ecName) {
        this.ecName = new EcName(ecName);
        return this;
    }

    /**
     * Sets the {@code EmergencyPhone} of the {@code Person} that we are building.
     */
    public PersonBuilder withEcNumber(String ecNumber) {
        this.ecNumber = new EcNumber(ecNumber);
        return this;
    }

    /**
     * Sets the {@code attendances} of the {@code Person} that we are building.
     */
    public PersonBuilder withAttendance(HashMap<AbsentDate, AbsentReason> attendances) {
        this.attendances = attendances;
        return this;
    }

    /**
     * Builds a new Person with all the required attributes.
     */
    public Person build() {
        return new Person(name, phone, email, address, registerNumber, sex, studentClass, ecName, ecNumber, exams,
                tags, attendances, submissions);
    }

}
