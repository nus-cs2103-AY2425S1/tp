package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.attendance.Attendance;
import seedu.address.model.person.Email;
import seedu.address.model.person.FavouriteStatus;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Telegram;
import seedu.address.model.role.Role;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_TELEGRAM = "amyBee";
    public static final String DEFAULT_FAVOURITE_STATUS = "NOT_FAVOURITE";

    private Name name;
    private Phone phone;
    private Email email;
    private Telegram telegram;
    private Set<Role> roles;
    private Set<Attendance> attendance;
    private FavouriteStatus favouriteStatus = FavouriteStatus.NOT_FAVOURITE;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        telegram = new Telegram(DEFAULT_TELEGRAM);
        roles = new HashSet<>();
        attendance = new HashSet<>();
        favouriteStatus = FavouriteStatus.valueOf(DEFAULT_FAVOURITE_STATUS);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        telegram = personToCopy.getTelegram();
        roles = new HashSet<>(personToCopy.getRoles());
        attendance = new HashSet<>(personToCopy.getAttendance());
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
    public PersonBuilder withRoles(String ... roles) {
        this.roles = SampleDataUtil.getRoleSet(roles);
        return this;
    }

    // TODO: make a withAttendance method like withRoles to help test attendance

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withTelegram(String telegram) {
        this.telegram = new Telegram(telegram);
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
     * Sets the {@code FavouriteStatus} of the {@code Person} that we are building.
     */
    public PersonBuilder withFavourite() {
        this.favouriteStatus = FavouriteStatus.FAVOURITE;
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, telegram, roles, attendance, favouriteStatus);
    }

}
