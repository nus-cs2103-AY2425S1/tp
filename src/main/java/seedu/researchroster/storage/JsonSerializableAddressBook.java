package seedu.researchroster.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.researchroster.commons.exceptions.IllegalValueException;
import seedu.researchroster.model.ReadOnlyResearchRoster;
import seedu.researchroster.model.ResearchRoster;
import seedu.researchroster.model.person.Person;

/**
 * An Immutable ResearchRoster that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyResearchRoster} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyResearchRoster source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this researchroster book into the model's {@code ResearchRoster} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ResearchRoster toModelType() throws IllegalValueException {
        ResearchRoster researchRoster = new ResearchRoster();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (researchRoster.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            researchRoster.addPerson(person);
        }
        return researchRoster;
    }

}
