package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.Vendor;
import seedu.address.model.tag.Tag;
import seedu.address.model.wedding.Wedding;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_VENDOR = "Vendors list contains duplicate vendor(s).";
    public static final String MESSAGE_DUPLICATE_TAG = "Tags list contains duplicate tag(s).";
    public static final String MESSAGE_DUPLICATE_WEDDING = "Weddings list contains duplicate wedding(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedVendor> vendors = new ArrayList<>();
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final List<JsonAdaptedWedding> weddings = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons, tags, and weddings.
     */
    @JsonCreator
    public JsonSerializableAddressBook(
            @JsonProperty("persons") List<JsonAdaptedPerson> persons,
            @JsonProperty("vendors") List<JsonAdaptedVendor> vendors,
            @JsonProperty("tags") List<JsonAdaptedTag> tags,
            @JsonProperty("weddings") List<JsonAdaptedWedding> weddings) {
        this.persons.addAll(persons);
        this.vendors.addAll(vendors);
        this.tags.addAll(tags);
        this.weddings.addAll(weddings);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        vendors.addAll(source.getVendorList().stream().map(JsonAdaptedVendor::new).collect(Collectors.toList()));
        tags.addAll(source.getTagList().stream().map(JsonAdaptedTag::new).collect(Collectors.toList()));
        weddings.addAll(source.getWeddingList().stream().map(JsonAdaptedWedding::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
        }
        for (JsonAdaptedVendor jsonAdaptedVendor : vendors) {
            Vendor vendor = jsonAdaptedVendor.toModelType();
            if (addressBook.hasVendor(vendor)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_VENDOR);
            }
            addressBook.addVendor(vendor);
        }
        for (JsonAdaptedTag jsonAdaptedTag : tags) {
            Tag tag = jsonAdaptedTag.toModelType();
            if (addressBook.hasTag(tag)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TAG);
            }
            addressBook.addTag(tag);
        }
        for (JsonAdaptedWedding jsonAdaptedWedding : weddings) {
            Wedding wedding = jsonAdaptedWedding.toModelType();
            if (addressBook.hasWedding(wedding)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_WEDDING);
            }
            addressBook.addWedding(wedding);
        }
        return addressBook;
    }
}
