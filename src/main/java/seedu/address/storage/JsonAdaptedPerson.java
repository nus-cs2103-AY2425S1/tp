package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Hours;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Subject;
import seedu.address.model.person.Tutee;
import seedu.address.model.person.Tutor;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
public class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    @CsvBindByName
    private int id;
    @CsvBindByName
    private String name;
    @CsvBindByName
    private String phone;
    @CsvBindByName
    private String email;
    @CsvBindByName
    private String address;
    @CsvBindByName
    private String hours;
    @CsvCustomBindByName(converter = JsonAdaptedTagConverter.class)
    private List<JsonAdaptedTag> tags = new ArrayList<>();
    @CsvBindByName()
    private String role;
    @CsvCustomBindByName(converter = JsonAdaptedSubjectConverter.class)
    private List<JsonAdaptedSubject> subjects = new ArrayList<>();
    public JsonAdaptedPerson() {}



    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("id") int id, @JsonProperty("name") String name,
            @JsonProperty("phone") String phone, @JsonProperty("email") String email,
            @JsonProperty("address") String address, @JsonProperty("hours") String hours,
            @JsonProperty("tags") List<JsonAdaptedTag> tags, @JsonProperty("role") String role,
            @JsonProperty("subjects") List<JsonAdaptedSubject> subjects) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.hours = hours;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        if (subjects != null) {
            this.subjects.addAll(subjects);
        }
        // TODO IMPLEMENT A BETTER ROLE, FOR NOW THIS WILL PLACEHOLDER
        this.role = role;
    }

    /**
     * Alternative constructor for a {@code JsonAdaptedPerson} without the id.
     */
    public JsonAdaptedPerson(@JsonProperty("name") String name,
                             @JsonProperty("phone") String phone, @JsonProperty("email") String email,
                             @JsonProperty("address") String address, @JsonProperty("hours") String hours,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags, @JsonProperty("role") String role,
                             @JsonProperty("subjects") List<JsonAdaptedSubject> subjects) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.hours = hours;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        if (subjects != null) {
            this.subjects.addAll(subjects);
        }
        // TODO IMPLEMENT A BETTER ROLE, FOR NOW THIS WILL PLACEHOLDER
        this.role = role;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        hours = source.getHours().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        subjects.addAll(source.getSubjects().stream()
                .map(JsonAdaptedSubject::new)
                .collect(Collectors.toList()));
        role = (source instanceof Tutor) ? "Tutor" : "Tutee";
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public List<JsonAdaptedTag> getTags() {
        return tags;
    }

    public void setTags(List<JsonAdaptedTag> tags) {
        this.tags = tags;
    }

    public List<JsonAdaptedSubject> getSubjects() {
        return subjects;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        int id = this.getId();
        String name = this.getName();
        String phone = this.getPhone();
        String email = this.getEmail();
        String address = this.getAddress();
        String hours = this.getHours();
        List<JsonAdaptedTag> tags = this.getTags();
        String role = this.getRole();

        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
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

        final Set<Tag> modelTags = new HashSet<>(personTags);

        if (Objects.equals(role, "Tutor")) {
            return new Tutor(id, modelName, modelPhone, modelEmail, modelAddress, modelHours, modelTags, modelSubjects);
        } else {
            return new Tutee(id, modelName, modelPhone, modelEmail, modelAddress, modelHours, modelTags, modelSubjects);
        }

    }

}
