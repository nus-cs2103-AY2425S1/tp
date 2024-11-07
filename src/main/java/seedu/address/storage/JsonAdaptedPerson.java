package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Parent;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;

/**
 * Jackson-friendly version of {@link Person}.
 */
public abstract class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String type;
    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final boolean isPinned;
    private final boolean isArchived;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    public JsonAdaptedPerson(String type, String name, String phone, String email, String address,
            List<JsonAdaptedTag> tags, boolean isPinned, boolean isArchived) {

        this.type = type;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        this.isPinned = isPinned;
        this.isArchived = isArchived;
    }

    /**
     * Converts a given {@code Student} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Student source) {
        type = "Student";
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        tags.addAll(source.getTags().stream().map(JsonAdaptedTag::new).collect(Collectors.toList()));
        isPinned = source.isPinned();
        isArchived = source.isArchived();
    }

    /**
     * Converts a given {@code Parent} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Parent source) {
        type = "Parent";
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        tags.addAll(source.getTags().stream().map(JsonAdaptedTag::new).collect(Collectors.toList()));
        isPinned = source.isPinned();
        isArchived = source.isArchived();
    }

    /**
     * Creates an instance of the appropriate subtype of {@code JsonAdaptedPerson} from the given details.
     */
    @JsonCreator
    public static JsonAdaptedPerson of(@JsonProperty("type") String type, @JsonProperty("name") String name,
            @JsonProperty("phone") String phone, @JsonProperty("email") String email,
            @JsonProperty("address") String address, @JsonProperty("education") String education,
            @JsonProperty("grade") String grade, @JsonProperty("parentName") String parentName,
            @JsonProperty("childrensNames") List<String> childrensNames,
            @JsonProperty("tags") List<JsonAdaptedTag> tags, @JsonProperty("isPinned") boolean isPinned,
            @JsonProperty("isArcived") boolean isArchived)
            throws IllegalValueException {

        switch (type) {
        case ("Student"):
            return new JsonAdaptedStudent(name, phone, email, address, education, grade, parentName, tags, isPinned,
                    isArchived);

        case ("Parent"):
            return new JsonAdaptedParent(name, phone, email, address, childrensNames, tags, isPinned, isArchived);

        default:
            throw new IllegalValueException("Illegal contact type in save file");
        }

    }

    /**
     * Creates an instance of the appropriate subtype of {@code JsonAdaptedPerson} from the given {@code Person}.
     */
    public static JsonAdaptedPerson of(Person source) {
        if (source instanceof Student) {
            return new JsonAdaptedStudent((Student) source);
        }
        return new JsonAdaptedParent((Parent) source);
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public List<JsonAdaptedTag> getTags() {
        return tags;
    }

    public boolean getPinned() {
        return isPinned;
    }

    public boolean isArchived() {
        return isArchived;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public abstract Person toModelType() throws IllegalValueException;

}
