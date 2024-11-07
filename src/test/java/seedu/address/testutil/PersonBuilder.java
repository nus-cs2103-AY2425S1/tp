package seedu.address.testutil;

import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.StudentClass;
import seedu.address.model.tag.Tags;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_CLASS = "4A";
    public static final String DEFAULT_PHONE = "85355255";

    private Name name;
    private StudentClass studentClass;
    private Phone phone;
    private Tags tags;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        studentClass = new StudentClass(DEFAULT_CLASS);
        phone = new Phone(DEFAULT_PHONE);
        tags = new Tags();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        studentClass = personToCopy.getStudentClass();
        phone = personToCopy.getPhone();
        tags = personToCopy.getTags();
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
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code StudentClass} of the {@code Person} that we are building.
     */
    public PersonBuilder withClass(String studentClass) {
        this.studentClass = new StudentClass(studentClass);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }


    public Person build() {
        return new Person(name, studentClass, phone, tags);
    }

}
