package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Schedule;
import seedu.address.model.person.SocialMedia;
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
    public static final String DEFAULT_SCHEDULE_NAME = "";
    public static final String DEFAULT_SCHEDULE_DATE = "";
    public static final String DEFAULT_SCHEDULE_TIME = "";
    public static final String DEFAULT_SOCIALMEDIA = " ";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Schedule schedule;
    private Set<Tag> tags;
    private SocialMedia socialMedia;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        schedule = new Schedule(DEFAULT_SCHEDULE_NAME, DEFAULT_SCHEDULE_DATE, DEFAULT_SCHEDULE_TIME);
        tags = new HashSet<>();
        socialMedia = new SocialMedia(DEFAULT_SOCIALMEDIA, SocialMedia.Platform.UNNAMED);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        schedule = personToCopy.getSchedule();
        tags = new HashSet<>(personToCopy.getTags());
        socialMedia = personToCopy.getSocialMedia();
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
     * Sets the {@code scheduleName} of the {@code Person} that we are building.
     */
    public PersonBuilder withScheduleName(String scheduleName) {
        this.schedule = new Schedule(scheduleName, this.schedule.dateString, this.schedule.timeString);
        return this;
    }

    /**
     * Sets the {@code scheduleDate} of the {@code Person} that we are building.
     */
    public PersonBuilder withScheduleDate(String scheduleDate) {
        this.schedule = new Schedule(this.schedule.scheduleName, scheduleDate, this.schedule.timeString);
        return this;
    }

    /**
     * Sets the {@code scheduleTime} of the {@code Person} that we are building.
     */
    public PersonBuilder withScheduleTime(String scheduleTime) {
        this.schedule = new Schedule(this.schedule.scheduleName, this.schedule.dateString, scheduleTime);
        return this;
    }

    /**
     * Sets the {@code SocialMedia} of the {@code Person} that we are building.
     */
    public PersonBuilder withSocialMedia(String socialMedia) {
        if (socialMedia.startsWith("[ig-")) {
            this.socialMedia = new SocialMedia(socialMedia.substring(4, socialMedia.length() - 1),
                    SocialMedia.Platform.INSTAGRAM);
            return this;
        } else if (socialMedia.startsWith("[fb-")) {
            this.socialMedia = new SocialMedia(socialMedia.substring(4, socialMedia.length() - 1),
                    SocialMedia.Platform.FACEBOOK);
            return this;
        } else {
            this.socialMedia = new SocialMedia(socialMedia.substring(4, socialMedia.length() - 1),
                    SocialMedia.Platform.CAROUSELL);
            return this;
        }
    }

    /**
     * builds a new person.
     * @return Person
     */
    public Person build() {
        Person p = new Person(name, phone, email, address, schedule, tags);
        p.setSocialMedia(socialMedia);
        return p;
    }
}
