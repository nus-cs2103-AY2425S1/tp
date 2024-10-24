package seedu.address.testutil;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.person.ContactType;
import seedu.address.model.person.Email;
import seedu.address.model.person.ModuleName;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.TelegramHandle;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_CONTACTTYPE = "Work";
    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_TELEHANDLE = "@amybee";
    public static final String DEFAULT_MODULENAME = "CS1101S";

    private ContactType contactType;
    private Name name;
    private Optional<Phone> phone;
    private Optional<Email> email;
    private TelegramHandle telegramHandle;
    private ModuleName moduleName;
    private Set<Tag> tags;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        contactType = new ContactType(DEFAULT_CONTACTTYPE);
        name = new Name(DEFAULT_NAME);
        phone = Optional.of(new Phone(DEFAULT_PHONE));
        email = Optional.of(new Email(DEFAULT_EMAIL));
        telegramHandle = new TelegramHandle(DEFAULT_TELEHANDLE);
        moduleName = new ModuleName(DEFAULT_MODULENAME);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        contactType = personToCopy.getContactType();
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        telegramHandle = personToCopy.getTelegramHandle();
        moduleName = personToCopy.getModuleName();
        tags = new HashSet<>(personToCopy.getTags());
    }

    /**
     * Sets the {@code ContactType} of the {@code Person} that we are building.
     */
    public PersonBuilder withContactType(String contactType) {
        this.contactType = new ContactType(contactType);
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
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = (phone == null || phone.isEmpty()) ? Optional.empty() : Optional.of(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = (email == null || email.isEmpty()) ? Optional.empty() : Optional.of(new Email(email));
        return this;
    }

    /**
     * Sets the {@code TelegramHandle} of the {@code Person} that we are building.
     */
    public PersonBuilder withTelegramHandle(String telegramHandle) {
        this.telegramHandle = new TelegramHandle(telegramHandle);
        return this;
    }

    /**
     * Sets the {@code ModuleName} of the {@code Person} that we are building.
     */
    public PersonBuilder withModuleName(String moduleName) {
        this.moduleName = new ModuleName(moduleName);
        return this;
    }

    public Person build() {
        return new Person(contactType, name, phone, email, telegramHandle, moduleName, tags);
    }

}
