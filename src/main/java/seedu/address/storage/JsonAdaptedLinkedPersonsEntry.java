package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.types.common.LinkedPersonsEntry;
import seedu.address.model.types.person.Person;

/**
 * Jackson-friendly version of {@link LinkedPersonsEntry}.
 */
public class JsonAdaptedLinkedPersonsEntry {

    private final JsonAdaptedEvent event;

    private final List<JsonAdaptedPerson> persons;

    /**
     * Constructs a {@code JsonAdaptedLinkedPersonsEntry} with the given linked persons entry details.
     */
    @JsonCreator
    public JsonAdaptedLinkedPersonsEntry(@JsonProperty("event") JsonAdaptedEvent event,
                                         @JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.event = event;
        this.persons = persons;
    }

    /**
     * Converts a given {@code LinkedPersonsEntry} into this class for Jackson use.
     */
    public JsonAdaptedLinkedPersonsEntry(LinkedPersonsEntry source) {
        this.event = new JsonAdaptedEvent(source.getEvent());
        this.persons = source.getPersons().stream()
                .map(JsonAdaptedPerson::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this Jackson-friendly adapted linked persons entry object into the model's {@code LinkedPersonsEntry}
     * object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted linked persons entry.
     */
    public LinkedPersonsEntry toModelType() throws IllegalValueException {
        ArrayList<Person> modelPersons = new ArrayList<>();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            try {
                modelPersons.add(jsonAdaptedPerson.toModelType());
            } catch (IllegalValueException e) {
                throw new IllegalValueException("Error converting JsonAdaptedPerson to Person: " + e.getMessage());
            }
        }
        return new LinkedPersonsEntry(event.toModelType(), modelPersons);
    }
}
