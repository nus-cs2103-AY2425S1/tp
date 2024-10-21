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
import seedu.address.model.link.Link;
import seedu.address.model.owner.Owner;
import seedu.address.model.person.Person;
import seedu.address.model.pet.Pet;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    public static final String MESSAGE_DUPLICATE_OWNER = "Owners list contains duplicate owner(s).";

    public static final String MESSAGE_DUPLICATE_PET = "Pets list contains duplicate pet(s).";

    public static final String MESSAGE_DUPLICATE_LINK = "Pets list contains duplicate link(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedOwner> owners = new ArrayList<>();
    private final List<JsonAdaptedPet> pets = new ArrayList<>();
    private final List<JsonAdaptedLink> links = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given owners and pets.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                       @JsonProperty("pets") List<JsonAdaptedPet> pets,
                                       @JsonProperty("owners") List<JsonAdaptedOwner> owners,
                                       @JsonProperty("links") List<JsonAdaptedLink> links) {
        if (persons != null) {
            this.persons.addAll(persons);
        }
        if (pets != null) {
            this.pets.addAll(pets);
        }
        if (owners != null) {
            this.owners.addAll(owners);
        }
        if (links != null) {
            this.links.addAll(links);
        }
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        owners.addAll(source.getOwnerList().stream().map(JsonAdaptedOwner::new).collect(Collectors.toList()));
        pets.addAll(source.getPetList().stream().map(JsonAdaptedPet::new).collect(Collectors.toList()));
        links.addAll(source.getLinkList().stream().map(JsonAdaptedLink::new).collect(Collectors.toList()));
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
        for (JsonAdaptedOwner jsonAdaptedOwner : owners) {
            Owner owner = jsonAdaptedOwner.toModelType();
            if (addressBook.hasOwner(owner)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_OWNER);
            }
            addressBook.addOwner(owner);
        }
        for (JsonAdaptedPet jsonAdaptedPet : pets) {
            Pet pet = jsonAdaptedPet.toModelType();
            if (addressBook.hasPet(pet)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PET);
            }
            addressBook.addPet(pet);
        }
        for (JsonAdaptedLink jsonAdaptedLink : links) {
            Link link = jsonAdaptedLink.toModelType(addressBook.getOwnerList(), addressBook.getPetList());
            if (addressBook.hasLink(link)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PET);
            }
            addressBook.addLink(link);
        }
        return addressBook;
    }

}
