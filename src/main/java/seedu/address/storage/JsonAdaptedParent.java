package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Parent;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Parent}.
 */
public class JsonAdaptedParent extends JsonAdaptedPerson {

    private final String childName;

    /**
     * Constructs a {@code JsonAdaptedStudent} with the given details.
     */
    @JsonCreator
    public JsonAdaptedParent(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
            @JsonProperty("childName") String childName,  @JsonProperty("tags") List<JsonAdaptedTag> tags,
            @JsonProperty("isPinned") boolean isPinned, @JsonProperty("isArchived") boolean isArchived) {
        super("Parent", name, phone, email, address, tags, isPinned, isArchived);
        this.childName = childName;
    }

    /**
     * Constructs a {@code JsonAdaptedStudent} with the given {@code Student}.
     */
    public JsonAdaptedParent(Parent source) {
        super(source);
        childName = Optional.ofNullable(source.getChildName()).map(c -> c.fullName).orElse(null);
    }

    @Override
    public Parent toModelType() throws IllegalValueException {

        String name = this.getName();
        String phone = this.getPhone();
        String email = this.getEmail();
        String address = this.getAddress();
        List<JsonAdaptedTag> tags = this.getTags();
        boolean isPinned = this.getPinned();
        boolean isArchived = this.isArchived();

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
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final Name modelChildName;
        if (childName != null) {
            if (!Name.isValidName(childName)) {
                throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
            }
            modelChildName = new Name(childName);
        } else {
            modelChildName = null;
        }

        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Parent(modelName, modelPhone, modelEmail, modelAddress, modelChildName, modelTags, isPinned, isArchived);
    }
}
