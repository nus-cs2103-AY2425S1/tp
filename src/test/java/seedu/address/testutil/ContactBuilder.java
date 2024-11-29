package seedu.address.testutil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Nickname;
import seedu.address.model.contact.Role;
import seedu.address.model.contact.StudentStatus;
import seedu.address.model.contact.TelegramHandle;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Contact objects.
 */
public class ContactBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_TELEGRAM = "85355255";

    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_STUDENT_STATUS = "Undergraduate 1";

    public static final String[] DEFAULT_ROLES = {"Admin"};

    public static final String DEFAULT_NICKNAME = "";

    private Name name;
    private TelegramHandle telegramHandle;
    private Email email;
    private StudentStatus studentStatus;
    private Set<Role> roles;
    private Nickname nickname;

    /**
     * Creates a {@code ContactBuilder} with the default details.
     */
    public ContactBuilder() {
        name = new Name(DEFAULT_NAME);
        telegramHandle = new TelegramHandle(DEFAULT_TELEGRAM);
        email = new Email(DEFAULT_EMAIL);
        studentStatus = new StudentStatus(DEFAULT_STUDENT_STATUS);
        roles = new HashSet<>(Arrays.stream(DEFAULT_ROLES).map(Role::new).toList());
        nickname = new Nickname(DEFAULT_NICKNAME);
    }

    //@@author
    /**
     * Initializes the ContactBuilder with the data of {@code contactToCopy}.
     */
    public ContactBuilder(Contact contactToCopy) {
        name = contactToCopy.getName();
        telegramHandle = contactToCopy.getTelegramHandle();
        email = contactToCopy.getEmail();
        studentStatus = contactToCopy.getStudentStatus();
        roles = new HashSet<>(contactToCopy.getRoles());
        nickname = contactToCopy.getNickname();
    }

    /**
     * Sets the {@code Name} of the {@code Contact} that we are building.
     */
    public ContactBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code roles} into a {@code Set<Role>} and set it to the {@code Contact} that we are building.
     */
    public ContactBuilder withRoles(String ... roles) {
        this.roles = SampleDataUtil.getRoleSet(roles);
        return this;
    }

    //@@author LowXiSi
    /**
     * Sets the {@code Address} of the {@code Contact} that we are building.
     */
    public ContactBuilder withStudentStatus(String studentStatus) {
        this.studentStatus = new StudentStatus(studentStatus);
        return this;
    }

    //@@author WinstonJin
    /**
     * Sets the {@code TelegramHandle} of the {@code Contact} that we are building.
     */
    public ContactBuilder withTelegramHandle(String telegramHandle) {
        this.telegramHandle = new TelegramHandle(telegramHandle);
        return this;
    }

    //@@author
    /**
     * Sets the {@code Email} of the {@code Contact} that we are building.
     */
    public ContactBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    //@@author cth06-Github
    /**
     * Sets the {@code Nickname} of the {@code Contact} that we are building.
     */
    public ContactBuilder withNickname(String nickname) {
        this.nickname = new Nickname(nickname);
        return this;
    }

    //@@author WinstonJin
    /**
     * Builds and returns a new {@code Contact} instance using the current values set
     * for name, telegramHandle, email, studentStatus, roles, and nickname.
     *
     * @return A new {@code Contact} object with the properties specified by
     *         the current state of the builder.
     */
    //@@author
    public Contact build() {
        return new Contact(name, telegramHandle, email, studentStatus, roles, nickname);
    }

}
