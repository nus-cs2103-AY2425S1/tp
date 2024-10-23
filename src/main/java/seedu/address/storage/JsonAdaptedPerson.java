package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateOfLastVisit;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmergencyContact;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";
    private static final String EMPTY_DATA_FIELD_STRING = "";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final String dateOfLastVisit;
    private final String emergencyContact;
    private final String remark;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
            @JsonProperty("tags") List<JsonAdaptedTag> tags,
            @JsonProperty("dateOfLastVisit") String dateOfLastVisit,
            @JsonProperty("emergencyContact") String emergencyContact,
            @JsonProperty("remark") String remark) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        this.dateOfLastVisit = dateOfLastVisit;
        this.emergencyContact = emergencyContact;
        this.remark = remark;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.hasEmail() ? source.getEmail().get().value : EMPTY_DATA_FIELD_STRING;
        address = source.hasAddress() ? source.getAddress().get().value : EMPTY_DATA_FIELD_STRING;
        tags.addAll(source.getTags().stream().map(JsonAdaptedTag::new).collect(Collectors.toList()));
        dateOfLastVisit = source.hasDateOfLastVisit() ? source.getDateOfLastVisit().get().value
                : EMPTY_DATA_FIELD_STRING;
        emergencyContact = source.hasEmergencyContact() ? source.getEmergencyContact().get().value.toString()
                : EMPTY_DATA_FIELD_STRING;
        remark = source.hasRemark() ? source.getRemark().value : EMPTY_DATA_FIELD_STRING;
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
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        final Optional<Email> modelEmail;
        if (email == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (email.isEmpty()) {
            modelEmail = Optional.empty();
        } else if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        } else {
            modelEmail = Optional.of(new Email(email));
        }

        Optional<Address> modelAddress;
        if (address == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (address.isEmpty()) {
            modelAddress = Optional.empty();
        } else if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        } else {
            modelAddress = Optional.of(new Address(address));
        }

        final Set<Tag> modelTags = new HashSet<>(personTags);

        Optional<DateOfLastVisit> modelDateOfLastVisit;
        if (dateOfLastVisit == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, DateOfLastVisit.class.getSimpleName()));
        }
        if (dateOfLastVisit.isEmpty()) {
            modelDateOfLastVisit = Optional.empty();
        } else if (!DateOfLastVisit.isValidDateOfLastVisit(dateOfLastVisit)) {
            throw new IllegalValueException(DateOfLastVisit.MESSAGE_CONSTRAINTS);
        } else {
            modelDateOfLastVisit = Optional.of(new DateOfLastVisit(dateOfLastVisit));
        }

        Optional<EmergencyContact> modelEmergencyContact;
        if (emergencyContact == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, EmergencyContact.class.getSimpleName()));
        } else if (emergencyContact.isEmpty()) {
            modelEmergencyContact = Optional.empty();
        } else if (!EmergencyContact.isValidEmergencyContact(emergencyContact)) {
            throw new IllegalValueException(EmergencyContact.MESSAGE_CONSTRAINTS);
        } else {
            modelEmergencyContact = Optional.of(new EmergencyContact(emergencyContact));
        }

        if (remark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName()));
        }
        final Remark modelRemark = new Remark(remark);

        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelTags, modelDateOfLastVisit,
                modelEmergencyContact, modelRemark);
    }

}
