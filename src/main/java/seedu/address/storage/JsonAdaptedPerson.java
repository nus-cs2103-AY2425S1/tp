package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Email;
import seedu.address.model.person.Information;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Supplier;
import seedu.address.model.product.Ingredient;
import seedu.address.model.product.IngredientCatalogue;
import seedu.address.model.product.Ingredients;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.Remark;


/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String remark;
    private final String information; // Customer-specific
    private final List<String> ingredientsSupplied; // Supplier-specific

    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name,
                             @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email,
                             @JsonProperty("address") String address,
                             @JsonProperty("remark") String remark,
                             @JsonProperty("information") String information,
                             @JsonProperty("ingredientsSupplied") List<String> ingredientsSupplied,
                             @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.remark = remark;
        this.information = information;
        this.ingredientsSupplied = ingredientsSupplied != null ? new ArrayList<>(ingredientsSupplied) : null;
        if (tagged != null) {
            tagged.addAll(tags);
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
        remark = source.getRemark().value;
        // Setting the 'information' field
        if (source instanceof Customer) {
            Customer customer = (Customer) source;
            information = customer.getInformation().value;
        } else {
            information = null;
        }
        // Setting the 'ingredientsSupplied' field
        if (source instanceof Supplier) {
            Supplier supplier = (Supplier) source;
            ingredientsSupplied = supplier.getIngredientsSupplied().getIngredientNames();
        } else {
            ingredientsSupplied = null;
        }

        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
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

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        final Email modelEmail;

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Email.class.getSimpleName()));
        } else if (email.isEmpty()) {
            modelEmail = new Email();
        } else if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        } else {
            modelEmail = new Email(email);
        }

        final Address modelAddress;

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Address.class.getSimpleName()));
        } else if (address.isEmpty()) {
            modelAddress = new Address();
        } else if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        } else {
            modelAddress = new Address(address);
        }

        if (remark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Remark.class.getSimpleName()));
        }

        final Remark modelRemark = new Remark(remark);

        final Set<Tag> modelTags = new HashSet<>(personTags);
        if (information != null) {
            final Information modelInformation = new Information(information);
            return new Customer(modelName, modelPhone, modelEmail, modelAddress,
                    modelInformation, modelRemark, modelTags);
        } else if (ingredientsSupplied != null) {
            System.out.println("IngredientsSupplied is not null. Looking up in the catalogue...");
            List<Ingredient> ingredientList = new ArrayList<>();
            IngredientCatalogue catalogue = IngredientCatalogue.getInstance(); // Access the singleton

            for (String name : ingredientsSupplied) {
                try {
                    Ingredient catalogueIngredient = catalogue.getIngredientByName(name);
                    ingredientList.add(catalogueIngredient); // Use the catalogue ingredient
                } catch (NoSuchElementException e) {
                    throw new IllegalValueException("Ingredient '" + name
                            + "' not found in the catalogue. Please add it first.");
                }
            }

            final Ingredients modelIngredients = new Ingredients(ingredientList);
            return new Supplier(modelName, modelPhone, modelEmail, modelAddress,
                    modelIngredients, modelRemark, modelTags);
        }
        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelRemark, modelTags);
    }
}
