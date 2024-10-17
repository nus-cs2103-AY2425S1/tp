package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.CampusConnect;
import seedu.address.model.ReadOnlyCampusConnect;
import seedu.address.model.person.Person;

/**
 * An Immutable CampusConnect that is serializable to JSON format.
 */
@JsonRootName(value = "CampusConnect")
class JsonSerializableCampusConnect {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableCampusConnect} with the given persons.
     */
    @JsonCreator
    public JsonSerializableCampusConnect(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyCampusConnect} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableCampusConnect}.
     */
    public JsonSerializableCampusConnect(ReadOnlyCampusConnect source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code CampusConnect} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public CampusConnect toModelType() throws IllegalValueException {
        CampusConnect campusConnect = new CampusConnect();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (campusConnect.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            campusConnect.addPerson(person);
        }
        return campusConnect;
    }

}
