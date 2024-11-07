package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Parent;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Parent}.
 */
public class JsonAdaptedParent extends JsonAdaptedPerson {

    private final List<String> childrensNames;

    /**
     * Constructs a {@code JsonAdaptedStudent} with the given details.
     */
    @JsonCreator
    public JsonAdaptedParent(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
            @JsonProperty("childrensNames") List<String> childrensNames,
            @JsonProperty("tags") List<JsonAdaptedTag> tags, @JsonProperty("isPinned") boolean isPinned,
            @JsonProperty("isArchived") boolean isArchived) {
        super("Parent", name, phone, email, address, tags, isPinned, isArchived);
        this.childrensNames = childrensNames;
    }

    /**
     * Constructs a {@code JsonAdaptedStudent} with the given {@code Student}.
     */
    public JsonAdaptedParent(Parent source) {
        super(source);
        this.childrensNames = source.getChildrensNames().stream().map(n -> n.fullName).toList();
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

        final Set<Name> modelChildrensNames = new HashSet<>(childrensNames.stream().map(Name::new).toList());

        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Parent(modelName, modelPhone, modelEmail, modelAddress, modelChildrensNames, modelTags,
                isPinned, isArchived);
    }
}
