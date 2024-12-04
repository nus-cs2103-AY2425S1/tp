package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.company.Company;
import seedu.address.model.person.company.Industry;
import seedu.address.model.person.student.Student;
import seedu.address.model.person.student.StudentId;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_STUDENTID = "A1234567X";
    public static final String DEFAULT_INDUSTRY = "Technology";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private StudentId studentID;
    private Industry industry;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private String category;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        studentID = new StudentId(DEFAULT_STUDENTID);
        industry = new Industry(DEFAULT_INDUSTRY);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        category = "student"; // default to student
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        tags = new HashSet<>(personToCopy.getTags());

        if (personToCopy instanceof Student) {
            studentID = ((Student) personToCopy).getStudentId();
            category = "student";
        } else if (personToCopy instanceof Company) {
            industry = ((Company) personToCopy).getIndustry();
            category = "company";
        } else {
            category = "person";
        }
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Category} of the {@code Person} that we are building.
     */
    public PersonBuilder withCategory(String category) {
        this.category = category;
        return this;
    }

    /**
     * Sets the {@code StudentID} of the {@code Student} that we are building.
     */
    public PersonBuilder withStudentID(String studentID) {
        this.studentID = new StudentId(studentID);
        return this;
    }

    /**
     * Sets the {@code Industry} of the {@code Company} that we are building.
     */
    public PersonBuilder withIndustry(String industry) {
        this.industry = new Industry(industry);
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
     * Builds and returns a {@code Person} based on the current settings.
     */
    public Person build() {
        if ("student".equalsIgnoreCase(category)) {
            return new Student(name, studentID, phone, email, address, tags);
        } else if ("company".equalsIgnoreCase(category)) {
            return new Company(name, industry, phone, email, address, tags);
        } else {
            // Default to student if no specific category is specified
            return new Student(name, studentID, phone, email, address, tags);
        }
    }
}
