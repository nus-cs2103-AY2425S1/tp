package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Schedule;
import seedu.address.model.person.SocialMedia;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String scheduleName;
    private final String date;
    private final String time;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final String socialMedia;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email, @JsonProperty("address") String address,
                             @JsonProperty("scheduleName") String scheduleName, @JsonProperty("date") String date,
                             @JsonProperty("time") String time, @JsonProperty("tags") List<JsonAdaptedTag> tags,
                             @JsonProperty("socialmedia") String socialMedia) {

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.scheduleName = scheduleName;
        this.date = date;
        this.time = time;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        this.socialMedia = socialMedia;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        scheduleName = source.getSchedule().scheduleName;
        date = source.getSchedule().dateString;
        time = source.getSchedule().timeString;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        socialMedia = source.getSocialMedia().toString();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (scheduleName == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, Schedule.class.getSimpleName()));
        }
        if (!Schedule.isValidName(scheduleName)) {
            throw new IllegalValueException(Schedule.SCHEDULE_NAME_CONSTRAINTS);
        }
        if (date == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, Schedule.class.getSimpleName()));
        }
        if (!Schedule.isValidDate(date)) {
            throw new IllegalValueException(Schedule.DATE_CONSTRAINTS);
        }

        if (time == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, Schedule.class.getSimpleName()));
        }
        if (!Schedule.isValidTime(time)) {
            throw new IllegalValueException(Schedule.TIME_CONSTRAINTS);
        }
        final Schedule schedule = new Schedule(scheduleName, date, time);

        if (socialMedia == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    SocialMedia.class.getSimpleName()));
        }
        final SocialMedia modelSocialMedia;
        if (!socialMedia.equals(" ")) {
            if (socialMedia.startsWith("[ig-")) {
                if (!SocialMedia.isValidHandleName(socialMedia.substring(4, socialMedia.length() - 1))) {
                    throw new IllegalValueException(SocialMedia.MESSAGE_CONSTRAINTS);
                }
                modelSocialMedia = new SocialMedia(socialMedia.substring(4, socialMedia.length() - 1),
                        SocialMedia.Platform.INSTAGRAM);
            } else if (socialMedia.startsWith("[fb-")) {
                if (!SocialMedia.isValidHandleName(socialMedia.substring(4, socialMedia.length() - 1))) {
                    throw new IllegalValueException(SocialMedia.MESSAGE_CONSTRAINTS);
                }
                modelSocialMedia = new SocialMedia(socialMedia.substring(4, socialMedia.length() - 1),
                        SocialMedia.Platform.FACEBOOK);
            } else {
                if (!SocialMedia.isValidHandleName(socialMedia.substring(4, socialMedia.length() - 1))) {
                    throw new IllegalValueException(SocialMedia.MESSAGE_CONSTRAINTS);
                }
                modelSocialMedia = new SocialMedia(socialMedia.substring(4, socialMedia.length() - 1),
                        SocialMedia.Platform.CAROUSELL);
            }
        } else {
            modelSocialMedia = new SocialMedia(" ", SocialMedia.Platform.UNNAMED);
        }

        final Set<Tag> modelTags = new HashSet<>(personTags);

        Person person = new Person(modelName, modelPhone, modelEmail, modelAddress, schedule, modelTags);
        person.setSocialMedia(modelSocialMedia);
        return person;
    }
}
