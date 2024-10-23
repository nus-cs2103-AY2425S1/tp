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
import seedu.address.model.person.Hours;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Subject;
import seedu.address.model.person.Tutee;
import seedu.address.model.tag.Tag;


/**
 * Jackson-friendly version of {@link Tutee}.
 */
class JsonAdaptedTutee extends JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Tutee's %s field is missing!";


    /**
     * Constructs a {@code JsonAdaptedTutee} with the given tutee details.
     */
    @JsonCreator
    public JsonAdaptedTutee(@JsonProperty("id") int id, @JsonProperty("name") String name,
                            @JsonProperty("phone") String phone, @JsonProperty("email") String email,
                            @JsonProperty("address") String address, @JsonProperty("hours") String hours,
                            @JsonProperty("tags") List<JsonAdaptedTag> tags,
                            @JsonProperty("subjects") List<JsonAdaptedSubject> subjects) {

        super(id, name, phone, email, address, hours, tags, "Tutee", subjects);
    }

    @JsonCreator
    public JsonAdaptedTutee(@JsonProperty("name") String name,
                            @JsonProperty("phone") String phone, @JsonProperty("email") String email,
                            @JsonProperty("address") String address, @JsonProperty("hours") String hours,
                            @JsonProperty("tags") List<JsonAdaptedTag> tags,
                            @JsonProperty("subjects") List<JsonAdaptedSubject> subjects) {

        super(name, phone, email, address, hours, tags, "Tutee", subjects);
    }

    /**
     * Converts a given {@code Tutee} into this class for Jackson use.
     */
    public JsonAdaptedTutee(Person source) {
        super(source.getId(), source.getName().fullName, source.getPhone().value, source.getEmail().value,
                source.getAddress().value, source.getHours().value,
                        source.getTags().stream()
                        .map(JsonAdaptedTag::new)
                        .collect(Collectors.toList()),
                "Tutee", source.getSubjects().stream().map(JsonAdaptedSubject::new).collect(Collectors.toList())
        );
    }



    /**
     * Converts this Jackson-friendly adapted tutee object into the model's {@code Tutee} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tutee.
     */
    public Tutee toModelType() throws IllegalValueException {
        String name = this.getName();
        String phone = this.getPhone();
        String email = this.getEmail();
        String address = this.getAddress();
        String hours = this.getHours();
        List<JsonAdaptedTag> tags = this.getTags();
        List<JsonAdaptedSubject> subjects = this.getSubjects();

        final List<Tag> tuteeTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            tuteeTags.add(tag.toModelType());
        }

        final List<Subject> personSubjects = new ArrayList<>();
        for (JsonAdaptedSubject subject : subjects) {
            personSubjects.add(subject.toModelType());
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
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (hours == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Hours.class.getSimpleName()));
        }
        if (!Hours.isValidHours(hours)) {
            throw new IllegalValueException(Hours.MESSAGE_CONSTRAINTS);
        }
        final Hours modelHours = new Hours(hours);

        final Set<Subject> modelSubjects = new HashSet<>(personSubjects);


        final Set<Tag> modelTags = new HashSet<>(tuteeTags);

        return new Tutee(modelName, modelPhone, modelEmail, modelAddress, modelHours, modelTags, modelSubjects);
    }

}
