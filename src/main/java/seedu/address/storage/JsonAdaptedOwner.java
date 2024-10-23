package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.owner.Address;
import seedu.address.model.owner.Email;
import seedu.address.model.owner.IdentificationCardNumber;
import seedu.address.model.owner.Name;
import seedu.address.model.owner.Owner;
import seedu.address.model.owner.Phone;

/**
 * Jackson-friendly version of {@link Owner}.
 */
class JsonAdaptedOwner {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Owner's %s field is missing!";

    private final String identificationNumber;
    private final String name;
    private final String phone;
    private final String email;
    private final String address;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedOwner(@JsonProperty("identificationNumber") String identificationNumber,
                            @JsonProperty("name") String name, @JsonProperty("phone") String phone,
                            @JsonProperty("email") String email, @JsonProperty("address") String address) {
        this.identificationNumber = identificationNumber;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    /**
     * Converts a given {@code Owner} into this class for Jackson use.
     */
    public JsonAdaptedOwner(Owner source) {
        identificationNumber = source.getIdentificationNumber().value;
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Owner} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted owner.
     */
    public Owner toModelType() throws IllegalValueException {
        if (identificationNumber == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    IdentificationCardNumber.class.getSimpleName()));
        }
        if (!IdentificationCardNumber.isValidIcNumber(identificationNumber)) {
            throw new IllegalValueException(IdentificationCardNumber.MESSAGE_CONSTRAINTS);
        }
        final IdentificationCardNumber modelIdentificationNumber = new IdentificationCardNumber(identificationNumber);

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

        return new Owner(modelIdentificationNumber, modelName, modelPhone, modelEmail, modelAddress);
    }

}
