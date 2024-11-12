package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
public class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_WEDDING = "Weddings list contains duplicate wedding(s).";
    public static final String MESSAGE_CORRUPTED_WEDDING_DATA =
            "Address book data is corrupted: Found weddings without corresponding clients.";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedWedding> weddings = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                       @JsonProperty("weddings") List<JsonAdaptedWedding> weddings) {
        this.persons.addAll(persons);
        this.weddings.addAll(weddings);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        weddings.addAll(source.getWeddingList().stream().map(JsonAdaptedWedding::new).collect(Collectors.toList()));
    }

    /**
     * Checks if the wedding data integrity is maintained.
     * All wedding jobs must have corresponding clients (non-zero ownWedding hashcodes).
     *
     * @throws IllegalValueException if wedding data integrity is violated.
     */
    private void validateWeddingIntegrity(List<Wedding> weddings) throws IllegalValueException {
        // Collect all valid wedding hash codes
        Set<Integer> validWeddingHashCodes = weddings.stream()
                .map(Wedding::hashCode)
                .collect(Collectors.toSet());

        for (JsonAdaptedPerson person : persons) {
            int ownWeddingHash = person.getOwnWedding();

            // Check if own wedding hash is valid, ignore if 0 (unassigned)
            boolean personIsClient = ownWeddingHash != 0;
            boolean weddingHashCodeExists = validWeddingHashCodes.contains(ownWeddingHash);
            if (personIsClient && !weddingHashCodeExists) {
                throw new IllegalValueException(MESSAGE_CORRUPTED_WEDDING_DATA);
            }

            // Validate each wedding job hash
            List<Integer> weddingJobs = person.getWeddingJobs();
            if (weddingJobs != null) {
                for (Integer weddingJobHash : weddingJobs) {
                    boolean weddingJobHashExists = validWeddingHashCodes.contains(weddingJobHash);
                    if (!weddingJobHashExists) {
                        throw new IllegalValueException(MESSAGE_CORRUPTED_WEDDING_DATA);
                    }
                }
            }
        }
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {


        AddressBook addressBook = new AddressBook();

        // Add weddings first
        for (JsonAdaptedWedding jsonAdaptedWedding : weddings) {
            Wedding wedding = jsonAdaptedWedding.toModelType(addressBook.getWeddingList());
            if (addressBook.hasWedding(wedding)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_WEDDING);
            }
            addressBook.addWedding(wedding);
        }
        validateWeddingIntegrity(addressBook.getWeddingList());

        // Add persons
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType(addressBook.getWeddingList());
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
        }

        // Link clients to weddings
        for (Wedding wedding : addressBook.getWeddingList()) {
            for (Person person : addressBook.getPersonList()) {
                if (person.getOwnWedding() != null && person.getOwnWedding().equals(wedding)) {
                    person.setOwnWedding(wedding);
                }
            }
        }
        return addressBook;
    }
}
