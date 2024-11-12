package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.id.counter.list.IdCounterList;

/**
 * Jackson-friendly version of {@link IdCounterList}.
 */
class JsonAdaptedIdCounterList {
    private final int personIdCounter;
    private final int eventIdCounter;

    /**
     * Constructs a {@code JsonAdaptedIdCounterList} with the given ID counters.
     */
    @JsonCreator
    public JsonAdaptedIdCounterList(@JsonProperty("personIdCounter") int personIdCounter,
                                    @JsonProperty("eventIdCounter") int eventIdCounter) {
        this.personIdCounter = personIdCounter;
        this.eventIdCounter = eventIdCounter;
    }

    /**
     * Converts a given {@code IdCounterList} into this class for Jackson use.
     */
    public JsonAdaptedIdCounterList(IdCounterList source) {
        personIdCounter = source.getPersonIdCounter();
        eventIdCounter = source.getEventIdCounter();
    }

    /**
     * Converts this Jackson-friendly adapted ID counter list object into the model's {@code IdCounterList} object.
     */
    public IdCounterList toModelType() {
        return new IdCounterList(personIdCounter, eventIdCounter);
    }

}
