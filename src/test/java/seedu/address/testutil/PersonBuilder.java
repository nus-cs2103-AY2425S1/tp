package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Age;
import seedu.address.model.person.Detail;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.tag.StudyGroupTag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_EMAIL = "amy@example.com";
    public static final String DEFAULT_GENDER = "F";
    public static final String DEFAULT_AGE = "20";

    private Name name;
    private Email email;
    private Gender gender;
    private Age age;
    private Detail detail;
    private Set<StudyGroupTag> studyGroupTags;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        email = new Email(DEFAULT_EMAIL);
        gender = new Gender(DEFAULT_GENDER);
        age = new Age(DEFAULT_AGE);
        detail = new Detail("");
        studyGroupTags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        email = personToCopy.getEmail();
        gender = personToCopy.getGender();
        age = personToCopy.getAge();
        studyGroupTags = new HashSet<>(personToCopy.getStudyGroupTags());
        detail = personToCopy.getDetail();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
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
     * Sets the {@code Gender} of the {@code Person} that we are building.
     */
    public PersonBuilder withGender(String gender) {
        this.gender = new Gender(gender);
        return this;
    }

    /**
     * Sets the {@code Age} of the {@code Person} that we are building.
     */
    public PersonBuilder withAge(String age) {
        this.age = new Age(age);
        return this;
    }

    /**
     * Sets the {@code Detail} of the {@code Person} that we are building.
     */
    public PersonBuilder withDetail(String detail) {
        this.detail = detail != null ? new Detail(detail) : null;
        return this;
    }

    /**
     * Parses the {@code studyGroups} into a {@code Set<StudyGroupTag>} and set it to the {@code Person} that we are
     * building.
     */
    public PersonBuilder withStudyGroupTags(String... studyGroups) {
        this.studyGroupTags = SampleDataUtil.getStudyGroupTagSet(studyGroups);
        return this;
    }

    /**
     * Parses the {@code studyGroups} into a {@code Set<StudyGroupTag>} and add it to the {@code Person} that we are
     * building's existing set of study group tags .
     */
    public PersonBuilder withAppendStudyGroupTags(String... studyGroups) {
        this.studyGroupTags.addAll(SampleDataUtil.getStudyGroupTagSet(studyGroups));
        return this;
    }

    public Person build() {
        return new Person(name, email, gender, age, detail, studyGroupTags);
    }

}
