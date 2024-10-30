package bizbook.storage;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import bizbook.commons.exceptions.IllegalValueException;
import bizbook.model.person.Address;
import bizbook.model.person.Email;
import bizbook.model.person.Name;
import bizbook.model.person.Note;
import bizbook.model.person.Person;
import bizbook.model.person.Phone;
import bizbook.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final List<JsonAdaptedNote> notes = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
            @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        // to modify for interaction with GUI
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .toList());
        notes.addAll(source.getNotes().stream()
                .map(JsonAdaptedNote::new)
                .toList());
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

        final List<Note> personNotes = new ArrayList<>();
        for (JsonAdaptedNote note : notes) {
            personNotes.add(note.toModelType());
        }

        final Name modelName = new Name(validateField(name, Name.class.getSimpleName(), Name.MESSAGE_CONSTRAINTS,
                Name::isValidName));
        final Phone modelPhone = new Phone(validateField(phone, Phone.class.getSimpleName(), Phone.MESSAGE_CONSTRAINTS,
                Phone::isValidPhone));
        final Email modelEmail = new Email(validateField(email, Email.class.getSimpleName(), Email.MESSAGE_CONSTRAINTS,
                Email::isValidEmail));
        final Address modelAddress = new Address(validateField(address, Address.class.getSimpleName(),
                Address.MESSAGE_CONSTRAINTS, Address::isValidAddress));


        final Set<Tag> modelTags = new LinkedHashSet<>(personTags);
        final Set<Note> modelNotes = new LinkedHashSet<>(personNotes);

        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelTags, modelNotes);
    }

    /**
     * Validates a field based on its presence and provided constraints.
     *
     * @param field the field to be validated.
     * @param fieldName the name of the field that is used for error messages.
     * @param constraintMessage the message to show if validation fails.
     * @param isValid a function that checks if the field meets the validation constraints.
     * @return the validated field value.
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    private String validateField(String field, String fieldName, String constraintMessage,
                                 Function<String, Boolean> isValid) throws IllegalValueException {
        // Check if the field is missing
        if (field == null) {
            String errorMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, fieldName);
            throw new IllegalValueException(errorMessage);
        }

        // Check if the field violates constraints, if not valid, throw IllegalValueException
        if (!isValid.apply(field)) {
            throw new IllegalValueException(constraintMessage);
        }
        return field;
    }
}
