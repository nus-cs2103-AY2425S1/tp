package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AgentAssist;
import seedu.address.model.ReadOnlyAgentAssist;
import seedu.address.model.person.Person;

/**
 * An Immutable AgentAssist that is serializable to JSON format.
 */
@JsonRootName(value = "agentassist")
class JsonSerializableAgentAssist {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAgentAssist} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAgentAssist(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyAgentAssist} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAgentAssist}.
     */
    public JsonSerializableAgentAssist(ReadOnlyAgentAssist source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AgentAssist} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AgentAssist toModelType() throws IllegalValueException {
        AgentAssist agentAssist = new AgentAssist();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (agentAssist.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            agentAssist.addPerson(person);
        }
        return agentAssist;
    }

}
