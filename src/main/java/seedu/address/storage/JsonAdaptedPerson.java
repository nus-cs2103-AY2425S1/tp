package seedu.address.storage;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Frequency;
import seedu.address.model.person.LastPaidDate;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProfilePicFilePath;
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
    private final String birthday;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final String hasPaid;
    private final String lastPaidDate;
    private final String frequency;
    private final String profilePicFilePath;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
            @JsonProperty("birthday") String birthday, @JsonProperty("tags") List<JsonAdaptedTag> tags,
            @JsonProperty("hasPaid") String hasPaid, @JsonProperty("lastPaidDate") String lastPaidDate,
            @JsonProperty("frequency") String frequency,
            @JsonProperty("profilePicFilePath") String profilePicFilePath) {

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.birthday = birthday;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        this.hasPaid = hasPaid;
        this.lastPaidDate = lastPaidDate;
        this.frequency = frequency;
        this.profilePicFilePath = profilePicFilePath;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        birthday = source.getBirthday().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        hasPaid = source.getHasPaid().toString();
        lastPaidDate = source.getLastPaidDate().value.toString();
        frequency = source.getFrequency().value;
        profilePicFilePath = source.getProfilePicFilePath().toString();
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
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        final Address modelAddress = new Address(address);

        if (birthday == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Birthday.class.getSimpleName()));
        }
        if (!Birthday.isValidBirthday(birthday)) {
            throw new IllegalValueException(Birthday.MESSAGE_CONSTRAINTS);
        }
        final Birthday modelBirthday = new Birthday(birthday);

        final Set<Tag> modelTags = new HashSet<>(personTags);

        if (hasPaid == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "hasPaid"));
        }

        Boolean modelHasPaid = hasPaid.equals("true");

        if (lastPaidDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LastPaidDate.class.getSimpleName()));
        }
        if (!LastPaidDate.isValidDate(lastPaidDate)) {
            throw new IllegalValueException(LastPaidDate.MESSAGE_CONSTRAINTS);
        }
        final LastPaidDate modelLastPaidDate = new LastPaidDate(lastPaidDate);

        if (frequency == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Frequency.class.getSimpleName()));
        }
        if (!Frequency.isValidFrequency(frequency)) {
            throw new IllegalValueException(Frequency.MESSAGE_CONSTRAINTS);
        }
        final Frequency modelFrequency = new Frequency(frequency);

        if (profilePicFilePath == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ProfilePicFilePath.class.getSimpleName()));
        }
        final ProfilePicFilePath modelProfilePicFilePath = new ProfilePicFilePath(Paths.get(profilePicFilePath));

        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelBirthday,
                modelTags, modelHasPaid, modelLastPaidDate, modelFrequency, modelProfilePicFilePath);
    }

}
