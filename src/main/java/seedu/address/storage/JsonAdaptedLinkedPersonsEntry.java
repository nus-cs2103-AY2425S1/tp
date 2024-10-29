package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.types.common.LinkedPersonsEntry;

/**
 * Jackson-friendly version of {@link LinkedPersonsEntry}.
 */
public class JsonAdaptedLinkedPersonsEntry {

    private final JsonAdaptedEvent event;

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedLinkedPersonsEntry} with the given linked persons entry details.
     */
    @JsonCreator
    public JsonAdaptedLinkedPersonsEntry(@JsonProperty("event") JsonAdaptedEvent event,
                                         @JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.event = event;
        if (persons != null) {
            this.persons.addAll(persons);
        }
    }
}
