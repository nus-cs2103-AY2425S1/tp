package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Property;
import seedu.address.model.tag.Tag;

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
    private final List<JsonAdaptedProperty> sellProperties = new ArrayList<>();
    private final List<JsonAdaptedProperty> buyProperties = new ArrayList<>();
    private final List<JsonAdaptedProperty> soldProperties = new ArrayList<>();
    private final List<JsonAdaptedProperty> boughtProperties = new ArrayList<>();


    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email, @JsonProperty("address") String address,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags,
                             @JsonProperty("sellProperties") List<JsonAdaptedProperty> sellProperties,
                             @JsonProperty("buyProperties") List<JsonAdaptedProperty> buyProperties,
                             @JsonProperty("soldProperties") List<JsonAdaptedProperty> soldProperties,
                             @JsonProperty("boughtProperties") List<JsonAdaptedProperty> boughtProperties) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        if (sellProperties != null) {
            this.sellProperties.addAll(sellProperties);
        }
        if (buyProperties != null) {
            this.buyProperties.addAll(buyProperties);
        }
        if (soldProperties != null) {
            this.soldProperties.addAll(soldProperties);
        }
        if (boughtProperties != null) {
            this.boughtProperties.addAll(boughtProperties);
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
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        sellProperties.addAll(source.getListOfSellingProperties().stream()
                .map(JsonAdaptedProperty::new)
                .collect(Collectors.toList()));
        buyProperties.addAll(source.getListOfBuyingProperties().stream()
                .map(JsonAdaptedProperty::new)
                .collect(Collectors.toList()));
        soldProperties.addAll(source.getListOfPropertiesSold().stream()
                .map(JsonAdaptedProperty::new)
                .collect(Collectors.toList()));
        boughtProperties.addAll(source.getListOfPropertiesBought().stream()
                .map(JsonAdaptedProperty::new)
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

        final List<Property> personSellProperties = new ArrayList<>();
        for (JsonAdaptedProperty property : sellProperties) {
            personSellProperties.add(property.toModelType());
        }

        final List<Property> personBuyProperties = new ArrayList<>();
        for (JsonAdaptedProperty property : buyProperties) {
            personBuyProperties.add(property.toModelType());
        }

        final List<Property> personSoldProperties = new ArrayList<>();
        for (JsonAdaptedProperty property : soldProperties) {
            personSoldProperties.add(property.toModelType());
        }

        final List<Property> personBoughtProperties = new ArrayList<>();
        for (JsonAdaptedProperty property : boughtProperties) {
            personBoughtProperties.add(property.toModelType());
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

        final ObservableList<Property> modelSellProperties = FXCollections.observableArrayList(personSellProperties);
        //final List<Property> modelSellProperties = new ArrayList<>(personSellProperties);

        //final ObservableList<Property> modelBuyProperties = new ArrayList<>(personBuyProperties);
        final ObservableList<Property> modelBuyProperties = FXCollections.observableArrayList(personBuyProperties);
        final ObservableList<Property> modelSoldProperties = FXCollections.observableArrayList(personSoldProperties);
        final ObservableList<Property> modelBoughtProperties = FXCollections
                .observableArrayList(personBoughtProperties);


        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelTags,
                modelSellProperties, modelBuyProperties, modelSoldProperties, modelBoughtProperties);
    }

}
