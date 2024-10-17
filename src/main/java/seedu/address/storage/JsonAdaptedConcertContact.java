package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.concert.ConcertContact;

/**
 * Jackson-friendly version of {@link ConcertContact}
 */
public class JsonAdaptedConcertContact {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "ConcertContact's %s field is missing!";

    private final JsonAdaptedConcert concert;
    private final JsonAdaptedPerson person;

    /**
     * Constructs a {@code JsonAdaptedConcertContact} with the given concertContact details.
     */
    @JsonCreator
    public JsonAdaptedConcertContact(@JsonProperty("concert") JsonAdaptedConcert concert,
            @JsonProperty("person") JsonAdaptedPerson person) {
        this.concert = concert;
        this.person = person;
    }

    /**
     * Converts a given {@code ConcertContact} into this class for Jackson use.
     */
    public JsonAdaptedConcertContact(ConcertContact source) {
        concert = new JsonAdaptedConcert(source.getConcert());
        person = new JsonAdaptedPerson(source.getPerson());
    }

    /**
     * Converts this Jackson-friendly adapted concertContact object into the model's {@code ConcertContact} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     *         concert.
     */
    public ConcertContact toModelType() throws IllegalValueException {
        // verify concert
        if (concert == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    JsonAdaptedConcert.class));
        }

        if (person == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    JsonAdaptedPerson.class));
        }

        return new ConcertContact(person.toModelType(), concert.toModelType());
    }
}
