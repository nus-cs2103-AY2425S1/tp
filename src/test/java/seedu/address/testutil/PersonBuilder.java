package seedu.address.testutil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentStatus;
import seedu.address.model.person.Telegram;
import seedu.address.model.tag.Nickname;
import seedu.address.model.tag.Role;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_TELEGRAM = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_STUDENT_STATUS = "undergraduate 1";
    public static final String[] DEFAULT_ROLES = {"Admin"};
    public static final String DEFAULT_NICKNAME = "";

    private Name name;
    private Telegram telegram;
    private Email email;
    private StudentStatus studentStatus;
    private Set<Role> roles;
    private Nickname nickname;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        telegram = new Telegram(DEFAULT_TELEGRAM);
        email = new Email(DEFAULT_EMAIL);
        studentStatus = new StudentStatus(DEFAULT_STUDENT_STATUS);
        roles = new HashSet<>(Arrays.stream(DEFAULT_ROLES).map(Role::new).toList());
        nickname = new Nickname(DEFAULT_NICKNAME);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        telegram = personToCopy.getTelegram();
        email = personToCopy.getEmail();
        studentStatus = personToCopy.getStudentStatus();
        roles = new HashSet<>(personToCopy.getRoles());
        nickname = personToCopy.getNickname();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code roles} into a {@code Set<Role>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withRoles(String ... tags) {
        this.roles = SampleDataUtil.getRoleSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withStudentStatus(String studentStatus) {
        this.studentStatus = new StudentStatus(studentStatus);
        return this;
    }

    /**
     * Sets the {@code Telegram} of the {@code Person} that we are building.
     */
    public PersonBuilder withTelegram(String telegram) {
        this.telegram = new Telegram(telegram);
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
     * Sets the {@code Nickname} of the {@code Person} that we are building.
     */
    public PersonBuilder withNickname(String nickname) {
        this.nickname = new Nickname(nickname);
        return this;
    }

    public Person build() {
        return new Person(name, telegram, email, studentStatus, roles, nickname);
    }

}
