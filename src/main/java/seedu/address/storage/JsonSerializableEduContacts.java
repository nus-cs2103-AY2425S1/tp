package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.EduContacts;
import seedu.address.model.ReadOnlyEduContacts;
import seedu.address.model.person.Person;

/**
 * An Immutable EduContacts that is serializable to JSON format.
 */
@JsonRootName(value = "educontacts")
class JsonSerializableEduContacts {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableEduContacts} with the given persons.
     */
    @JsonCreator
    public JsonSerializableEduContacts(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyEduContacts} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableEduContacts}.
     */
    public JsonSerializableEduContacts(ReadOnlyEduContacts source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this EduContacts into the model's {@code EduContacts} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public EduContacts toModelType() throws IllegalValueException {
        EduContacts eduContacts = new EduContacts();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (eduContacts.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            eduContacts.addPerson(person);
        }
        return eduContacts;
    }

}
