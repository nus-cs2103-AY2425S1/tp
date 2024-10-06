package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.car.Car;
import seedu.address.model.car.CarMake;
import seedu.address.model.car.CarModel;
import seedu.address.model.car.Vin;
import seedu.address.model.car.Vrn;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}. CONVERTS JSON TO PERSON
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";
    public static final String MISSING_CAR_FIELD_MESSAGE_FORMAT = "Car's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String vin;
    private final String vrn;
    private final String make;
    private final String model;

    private final List<JsonAdaptedTag> tags = new ArrayList<>();


    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
            @JsonProperty("tags") List<JsonAdaptedTag> tags,
            @JsonProperty("vin") String vin,
            @JsonProperty("vrn") String vrn,
            @JsonProperty("make") String make,
            @JsonProperty("model") String model) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.vin = vin;
        this.vrn = vrn;
        this.make = make;
        this.model = model;

        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        if (source.getCar() != null) {
            vin = source.getCar().getVin().toString();
            vrn = source.getCar().getVrn().toString();
            make = source.getCar().getCarMake().toString();
            model = source.getCar().getCarModel().toString();
        } else {
            vin = null;
            vrn = null;
            make = null;
            model = null;
        }
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
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

        final Set<Tag> modelTags = new HashSet<>(personTags);


        if (vin == null && vrn == null && make == null && model == null) {
            return new Person(modelName, modelPhone, modelEmail, modelAddress, modelTags);
        }

        checkForMissingCarField();
        checkValidCarFields();

        // If all car details are present and valid, return a person with a car
        final Car car = new Car(new Vrn(vrn), new Vin(vin), new CarMake(make), new CarModel(model));
        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelTags, car);
    }

    public void checkForMissingCarField() throws IllegalValueException {
        // If any car details are missing, throw an exception
        if (vin == null) {
            throw new IllegalValueException(String.format(MISSING_CAR_FIELD_MESSAGE_FORMAT, Vin.class.getSimpleName()));
        }

        if (vrn == null) {
            throw new IllegalValueException(String.format(MISSING_CAR_FIELD_MESSAGE_FORMAT, Vrn.class.getSimpleName()));
        }

        if (make == null) {
            throw new IllegalValueException(String.format(MISSING_CAR_FIELD_MESSAGE_FORMAT,
                                            CarMake.class.getSimpleName()));
        }

        if (model == null) {
            throw new IllegalValueException(String.format(MISSING_CAR_FIELD_MESSAGE_FORMAT,
                                            CarModel.class.getSimpleName()));
        }
    }

    public void checkValidCarFields() throws IllegalValueException {
        if (!Vin.isValidVin(vin)) {
            throw new IllegalValueException(Vin.MESSAGE_CONSTRAINTS);
        }

        if (!Vrn.isValidVrn(vrn)) {
            throw new IllegalValueException(Vrn.MESSAGE_CONSTRAINTS);
        }

        if (!CarMake.isValidCarMake(make)) {
            throw new IllegalValueException(CarMake.MESSAGE_CONSTRAINTS);
        }

        if (!CarModel.isValidCarModel(model)) {
            throw new IllegalValueException(CarModel.MESSAGE_CONSTRAINTS);
        }
    }

}

