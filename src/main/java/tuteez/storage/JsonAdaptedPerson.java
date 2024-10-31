package tuteez.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tuteez.commons.exceptions.IllegalValueException;
import tuteez.model.person.Address;
import tuteez.model.person.Email;
import tuteez.model.person.Name;
import tuteez.model.person.Person;
import tuteez.model.person.Phone;
import tuteez.model.person.TelegramUsername;
import tuteez.model.person.lesson.Lesson;
import tuteez.model.remark.RemarkList;
import tuteez.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String telegramUsername;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final List<JsonAdaptedLesson> lessons = new ArrayList<>();
    private final JsonAdaptedRemarkList remarkList;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
            @JsonProperty("telegramUsername") String telegramUsername,
            @JsonProperty("tags") List<JsonAdaptedTag> tags, @JsonProperty("lessons") List<JsonAdaptedLesson> lessons,
            @JsonProperty("remarkList") JsonAdaptedRemarkList remarkList) {

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.telegramUsername = telegramUsername;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        if (lessons != null) {
            this.lessons.addAll(lessons);
        }
        this.remarkList = remarkList;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        telegramUsername = source.getTelegramUsername().telegramUsername;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        lessons.addAll(source.getLessons().stream()
                .map(JsonAdaptedLesson::new)
                .collect(Collectors.toList()));
        remarkList = new JsonAdaptedRemarkList(source.getRemarkList());
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {

        final Name modelName = getModelName(name);

        final Phone modelPhone = getModelPhone(phone);

        final Email modelEmail = getModelEmail(email);

        final Address modelAddress = getModelAddress(address);

        TelegramUsername modelTelegramUsername = getModelTelegramUsername(telegramUsername);

        final RemarkList modelRemarkList = getModelRemarkList(remarkList);

        final Set<Tag> modelTags = getModelTags(tags);

        final List<Lesson> modelLessons = getModelLessons(lessons);

        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelTelegramUsername, modelTags,
                modelLessons, modelRemarkList);
    }

    private Name getModelName(String name) throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(name);
    }

    private Phone getModelPhone(String phone) throws IllegalValueException {
        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(phone);
    }

    private Email getModelEmail(String email) throws IllegalValueException {
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(email);
    }

    private Address getModelAddress(String address) throws IllegalValueException {
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(address);
    }

    private TelegramUsername getModelTelegramUsername(String username) throws IllegalValueException {
        if (username == null) {
            return TelegramUsername.empty();
        }
        if (!TelegramUsername.isValidTelegramHandle(username)) {
            throw new IllegalValueException(TelegramUsername.MESSAGE_CONSTRAINTS);
        }
        return TelegramUsername.of(username);
    }

    private Set<Tag> getModelTags(List<JsonAdaptedTag> tags) throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }
        return new HashSet<>(personTags);
    }

    private List<Lesson> getModelLessons(List<JsonAdaptedLesson> lessons) throws IllegalValueException {
        final List<Lesson> personLessons = new ArrayList<>();
        for (JsonAdaptedLesson lesson : lessons) {
            personLessons.add(lesson.toModelType());
        }
        return new ArrayList<>(personLessons);
    }

    private RemarkList getModelRemarkList(JsonAdaptedRemarkList remarkList) throws IllegalValueException {
        if (remarkList == null) {
            return new RemarkList();
        }
        return remarkList.toModelType();
    }

}
