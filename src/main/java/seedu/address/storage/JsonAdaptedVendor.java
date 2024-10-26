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
import seedu.address.model.person.Phone;
import seedu.address.model.person.Vendor;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;
import seedu.address.model.wedding.Wedding;

class JsonAdaptedVendor extends JsonAdaptedPerson {

    /**
     * Constructs a {@code JsonAdaptedVendor} with the given vendor details.
     */
    @JsonCreator
    public JsonAdaptedVendor(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email, @JsonProperty("address") String address,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags,
                             @JsonProperty("weddings") List<JsonAdaptedWedding> weddings,
                             @JsonProperty("tasks") List<JsonAdaptedTask> tasks) {
        super(name, phone, email, address, tags, weddings, tasks);
    }

    /**
     * Converts a given {@code Vendor} into this class for Jackson use.
     */
    public JsonAdaptedVendor(Vendor source) {
        super(source);
    }


    /**
     * Converts this Jackson-friendly adapted vendor object into the model's {@code Vendor} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted vendor.
     */
    public Vendor toModelType() throws IllegalValueException {
        final List<Tag> vendorTags = new ArrayList<>();
        final List<Wedding> vendorWeddings = new ArrayList<>();
        final List<Task> vendorTasks = new ArrayList<>();

        for (JsonAdaptedTag tag : getTags()) {
            vendorTags.add(tag.toModelType());
        }

        for (JsonAdaptedTask task : getTasks()) {
            vendorTasks.add(task.toModelType());
        }

        for (JsonAdaptedWedding wedding : getWeddings()) {
            if (!Wedding.isValidWeddingName(wedding.getWeddingName())) {
                throw new IllegalValueException(Wedding.MESSAGE_CONSTRAINTS);
            }
            vendorWeddings.add(wedding.toModelType());
        }

        // Validation and conversion of inherited fields using getters
        if (getName() == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(getName())) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(getName());

        if (getPhone() == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(getPhone())) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(getPhone());

        if (getEmail() == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(getEmail())) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(getEmail());

        if (getAddress() == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        final Address modelAddress = new Address(getAddress());

        final Set<Tag> modelTags = new HashSet<>(vendorTags);
        final Set<Wedding> modelWeddings = new HashSet<>(vendorWeddings);
        final Set<Task> modelTasks = new HashSet<>(vendorTasks);

        return new Vendor(modelName, modelPhone, modelEmail, modelAddress, modelTags, modelWeddings, modelTasks);
    }
}
